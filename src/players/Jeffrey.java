package players;

import resources.Sprite;

public class Jeffrey extends Player {
	
	public static final Sprite JEFFREY_WALKING = new Sprite ("resources/sprites/config/jeffrey_walking.txt");
	public static final Sprite JEFFREY_IDLE = new Sprite("resources/sprites/config/jeffrey_idle.txt");
	public static final Sprite JEFFREY_CROUCHING = new Sprite ("resources/sprites/config/crouching_Jeffrey.txt");
	public static final Sprite JEFFREY_WALKING_POGO = new Sprite ("resources/sprites/config/jeffrey_walking_pogo.txt");
	
	public Jeffrey () {
		super();
		Sprite [] sprites = new Sprite [] {JEFFREY_WALKING,JEFFREY_IDLE,JEFFREY_CROUCHING};
		this.setSprites(sprites);
	}
	
	@Override
	public void declare () {
		super.declare();
	}
	
	@Override
	public int getPlayerNum () {
		return 0;
	}
	
}
