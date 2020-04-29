package enemys;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

import gameObjects.BugAimer;
import gameObjects.Point;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import resources.Sprite;

public class BuggyBoi extends Enemy {
	private int speed;
	private boolean circling;
	Stack <Point> path;
	Stack <Point> backupPath;
	boolean immedetly;
	Point startPoint;
	boolean presetPath;
	boolean chaseing;
	boolean wasCircling;
	boolean startToEnd;
	Point endPoint;
	BugAimer aimer;
	public BuggyBoi() {
		this.getAnimationHandler().setFrameTime(30);
		RNG = new Random ();
		this.setHealth(20);
		this.defence = 0;
		aimer = new BugAimer (this);
		presetPath =false;
		startToEnd = true;
		chaseing = true;
		immedetly = true;
		timer = 0;
		path = new Stack<Point>();
		this.defence = 0;
		wasCircling = false;
		circling = false;
		speed = 3;
		//testPoint.declare(185, 380);
		this.diesNormally = false;
		this.setSprite(new Sprite ("resources/sprites/config/bug_boi.txt"));
		this.setHitboxAttributes(0, 0, 15,15);
	}
	@Override 
	public void enemyFrame () {
		if (this.health <= 0) {
			aimer.forget();
			this.deathEvent();
		}
		//System.out.println(this.health);
		if (immedetly) {
			aimer.declare(this.getX(), this.getY());
			path = this.regeneratePath();
			startPoint = new Point (this.getX(), this.getY());
			startPoint.declare(startPoint.getX(),startPoint.getY());
			immedetly = false;
		}
		/*if (this.circling) {
			this.followCircularPath(40, speed);
		} */
		if (chaseing && presetPath) {
			if (pathFallowed) {
				if (!startToEnd) {
					path = this.regeneratePath();
					startToEnd = !startToEnd;
					pathFallowed = false;
				} else {
				startToEnd = !startToEnd;
				pathFallowed = false;
				path = this.generateBackwardsPath();
				}
			}
		this.followPresetPath(path, speed);
		aimer.setX(this.getX() + aimer.getXDisplacement());
		aimer.setY(this.getY() + aimer.getYDisplacement());
		}
		/*if (this.isNearPlayerX(0, 80, 0, 80) && !chaseing) {
			this.circling = true;
		}
		if (this.circles == 2) {
			circles = 0;
			circling = false;
			chaseing = true;
			endPoint = new Point (GameCode.testJeffrey.getX(), GameCode.testJeffrey.getY());
			endPoint.declare(endPoint.x, endPoint.y);
		}
		//this.swarm(20);
	//	timer = timer + 1;
		//if (this.leadingEnemy) {
		//	this.requestMovement(speed, direction);
		//}
		/*if (timer > oldTimer + 1 && circling) {
			oldTimer = timer;
			direction = direction + 0.1;
		}*/
		/*if (this.fallowingEnemy) {
			int rng = RNG.nextInt(9);
			if (rng > 4) {
				this.goX(this.getX() - (rng - 4));	
				if (!this.isColliding(this.enemyToFallow.swarmBox)){
					this.goX(this.getX() + (rng - 4));
				}
			} else {
				this.goX(this.getX() + rng);
				if (!this.isColliding(this.enemyToFallow.swarmBox)){
					this.goX(this.getX() - rng);
				}
			}
			rng = RNG.nextInt(9);
			if (rng > 4) {
				this.goY(this.getY() - (rng - 4));	
				if (!this.isColliding(this.enemyToFallow.swarmBox)){
					this.goY(this.getY() + (rng - 4));
				}
			} else {
				this.goY(this.getY() + rng);
				if (!this.isColliding(this.enemyToFallow.swarmBox)){
					this.goY(this.getY() - rng);
				}
			}	
		}*/
	}

	/**
	 * returns the speed the buggy boi is going
	 */
	public int getSpeed () {
		return speed;
	}
	public Stack <Point> regeneratePath(){
		try {
			Iterator<GameObject> iter;
			ArrayList<Point> working = new ArrayList<Point>();
			iter = ObjectHandler.getObjectsByName("Point").iterator();
			while (iter.hasNext()) {
			GameObject currentPoint = iter.next();
			if (this.getVariantAttribute("wayPoint").equals(currentPoint.getVariantAttribute("wayPoint"))) {
				working.add((Point) currentPoint);
			}
			}
			Stack <Point> workinger = new Stack <Point>();
			while (!working.isEmpty()){
				int highestWayPoint =0;
				int index = 0;
				for (int i = 0; i < working.size(); i++) {
					if (Integer.parseInt(working.get(i).getVariantAttribute("PointNumber")) > highestWayPoint ) {
						highestWayPoint = Integer.parseInt(working.get(i).getVariantAttribute("PointNumber"));
						index = i;
					}
				}
				workinger.push(working.get(index));
				working.remove(index);
			}
			presetPath = true;
			return workinger;
			} catch (NullPointerException e) {
				return new Stack <Point>();
			}
	}
public Stack <Point> generateBackwardsPath(){
	try {
		Iterator<GameObject> iter;
		ArrayList<Point> working = new ArrayList<Point>();
		iter = ObjectHandler.getObjectsByName("Point").iterator();
		while (iter.hasNext()) {
		GameObject currentPoint = iter.next();
		if (this.getVariantAttribute("wayPoint").equals(currentPoint.getVariantAttribute("wayPoint"))) {
			working.add((Point) currentPoint);
		}
		}
		Stack <Point> workinger = new Stack <Point>();
		while (!working.isEmpty()){
			int highestWayPoint =100000;
			int index = 0;
			for (int i = 0; i < working.size(); i++) {
				if (Integer.parseInt(working.get(i).getVariantAttribute("PointNumber")) < highestWayPoint ) {
					highestWayPoint = Integer.parseInt(working.get(i).getVariantAttribute("PointNumber"));
					index = i;
				}
			}
			workinger.push(working.get(index));
			working.remove(index);
		}
		presetPath = true;
		return workinger;
		} catch (NullPointerException e) {
			return new Stack <Point>();
		}
	}
}
