package GUIObjects;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import globalfunctions.Contact;
import GuiElements.ClickablePanel;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import serverclient.Mainstart;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class AppWindow extends BasicWindow
{
	private static final long serialVersionUID = 1L;
	private Box box = Box.createVerticalBox();

	public AppWindow()
	{
		
		setSize(300,500);
		setTitle("IPConnect");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		createAppWindow();
	}
	
	private void createAppWindow()
	{

		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem mntmSpecifyRange = new JMenuItem("Specify Range");
		menuBar.add(mntmSpecifyRange);
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300};
		gridBagLayout.rowHeights = new int[]{500};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		getContentPane().setLayout(gridBagLayout);
		
		
		JScrollPane scrollPane = new JScrollPane(box);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		getContentPane().add(scrollPane, gbc_scrollPane);
				
		setVisible(true);
		for (String key : Mainstart.people.keySet()) {
			  Contact person = (Contact) Mainstart.people.get(key);
			  box.add(new ClickablePanel(person));
			}
	}
	public void addnewperson(final Contact person) // ListenThread can call to add more people as they join
	{
		java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
		    	box.add(new ClickablePanel(person));
				revalidate();
		    }
		} );
		
	}
			
	
	
	
}