package GUIObjects;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalityType;

import javax.swing.JTextField;
import javax.swing.JLabel;

public class usernameDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private String username;
	
	public String getusername()
	{
		return username;
	}


	public usernameDialog()
	{
		setTitle("Choose Name");
		setModalityType(ModalityType.APPLICATION_MODAL);
		
	
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 252, 343);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		txtName = new JTextField();
		txtName.setBounds(62, 134, 114, 19);
		contentPanel.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(82, 107, 52, 15);
		contentPanel.add(lblName);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("Login");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String user = txtName.getText();
					if (user.isEmpty() || user==null || user.equals(""))
						JOptionPane.showMessageDialog(getParent(), "Enter Username Please");
					else
					{
						username=user;
						dispose();
					}
						
				}
			});
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
			JButton cancelButton = new JButton("Close");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
		
		setVisible(true);
		
	}
}