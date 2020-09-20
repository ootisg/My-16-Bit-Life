package players;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import resources.Sprite;

public class JeffreyTopDown extends GameObject {
	boolean inzialized = false;
	public JeffreyTopDown () {
		this.setSprite(new Sprite ("resources/sprites/JeffreyTopDown.png"));
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override
	public void frameEvent () {
		if (!inzialized) {
			int veiwX = (int)(this.getX() -this.getX()%213);
			int veiwY = (int)(this.getY() -this.getY()%160);
			if (veiwY + 480 > Room.getHeight()*16) {
				veiwY = Room.getHeight()*16 - 480;
				if (veiwY < 0) {
					veiwY = 0;
				}
			}
			if (veiwX + + 640 > Room.getWidth()*16) {
				veiwX = Room.getWidth()*16 - 640;
				if (veiwX < 0) {
					veiwX = 0;
				}
			}
			Room.setView(veiwX, veiwY);
			inzialized = true;
		}
		if (keyDown ('W')) {
			this.goY(this.getY() - 2);
		}
		if (keyDown ('S')) {
			this.goY(this.getY() + 2);
		}
		if (keyDown ('A')) {
			this.goX(this.getX() - 2);
		}
		if (keyDown ('D')) {
			this.goX(this.getX() + 2);
		}
		double x = this.getX ();
		double y = this.getY ();
		int viewX = Room.getViewX ();
		int viewY = Room.getViewY ();
		if (y - viewY >= 320 && y - 320 < Room.getHeight () * 16 - 480) {
			viewY = (int) y - 320;
			Room.setView (Room.getViewX (), viewY);
		}
		if (y - viewY <= 160 && y - 160 > 0) {
			viewY = (int) y - 160;
			Room.setView (Room.getViewX (), viewY);
		}
		if (x - viewX >= 427 && x - 427 < Room.getWidth () * 16 - 640) {
			viewX = (int) x - 427;
			Room.setView (viewX, Room.getViewY ());
		}
		
		if (x - viewX <= 213  && x - 213  > 0) {
			viewX = (int) x - 213;
			Room.setView (viewX, Room.getViewY ());
		}
	}
	public static JeffreyTopDown getActiveJeffrey () {
		return (JeffreyTopDown) ObjectHandler.getObjectsByName("JeffreyTopDown").get(0);
	}
}
