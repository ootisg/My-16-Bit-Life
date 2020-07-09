package gameObjects;

import java.util.ArrayList;
import java.util.Arrays;

import main.GameObject;
import main.XposComparator;
import resources.Sprite;

public class Minecart extends EnterableObject{
	double vx = 0;
	boolean falling = true;
	boolean taken = false;
	boolean move = true;
	double direction = 0;
	public Minecart () {
		this.adjustHitboxBorders();
		this.setGameLogicPriority(2);
		this.setRenderPriority(2);
		this.setHitboxAttributes(0, 0, 32, 12);
		this.setSprite(new Sprite ("resources/sprites/Minecart.png"));
		this.rotateSprite(0);
	}
	@Override 
	public void frameEvent () {
		super.frameEvent();
			if (vx != 0 && falling) {
				if ((direction == 1 && vx < 0) || (direction == 2 && vx > 0)){ 
					this.goY(this.getY() - (Math.abs(vx)));
				} else {
					this.goY(this.getY() + (Math.abs(vx)));
				}
			} else {
			if (!this.isColliding("Rail")) {
			this.goY(this.getY() + 3);
			}
			}
		if (move) {
		this.goX(this.getX() + vx);
		}
		if (taken) {
			this.isColliding("Rail");
			int railType = 420;
			boolean broken = false;
			for (int i = 0; i < this.getCollisionInfo().getCollidingObjects().size();i++) {
				Rail workingRail = (Rail)this.getCollisionInfo().getCollidingObjects().get(i); 
				if (railType == 420) {
					railType = workingRail.getDirection();
				} else if (railType != workingRail.getDirection()) {
					broken = true;
					break;
				}
			}
			taken = broken;
		}
		if (this.inside) {
			if (keyDown('D')) {
				vx = vx + 1;
			}
			if (keyDown('A')) {
				vx = vx -1;
			}
			if (!keyDown('A') && !keyDown('D')) {
				vx = 0;
			}
		}
		/*this.goX(this.getX() + vx);
		if (vx > 0) {
			while (this.isColliding("Rail")) {
				this.setY(this.getY() - 1);
			}
			this.setY(this.getY() + 1);
		} else {
			while (!this.isColliding("Rail")) {
				
				this.setY(this.getY() + 1);
			}
		}
		/*while (true) {
			this.isColliding("Rail");
			this.setY(this.getY() + 1);
			count = count + 1;
			if (collidingObjects <= this.getCollisionInfo().getCollidingObjects().size()) {
				if (collidingObjects < this.getCollisionInfo().getCollidingObjects().size()) {
					mostY = this.getY();
				}
				collidingObjects = this.getCollisionInfo().getCollidingObjects().size();
			} else {
				this.setY(mostY);
				this.isColliding("Rail");
				break;
			}
			if (count > 220) {
				found = false;
				this.setY(originalY + 1);
				break;
			}
		}
			Rail working;
			XposComparator comp = new XposComparator();
			Object[] garbage = this.getCollisionInfo().getCollidingObjects().toArray();
			GameObject[] rails = new GameObject [garbage.length];
			for (int i = 0; i < garbage.length; i++) {
				rails[i] = (GameObject) garbage[i];
			}
			Arrays.parallelSort(rails,comp);
			if (vx >= 0) {
				working = (Rail)rails[rails.length - 1];
			} else {
				working = (Rail)rails[0];
			}
			switch (working.getDirection()) {
			case 0:
				this.getAnimationHandler().rotate(0);
				if (vx > 0) {
					vx = vx - 0.1;
					if (vx < 0) {
						vx = 0;
					}
				} else {
					if (vx != 0) {
						vx = vx + 0.1;
						if (vx > 0) {
							vx = 0;
						}
					} 
				}
			case 1:
				this.getAnimationHandler().rotate(60);
				if (vx < 15) {
				vx = vx + 1;
				}
			case 2:
				this.getAnimationHandler().rotate(310);
				if (vx > -15) {
				vx = vx - 1;
			}
		}*/
	}
	public void setFall (boolean newFall) {
		falling = newFall;
	}
}
