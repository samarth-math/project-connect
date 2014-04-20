package GuiElements;

/*
* this panel is for accepting or rejecting the incoming file
* */
import fileSending.Server;
import fileSending.TestPane;
import globalfunctions.Contact;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.border.MatteBorder;

public class FileTransferPanel extends JPanel{

private static final long serialVersionUID = 1L;
private JButton btnAccept;
private JButton btnReject;
private JButton btnCancel;
private Server serverThread;
private TestPane progBar;
private JPanel panel_1;
private JLabel lblStatus;

public FileTransferPanel(Contact person, String filename, String fileSize, int sendPanelId, String timeStamp, boolean all) {

setBackground(Color.WHITE);
setBorder(new MatteBorder(0, 5, 0, 0, (Color) new Color(0, 0, 102)));
GridBagLayout gridBagLayout = new GridBagLayout();
gridBagLayout.columnWidths = new int[]{200};
gridBagLayout.rowHeights = new int[]{90,25};
gridBagLayout.columnWeights = new double[]{1.0};
gridBagLayout.rowWeights = new double[]{1.0, 0.0};
setLayout(gridBagLayout);
createInsidePanel(person,filename,fileSize,sendPanelId,timeStamp,all);

}


private void createInsidePanel(final Contact person, final String filename, final String fileSize, final int sendPanelId, final String timeStamp, final boolean all){


JPanel panel = new JPanel();
panel.setBorder(null);
panel.setBackground(Color.WHITE);
panel.setBorder(null);
GridBagConstraints gbc_panel = new GridBagConstraints();
gbc_panel.insets = new Insets(0, 0, 0, 0);
gbc_panel.fill = GridBagConstraints.BOTH;
gbc_panel.gridx = 0;
gbc_panel.gridy = 0;
add(panel, gbc_panel);
GridBagLayout gbl_panel = new GridBagLayout();
gbl_panel.columnWidths = new int[]{200};
gbl_panel.rowHeights = new int[]{70,20};
gbl_panel.columnWeights = new double[]{1.0};
gbl_panel.rowWeights = new double[]{1.0};
panel.setLayout(gbl_panel);


progBar = new TestPane();
progBar.setVisible(false);

panel_1 = new JPanel();
panel_1.setBackground(Color.WHITE);
panel_1.setLayout(null);
GridBagConstraints gbc_panel_1 = new GridBagConstraints();
gbc_panel_1.insets = new Insets(0, 0, 5, 0);
gbc_panel_1.fill = GridBagConstraints.BOTH;
gbc_panel_1.gridx = 0;
gbc_panel_1.gridy = 0;
panel.add(panel_1, gbc_panel_1);

JLabel lblFile = new JLabel(filename);
lblFile.setFont(new Font("Ubuntu", Font.PLAIN, 14));
lblFile.setBounds(12, 12, 177, 28);
panel_1.add(lblFile);

lblStatus = new JLabel("Receiving File");
lblStatus.setForeground(new Color(128, 128, 128));
lblStatus.setBounds(12, 0, 189, 15);
panel_1.add(lblStatus);

JLabel lblSize = new JLabel("(" + Long.parseLong(fileSize)/(1024*1024) + "Mb)");
lblSize.setFont(new Font("Dialog", Font.PLAIN, 14));
lblSize.setBounds(169, 16, 66, 23);
panel_1.add(lblSize);
btnAccept = new JButton("Accept");
btnAccept.setBounds(22, 52, 81, 25);
panel_1.add(btnAccept);



btnReject = new JButton("Reject");
btnReject.setBounds(115, 52, 78, 25);
panel_1.add(btnReject);

btnCancel = new JButton("Cancel");
btnCancel.setBounds(115, 52, 81, 25);
panel_1.add(btnCancel);
btnCancel.setVisible(false);
btnCancel.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
//CANCEL FUNCTIONALITY
serverThread.stop=true;
btnCancel.setVisible(false);
progBar.setVisible(false);
try {
person.sendRejectFile(sendPanelId);
} catch (SocketException e1) {
//e1.printStackTrace();
} catch (IOException e1) {
//e1.printStackTrace();
}
}
});
btnReject.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
try {
person.sendRejectFile(sendPanelId);
onRejectUI();
} catch (SocketException exc) {
// Do stuff

} catch (IOException exc) {
// Do stuff
}

}
});
final FileTransferPanel ftp = this;

btnAccept.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ftp);	

FileDialog fileDialog = new FileDialog((Frame) topFrame,"Select File To Send",FileDialog.SAVE);
fileDialog.setFile(filename);
fileDialog.setVisible(true);
String SaveAsPath=fileDialog.getDirectory()+fileDialog.getFile();
serverThread = new Server(3333, ftp,SaveAsPath);
new Thread(serverThread).start();
try {
person.sendAcceptFile(sendPanelId, all);
onAcceptUI();
} catch (SocketException exc) {
// Do stuff

} catch (IOException exc) {
// Do stuff
}
}
});

GridBagConstraints gbc_progBar = new GridBagConstraints();
gbc_progBar.anchor= GridBagConstraints.WEST;
gbc_progBar.gridx = 0;
gbc_progBar.gridy = 1;
panel.add(progBar, gbc_progBar);

JLabel lblTimestamp = new JLabel(timeStamp);
lblTimestamp.setFont(new Font("Ubuntu Light", Font.PLAIN, 10));
lblTimestamp.setBackground(Color.WHITE);

GridBagConstraints gbc_lblTimestamp = new GridBagConstraints();
gbc_lblTimestamp.insets = new Insets(0, 0, 0, 0);
gbc_lblTimestamp.gridx = 0;
gbc_lblTimestamp.gridy = 1;
gbc_lblTimestamp.gridwidth=3;
gbc_lblTimestamp.anchor = GridBagConstraints.NORTHEAST;
add(lblTimestamp, gbc_lblTimestamp);


if(all)
{
JLabel lblUsername = new JLabel("Sent By "+person.getUserName());
lblUsername.setFont(new Font("Ubuntu Light", Font.BOLD, 14));
lblUsername.setBackground(Color.WHITE);

GridBagConstraints gbc_lblUsername = new GridBagConstraints();
gbc_lblUsername.insets = new Insets(0, 3, 0, 0);
gbc_lblUsername.gridx = 0;
gbc_lblUsername.gridy = 1;
gbc_lblUsername.gridwidth=3;
gbc_lblUsername.anchor = GridBagConstraints.WEST;
add(lblUsername, gbc_lblUsername);

}
}//constructor ends here

public void showMsg(String msg)
{
lblStatus.setText(msg);
revalidate();
}
private void onAcceptUI(){
java.awt.EventQueue.invokeLater(new Runnable() {
public void run() {
lblStatus.setText("File Transfer Accepted");
btnAccept.setVisible(false);
btnReject.setVisible(false);
btnCancel.setVisible(true);
revalidate();
repaint();
}
} );
}
private void onRejectUI(){
java.awt.EventQueue.invokeLater(new Runnable() {
public void run() {
lblStatus.setText("File Transfer Rejected");
btnAccept.setVisible(false);
btnReject.setVisible(false);
revalidate();
repaint();
}
} );
}
public void onCompleteUI(){
java.awt.EventQueue.invokeLater(new Runnable() {
public void run() {
lblStatus.setText("File Transfer Completed");
btnAccept.setVisible(false);
btnReject.setVisible(false);
btnCancel.setVisible(false);
progBar.setVisible(false);
revalidate();
repaint();
}
} );
}


public TestPane getprogbar()
{
return progBar;
}
}


