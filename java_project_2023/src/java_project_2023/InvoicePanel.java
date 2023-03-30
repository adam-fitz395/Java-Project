package java_project_2023;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;

import javax.swing.JComboBox;

import net.proteanit.sql.DbUtils;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;

public class InvoicePanel extends JPanel
{

	// Declare variables
	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	public InvoicePanel()
	{
		setLayout(new MigLayout("", "[][109px][]", "[23px][20px][][23px][][172.00,grow][][]"));

		JButton addCustomerButton = new JButton("Add Customer");
		addCustomerButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CustomerPanel customerPanel = new CustomerPanel();
				JFrame mainFrame = (JFrame) getTopLevelAncestor();
				mainFrame.getContentPane().removeAll();
				mainFrame.getContentPane().add(customerPanel);
				mainFrame.revalidate();
				mainFrame.repaint();
			}
		});
		add(addCustomerButton, "cell 0 0");

		JButton receiveInvButton = new JButton("Receive Invoice");
		receiveInvButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				InvoicePanel invoicePanel = new InvoicePanel();
				JFrame mainFrame = (JFrame) getTopLevelAncestor();
				mainFrame.getContentPane().removeAll();
				mainFrame.getContentPane().add(invoicePanel);
				mainFrame.revalidate();
				mainFrame.repaint();
			}
		});
		add(receiveInvButton, "flowx,cell 1 0,growx,aligny top");

		JButton updateSupplierButton = new JButton("Update Supplier");
		updateSupplierButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SupplierPanel supplierPanel = new SupplierPanel();
				JFrame mainFrame = (JFrame) getTopLevelAncestor();
				mainFrame.getContentPane().removeAll();
				mainFrame.getContentPane().add(supplierPanel);
				mainFrame.revalidate();
				mainFrame.repaint();
			}
		});
		add(updateSupplierButton, "cell 2 0");

		JLabel invIDLabel = new JLabel("Invoice ID:");
		add(invIDLabel, "cell 0 2");

		JComboBox<String> invIDComboBox = new JComboBox<String>();
		getInvIDs(invIDComboBox);
		add(invIDComboBox, "cell 1 2,growx");

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 5 3 1,grow");

		JTable table = new JTable();
		scrollPane.setViewportView(table);

		JButton getInvButton = new JButton("Get Info");
		getInvButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				populateTable(invIDComboBox, table);
			}
		});
		add(getInvButton, "cell 0 4,growx");

		JRadioButton invConfirmRadioButton = new JRadioButton("Confirm?");
		add(invConfirmRadioButton, "cell 0 6");

		JButton invConfirmButton = new JButton("Received");
		invConfirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (invConfirmRadioButton.isSelected())
				{
					invoiceReceived(invIDComboBox);
					JOptionPane.showMessageDialog(null, "Invoice has been received.");
				}

				else
				{
					JOptionPane.showMessageDialog(null, "Invoice must be confirmed for receival.");
				}
			}
		});
		add(invConfirmButton, "cell 0 7,growx");

	}

	public static void getInvIDs(JComboBox<String> invIDComboBox)
	{
		final String DATABASE_URL = "jdbc:mysql://localhost/customerdb";
		Connection con = null;
		try
		{ // establish connection to database
			con = DriverManager.getConnection(DATABASE_URL, "root", "");
			// create Prepared Statement for inserting data into table
			Statement pstat = con.createStatement();

			ResultSet rs = pstat.executeQuery("SELECT invoiceID FROM invoice ORDER BY invoiceID");
			while (rs.next())
			{
				String currentID = rs.getString("invoiceID");
				invIDComboBox.addItem(currentID);
			}

			// insert data into table
			System.out.println("Updated combobox contents.");

		}

		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}

		finally
		{
			try
			{
				con.close();
			} catch (Exception exception)
			{
				exception.printStackTrace();
			}
		}

	}

	public static void populateTable(JComboBox<String> invIDComboBox, JTable table)
	{
		final String DATABASE_URL = "jdbc:mysql://localhost/customerdb";
		Connection con = null;

		try
		{ // establish connection to database
			con = DriverManager.getConnection(DATABASE_URL, "root", "");

			// Get selected ID in combo box as integer
			String selectedIDString = invIDComboBox.getSelectedItem().toString();

			// create Prepared Statement for inserting data into table
			String query = "SELECT invoiceDate, itemName, supplierName, supplierAddress, supplierPhone FROM  invoice INNER JOIN supplier ON supplier.supplierID WHERE invoiceID = ? AND invoice.supplierID = supplier.supplierID";
			PreparedStatement pstat = con.prepareStatement(query);
			pstat.setString(1, selectedIDString);
			ResultSet rs = pstat.executeQuery();

			// insert data into jtable
			table.setModel(DbUtils.resultSetToTableModel(rs));
			System.out.println("Updated table contents.");

		}

		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}

		finally
		{
			try
			{
				con.close();
			}

			catch (Exception exception)
			{
				exception.printStackTrace();
			}
		}

	}

	public static void invoiceReceived(JComboBox<String> invIDComboBox)
	{
		final String DATABASE_URL = "jdbc:mysql://localhost/customerdb";
		Connection con = null;

		try
		{ // establish connection to database
			con = DriverManager.getConnection(DATABASE_URL, "root", "");

			// Get selected ID in combo box as integer
			String selectedIDString = invIDComboBox.getSelectedItem().toString();

			// create Prepared Statement for inserting data into table
			String query = "DELETE FROM invoice WHERE invoiceID = ?";
			PreparedStatement pstat = con.prepareStatement(query);
			pstat.setString(1, selectedIDString);
			pstat.executeUpdate();

			System.out.println("Deleted invoice " + selectedIDString + ".");

		}

		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}

		finally
		{
			try
			{
				con.close();
			}

			catch (Exception exception)
			{
				exception.printStackTrace();
			}
		}

	}
}