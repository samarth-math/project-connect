package GUIObjects;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;


public class AboutUs extends BasicWindow {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public AboutUs() {
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setTitle("About IPtalk v1.02");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 462, 252);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html><br><br>IPTalk is a LAN Messenger service, based on a distributed<br> systems model (i.e. you don't need to setup a server)<br><br>This application was designed and developed by<br> Rajat Vyas, Rishabh Baid and Samarth Mathur with some<br> helpful input from Shasak Raina,<br> Students of LNMIIT batch-y10. <br><br> In order to submit a bug report use the following link <a href =\"https://github.com/samarth-math/project-connect/issues/new\">https://github.com/samarth-math/project-connect/issues/new</a></html>");
		
		lblNewLabel.setBounds(12, -17, 432, 186);
		contentPane.add(lblNewLabel);
		
		/*JTextPane txtpnAbout = new JTextPane();
		txtpnAbout.setEditable(false);
		txtpnAbout.setText("This application was designed and developed by Rajat Vyas, Rishabh Baid and Samarth Mathur with some helpful input from Shasak Raina, students of LNMIIT batch-y10. \n\n In order to submit a bug report click on the following link <a href =\"https://github.com/samarth-math/project-connect/issues/new\">Submit a Bug Report</a>");
		contentPane.add(txtpnAbout, BorderLayout.CENTER);*/
	}
}
