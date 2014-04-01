package fileSending;


import javax.swing.SwingWorker;


public class ProgressWorker extends SwingWorker<Object, Object> {

    @Override
    protected Object doInBackground() throws Exception {
       
        while (Receiver.i < Receiver.max) {
     
            int progress = Math.round(((float)Receiver.i / (float)Receiver.max) * 100f);
            setProgress(progress);
            try {
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}

