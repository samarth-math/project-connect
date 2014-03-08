package Interface;
import java.awt.EventQueue;
import GUIObjects.AppWindow;
public class RunInterface {

	/**
	 * Launch the interface
	 */
	public static void main(String[] args) {
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