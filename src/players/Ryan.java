package players;

import resources.Sprite;

public class Ryan extends Player {
	//Ryan Sprites
		public static final Sprite RYAN_IDLE = new Sprite ("resources/sprites/config/ryan_idle.txt");
		public static final Sprite RYAN_WALKING = new Sprite ("resources/sprites/config/ryan_walking.txt");
		public static final Sprite RYAN_CROUCHING = new Sprite ("resources/sprites/config/crouching_Ryan.txt");
		public static final Sprite RYAN_WALKING_POGO = new Sprite ("resources/sprites/config/ryan_walking_pogo.txt");	
		public static final Sprite RYAN_MICROPHONE_IDLE = new Sprite ("resources/sprites/config/ryan_idle_microphone.txt");
		public static final Sprite RYAN_MICROPHONE_WALKING = new Sprite("resources/sprites/config/ryan_walking_microphone.txt");
		public static final Sprite RYAN_WHIPPING = new Sprite ("resources/sprites/config/microphoneWhip.txt");
		public static final Sprite WHIP_LENGTH = new Sprite ("resources/sprites/config/microphoneWhipVariableFrame.txt");
		
	public Ryan () {
		super();
		Sprite [] sprites = new Sprite [] {RYAN_WALKING,RYAN_IDLE,RYAN_CROUCHING};
		this.setSprites(sprites);
	}
	
	@Override
	public int getPlayerNum () {
		return 2;
	}
}
