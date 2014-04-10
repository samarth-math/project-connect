package GUIObjects;

//import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import globalfunctions.Contact;
import GuiElements.ClickablePanel;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import serverclient.MainStart;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AppWindow extends BasicWindow
{
	private static final long serialVersionUID = 1L;
	//private Box box = Box.createVerticalBox();
	private DefaultListModel<Contact> model = new DefaultListModel<Contact>();

	public AppWindow()
	{
		
		setSize(500,600);
		setTitle("IPConnect");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenuItem mntmSpecifyRange = new JMenuItem("Specify Range");
		menuBar.add(mntmSpecifyRange);
		
		createAppWindow();
	}
	
	private void createAppWindow()
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300};
		gridBagLayout.rowHeights = new int[]{500};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		getContentPane().setLayout(gridBagLayout);
	    
	    final JList<Contact> list = new JList<Contact>(model);
	    ListCellRenderer<Contact> renderer = new AppWindowListCellRenderer();
	    list.setCellRenderer(renderer);
		
		JScrollPane scrollPane = new JScrollPane(list);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
	    list.addMouseListener(new MouseAdapter() {
		      public void mouseClicked(MouseEvent mouseEvent) {
			        if (mouseEvent.getClickCount() == 2) {
			          int index = list.getSelectedIndex();
			          if (index >= 0) {
			            Contact person = (Contact) list.getModel().getElementAt(index);
			            person.startChat();
			          }
			        }
		      }
		});
	    
	    
	setVisible(true);
	for (String key : MainStart.people.keySet()) {
		  Contact person = (Contact) MainStart.people.get(key);
		  model.addElement(person);
		}
				

		//Check if this works with adding new people to the list.
		//if not, then use 
		/*
		 * MouseListener ml = new MouseAdapter() {
		      public void mouseClicked(MouseEvent mouseEvent) {
		        JList<Contact> theList = (JList<Contact>) mouseEvent.getSource();
		        if (mouseEvent.getClickCount() == 2) {
		          int index = theList.getSelectedIndex();
		          if (index >= 0) {
		            Contact person = (Contact) theList.getModel().getElementAt(index);
		            person.startChat();
		          }
		        }
		      }
		    };
		    
		    list.addMouseListener(ml);
		 */
		    

	}
	public void addnewperson(final Contact person) // ListenThread can call to add more people as they join
	{
		java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
		    	model.addElement(person);
				revalidate();
		    }
		} );	
	}
}

