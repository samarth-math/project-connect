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
	private JLabel lbl_fileName;
	//private TitledBorder t = new TitledBorder(new LineBorder(new Color(192, 192, 192)), null,TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(128, 128, 128));
	
	public FileTransferPanelS(String filename, String timeStamp){
		
		totalNum++;
		index=totalNum;		
		setBackground(Color.WHITE);
		setMaximumSize(new Dimension(3000,1000));
		setPreferredSize(new Dimension(500,100));
		setBorder(new MatteBorder(0, 3, 0, 0, (Color) new Color(102, 204, 204)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{500};
		gridBagLayout.rowHeights = new int[]{45,25};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0};
		setLayout(gridBagLayout);
		createInsidePanel(filename, timeStamp);
		
	}

	
	private void createInsidePanel(String filename, String timeStamp){
	
		//the filename label is here
		lbl_fileName = new JLabel("<html>"+filename+"</html>");
		//lbl_fileName.setMinimumSize(new Dimension(220, 50));
		GridBagConstraints gbc_lbl_fileName = new GridBagConstraints();
		gbc_lbl_fileName.anchor = GridBagConstraints.WEST;
		gbc_lbl_fileName.insets = new Insets(3, 3, 0, 0);
		gbc_lbl_fileName.gridx = 0;
		gbc_lbl_fileName.gridy = 0;
		lbl_fileName.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "<html>Awaiting response</html>",TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(128, 128, 128)));
		
		lbl_fileName.setFont(new Font("Ubuntu", Font.PLAIN, 14));
		add(lbl_fileName, gbc_lbl_fileName);
	
	
		JLabel lblTimestamp = new JLabel(timeStamp);
		lblTimestamp.setFont(new Font("Ubuntu Light", Font.PLAIN, 10));
		lblTimestamp.setBackground(Color.WHITE);
		
		GridBagConstraints gbc_lblTimestamp = new GridBagConstraints();
		gbc_lblTimestamp.gridx = 0;
		gbc_lblTimestamp.gridy = 1;
		gbc_lblTimestamp.anchor = GridBagConstraints.NORTHEAST;
		add(lblTimestamp, gbc_lblTimestamp);
	
	
	}
	public int getIndex()
	{
		System.out.println("This is the index " + index);
		return index;
	}
	
	
	public void showMsg(String msg){
	
		this.lbl_fileName.setBorder(new TitledBorder(null, new String("<html>"+msg+"</html>"),TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(128, 128, 128)));
		this.revalidate();
	}
	
	public void onAcceptance(){
		setBackground(new Color(180,250,180));
		getParent().revalidate();
		getParent().repaint();
		showMsg("sending...");
	}
	
	public void onReject(){
		System.out.println("Being Rejected");
		setBackground(new Color(255,228,228));
		getParent().revalidate();
		getParent().repaint();
		showMsg("File Transfer Rejected");
	}
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
