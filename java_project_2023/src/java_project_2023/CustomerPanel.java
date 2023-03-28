package java_project_2023;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;

public class CustomerPanel extends JPanel 
{

	//Declare variables
	private static final long serialVersionUID = 1L;
	private static JTextField custNameTfield;
	private static JTextField custSurnameTfield;
	private static JTextField custDOBTfield;

	// Launch
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					CustomerPanel frame = new CustomerPanel();
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
	public CustomerPanel() 
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
		setLayout(new MigLayout("", "[61px][][]", "[23px][20px][20px][][20px]"));
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
		add(receiveInvButton, "cell 1 0,growx,aligny top");
		
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
		
		JLabel custNameLabel = new JLabel("Name:");
		add(custNameLabel, "cell 0 1");
		
		custNameTfield = new JTextField();
		add(custNameTfield, "cell 1 1");
		custNameTfield.setColumns(10);
		
		JLabel custSurnameLabel = new JLabel("Surname:");
		add(custSurnameLabel, "cell 0 2");
		
		custSurnameTfield = new JTextField();
		add(custSurnameTfield, "cell 1 2");
		custSurnameTfield.setColumns(10);
		
		JLabel custDOBLabel = new JLabel("DOB:");
		add(custDOBLabel, "cell 0 3");
		
		custDOBTfield = new JTextField();
		add(custDOBTfield, "cell 1 3");
		custDOBTfield.setColumns(10);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener () 
        {
            public void actionPerformed (ActionEvent e)
            {
                boolean nameValid;
                boolean dobValid;
                nameValid = nameCheck(custNameTfield, custSurnameTfield);
                dobValid = isDateFormatValid(custDOBTfield);
                
                if (nameValid == false)
                {
                    JOptionPane.showMessageDialog(null, "Invalid Name or Surname");
                }
                
                if(dobValid == false)
                {
                	JOptionPane.showMessageDialog(null, "Invalid format for date of birth. Use format 'YYYY-MM-DD'.");
                }
                
                if (nameValid == true && dobValid == true)
                {
                	customerInsertion(custNameTfield, custSurnameTfield, custDOBTfield);
                	JOptionPane.showMessageDialog(null, "New customer has been added.");
                }
            }
        });
		add(addButton, "cell 0 4");
		
		
	
	}
	
	// Method to check if name is valid
    public static boolean nameCheck(JTextField nameField, JTextField surnameField)
    {
        String regex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
        Pattern nameCheckPattern = Pattern.compile(regex);
        // Test first name
        String name = custNameTfield.getText();
        String surname = custSurnameTfield.getText();
        if ((name == null || name.isEmpty() || surname == null || surname.isEmpty()))
        {
            return false;
        }

        else
        {
            Matcher nameMatch = nameCheckPattern.matcher(name);
            Matcher surnameMatch = nameCheckPattern.matcher(surname);
            if(nameMatch.matches() && surnameMatch.matches())
            {
                return true;
            }
        
            else
            {
                return false;
            }
        }
    }
    
    // Method to check if date format is valid 
    public static boolean isDateFormatValid(JTextField custDOBTfield) 
    {
        String regex = "^\\d{4}-\\d{2}-\\d{2}$"; // regular expression for yyyy-mm-dd format
        Pattern dobCheckPattern = Pattern.compile(regex);
        String currentDOB = custDOBTfield.getText();
        if ((currentDOB == null || currentDOB.isEmpty()))
        {
            return false;
        }

        else
        {
            Matcher dobMatch = dobCheckPattern.matcher(currentDOB);
            if(dobMatch.matches())
            {
                return true;
            }
        
            else
            {
                return false;
            }
        }
    }
    
    public static void customerInsertion(JTextField custNameTfield, JTextField custSurnameTfield, JTextField custDOBTfield)
    {	
    	final String DATABASE_URL = "jdbc:mysql://localhost/customerdb";
        Connection con = null ;
        PreparedStatement pstat = null;
        String firstname = custNameTfield.getText();
        String surname = custSurnameTfield.getText();
        String dobString = custDOBTfield.getText();
        Date dob = java.sql.Date.valueOf(dobString);
        int i=0;
        try    
        {       // establish connection to database
            con = DriverManager.getConnection(DATABASE_URL, "root", "" );
            // create Prepared Statement for inserting data into table
            pstat = con.prepareStatement("INSERT INTO customer (firstName, surname, dob) VALUES (?,?,?)");
            pstat.setString (1, firstname);
            pstat.setString (2, surname);
            pstat.setDate(3, dob);
            // insert data into table
            i = pstat .executeUpdate();
            System.out. println ( i + " record successfully added to the table .");
        
        }    
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
            
        finally 
        {
            try 
            {
                pstat.close () ;
                con.close () ;
            }
            catch (Exception exception)
            {
                exception . printStackTrace () ;
            }
        }
            
    }
}
