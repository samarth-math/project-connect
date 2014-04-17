package GuiElements;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.MatteBorder;


public class BroadCastReceiver extends JPanel{

	private static final long serialVersionUID = 1L;

	public BroadCastReceiver(String userName, String inputText, String timeStamp) {

		setBackground(Color.WHITE);
		setBorder(new MatteBorder(0, 3, 0, 0, Color.RED));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{200};
		gridBagLayout.rowHeights = new int[]{35,10};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0};
		setLayout(gridBagLayout);
		createInsidePanel(userName, inputText, timeStamp);
		
	}
	
	private void createInsidePanel(String userName,String inputText, String timeStamp){
		
		JLabel lblUser = new JLabel(userName.toUpperCase());
		lblUser.setFont(new Font("Lucida Sans", Font.BOLD, 12));
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.insets = new Insets(3, 3, 2, 0);
		gbc_lblUser.gridx = 0;
		gbc_lblUser.gridy = 0;
		gbc_lblUser.anchor = GridBagConstraints.NORTHWEST;
		add(lblUser, gbc_lblUser);
		
		JTextPane txtpnMessage = new JTextPane();
		txtpnMessage.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		txtpnMessage.setEditable(false);
		txtpnMessage.setBackground(Color.WHITE);
		txtpnMessage.setText(inputText);
		
		GridBagConstraints gbc_txtpnMessage = new GridBagConstraints();
		gbc_txtpnMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtpnMessage.gridx = 0;
		gbc_txtpnMessage.gridy = 0;
		gbc_txtpnMessage.insets = new Insets(3, 3, 0, 0);
		gbc_txtpnMessage.anchor = GridBagConstraints.SOUTH;
		add(txtpnMessage, gbc_txtpnMessage);
		
		
		JLabel lblTimestamp = new JLabel(timeStamp);
		lblTimestamp.setFont(new Font("Ubuntu Light", Font.PLAIN, 10));
		lblTimestamp.setBackground(Color.WHITE);
		
		GridBagConstraints gbc_lblTimestamp = new GridBagConstraints();
		gbc_lblTimestamp.gridx = 0;
		gbc_lblTimestamp.gridy = 1;
		gbc_lblTimestamp.anchor = GridBagConstraints.NORTHEAST;
		add(lblTimestamp, gbc_lblTimestamp);


	}
}