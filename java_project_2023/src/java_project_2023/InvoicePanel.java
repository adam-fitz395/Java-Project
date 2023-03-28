package java_project_2023;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;

public class InvoicePanel extends JPanel 
{

	//Declare variables
	private static final long serialVersionUID = 1L;
	private JTextField invIDTfield;
	private JTable table;

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
		
		invIDTfield = new JTextField();
		add(invIDTfield, "cell 1 1");
		invIDTfield.setColumns(10);
		
		JButton getInvButton = new JButton("Get Invoice");
		add(getInvButton, "cell 0 2");
		
		table = new JTable();
		add(table, "cell 0 3 3 1,grow");
		
		JButton invConfirmButton = new JButton("Confirm");
		add(invConfirmButton, "cell 0 4");
	
	}
}