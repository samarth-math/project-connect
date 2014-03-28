/*
 * Layout of the basic chat window
 */
package GUIObjects;
import globalfunctions.Contact;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import serverclient.Mainstart;
import serverclient.SendMessage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.ScrollPaneConstants;

import java.awt.event.WindowAdapter;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChatWindow extends BasicWindow
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;	
	private JTextArea txtMessage;
	private Box history = Box.createVerticalBox();
	private Contact person;
	
	public ChatWindow(String id)
	{
		this.person=Mainstart.people.get(id);
		setTitle("Chat with "+ person.getusername());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(650,500);
		setLocationRelativeTo(null);
		createwindow();
	}
	
	private void createwindow()
	{
		this.addWindowListener(new WindowAdapter() {
			  @Override
			  public void windowClosing(WindowEvent e) {
				person.setWindowNull();
			  }
			});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);	
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{5,620,20, 5};
		gbl_contentPane.rowHeights = new int[]{5, 440, 50};
		gbl_contentPane.columnWeights = new double[]{0, 1.0, 0};
		gbl_contentPane.rowWeights = new double[]{0, 1.0, 0};
		contentPane.setLayout(gbl_contentPane);
		
		
		/*history = new JTextArea();
		history.setLineWrap(true);
		history.setWrapStyleWord(true);
		history.setEditable(false);*/
		JScrollPane scroll1 = new JScrollPane(history);
		scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints scrollConstraints = new GridBagConstraints();
		scrollConstraints.insets = new Insets(0, 0, 5, 5);
		scrollConstraints.fill = GridBagConstraints.BOTH;
		scrollConstraints.gridwidth=2;
		scrollConstraints.gridx = 1;
		scrollConstraints.gridy = 1;
		contentPane.add(scroll1, scrollConstraints);
		JScrollBar vsbar = scroll1.getVerticalScrollBar();
		vsbar.setUnitIncrement(16);
		
		txtMessage = new JTextArea();
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
							SendMessage SM = new SendMessage(person, message1);
							new Thread(SM).start();
							message = message.substring(1003);
						}
					SendMessage SM = new SendMessage(person, message);
					new Thread(SM).start();
					txtMessage.setText("");
				}

			}
		}
);
		JScrollPane scroll2 = new JScrollPane(txtMessage);
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.insets = new Insets(0, 0, 0, 5);
		gbc_txtMessage.fill = GridBagConstraints.BOTH;
		gbc_txtMessage.gridx = 1;
		gbc_txtMessage.gridy = 2;
		contentPane.add(scroll2, gbc_txtMessage);
		txtMessage.setColumns(10);
		
		JButton btnFile = new JButton("File");
		
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(getParent());

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            String fileP= file.getAbsolutePath();
		            Path filePath = Paths.get(fileP);
		            System.out.println("File Path " + filePath);
		            SendMessage SM = new SendMessage(person, filePath);
					new Thread(SM).start();
		        } else {
/*************************** //What to do if the person closes the file chooser****/
		        }
			}
		});
		GridBagConstraints gbc_btnFile = new GridBagConstraints();
		gbc_btnFile.insets = new Insets(0, 0, 0, 5);
		gbc_btnFile.gridx = 2;
		gbc_btnFile.gridy = 2;
		contentPane.add(btnFile, gbc_btnFile);
			
		
		setVisible(true);
		txtMessage.requestFocusInWindow();
	}
	/*public void chatconsole(String M)//use this
	{
	
		history.append(M+"\n\r");
		history.setCaretPosition(history.getDocument().getLength());
	}
	*/
	public void chatconsole(JPanel M)
	{
		history.add(M);
		validate();
		int height = (int)history.getPreferredSize().getHeight();
        Rectangle rect = new Rectangle(0,height,10,10);
        history.scrollRectToVisible(rect);
		
	}
}