package fileSending;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import javax.swing.border.EmptyBorder;

public class TestPane extends JPanel {

    private JProgressBar pbProgress;
    private JButton start;

    public TestPane() {

        setBorder(new EmptyBorder(10, 10, 10, 10));
        pbProgress = new JProgressBar();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(pbProgress, gbc);

               
                ProgressWorker pw = new ProgressWorker();
                pw.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        String name = evt.getPropertyName();
                        if (name.equals("progress")) {
                            int progress = (int) evt.getNewValue();
                            pbProgress.setValue(progress);
                            repaint();
                        } else if (name.equals("state")) {
                            SwingWorker.StateValue state = (SwingWorker.StateValue) evt.getNewValue();
                            switch (state) {
                                case DONE:
                                    start.setEnabled(true);
                                    break;
                            }
                        }
                    }

                });
                pw.execute();
    }
}