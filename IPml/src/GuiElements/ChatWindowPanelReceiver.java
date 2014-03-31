package GuiElements;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;


public class ChatWindowPanelReceiver extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JTextPane textpn_chatText;
	private JLabel lbl_chatTimeStamp;
	
	
	public ChatWindowPanelReceiver(String inputText, String timeStamp) {
		
		//panel properties 
		
		setMaximumSize(new Dimension(3000,70));
		setPreferredSize(new Dimension(280,70));
		setBackground(Color.WHITE);
		setBorder(new MatteBorder(0, 3, 0, 0, (Color) Color.RED));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{2.0};
		gridBagLayout.columnWeights = new double[]{2.0};
		setLayout(gridBagLayout);
		
		//create text pane
		textpn_chatText = new JTextPane();
		textpn_chatText.setBackground(Color.WHITE);
		textpn_chatText.setBorder(null);
		textpn_chatText.setText(inputText);
		textpn_chatText.setEditable(false);
		
		//add text-pane to panel
		GridBagConstraints gbc_textpn_chatText = new GridBagConstraints();
		gbc_textpn_chatText.insets = new Insets(0, 9, 5, 0);
		gbc_textpn_chatText.fill = GridBagConstraints.BOTH;
		gbc_textpn_chatText.gridx = 0;
		gbc_textpn_chatText.gridy = 0;
		add(textpn_chatText, gbc_textpn_chatText);
		
		//create time-stamp label
		lbl_chatTimeStamp = new JLabel(timeStamp);
		lbl_chatTimeStamp.setBorder(null);
		lbl_chatTimeStamp.setBackground(Color.WHITE);
		lbl_chatTimeStamp.setFont(new Font("Ubuntu Light", Font.PLAIN, 10));
		
		//add time-stamp label to panel
		GridBagConstraints gbc_lbl_chatTimeStamp = new GridBagConstraints();
		gbc_lbl_chatTimeStamp.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_chatTimeStamp.anchor = GridBagConstraints.EAST;
		gbc_lbl_chatTimeStamp.gridx = 0;
		gbc_lbl_chatTimeStamp.gridy = 1;
		add(lbl_chatTimeStamp, gbc_lbl_chatTimeStamp);
	}
}