package GuiElements;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.MatteBorder;

public class ChatWindowPanelSender extends JPanel{

	private static final long serialVersionUID = 1L;

	public ChatWindowPanelSender(String inputText, String timeStamp) {
	
		setBackground(new Color(240, 255, 240));
		setBorder(new MatteBorder(0, 3, 0, 0, (Color) new Color(0, 255, 0)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{500};
		gridBagLayout.rowHeights = new int[]{45,25};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0,0.0};
		setLayout(gridBagLayout);
		createInsidePanel(inputText, timeStamp);
		
	}
	
	private void createInsidePanel(String inputText, String timeStamp){
		
		
		JTextPane txtpnMessage = new JTextPane();
		txtpnMessage.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		txtpnMessage.setEditable(false);
		txtpnMessage.setBackground(new Color(240, 255, 240));
		txtpnMessage.setText(inputText);
		
		GridBagConstraints gbc_txtpnMessage = new GridBagConstraints();
		gbc_txtpnMessage.fill = GridBagConstraints.BOTH;
		gbc_txtpnMessage.gridx = 0;
		gbc_txtpnMessage.gridy = 0;
		gbc_txtpnMessage.insets = new Insets(3, 3, 0, 0);
		add(txtpnMessage, gbc_txtpnMessage);
		
		JLabel lblTimestamp = new JLabel(timeStamp);
		lblTimestamp.setFont(new Font("Ubuntu Light", Font.PLAIN, 10));
		
		GridBagConstraints gbc_lblTimestamp = new GridBagConstraints();
		gbc_lblTimestamp.gridx = 0;
		gbc_lblTimestamp.gridy = 1;
		gbc_lblTimestamp.anchor = GridBagConstraints.NORTHEAST;
		add(lblTimestamp, gbc_lblTimestamp);


	}
	
	//call this method on the panel when the acknowledgment is received 
	
	public void showDeliveryStatus(boolean s){ 
		
		JLabel lbl_cnfrmMessage;
		if(s)
		{
			lbl_cnfrmMessage=new JLabel("Message Delivered");
			lbl_cnfrmMessage.setForeground(new Color(50, 155, 50));
			lbl_cnfrmMessage.setFont(new Font("Ubuntu Light", Font.PLAIN, 10));
			
			GridBagConstraints gbc_lbl_cnfrmMessage = new GridBagConstraints();
			gbc_lbl_cnfrmMessage.gridx = 0;
			gbc_lbl_cnfrmMessage.gridy = 1;
			gbc_lbl_cnfrmMessage.anchor = GridBagConstraints.SOUTHEAST;
			add(lbl_cnfrmMessage, gbc_lbl_cnfrmMessage);
			revalidate();
		}
		else
		{
			lbl_cnfrmMessage=new JLabel("No Confirmation Received");
			lbl_cnfrmMessage.setForeground(new Color(205, 0, 50));
			lbl_cnfrmMessage.setFont(new Font("Ubuntu Light", Font.PLAIN, 10));
			
			GridBagConstraints gbc_lbl_cnfrmMessage = new GridBagConstraints();
			gbc_lbl_cnfrmMessage.gridx = 0;
			gbc_lbl_cnfrmMessage.gridy = 1;
			gbc_lbl_cnfrmMessage.anchor = GridBagConstraints.SOUTHEAST;
			add(lbl_cnfrmMessage, gbc_lbl_cnfrmMessage);
			revalidate();
		}
	}
}
