package gameObjects;

import java.util.EmptyStackException;
import java.util.Stack;

import main.GameObject;
import map.Room;
import resources.Sprite;

public class Point extends GameObject {
	double x;
	double y;
	Stack<Point> points;
	public Point() { 
		points = new Stack<Point>();
		//this.setSprite(new Sprite ("resources/sprites/config/bug_boi.txt"));
		this.setHitboxAttributes(0, 0, 5, 5);
	}
	public Point (double xi, double yi) {
	x = xi;
	y = yi;
	this.setX(xi);
	this.setY(yi);
	points = new Stack<Point>();
	//this.setSprite(new Sprite ("resources/sprites/config/bug_boi.txt"));
	this.setHitboxAttributes(0, 0, 5, 5);
	}
	@Override 
	public void frameEvent () {
		x = this.getX();
		y = this.getY();
	}
	public Stack <Point> getPath(){
		return points;
	}
	public Stack<Point> generatePath(Point destanation) { 
		while (true) {
			if (points.empty()) {
		if (destanation.equals(this.getNextPoint(destanation))) {
			destanation.declare(destanation.x,destanation.y);
			points.push(destanation);
			points.peek().points = points;
			break;
		} else {
			
			points.push(this.getNextPoint(destanation));
			points.peek().points = points;
		}
		} else {
		//	System.out.println(destanation.equals(points.peek().getNextPoint(destanation)));
			if (destanation == points.peek()) {
				destanation.declare(destanation.x,destanation.y);
				points.push(destanation);
				points.peek().points = points;
				break;
			} else {
				points.push(points.peek().getNextPoint(destanation));
				points.peek().points = points;
			//	System.out.println(destanation.x);
				//System.out.println(points.peek().x);
			}
		}
		}
		return this.flipStack(points);
	}
	public Stack <Point> flipStack(Stack <Point> startStack){
		Stack <Point> working = new Stack <Point>();
		while (!startStack.isEmpty()) {
			working.push(startStack.pop());
		}
		return working;
	}
	public Point getNextPoint(Point destanation) {
		//DirectionBullet bullet = new DirectionBullet(x,y);
		//double direction1 = bullet.findDirection(destanation);
		//double direction2 = bullet.findDirection(destanation);
		double yDiffrence;
		yDiffrence = destanation.y - this.y;
		/*while(Room.isColliding(this)){
			this.setY(this.getY() + 1);
		}*/
		if (Room.doHitboxVectorCollison(this.hitbox(), destanation.x, destanation.y) == null) {
			//System.out.println("debug");
			return destanation;
		} else {
			try {
			double crashX = Room.doHitboxVectorCollison(this.hitbox(), destanation.x, this.y + (yDiffrence/3))[0];
			double crashY = Room.doHitboxVectorCollison(this.hitbox(), destanation.x, this.y + (yDiffrence/3))[1];
			try {
			//points.pop();
			this.getAnimationHandler().hide();
			Point pointToCheck = points.peek();
			if(points.contains(this)) {
				points.pop();
				
			}
			return (pointToCheck.getCorrectoedDirectionPoint(destanation));
			} catch (EmptyStackException e) {
			return (this.getCorrectoedDirectionPoint(destanation));
			}
			} catch (NullPointerException e) {
				Point coolPoint = new Point (destanation.x, this.y + (yDiffrence/3));
				coolPoint.declare(coolPoint.x, coolPoint.y);
				return coolPoint;
			}
		}
	}
	public double getSlope (Point destanation) {
	double run = this.x - destanation.getX();
	if (run < 0) {
		run = run *-1;
	}
	double rise = destanation.getY() - this.y;
	return rise/run;
	}
	public Point getCorrectoedDirectionPoint(Point destanation){
		double xto = 0;
		double xto1 = 0;
		int devation1 = 0;
		int devation2 = 0;
		double yDiffrence;
		yDiffrence = destanation.y - this.y;
		double crashX = Room.doHitboxVectorCollison(this.hitbox(), destanation.x, this.y + (yDiffrence/3) )[0];
		double crashY = Room.doHitboxVectorCollison(this.hitbox(), destanation.x, this.y + (yDiffrence/3))[1];
		if (Room.doHitboxVectorCollison(this.hitbox(), destanation.x + xto, crashY) == null) {
			Point coolPoint = new Point (destanation.x, crashY);
			coolPoint.declare(coolPoint.x, coolPoint.y);
			return coolPoint;
		}
		while (Room.doHitboxVectorCollison(this.hitbox(), (destanation.x + xto), crashY) != null) {
			xto = xto + 1;
			devation1 = devation1 + 1;
			if (devation1 == 690) {
				break;
			}
		}
		while (Room.doHitboxVectorCollison(this.hitbox(), destanation.x + xto1, crashY) != null) {
			xto1 = xto1 - 1;
			devation2 = devation2 + 1;
			if (devation2 == 690) {
				break;
			}
		}
		if (devation1 == 690 && devation2 == 690 ) {
			try {
			crashY = Room.doHitboxVectorCollison(this.hitbox(), destanation.x, this.y - (yDiffrence/3))[1];
			} catch (NullPointerException e) {
			crashY = this.y - (yDiffrence/3);
			}
			devation2 = 0;
			devation1 = 0;
			xto = 0;
			xto1 = 0;
			while (Room.doHitboxVectorCollison(this.hitbox(), (destanation.x + xto), crashY) != null) {
				xto = xto + 1;
				devation1 = devation1 + 1;
				if (devation1 == 690) {
					break;
				}
			}
			while (Room.doHitboxVectorCollison(this.hitbox(), destanation.x + xto1, crashY) != null) {
				xto1 = xto1 - 1;
				devation2 = devation2 + 1;
				if (devation2 == 690) {
					break;
				}
			}
		}
		if (devation1 <= devation2 ) {
			Point coolPoint = new Point (crashX + xto, crashY);
			coolPoint.declare(coolPoint.x, coolPoint.y);
			return coolPoint;
		} else {
			Point coolPoint = new Point (crashX+ xto1, crashY);
			coolPoint.declare(coolPoint.x, coolPoint.y);
			return coolPoint;
			}
		}
	
	public void getPreviosPoint(Point fromWhere) {
		
	}
}
