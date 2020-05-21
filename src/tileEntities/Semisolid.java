package tileEntities;


import java.awt.Rectangle;


import main.GameObject;
import main.ObjectHandler;
import map.MapTile;
import map.Room;
import map.TileEntitiy;
import players.Jeffrey;

public class Semisolid extends TileEntitiy{
	public boolean collide = true;
	public Semisolid() {
		super();
	}
	@Override 
	public void frameEvent () {
		Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
		if (j.vy < 0 || ((j.getX() + 7 < this.getX()*16 && j.vx > 0 && j.isColliding(new Rectangle ((this.getX() * 16) - 6,this.getY()*16,8,16))) || (j.vx < 0 && j.isColliding(new Rectangle ((this.getX()* 16) + 14,this.getY()* 16,8,16))))){
			
			collide = false;
		} 
		if (!collide) {
		
			MapTile [] collidingTiles = Room.getCollidingTiles(j,this.getType());
			boolean working = true;
			for (int i = 0; i < collidingTiles.length; i++) {
				if (collidingTiles[i].y == this.getY()*16) {
					working = false;
					break;
			}
		}
		collide = working;
	}
}
	@Override 
	public boolean doesColide (GameObject o) {
		
		if (o.getClass().getSimpleName().equals("Jeffrey")) {
		Jeffrey j = (Jeffrey) o;
		if (j.vy < 0 || ((j.getX() + 7 < this.getX()*16 && (j.vx > 0 && j.isColliding(new Rectangle ((this.getX() * 16) - 1,this.getY()*16,3,16)) && !Room.getTileName(Room.collisionLayer, this.getX()-1, this.getY()).equals("semisolid.0"))) || ((j.vx < 0 && j.isColliding(new Rectangle ((this.getX()* 16) + 14,this.getY()* 16,4,16)) && !Room.getTileName(Room.collisionLayer, this.getX()+1, this.getY()).equals("semisolid.0") && j.getX() < (this.getX()*16) + 16)))){
				collide = false;
			} 
				return collide;
		} else {
			if (o.getClass().getPackageName().equals("projectiles")) {
				return false;
			}
			return true;
		}
	}
}