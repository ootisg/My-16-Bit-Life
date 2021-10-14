package gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import main.GameObject;
import main.RenderLoop;
import players.Player;
import resources.ColidableVector;
import resources.LoopableSprite;
import resources.Sprite;
import java.awt.Point;

public class SwingingVine extends GrabbableObject {
	
	double roatationFactor = 1;
	double speed = 0;
	
	int height = 200;
	
	boolean grabbedOn = false;
	
	int VINE_WIDTH = 16;
	
	double contactHeight = -1;
	
	boolean goinUp;
	
	LoopableSprite loop;
	
	
	public SwingingVine () {
	
		this(200);
	}
	public SwingingVine (int height) {
		
		this.setRenderPriority(-60);  
		
		this.height = height;
		
		loop = new LoopableSprite (new Sprite ("resources/sprites/vine.png"),0,32,-1,height);
		
		this.setSprite(loop);
	}
	@Override
	public void setY(double val) {
		super.setY(val);
		loop = new LoopableSprite (new Sprite ("resources/sprites/vine.png"),0,32,-1,height + (int)val);
	}
	
	@Override
	public void frameEvent () {
	
		
		roatationFactor = roatationFactor + speed;
		
		if (roatationFactor > 0) {
			speed = speed - 0.01;
		} else {
			speed = speed + 0.01;
		}
		super.frameEvent();
	}
	
	@Override
	public void onGrab() {
		Player j = Player.getActivePlayer();
		
		contactHeight = Math.sqrt(Math.pow((j.getY() - this.getY()),2) + Math.pow((j.getX()  - this.getX()),2));
	}
	@Override
	public void onRelease () {
		Player j = Player.getActivePlayer();
		
		j.vx = Math.cos(roatationFactor + (3*Math.PI)/2) * -contactHeight/7;
		j.vy = Math.sin(roatationFactor + (3*Math.PI)/2) * contactHeight/7;
		j.setDrawRotation(0);
	}
	
	@Override
	public void whileGrabbed() {
		Player j = Player.getActivePlayer();
		
		j.setX(this.getX() - contactHeight* Math.cos(roatationFactor + (3*Math.PI)/2));
		j.setY(this.getY() - contactHeight* Math.sin(roatationFactor + (3*Math.PI)/2));
		
		j.setDrawRotation(roatationFactor);
		
		if (keyDown('W')) {
			if (j.goY(j.getY() - 3)) {
			
			Point start3 = new Point ((int)(this.getX() - Math.cos(roatationFactor + (3*Math.PI/2)) + VINE_WIDTH/2),(int) (this.getY() - Math.sin(roatationFactor + (3*3.14)/2)));
			Point end3 = new Point ((int)(this.getX() - height* Math.cos(roatationFactor + (3*Math.PI)/2) + VINE_WIDTH/2), (int)(this.getY() - height * Math.sin(roatationFactor + (3*3.14)/2)));
			ColidableVector vSlope = new ColidableVector (start3,end3);
			
			double slope = vSlope.getSlope();
			
			j.goX(j.getX() - 3/slope);
			contactHeight = Math.sqrt(Math.pow((j.getY() - this.getY()),2) + Math.pow((j.getX()  - this.getX()),2));
			}
		}
		if (keyDown('S')) {
			if (j.goY(j.getY() + 3)) {
				Point start3 = new Point ((int)(this.getX() - Math.cos(roatationFactor + (3*Math.PI/2)) + VINE_WIDTH/2),(int) (this.getY() - Math.sin(roatationFactor + (3*3.14)/2)));
				Point end3 = new Point ((int)(this.getX() - height* Math.cos(roatationFactor + (3*Math.PI)/2) + VINE_WIDTH/2), (int)(this.getY() - height * Math.sin(roatationFactor + (3*3.14)/2)));
				ColidableVector vSlope = new ColidableVector (start3,end3);
				
				double slope = vSlope.getSlope();
				j.goX(j.getX() + 3/slope);
				contactHeight = Math.sqrt(Math.pow((j.getY() - this.getY()),2) + Math.pow((j.getX()  - this.getX()),2));
				if (contactHeight > height) {
					contactHeight = height;
					j.setX(j.getX() - 3/slope);
					j.setY(j.getY() - 3);
				}
			}
		}
	}
	
	@Override
	public void draw () {
		loop.drawRotated((int)this.getX(), (int)this.getY(), 0, 8, 0, roatationFactor);
		
//		Graphics g = RenderLoop.window.getBufferGraphics();
//		
//		g.setColor(new Color(0));
//		
//		Point start = new Point ((int)(this.getX() - Math.cos(roatationFactor + (3*Math.PI)/2)), (int)(this.getY() - Math.sin(roatationFactor + (3*3.14)/2)));
//		Point end = new Point ((int)(this.getX() - height* Math.cos(roatationFactor + (3*Math.PI)/2)), (int)(this.getY() - height * Math.sin(roatationFactor + (3*3.14)/2)));
//
//		Point start2 = new Point ((int)(this.getX() - Math.cos(roatationFactor + (3*Math.PI/2)) + VINE_WIDTH),(int) (this.getY() - Math.sin(roatationFactor + (3*3.14)/2)));
//		Point end2 = new Point ((int)(this.getX() - height* Math.cos(roatationFactor + (3*Math.PI)/2) + VINE_WIDTH), (int)(this.getY() - height * Math.sin(roatationFactor + (3*3.14)/2)));
//		
//		
//		g.drawLine((int)start.x, (int)start.y, (int)end.x, (int)end.y);
//		g.drawLine((int)start2.x, (int)start2.y, (int)end2.x, (int)end2.y);
	}
	
	@Override
	public boolean isColliding (GameObject obj) {
		Point start = new Point ((int)(this.getX() - Math.cos(roatationFactor + (3*Math.PI)/2)), (int)(this.getY() - Math.sin(roatationFactor + (3*3.14)/2)));
		Point end = new Point ((int)(this.getX() - height* Math.cos(roatationFactor + (3*Math.PI)/2)), (int)(this.getY() - height * Math.sin(roatationFactor + (3*3.14)/2)));

		Point start2 = new Point ((int)(this.getX() - Math.cos(roatationFactor + (3*Math.PI/2)) + VINE_WIDTH),(int) (this.getY() - Math.sin(roatationFactor + (3*3.14)/2)));
		Point end2 = new Point ((int)(this.getX() - height* Math.cos(roatationFactor + (3*Math.PI)/2) + VINE_WIDTH), (int)(this.getY() - height * Math.sin(roatationFactor + (3*3.14)/2)));
		
		Point start3 = new Point ((int)(this.getX() - Math.cos(roatationFactor + (3*Math.PI/2)) + VINE_WIDTH/2),(int) (this.getY() - Math.sin(roatationFactor + (3*3.14)/2)));
		Point end3 = new Point ((int)(this.getX() - height* Math.cos(roatationFactor + (3*Math.PI)/2) + VINE_WIDTH/2), (int)(this.getY() - height * Math.sin(roatationFactor + (3*3.14)/2)));
		
		
		ColidableVector vLeft = new ColidableVector (start,end);
		ColidableVector vRight = new ColidableVector (start2,end2);
		ColidableVector vCenter = new ColidableVector (start3,end3);
		
		return (vLeft.isCollidng(obj) || vRight.isCollidng(obj) || vCenter.isCollidng(obj));
		
	}
}
