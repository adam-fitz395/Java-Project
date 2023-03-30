package java_project_2023;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;

public class SupplierPanel extends JPanel
{
	private static final long serialVersionUID = 19266060175986551L;
	private JTextField suppNameTfield;
	private JTextField suppAddressTfield;
	private JTextField suppPhoneTfield;

	// Create Panel
	public SupplierPanel()
	{

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
		setLayout(new MigLayout("", "[86px][111.00px][]", "[23px][20px][][20px][][][][][]"));
		add(addCustomerButton, "cell 0 0,growx,aligny top");

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
		add(receiveInvButton, "cell 1 0,growx");

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

		JLabel suppIDLabel = new JLabel("Supplier ID:");
		add(suppIDLabel, "cell 0 2");

		JComboBox<Object> suppIDComboBox = new JComboBox<Object>();
		add(suppIDComboBox, "cell 1 2,growx");

		getSuppIDS(suppIDComboBox);

		JLabel suppNameLabel = new JLabel("Supplier Name:");
		add(suppNameLabel, "cell 0 3,growx,aligny center");

		suppNameTfield = new JTextField();
		add(suppNameTfield, "cell 1 3,growx");
		suppNameTfield.setColumns(10);

		JLabel suppAddressLabel = new JLabel("Supplier Address:");
		add(suppAddressLabel, "cell 0 4");

		suppAddressTfield = new JTextField();
		add(suppAddressTfield, "cell 1 4,growx");
		suppAddressTfield.setColumns(10);

		JLabel suppPhoneLabel = new JLabel("Supplier Phone:");
		add(suppPhoneLabel, "cell 0 5");

		suppPhoneTfield = new JTextField();
		add(suppPhoneTfield, "cell 1 5,growx");
		suppPhoneTfield.setColumns(10);

		JButton getDetailsButton = new JButton("Get Details");
		getDetailsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fillTextfields(suppIDComboBox, suppNameTfield, suppAddressTfield, suppPhoneTfield);
			}

		});
		add(getDetailsButton, "cell 0 7,growx");

		JButton suppConfirmButton = new JButton("Confirm Update");
		suppConfirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean fieldCheck;
				fieldCheck = checkNotNull(suppNameTfield, suppAddressTfield, suppPhoneTfield);
				if (fieldCheck == true)
				{
					updateDBTable(suppIDComboBox, suppNameTfield, suppAddressTfield, suppPhoneTfield);
					JOptionPane.showMessageDialog(null, "Database has been updated.");
				}

				else
				{
					JOptionPane.showMessageDialog(null, "One or more fields are empty.");
				}
			}
		});
		add(suppConfirmButton, "cell 1 7");
	}

	public static void getSuppIDS(JComboBox<Object> suppIDComboBox)
	{
		final String DATABASE_URL = "jdbc:mysql://localhost/customerdb";
		Connection con = null;
		try
		{ // establish connection to database
			con = DriverManager.getConnection(DATABASE_URL, "root", "");
			// create Prepared Statement for inserting data into table
			Statement pstat = con.createStatement();

			ResultSet rs = pstat.executeQuery("SELECT supplierID FROM supplier");
			while (rs.next())
			{
				String currentID = rs.getString("supplierID");
				suppIDComboBox.addItem(currentID);
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

	private void fillTextfields(JComboBox<Object> suppIDComboBox, JTextField suppNameTfield,
			JTextField suppAddressTfield, JTextField suppPhoneTfield)
	{
		final String DATABASE_URL = "jdbc:mysql://localhost/customerdb";
		Connection con = null;
		try
		{ // establish connection to database
			con = DriverManager.getConnection(DATABASE_URL, "root", "");

			String selectedIDString = suppIDComboBox.getSelectedItem().toString();

			// create Prepared Statement for inserting data into table
			String query = "SELECT supplierName, supplierAddress, supplierPhone FROM supplier WHERE supplierID = ?";
			PreparedStatement pstat = con.prepareStatement(query);
			pstat.setString(1, selectedIDString); // THIS IS EXTREMELY VULNERABLE TO SQL INJECTIONS
			ResultSet rs = pstat.executeQuery();

			rs.next();
			suppNameTfield.setText(rs.getString("supplierName"));
			suppAddressTfield.setText(rs.getString("supplierAddress"));
			suppPhoneTfield.setText(rs.getString("supplierPhone"));

			// insert data into table
			System.out.println("Updated text field contents.");

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

	public static void updateDBTable(JComboBox<Object> suppIDComboBox, JTextField suppNameTfield,
			JTextField suppAddressTfield, JTextField suppPhoneTfield)
	{
		final String DATABASE_URL = "jdbc:mysql://localhost/customerdb";
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			// establish connection to database
			con = DriverManager.getConnection(DATABASE_URL, "root", "");

			// create Prepared Statement for updating data in table
			pstmt = con.prepareStatement(
					"UPDATE supplier SET supplierName = ?, supplierAddress = ?, supplierPhone = ? WHERE supplierID = ?");

			// set parameter values
			pstmt.setString(1, suppNameTfield.getText());
			pstmt.setString(2, suppAddressTfield.getText());
			pstmt.setString(3, suppPhoneTfield.getText());
			pstmt.setString(4, suppIDComboBox.getSelectedItem().toString());

			// execute statement
			pstmt.executeUpdate();
			System.out.println("Table updated");

		} catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		} finally
		{
			// close resources
			try
			{
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException sqlException)
			{
				sqlException.printStackTrace();
			}
		}
	}

	public static boolean checkNotNull(JTextField suppNameTfield, JTextField suppAddressTfield,
			JTextField suppPhoneTfield)
	{

		String name = suppNameTfield.getText();
		String address = suppAddressTfield.getText();
		String phone = suppPhoneTfield.getText();

		if (name.isEmpty() || address.isEmpty() || phone.isEmpty())
		{
			return false;
		}

		else
		{
			return true;
		}
	}
}
