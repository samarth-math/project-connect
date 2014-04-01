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
	//wrapper panel boy 
	
	private JLabel lbl_fileName;
	private TitledBorder t = new TitledBorder(new LineBorder(new Color(192, 192, 192)), null,TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(128, 128, 128));
	
	public FileTransferPanelS(String filename, String timeStamp){
		setBackground(Color.WHITE);
		totalNum++;
		index=totalNum;
		this.filename = filename;
		createWindow(timeStamp);
						
	}
	
	public int getIndex()
	{
		return index;
	}
	
	private void createWindow(String timeStamp)
	{
		setMaximumSize(new Dimension(3000,80));
		setPreferredSize(new Dimension(505, 85));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{500};
		gridBagLayout.rowHeights = new int[]{50, 10};
		setLayout(gridBagLayout);
		setBorder(new MatteBorder(0, 3, 0, 0, (Color) Color.CYAN));
		System.out.println(t.getTitle());
		
		//the filename label is here
		lbl_fileName = new JLabel(filename);
		lbl_fileName.setMinimumSize(new Dimension(220, 48));
		GridBagConstraints gbc_lbl_fileName = new GridBagConstraints();
		gbc_lbl_fileName.anchor = GridBagConstraints.WEST;
		gbc_lbl_fileName.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_fileName.gridx = 0;
		gbc_lbl_fileName.gridy = 0;
		lbl_fileName.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Awaiting response",TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(128, 128, 128)));
		lbl_fileName.setPreferredSize(new Dimension(220, 50));
		lbl_fileName.setFont(new Font("Ubuntu", Font.PLAIN, 14));
		add(lbl_fileName, gbc_lbl_fileName);
		
		JLabel lbl_timeStamp = new JLabel(timeStamp);
		lbl_timeStamp.setFont(new Font("Ubuntu Light", Font.PLAIN, 10));
		GridBagConstraints gbc_lbl_timeStamp = new GridBagConstraints();
		gbc_lbl_timeStamp.anchor = GridBagConstraints.EAST;
		gbc_lbl_timeStamp.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_timeStamp.gridx = 0;
		gbc_lbl_timeStamp.gridy = 1;
		add(lbl_timeStamp, gbc_lbl_timeStamp);
		
		
		
	
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
	public void showDeliveryStatus(String msg){
		JLabel lbl_deliveryMsg = new JLabel(msg);
		lbl_deliveryMsg.setFont(new Font("Ubuntu Light", Font.PLAIN, 10));
		GridBagConstraints gbc_lbl_deliveryMsg = new GridBagConstraints();
		gbc_lbl_deliveryMsg.anchor = GridBagConstraints.EAST;
		gbc_lbl_deliveryMsg.gridx = 0;
		gbc_lbl_deliveryMsg.gridy = 2;
		add(lbl_deliveryMsg, gbc_lbl_deliveryMsg);
		validate();
		repaint();
	}
	
}
