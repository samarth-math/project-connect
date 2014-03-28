package GuiElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class FileTransferPanelS extends JPanel{

	private static final long serialVersionUID = 1L;
	//wrapper panel 
	private JLabel lbl_fileName;
	private TitledBorder t = new TitledBorder(new LineBorder(new Color(192, 192, 192)), null,TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(128, 128, 128));
	
	public FileTransferPanelS(String filename){
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{320, 50,50};
			gridBagLayout.rowHeights = new int[]{50};
			setLayout(gridBagLayout);
			setBorder(new LineBorder(new Color(255, 255, 0), 3));
			
			//the filename label is here
			lbl_fileName = new JLabel(filename);
			GridBagConstraints gbc_lbl_fileName = new GridBagConstraints();
			gbc_lbl_fileName.anchor = GridBagConstraints.WEST;
			gbc_lbl_fileName.insets = new Insets(0, 0, 0, 5);
			gbc_lbl_fileName.gridx = 0;
			gbc_lbl_fileName.gridy = 0;
			System.out.println(t.getTitle());
			lbl_fileName.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Awaiting response",TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(128, 128, 128)));
			lbl_fileName.setPreferredSize(new Dimension(200, 40));
			lbl_fileName.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
			add(lbl_fileName, gbc_lbl_fileName);
						
	}
	
	public void showMsg(String msg){
	
		this.lbl_fileName.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), msg,TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(128, 128, 128)));
		this.validate();
	}
	
	void onAcceptance(){
		this.setBackground(new Color(190,238,143));
		this.getParent().validate();
		this.getParent().repaint();
		showMsg("sending...");
	}
}
