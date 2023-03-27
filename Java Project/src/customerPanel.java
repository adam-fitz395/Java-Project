// Import necessary classes
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

public class customerPanel
{
    // Labels
    private static JLabel custNameLabel;
    private static JLabel custSurnameLabel;
    
    // Textfields
    private static JTextField custNameTfield;
    private static JTextField custSurnameTfield;

    // Buttons
    private static JButton okButton;
    private static JButton custPanelButton;
    public static void main(String[] args) 
    {
        // Create Customer frame and panel
        JFrame custFrame = new JFrame("Insert Customer");
        JPanel custPanel = new JPanel();
        custFrame.setSize(400,300);
        custFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        custFrame.add(custPanel);
        custPanel.setLayout(null);
        custFrame.setVisible(true);
        
        // Add labels and panels
        custNameLabel = new JLabel("Name:");
        custNameLabel.setBounds(10, 40, 75, 25);
        custPanel.add(custNameLabel);

        custNameTfield = new JTextField();
        custNameTfield.setBounds(90, 40, 100, 25);
        custPanel.add(custNameTfield);

        custSurnameLabel = new JLabel("Surname:");
        custSurnameLabel.setBounds(10, 70, 75, 25);
        custPanel.add(custSurnameLabel);

        custSurnameTfield = new JTextField();
        custSurnameTfield.setBounds(90, 70, 100, 25);
        custPanel.add(custSurnameTfield);

        // Add buttons
        // OK button
        okButton = new JButton("OK");
        okButton.setBounds(10, 100, 60, 25);
        okButton.addActionListener(new ActionListener () 
        {
            public void actionPerformed (ActionEvent e)
            {
                boolean nameValid;
                nameValid = nameCheck(custNameTfield, custSurnameTfield);
                
                if (nameValid == false)
                {
                    JOptionPane.showMessageDialog(null, "Invalid Name or Surname");
                }
            }
        });
        custPanel.add(okButton);

        // Customer Panel Button
        custPanelButton = new JButton("Customer");
        custPanelButton.setBounds(10, 10, 100, 25);
        custPanel.add(custPanelButton);

        custPanelButton = new JButton("Invoice");
        custPanelButton.setBounds(10, 10, 100, 25);
        custPanel.add(custPanelButton);

    }

    // Method to check if name is valid
    static boolean nameCheck(JTextField nameField, JTextField surnameField)
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
}
