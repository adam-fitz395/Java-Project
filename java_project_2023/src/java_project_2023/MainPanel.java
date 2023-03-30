package java_project_2023;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;

public class MainPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3349288903396400280L;

	public MainPanel()
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
		setLayout(new MigLayout("", "[118px][130px][130px]", "[23px]"));
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
		add(updateSupplierButton, "cell 2 0,growx,aligny top");
	}
}
