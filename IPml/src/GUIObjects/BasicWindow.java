/*
 * The Basic qualities possessed by every window which you wish for everyone to inherit.
 *  Preferring to implement as an abstract class than an interface, because there are no method definitions in interface
 *  whereas in abstract class there can be default method definitions for things that aren't defined.
 */
package GUIObjects;

import javax.swing.JFrame;

public abstract class BasicWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Define Everything that you want in a basic window, whenever anyone inherits this, they get all those properties
	//Some methods may be overwritten if they wish, and others will be rendered as defined here.
}