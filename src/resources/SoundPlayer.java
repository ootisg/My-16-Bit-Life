package resources;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

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
	Clip clip;
	public HashMap <String, Clip> cliptwooowwowows = new HashMap<String, Clip> ();
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
	public void playSoundEffect (float volume, String effectName){
		File soundFile;
		soundFile = new File (effectName);
		try {
		stream = AudioSystem.getAudioInputStream(soundFile);
		format = stream.getFormat();
		DataLine.Info info2 = new DataLine.Info(Clip.class, format);
		Clip clip2 = (Clip) AudioSystem.getLine (info2);
		clip2.open (stream);
		FloatControl gainControl2 = (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl2.setValue(volume);
		clip2.start();
		cliptwooowwowows.put(effectName, clip2);
		} catch (Exception e){
		e.printStackTrace();
		System.out.println("whoops (error message with sound player) ");
		}
	}
	public Clip getClip (String clipName) {
		ArrayList <String> entrysToRemove = new ArrayList <String> ();
		Iterator<Entry<String, Clip>>iter = cliptwooowwowows.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Clip> working = iter.next();
			if (!working.getValue().isActive()) {
				entrysToRemove.add(working.getKey());
			}
		}
		for (int i = 0; i < entrysToRemove.size(); i++) {
			cliptwooowwowows.remove(entrysToRemove.get(i));
		}
		return cliptwooowwowows.get(clipName);
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
