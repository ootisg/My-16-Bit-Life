package fightingminigame;

import main.GameObject;
import map.Room;
import resources.Sprite;

public class FJeffrey extends Fighter {
	
	public FJeffrey() {
		this.setSprite(new Sprite("resources/sprites/config/jeffrey_walking.txt"));
		this.setHitbox(0, 0, 7, 27);
		startLags = new int []{0,0,0,0,0,0,0,0,0,0,0,0};
		endLags = new int []{0,0,0,0,0,0,0,0,0,0,0,0};
	}
	
	@Override
	public void frameEvent () {
		super.frameEvent();
	}
	
	@Override
	public void useNutralAir () {
		OrbitTriangle t= new OrbitTriangle (this);
		t.declare(this.getX(), this.getY() + 13);
	}
	
	@Override
	public void useUpG () {
		Watermelon m = new Watermelon (this);
		m.declare(this.getX(),this.getY() + 13);
	}
	
	@Override
	public void useUpAir () {
		Iceberg i = new Iceberg (this);
		i.declare(this.getX(),this.getY() + 13);
	}
	
	@Override
	public void useSideAir(boolean direciton) {
		Paintball p = new Paintball (this,direciton);
		p.declare(this.getX(),this.getY() + 7);
	}
	
	private class Iceberg extends FightingObject {
		double vy = -10;
		
		
		public Iceberg (Fighter user) {
			super (user);
			this.setSprite(new Sprite ("resources/sprites/ice brick.png"));
			this.setHitbox(0,0,15,14);
			this.reAllowMovement(7);
		}
		
		@Override
		public void frameEvent () {
			if (!this.goY(this.getY() + vy)) {
				this.forget();//TODO ice breaking animation
			}
			
			vy = vy + Room.getGravity();
		}
	}
	
	private class Paintball extends FightingObject {
		public static final double SPEED = 10.0;
		
		boolean direction;
		
		public Paintball (Fighter user, boolean direction) {
			super(user);
			this.setSprite(new Sprite ("resources/sprites/redblack_ball.png"));
			this.setHitbox(0,0,4,4);
			this.reAllowMovement(2);
			this.direction = direction;
		}
		
		@Override
		public void frameEvent () {
			if (direction) {
				this.setX(this.getX() - SPEED);
			} else {
				this.setX(this.getX() + SPEED);
			}
			if (this.getX() < 0 || this.getX() > (Room.getWidth()*16) ) {
				this.forget();
			}
		}
		
	}
	
	private class Watermelon extends FightingObject {
		double vy = -7.5;
		
		public Watermelon (Fighter user) {
			super (user);
			this.setSprite(new Sprite ("resources/sprites/watermellon.png"));
			this.setHitbox(0,0,25,21);
			this.reAllowMovement(10);
		}
		
		@Override
		public void frameEvent () {
			if (!this.goY(this.getY() + vy)) {
				this.forget();//TODO watermelon breaking animation
			}
			
			vy = vy + Room.getGravity();
		}
	}
	
	private class OrbitTriangle extends FightingObject {
		public static final double TRIANGLE_SPEED = .8;
		public static final int CIRCLE_RADIUS = 20;
		
		double curAngle = 0;
		
		public OrbitTriangle (Fighter toOrbit) {
			super(toOrbit);
			this.setSprite(new Sprite ("resources/sprites/config/stationary_ninja_triangle.txt"));
			this.setHitbox(0,0,16,16);
			this.reAllowMovement(5);
		}
		
		@Override
		public void frameEvent () {
			this.setX(this.getFreindly().getX() + (CIRCLE_RADIUS * Math.cos(curAngle)));
			this.setY(this.getFreindly().getY() + 13 + (CIRCLE_RADIUS * Math.sin(curAngle)));
			curAngle = curAngle + TRIANGLE_SPEED;
			if (curAngle > 6.28) {
				this.forget();
			}
			
			
		}
		
	}
	
}


