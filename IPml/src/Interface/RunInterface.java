package Interface;
import java.awt.EventQueue;
import GUIObjects.AppWindow;
public class RunInterface implements Runnable {

	/**
	 * Launch the interface
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindow mainWindow = new AppWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}