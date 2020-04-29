package triggers;

import javax.sound.sampled.Clip;

import resources.SoundPlayer;

public class SoundTrigger extends Trigger {
	Clip clip;
	SoundPlayer player;
	public SoundTrigger () {
	player = new SoundPlayer ();
	}
	@Override
	public void triggerEvent () {
		try {
			player.playSoundEffect(Float.parseFloat(this.getVariantAttribute("volume")), "resources/audio/soundEffects/" + this.getVariantAttribute("effectName") +".wav", clip);
		} catch (NullPointerException e) {
			System.out.println("your sound effect diden't that you tried to trigger diden't play please check that the variant data is set up correctly");
		}
		
	}

}
