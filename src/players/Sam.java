package players;

import resources.Sprite;

public class Sam extends Player {
	
	//Sam Sprites
	public static final Sprite SAM_IDLE =new Sprite ("resources/sprites/config/sam_idle.txt");
	public static final Sprite SAM_WALKING = new Sprite ("resources/sprites/config/sam_walking.txt");
	public static final Sprite SAM_CROUCHING = new Sprite ("resources/sprites/config/crouching_Sam.txt");
	public static final Sprite SAM_WALKING_POGO = new Sprite ("resources/sprites/config/sam_walking_pogo.txt");
	public static final Sprite SAM_SWORD = new Sprite ("resources/sprites/config/sam_idle_with_sword.txt");
	public static final Sprite SAM_WALKING_SWORD = new Sprite ("resources/sprites/config/sam_walking_with_sword.txt");
	
	
	public Sam () {
		super ();
		Sprite [] sprites = new Sprite [] {SAM_WALKING,SAM_IDLE,SAM_CROUCHING};
		this.setSprites(sprites);
	}
	
	@Override
	public int getPlayerNum () {
		return 1;
	}
}
