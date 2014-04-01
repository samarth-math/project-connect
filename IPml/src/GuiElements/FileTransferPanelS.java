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
import javax.swing.border.MatteBorder;

public class FileTransferPanelS extends JPanel{

	private static final long serialVersionUID = 1L;
	private static int totalNum;
	private int index;
	private String filename;
	//wrapper panel 
	
	private JLabel lbl_fileName;
	private TitledBorder t = new TitledBorder(new LineBorder(new Color(192, 192, 192)), null,TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(128, 128, 128));
	
	public FileTransferPanelS(String filename){
		setBackground(Color.WHITE);
		totalNum++;
		index=totalNum;
		this.filename = filename;
		createWindow();
						
	}
	
	public int getIndex()
	{
		return index;
	}
	
	private void createWindow()
	{
		setMaximumSize(new Dimension(3000,80));
		setPreferredSize(new Dimension(500,80));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{320, 50,50};
		gridBagLayout.rowHeights = new int[]{50};
		setLayout(gridBagLayout);
		setBorder(new MatteBorder(0, 3, 0, 0, (Color) Color.CYAN));
		
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
	
	public void onAcceptance(){
		setBackground(new Color(180,250,180));
		getParent().revalidate();
		getParent().repaint();
		showMsg("sending...");
	}
	
	public void onReject(){
		setBackground(new Color(250,180,180));
		getParent().revalidate();
		getParent().repaint();
		showMsg("File Transfer Rejected");
	}
}
