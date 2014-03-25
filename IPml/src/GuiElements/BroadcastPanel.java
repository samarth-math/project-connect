package GuiElements;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

public class BroadcastPanel extends JPanel {

	
	private JTextPane textpn_chatText;
	private JLabel lbl_chatTimeStamp;

	
	public BroadcastPanel(String inputText, String timeStamp , String name){
	
	//panel properties 
			
			setBackground(Color.WHITE);
			setBorder(new LineBorder(Color.BLUE));
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.rowWeights = new double[]{0.0, 2.0, 0.0};
			gridBagLayout.columnWeights = new double[]{2.0};
			setLayout(gridBagLayout);
			
			//create name label 
			JLabel lbl_name = new JLabel(name);
			lbl_name.setBackground(Color.WHITE);
			lbl_name.setFont(new Font("Ubuntu Light", Font.BOLD, 12));
			
			//add name label
			GridBagConstraints gbc_lbl_name = new GridBagConstraints();
			gbc_lbl_name.anchor = GridBagConstraints.WEST;
			gbc_lbl_name.insets = new Insets(0, 0, 5, 0);
			gbc_lbl_name.gridx = 0;
			gbc_lbl_name.gridy = 0;
			add(lbl_name, gbc_lbl_name);
			
			//create text pane
			textpn_chatText = new JTextPane();
			textpn_chatText.setBackground(new Color(255, 255, 255));
			textpn_chatText.setBorder(null);
			textpn_chatText.setText(inputText);
			textpn_chatText.setEditable(false);
			
			//add text-pane to panel
			GridBagConstraints gbc_textpn_chatText = new GridBagConstraints();
			gbc_textpn_chatText.insets = new Insets(0, 0, 5, 0);
			gbc_textpn_chatText.fill = GridBagConstraints.BOTH;
			gbc_textpn_chatText.gridx = 0;
			gbc_textpn_chatText.gridy = 1;
			add(textpn_chatText, gbc_textpn_chatText);
			
			//create time-stamp label
			lbl_chatTimeStamp = new JLabel(timeStamp);
			lbl_chatTimeStamp.setBorder(null);
			lbl_chatTimeStamp.setBackground(Color.WHITE);
			lbl_chatTimeStamp.setFont(new Font("Ubuntu Light", Font.PLAIN, 10));
			
			//add time-stamp label to panel
			GridBagConstraints gbc_lbl_chatTimeStamp = new GridBagConstraints();
			gbc_lbl_chatTimeStamp.anchor = GridBagConstraints.EAST;
			gbc_lbl_chatTimeStamp.gridx = 0;
			gbc_lbl_chatTimeStamp.gridy = 2;
			add(lbl_chatTimeStamp, gbc_lbl_chatTimeStamp);
	}
}
