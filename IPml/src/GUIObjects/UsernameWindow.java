package GUIObjects;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UsernameWindow extends BasicWindow
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private JPanel contentPane;
	private JTextField txtUsername;
	public UsernameWindow() 
	{
		setResizable(false);
		setTitle("Choose Username");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 254, 279);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsername.setBounds(69, 115, 114, 19);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Name:");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(88, 88, 55, 15);
		contentPane.add(lblUsername);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username= txtUsername.getText().trim();
				dispose();
			}
		});
		btnLogin.setBounds(88, 198, 80, 25);
		contentPane.add(btnLogin);
		
		setVisible(true);
	}
	public String getusername()
	{
		return username;
	}

}
