package java_project_2023;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;

import javax.swing.JComboBox;

import net.proteanit.sql.DbUtils;
import javax.swing.JScrollPane;

public class InvoicePanel extends JPanel
{

	// Declare variables
	private static final long serialVersionUID = 1L;

	// Launch
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					InvoicePanel frame = new InvoicePanel();
					frame.setVisible(true);
				}

				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InvoicePanel()
	{
		setLayout(new MigLayout("", "[][109px][]", "[23px][20px][23px][grow][]"));

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
		add(invIDLabel, "cell 0 1");

		JComboBox<String> invIDComboBox = new JComboBox<String>();
		getInvIDs(invIDComboBox);
		add(invIDComboBox, "cell 1 1,growx");

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 3 3 1,grow");

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
		add(getInvButton, "cell 0 2");

		JButton invConfirmButton = new JButton("Confirm");
		invConfirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				invoiceReceived(invIDComboBox);
			}
		});
		add(invConfirmButton, "cell 0 4");

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

			ResultSet rs = pstat.executeQuery("SELECT invoiceID FROM invoice WHERE receivedFlag = 0");
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
			String query = "SELECT invoiceDate, itemName, supplierName, supplierAddress, supplierPhone FROM  invoice INNER JOIN supplier ON supplier.supplierID WHERE invoiceID = ? AND receivedFlag = 0";
			PreparedStatement pstat = con.prepareStatement(query);
			pstat.setString(1, selectedIDString); // THIS IS EXTREMELY VULNERABLE TO SQL INJECTIONS
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
			} catch (Exception exception)
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
			String query = "UPDATE invoice SET receivedFlag = 1 WHERE invoiceID = ?";
			PreparedStatement pstat = con.prepareStatement(query);
			pstat.setString(1, selectedIDString); // THIS IS EXTREMELY VULNERABLE TO SQL INJECTIONS
			pstat.executeUpdate();

			System.out.println("Updated receieved flag for " + selectedIDString + ".");

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
}