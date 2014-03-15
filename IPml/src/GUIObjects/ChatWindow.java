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

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;

import serverclient.SendMessage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatWindow extends BasicWindow
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;	
	private JTextField txtMessage;
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
		gbl_contentPane.columnWidths = new int[]{5,620,20, 5};
		gbl_contentPane.rowHeights = new int[]{5, 440, 50};
		gbl_contentPane.columnWeights = new double[]{0, 1.0, 0, 0};
		gbl_contentPane.rowWeights = new double[]{0, 1.0, 0};
		contentPane.setLayout(gbl_contentPane);
		
		history = new JTextArea();
		history.setEditable(false);
		JScrollPane scroll = new JScrollPane(history);
		GridBagConstraints scrollConstraints = new GridBagConstraints();
		scrollConstraints.insets = new Insets(0, 0, 5, 5);
		scrollConstraints.fill = GridBagConstraints.BOTH;
		scrollConstraints.gridwidth=2;
		scrollConstraints.gridx = 1;
		scrollConstraints.gridy = 1;
		contentPane.add(scroll, scrollConstraints);
		
		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()== KeyEvent.VK_ENTER)
				{
					String message = txtMessage.getText();
					if(!message.equals(""))
					{
						SendMessage SM = new SendMessage(person, message);
						new Thread(SM).start();
						txtMessage.setText("");
					}
				}
			}
		});
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMessage.gridx = 1;
		gbc_txtMessage.gridy = 2;
		contentPane.add(txtMessage, gbc_txtMessage);
		txtMessage.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = txtMessage.getText();
				if(!message.equals(""))
				{
					SendMessage SM = new SendMessage(person, message);
					new Thread(SM).start();
					txtMessage.setText("");
				}
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 2;
		gbc_btnSend.gridy = 2;
		contentPane.add(btnSend, gbc_btnSend);
		
		setVisible(true);
		txtMessage.requestFocusInWindow();
	
	}
	
	public void chatconsole(String M)
	{
		history.append(M+"\n\r");
		history.setCaretPosition(history.getDocument().getLength());
	}
}
/*SendMessage SM = new SendMessage(person, "This is the message I'm sending to you!!!");
new Thread(SM).start();*/