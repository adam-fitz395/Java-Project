package java_project_2023;

import javax.swing.JFrame;

public class Driver
{

	public static void main(String[] args)
	{
		JFrame mainFrame = new JFrame("Admin Menu");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MainPanel mainPanel = new MainPanel();
		mainFrame.getContentPane().add(mainPanel);

		mainFrame.setSize(500, 500);
		mainFrame.setVisible(true);
	}

}
