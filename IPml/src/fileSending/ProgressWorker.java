package fileSending;


import javax.swing.SwingWorker;


public class ProgressWorker extends SwingWorker<Object, Object> {
	
	private float i, max;
	public ProgressWorker(float i, float max){
		this.i = i;
		this.max = max;
	}

    @Override
    protected Object doInBackground() throws Exception {
       
        while (i < max) {
     
            int progress = Math.round(((float)i / (float)max) * 100f);
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

