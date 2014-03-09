package GuiElements;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
/*
 * this panel is for accepting or rejecting the incoming file
 * */

public class FileTransferPanel extends JPanel{
	public FileTransferPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{320, 50,50};
		gridBagLayout.rowHeights = new int[]{50};
		//gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		//gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lbl_fileName = new JLabel("gohomebitches.mkv");
		GridBagConstraints gbc_lbl_fileName = new GridBagConstraints();
		gbc_lbl_fileName.anchor = GridBagConstraints.WEST;
		gbc_lbl_fileName.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_fileName.gridx = 0;
		gbc_lbl_fileName.gridy = 0;
		add(lbl_fileName, gbc_lbl_fileName);
		
		JButton btn_accept = new JButton("A");
		GridBagConstraints gbc_btn_accept = new GridBagConstraints();
		gbc_btn_accept.insets = new Insets(0, 0, 0, 5);
		gbc_btn_accept.gridx = 1;
		gbc_btn_accept.gridy = 0;
		add(btn_accept, gbc_btn_accept);
		btn_accept.setSize(50, 50);
		
		JButton btn_reject = new JButton("R");
		GridBagConstraints gbc_btn_reject = new GridBagConstraints();
		gbc_btn_reject.gridx = 2;
		gbc_btn_reject.gridy = 0;
		add(btn_reject, gbc_btn_reject);
		btn_reject.setSize(50, 50);
	}
	
	//needs two buttons ACCEPT and REJECT
	
	
}
