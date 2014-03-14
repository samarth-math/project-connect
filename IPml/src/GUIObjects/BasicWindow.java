/*
 * The Basic qualities possessed by every window which you wish for everyone to inherit.

 *  Preferring to implement as an abstract class than an interface, because there are no method definitions in interface
 *  whereas in abstract class there can be default method definitions for things that aren't defined.
 *  
 */
package GUIObjects;

import javax.swing.JFrame;
import javax.swing.UIManager;

public abstract class BasicWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	public BasicWindow()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());// For making it look native in windows
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}