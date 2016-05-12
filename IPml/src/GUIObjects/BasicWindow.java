/*
 * The Basic qualities possessed by every window which you wish for everyone to inherit.

 *  Preferring to implement as an abstract class than an interface, because there are no method definitions in interface
 *  whereas in abstract class there can be default method definitions for things that aren't defined.
 *  
 */
package GUIObjects;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;


public abstract class BasicWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	public BasicWindow()
	{
		URL icon = getClass( ).getResource("images/iptalk.png");
		if(icon!=null) {
			ImageIcon img = new ImageIcon(icon);
			setIconImage(img.getImage());
		}
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());// For making it look native in windows
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}