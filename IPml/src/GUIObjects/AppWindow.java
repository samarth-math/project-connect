package GUIObjects;

//import javax.swing.Box;
import globalfunctions.Contact;
import globalfunctions.FileDrop;
import globalfunctions.JTextFieldLimit;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;

import serverclient.MainStart;
import serverclient.ShoutThread;
import GuiElements.AppWindowListCellRenderer;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;

public class AppWindow extends BasicWindow
{
	private static final long serialVersionUID = 1L;
	private Box history = Box.createVerticalBox();
	private DefaultListModel<Contact> model = new DefaultListModel<Contact>();

	public AppWindow()
	{
		setTitle("IPConnect");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(350, 600);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(280);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		final JList<Contact> list = new JList<Contact>(model);
	    ListCellRenderer<Contact> renderer = new AppWindowListCellRenderer();
	    list.setCellRenderer(renderer);
		
		JScrollPane scrollPane = new JScrollPane(list);
		splitPane.setLeftComponent(scrollPane);
  
	    list.addMouseListener(new MouseAdapter() {
	 		      public void mouseClicked(MouseEvent mouseEvent) {
		 		        @SuppressWarnings("unchecked")
				JList<Contact> theList = (JList<Contact>) mouseEvent.getSource();
 		        if (mouseEvent.getClickCount() == 2) {
 		          int index = theList.getSelectedIndex();
 		          if (index >= 0) {
 		            Contact person = (Contact) theList.getModel().getElementAt(index);
 		            person.startChat();
 		          }
 		        }
 		      }
 		 });
	    
		list.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "StartChat");
		list.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("F5"), "Refresh");
		
		list.getActionMap().put("StartChat", new AbstractAction(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e)
			{
			      int index = list.getSelectedIndex();
 		          if (index >= 0) {
 		            Contact person = (Contact) list.getModel().getElementAt(index);
 		            person.startChat();
 		          }
			}
		});
		
		list.getActionMap().put("Refresh", new AbstractAction(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e)
			{
				clearlist();
			}
		});

		
		
	    JPanel bChat = new JPanel();
		splitPane.setRightComponent(bChat);
		
	    GridBagLayout gbl_bChat = new GridBagLayout();
	    gbl_bChat.columnWidths = new int[]{330,20};
	    gbl_bChat.rowHeights = new int[]{50,55};
	    gbl_bChat.columnWeights = new double[]{Double.MIN_VALUE};
	    gbl_bChat.rowWeights = new double[]{1.0,0.0};
	    bChat.setLayout(gbl_bChat);
	    
		for (String key : MainStart.people.keySet()) {
			  Contact person = MainStart.people.get(key);
			  model.addElement(person);
		}		
		history.setBackground(new Color(240, 248, 255));
		
		JScrollPane scroll1 = new JScrollPane(history);
		scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints scrollConstraints = new GridBagConstraints();
		//scrollConstraints.insets = new Insets(0, 0, 5, 0);
		scrollConstraints.fill = GridBagConstraints.BOTH;
		scrollConstraints.gridwidth=2;
		scrollConstraints.gridx = 0;
		scrollConstraints.gridy = 0;
		bChat.add(scroll1, scrollConstraints);
		JScrollBar vsbar = scroll1.getVerticalScrollBar();
		vsbar.setUnitIncrement(16);
		scroll1.getViewport().setBackground(new Color(220,220,250));
		
		final JTextArea txtMessage = new JTextArea();
		
		txtMessage.setDocument(new JTextFieldLimit(2004));
		new FileDrop(txtMessage, new FileDrop.Listener()
		{
			public void filesDropped(File[] files)
			{
				 for(File f : files) {
		            	Path filepath = f.toPath();
		            	Contact.sendToAll(filepath);
		            }
			}
			
		});
		txtMessage.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "SendMessage");
		txtMessage.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("shift ENTER"), "NewLine");
		txtMessage.getActionMap().put("NewLine", new AbstractAction(){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				txtMessage.append("\n");
				txtMessage.setCaretPosition(txtMessage.getDocument().getLength());
			}
		});
		txtMessage.getActionMap().put("SendMessage", new AbstractAction(){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String message = txtMessage.getText();
				if(!message.equals(""))// if it's empty
				{
					if (message.length()>1003)// Splitting Large texts into multiple messages
						{
							String message1 = message.substring(0, 1003);
							Contact.sendToAll(message1);
							message = message.substring(1003);
						}
					Contact.sendToAll(message);
					txtMessage.setText("");
					txtMessage.requestFocusInWindow();
				}

			}
		}
);
		JScrollPane scroll2 = new JScrollPane(txtMessage);
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		//gbc_txtMessage.insets = new Insets(0, 0, 0, 0);
		gbc_txtMessage.fill = GridBagConstraints.BOTH;
		gbc_txtMessage.gridx = 0;
		gbc_txtMessage.gridy = 1;
		bChat.add(scroll2, gbc_txtMessage);
		//txtMessage.setColumns(10);
		
		JButton btnFile = new JButton("File");
		//btnFile.setSize(30, 15);
		
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FileDialog fileDialog = new FileDialog((Frame) getParent(),"Select a file which you want to send");
				fileDialog.setVisible(true);
				fileDialog.setMultipleMode(true);
						      
				File files [] = fileDialog.getFiles();
		            for(File f : files) {
		            	Path filepath = f.toPath();
		            	Contact.sendToAll(filepath);
		            }
			}
		});
		
		GridBagConstraints gbc_btnFile = new GridBagConstraints();
		gbc_btnFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFile.insets = new Insets(0, 0, 0, 0);
		gbc_btnFile.anchor = GridBagConstraints.NORTH;
		gbc_btnFile.gridx = 1;
		gbc_btnFile.gridy = 1;
		bChat.add(btnFile, gbc_btnFile);
		
		

		JButton btnFolder = new JButton("Folder");
		
		btnFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				JFileChooser folderChooser = new JFileChooser();
				folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = folderChooser.showOpenDialog(getParent());

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	File directory = folderChooser.getSelectedFile();
		        	System.out.println("Directory... " + directory.getAbsolutePath());
		        	Path filepath = directory.toPath();
		        	Contact.sendToAll(filepath);
		        }
			}
		});
		
		GridBagConstraints gbc_btnFolder = new GridBagConstraints();
		gbc_btnFolder.insets = new Insets(27, 0, 0, 0);
		//gbc_btnFolder.anchor = GridBagConstraints.SOUTH;
		gbc_btnFolder.gridx = 1;
		gbc_btnFolder.gridy = 1;
		gbc_btnFolder.fill = GridBagConstraints.HORIZONTAL;
		bChat.add(btnFolder,gbc_btnFolder);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JMenuItem mntmSpecifyIpRange = new JMenuItem("Specify IP Range");
		mntmSpecifyIpRange.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				java.awt.EventQueue.invokeLater(new Runnable() {
				    public void run() {
				    	IPRangeWindow frame = new IPRangeWindow();
						frame.setVisible(true);
				    }
				} );
			}
		});
		mnSettings.add(mntmSpecifyIpRange);
		
		JMenuItem mntmChangeDisplayName = new JMenuItem("Change Display Name");
		mntmChangeDisplayName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = null;
				File path = new File(System.getProperty("user.dir"));
				File namefile = new File(path,"username");
				username = (String)JOptionPane.showInputDialog("Enter Username");
				if (username!=null && !username.isEmpty())
				{
					FileOutputStream fos;			
					try {
						fos = new FileOutputStream(namefile);
						fos.write(username.getBytes());
						fos.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
	    			catch (IOException e1) {
						e1.printStackTrace();
					}
				MainStart.myUserName=username;
				clearlist();
				//System.out.println(MainStart.myUserName+ "is the value in mainstart username");
				}
				
			}
		});
		mnSettings.add(mntmChangeDisplayName);
		
		JMenuItem mntmRefreshf = new JMenuItem("Refresh (F5)");
		mntmRefreshf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearlist();
			}
		});
		mnSettings.add(mntmRefreshf);
		
		
		
		JMenuItem mntmDetectIp = new JMenuItem("Detect IP");
		mntmDetectIp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ip = (String)JOptionPane.showInputDialog("Detect IP");
				ShoutThread S = new ShoutThread(ip,ip);
				new Thread(S).start();
			}
		});
		mnSettings.add(mntmDetectIp);
		
		setVisible(true);
		

	}
	
	public void broadcastConsole(final JPanel M)
	{
		java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
		    	history.add(M);
				revalidate();
				int height = (int)history.getPreferredSize().getHeight();
		        Rectangle rect = new Rectangle(0,height,10,10);
		        history.scrollRectToVisible(rect);
		    }
		} );
	}

	public void addnewperson(Contact person) // sorting can call to add more people as they join
	{	    	
		model.addElement(person);
	}
	private void clearlist()
	{
		model.clear();
		MainStart.people.clear();
		MainStart.Shout();
	}
	
	public int getListIndex(Contact person)
	{
		return model.indexOf(person);
	}
	public void removeFromList(Contact person)
	{
		if(person!=null && model.indexOf(person)!=-1)
		{
			model.remove(model.indexOf(person));
		}
	}
}