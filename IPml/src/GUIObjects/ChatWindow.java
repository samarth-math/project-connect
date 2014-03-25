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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import serverclient.SendMessage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ScrollPaneConstants;

public class ChatWindow extends BasicWindow
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;	
	private JTextArea txtMessage;
	private JTextArea history;
	private Contact person;
	
	public ChatWindow(Contact person)
	{
		this.person=person;
		setTitle("Chat with "+ person.getusername());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(650,500);
		setLocationRelativeTo(null);
		createwindow();
	}
	
	private void createwindow()
	{
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);	
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{5,640, 5};
		gbl_contentPane.rowHeights = new int[]{5, 440, 50};
		gbl_contentPane.columnWeights = new double[]{0, 1.0, 0};
		gbl_contentPane.rowWeights = new double[]{0, 1.0, 0};
		contentPane.setLayout(gbl_contentPane);
		
		history = new JTextArea();
		history.setLineWrap(true);
		history.setWrapStyleWord(true);
		history.setEditable(false);
		JScrollPane scroll1 = new JScrollPane(history);
		scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints scrollConstraints = new GridBagConstraints();
		scrollConstraints.fill = GridBagConstraints.BOTH;
		scrollConstraints.gridx = 1;
		scrollConstraints.gridy = 1;
		contentPane.add(scroll1, scrollConstraints);
		
		txtMessage = new JTextArea();
		txtMessage.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "SendMessage");
		txtMessage.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("shift ENTER"), "NewLine");
		txtMessage.getActionMap().put("NewLine", new AbstractAction(){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				txtMessage.append("\n\r");
				txtMessage.setCaretPosition(txtMessage.getDocument().getLength());
			}
		});
		txtMessage.getActionMap().put("SendMessage", new AbstractAction(){
		
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String message = txtMessage.getText();
				if(!message.equals(""))
				{
					if (message.length()>1003)
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
		gbc_txtMessage.fill = GridBagConstraints.BOTH;
		gbc_txtMessage.gridx = 1;
		gbc_txtMessage.gridy = 2;
		contentPane.add(scroll2, gbc_txtMessage);
		txtMessage.setColumns(10);
		
		
		setVisible(true);
		txtMessage.requestFocusInWindow();
	
	}
	
	public void chatconsole(String M)
	{
		history.append(M+"\n\r");
		history.setCaretPosition(history.getDocument().getLength());
	}
	
	public void chatconsole(JPanel M)
	{
		history.append(M+"\n\r");
		
		history.setCaretPosition(history.getDocument().getLength());
	}
}
/*SendMessage SM = new SendMessage(person, "This is the message I'm sending to you!!!");
new Thread(SM).start();*/