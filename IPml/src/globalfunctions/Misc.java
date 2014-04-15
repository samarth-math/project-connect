package globalfunctions;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Misc {

	public static synchronized void playSound( final File soundfile) {
		  new Thread(new Runnable() {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundfile);
		        clip.open(inputStream);
		        clip.start();
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
		}
}
