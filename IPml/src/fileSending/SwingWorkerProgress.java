package fileSending;

import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import GuiElements.FileTransferPanel;

public class SwingWorkerProgress {
	

    public SwingWorkerProgress(final FileTransferPanel ftp, final float max, final Server s) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
                /*
                JFrame frame = new JFrame("Receiving");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                */
               
                ftp.add(new TestPane(max,s));
                ftp.revalidate();
                
            }
        });
    }
}