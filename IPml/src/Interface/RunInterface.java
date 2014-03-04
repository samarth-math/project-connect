package Interface;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.BoxLayout;

public class RunInterface {

	/**
	 * Launch the interface
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window mainWindow = new Window();
					mainWindow.setVisible(true);
					Container c= new Container();
					c= mainWindow.getContentPane();
					c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
					c.add(new ClickablePanel(null));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
