package fileSending;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import GuiElements.FileTransferPanel;

public class SwingWorkerProgress {

    public static void main(String[] args) {
       // new SwingWorkerProgress();
    }

    public SwingWorkerProgress(final FileTransferPanel ftp) {
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
                System.out.println("Safely working...");
                ftp.add(new TestPane());
                ftp.revalidate();
                
            }
        });
    }
}