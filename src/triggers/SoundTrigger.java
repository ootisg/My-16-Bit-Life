package triggers;



import main.GameCode;


public class SoundTrigger extends Trigger {

	public SoundTrigger () {
	}
	@Override
	public void triggerEvent () {
		try {
			GameCode.player.playSoundEffect(Float.parseFloat(this.getVariantAttribute("volume")), "resources/audio/soundEffects/" + this.getVariantAttribute("effectName") +".wav");
		} catch (NullPointerException e) {
			System.out.println("your sound effect diden't that you tried to trigger diden't play please check that the variant data is set up correctly");
		}
		
	}

}
