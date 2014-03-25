package GuiElements;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;

public class ChatWindowPanelReceiver extends JPanel{
	
	private JLabel lbl_cnfrmMessage;
	
	public ChatWindowPanelReceiver(String inputText, String timeStamp) {
		
		//panel properties yeah
		setBackground(Color.WHITE);
		setBorder(new LineBorder(Color.RED));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{2.0, 0.0};
		gridBagLayout.columnWeights = new double[]{2.0};
		setLayout(gridBagLayout);
		
		//text pane
		JTextPane textpn_chatText = new JTextPane();
		textpn_chatText.setBorder(null);
		textpn_chatText.setText(inputText);
		textpn_chatText.setEditable(false);
		GridBagConstraints gbc_textpn_chatText = new GridBagConstraints();
		gbc_textpn_chatText.insets = new Insets(0, 0, 5, 0);
		gbc_textpn_chatText.fill = GridBagConstraints.BOTH;
		gbc_textpn_chatText.gridx = 0;
		gbc_textpn_chatText.gridy = 0;
		add(textpn_chatText, gbc_textpn_chatText);
		
		
		
		//time-stamp label
		JLabel lbl_chatTimeStamp = new JLabel(timeStamp);
		lbl_chatTimeStamp.setBorder(null);
		lbl_chatTimeStamp.setBackground(Color.WHITE);
		lbl_chatTimeStamp.setFont(new Font("Ubuntu Light", Font.PLAIN, 10));
		GridBagConstraints gbc_lbl_chatTimeStamp = new GridBagConstraints();
		gbc_lbl_chatTimeStamp.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_chatTimeStamp.anchor = GridBagConstraints.EAST;
		gbc_lbl_chatTimeStamp.gridx = 0;
		gbc_lbl_chatTimeStamp.gridy = 1;
		add(lbl_chatTimeStamp, gbc_lbl_chatTimeStamp);//useless comment
		
	}
}