package GuiElements;

/*
 * this panel is for accepting or rejecting the incoming file
 * */
import fileSending.Server;
import globalfunctions.Contact;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.TitledBorder;

import serverclient.MainStart;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.nio.file.Path;

import javax.swing.border.MatteBorder;

public class FileTransferPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JButton btn_accept;
	private JButton btn_reject;
	private JButton btn_cancel;
	private Thread serverThread;
	
	public FileTransferPanel(final Contact person, final String filepath, final int sendPanelId, String timeStamp) {
		
		final String filename = fileName(filepath);
		//wrapper panel 
		setMaximumSize(new Dimension(3000,80));
		setPreferredSize(new Dimension(500,80));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{360, 70,70};
		gridBagLayout.rowHeights = new int[]{50,30};
		setLayout(gridBagLayout);
		setBorder(new MatteBorder(0, 3, 0, 0, (Color) Color.BLUE));
		
		//the filename label is here
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
		
		final FileTransferPanel ftp = this;
		//the accept button
		btn_accept = new JButton("Accept");
		btn_accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setVisible(true);
				fileChooser.setDialogTitle("Select a location to save the file");
				fileChooser.setSelectedFile(new File(filename));
				int returnVal = fileChooser.showSaveDialog(getParent());
				
				
				//int returnVal = fileChooser.showOpenDialog(getParent());
				String SaveAsPath="";
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            SaveAsPath = file.getAbsolutePath();	
		            System.out.println("SaveAsPath inside Filetransferpanel..." + SaveAsPath);
		        }
		        
		        
				serverThread = new Thread(new Server(6666, ftp,SaveAsPath));// Starts server
				serverThread.start();
				try {
					person.sendAcceptFile(filepath.toString().trim(),MainStart.myID,sendPanelId);
					onAcceptUI();
				} catch (SocketException exc) {
					// Do stuff
					
				} catch (IOException exc) {
					// Do stuff
				}
			}
		});
		GridBagConstraints gbc_btn_accept = new GridBagConstraints();
		gbc_btn_accept.insets = new Insets(0, 0, 0, 5);
		gbc_btn_accept.gridx = 1;
		gbc_btn_accept.gridy = 0;
		btn_accept.setBorder(new LineBorder(new Color(0, 255, 0), 2));
		btn_accept.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		add(btn_accept, gbc_btn_accept);
		
		
		//the reject button
		btn_reject = new JButton("Reject");
		btn_reject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					person.sendRejectFile(filepath.toString().trim(), MainStart.myID, sendPanelId);
					onRejectUI();
				} catch (SocketException exc) {
					// Do stuff
					
				} catch (IOException exc) {
					// Do stuff
				}
			}
		});
		GridBagConstraints gbc_btn_reject = new GridBagConstraints();
		gbc_btn_reject.gridx = 2;
		gbc_btn_reject.gridy = 0;
		btn_reject.setBorder(new LineBorder(new Color(255, 0, 0), 2));
		btn_reject.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		add(btn_reject, gbc_btn_reject);
		
	
	
	//the cancel button
			btn_cancel = new JButton("Cancel");
			btn_cancel.setVisible(false);
			btn_cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//
					// The CODE FOR THE CANCEL BUTTON COMES HERE
				}
			});
			GridBagConstraints gbc_btn_cancel = new GridBagConstraints();
			gbc_btn_cancel.gridx = 2;
			gbc_btn_cancel.gridy = 0;
			btn_cancel.setBorder(new LineBorder(new Color(255, 0, 0), 2));
			btn_cancel.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
			add(btn_cancel, gbc_btn_cancel);
			
			//create time-stamp label
			JLabel lbl_chatTimeStamp = new JLabel(timeStamp);
			lbl_chatTimeStamp.setBorder(null);
			lbl_chatTimeStamp.setBackground(Color.WHITE);
			lbl_chatTimeStamp.setFont(new Font("Ubuntu Light", Font.PLAIN, 10));
			
			//add time-stamp label
			GridBagConstraints gbc_lbl_chatTimeStamp = new GridBagConstraints();
			gbc_lbl_chatTimeStamp.insets = new Insets(0, 0, 5, 0);
			gbc_lbl_chatTimeStamp.anchor = GridBagConstraints.EAST;
			gbc_lbl_chatTimeStamp.gridx = 2;
			gbc_lbl_chatTimeStamp.gridy = 1;
			add(lbl_chatTimeStamp, gbc_lbl_chatTimeStamp);
			scrollRectToVisible(null);
			
		}//constructor ends here
	
	private void onAcceptUI(){
		java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
		    	btn_accept.setVisible(false);
		    	btn_reject.setVisible(false);
				btn_cancel.setVisible(true);
				revalidate();
				repaint();
		    }
		} );
	}
	private void onRejectUI(){
		java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
				btn_accept.setVisible(false);
				btn_reject.setVisible(false);
				revalidate();
				repaint();
		    }
		} );
	}
	private static String fileName(String filePath) {
		int pos=0;
		for(int i=filePath.length()-1;i>=0;i--) {
			if(filePath.charAt(i)=='\\' || filePath.charAt(i)=='/') {
				pos = i;
				break;
			}
		}
		return filePath.substring(pos+1,filePath.length());
	}
}
