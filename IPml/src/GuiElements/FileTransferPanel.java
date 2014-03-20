package GuiElements;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Dimension;
/*
 * this panel is for accepting or rejecting the incoming file
 * */

public class FileTransferPanel extends JPanel{
	
	public FileTransferPanel(String filename) {
		
		
		//wrapper panel 
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{320, 50,50};
		gridBagLayout.rowHeights = new int[]{50};
		setLayout(gridBagLayout);
		setBorder(new LineBorder(new Color(255, 255, 0), 3));
		
		//the filename label
		JLabel lbl_fileName = new JLabel(filename);
		GridBagConstraints gbc_lbl_fileName = new GridBagConstraints();
		gbc_lbl_fileName.anchor = GridBagConstraints.WEST;
		gbc_lbl_fileName.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_fileName.gridx = 0;
		gbc_lbl_fileName.gridy = 0;
		lbl_fileName.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "In coming", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(128, 128, 128)));
		lbl_fileName.setPreferredSize(new Dimension(200, 40));
		lbl_fileName.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		add(lbl_fileName, gbc_lbl_fileName);
		
		//the accept button
		JButton btn_accept = new JButton("Accept");
		GridBagConstraints gbc_btn_accept = new GridBagConstraints();
		gbc_btn_accept.insets = new Insets(0, 0, 0, 5);
		gbc_btn_accept.gridx = 1;
		gbc_btn_accept.gridy = 0;
		btn_accept.setBorder(new LineBorder(new Color(0, 255, 0), 2));
		btn_accept.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		add(btn_accept, gbc_btn_accept);
		
		
		//the reject button
		JButton btn_reject = new JButton("Reject");
		GridBagConstraints gbc_btn_reject = new GridBagConstraints();
		gbc_btn_reject.gridx = 2;
		gbc_btn_reject.gridy = 0;
		btn_reject.setBorder(new LineBorder(new Color(255, 0, 0), 2));
		btn_reject.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		add(btn_reject, gbc_btn_reject);
		
	}//constructor ends here
}
