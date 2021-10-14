package tileEntities;





import main.GameObject;
import map.TileEntitiy;
import players.Player;

public class Semisolid extends TileEntitiy{
	public Semisolid() {
		super();
	}
	@Override 
	public boolean doesColide (GameObject o) {
		
		if (o instanceof Player) {
		Player j = (Player) o;
		if (j.getVy() < 0 || (j.getYPrevious() + j.hitbox().height > this.getY() * 16)){
			return false;
		} 
			return true;
		} else {
			if (o.getClass().getPackageName().equals("projectiles")) {
				return false;
			}
			return true;
		}
	}
}