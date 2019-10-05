package weapons;

import items.RedBlackPaintBall;
import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import projectiles.Paintball;
import resources.Sprite;

public class redBlackPaintBallGun extends AimableWeapon {
	
	public static final Sprite outtaAmmo = new Sprite ("resources/sprites/Outta_Ammo.png");
	public static final Sprite gunSprite = new Sprite ("resources/sprites/redblack_gun.png");
	
	private int textTimer;
	private int cooldown;
	public RedBlackPaintBall testball;
	public redBlackPaintBallGun(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
		textTimer = 0;
		this.cooldown = 0;
		testball = new RedBlackPaintBall ();
		this.setSprite(new Sprite ("resources/sprites/redblack_gun.png"));
	}
	public void frameEvent () {
		Jeffrey jeffrey = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").getFirst ();
		if (this.cooldown > 0) {
			this.cooldown --;
		}
		if (mouseButtonClicked (0) && cooldown == 0 ) {
			if (jeffrey.inventory.checkAmmo(testball)) {
			this.shoot (new Paintball ());
			jeffrey.inventory.removeItem(testball);
			cooldown = 5;
			} else {
				textTimer = 10;
				outtaAmmo.draw((int)this.getX() - Room.getViewX(), (int)this.getY() - 20);
			}
		}
		if (textTimer > 0) {
			outtaAmmo.draw((int)this.getX() - Room.getViewX(), (int)this.getY() - 20);
			textTimer = textTimer - 1;
		}
	}
	
}
