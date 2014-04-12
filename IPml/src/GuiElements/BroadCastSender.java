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


public class BroadCastSender extends JPanel{

	private static final long serialVersionUID = 1L;

	public BroadCastSender(String inputText, String timeStamp) {

		setBackground(Color.WHITE);
		setBorder(new MatteBorder(0, 3, 0, 0, (Color) new Color(0, 100, 0)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{500};
		gridBagLayout.rowHeights = new int[]{45,25};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0};
		setLayout(gridBagLayout);
		createInsidePanel(inputText, timeStamp);
		
	}
	
	private void createInsidePanel(String inputText, String timeStamp){
		
		
		JTextPane txtpnMessage = new JTextPane();
		
		txtpnMessage.setEditable(false);
		txtpnMessage.setBackground(Color.WHITE);
		txtpnMessage.setText(inputText);
		
		GridBagConstraints gbc_txtpnMessage = new GridBagConstraints();
		gbc_txtpnMessage.fill = GridBagConstraints.BOTH;
		gbc_txtpnMessage.gridx = 0;
		gbc_txtpnMessage.gridy = 0;
		gbc_txtpnMessage.insets = new Insets(3, 3, 0, 0);
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