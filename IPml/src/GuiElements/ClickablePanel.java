package GuiElements;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Component;


public class ClickablePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel = new JLabel("New label");

	public ClickablePanel(){
		this.setMaximumSize(new Dimension(30000,50));
		this.setPreferredSize(new Dimension(300,50));
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		
		
		this.lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.lblNewLabel.setBounds(115, 12, 70, 26);
		this.add(lblNewLabel);
	}
}