package resources;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import main.GameCode;

public class SoundPlayer implements LineListener{
	public boolean playing = false;
	AudioInputStream stream;
	AudioFormat format;
	DataLine.Info info;
	Clip backup;
	Clip clip
	public SoundPlayer (){
	}
	public void play (String songName, float volume){
		
		
		try {
		File soundFile = new File (songName);
		stream = AudioSystem.getAudioInputStream(soundFile);
		format = stream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		clip = (Clip) AudioSystem.getLine (info);
		backup = clip;
		clip.addLineListener(this);
		clip.open (stream);
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(volume);
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		playing = true;
		} catch (Exception e){
		System.out.println("whoops (error message) ");
		}
		
	}
	public void stop () {
		clip.stop();
	}
	public boolean isPlaying () {
		return playing;
	}
	public void playSoundEffect (float volume, String effectName, Clip clip){
		File soundFile;
		soundFile = new File (effectName);
		try {
		stream = AudioSystem.getAudioInputStream(soundFile);
		format = stream.getFormat();
		DataLine.Info info2 = new DataLine.Info(Clip.class, format);
		clip = (Clip) AudioSystem.getLine (info2);
		clip.open (stream);
		FloatControl gainControl2 = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl2.setValue(volume);
		clip.start();
		} catch (Exception e){
		System.out.println("whoops (error message) ");
		}
	}
	@Override
	public void update(LineEvent event) {
		if (event.getType() == LineEvent.Type.START){
			playing = true;
		} else {
			if (event.getType() == LineEvent.Type.STOP){
				backup.stop ();
				backup.flush ();
				backup.setFramePosition(0);
				playing = false;
		}
			
		}
		
	}

}
