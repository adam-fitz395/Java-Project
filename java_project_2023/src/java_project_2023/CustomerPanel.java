package java_project_2023;

import java.awt.EventQueue;

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
		setLayout(null);
		
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
		addCustomerButton.setBounds(10, 11, 118, 23);
		add(addCustomerButton);
		
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
		receiveInvButton.setBounds(134, 11, 130, 23);
		add(receiveInvButton);
		
		custNameTfield = new JTextField();
		custNameTfield.setBounds(76, 45, 86, 20);
		add(custNameTfield);
		custNameTfield.setColumns(10);
		
		JLabel custNameLabel = new JLabel("Name:");
		custNameLabel.setBounds(10, 45, 61, 14);
		add(custNameLabel);
		
		custSurnameTfield = new JTextField();
		custSurnameTfield.setBounds(76, 76, 86, 20);
		add(custSurnameTfield);
		custSurnameTfield.setColumns(10);
		
		JLabel custSurnameLabel = new JLabel("Surname:");
		custSurnameLabel.setBounds(10, 79, 61, 14);
		add(custSurnameLabel);
		
		custDOBTfield = new JTextField();
		custDOBTfield.setBounds(76, 107, 86, 20);
		add(custDOBTfield);
		custDOBTfield.setColumns(10);
		
		JLabel custDOBLabel = new JLabel("DOB:");
		custDOBLabel.setBounds(10, 110, 61, 14);
		add(custDOBLabel);
		
		JButton addButton = new JButton("Add");
		addButton.setBounds(10, 138, 61, 23);
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
                
                else
                {
                	// Insert DB insertion here
                }
            }
        });
		add(addButton);
		
		
	
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
}
