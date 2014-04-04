package fileSending;


import javax.swing.SwingWorker;


public class ProgressWorker extends SwingWorker<Object, Object> {
	
	private float max;
	private Server s=null;
	private Client c=null;
	
	public ProgressWorker(float max, Server s){
		this.s = s;
		this.max = max;
	}
	
	public ProgressWorker(float max, Client c){
		this.c = c;
		this.max = max;
	}

    @Override
    protected Object doInBackground() throws Exception {
       if(s!=null)
       {
       	
           while (s.geti() < max) {
               int progress = Math.round(((float)s.geti() / (float)max) * 100f);
               setProgress(progress);
               try {
                   Thread.sleep(25);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
    	   
       }
       else if (c!=null)
       {
    	   while (c.geti() < max) {
               int progress = Math.round(((float)c.geti() / (float)max) * 100f);
               setProgress(progress);
               try {
                   Thread.sleep(25);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
    	   
       }
        return null;
    }
}

