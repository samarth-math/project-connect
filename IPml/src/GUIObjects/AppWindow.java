package GUIObjects;

import java.awt.Container;

import javax.swing.BoxLayout;

import GuiElements.ClickablePanel;

public class AppWindow extends BasicWindow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppWindow(){
		
		this.setSize(300,500);
		Container c = new Container();
		c= this.getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		
		this.setVisible(true);
		c.add(new ClickablePanel());
		
		
		
	}
	
}