package java_project_2023;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;

public class SupplierPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 19266060175986551L;
	private JTextField suppNameTfield;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
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
		setLayout(new MigLayout("", "[86px][111.00px][]", "[23px][20px][][20px][][]"));
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
		add(suppIDLabel, "cell 0 1");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setEditable(true);
		add(comboBox, "cell 1 1,growx");
		
		JLabel suppNameLabel = new JLabel("Supplier Name:");
		add(suppNameLabel, "cell 0 2,growx,aligny center");
		
		suppNameTfield = new JTextField();
		add(suppNameTfield, "cell 1 2");
		suppNameTfield.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Supplier Address:");
		add(lblNewLabel, "cell 0 3");
		
		textField = new JTextField();
		add(textField, "cell 1 3");
		textField.setColumns(10);
		
		JButton suppDeleteConfirmButton = new JButton("Confirm");
		add(suppDeleteConfirmButton, "cell 0 5");
	}
}
