package gameObjects;

import java.util.ArrayList;

import main.AnimationHandler;
import main.GameObject;
import map.Room;
import resources.AfterRenderDrawer;
import resources.Sprite;


public class DamageText extends GameObject {
	int momentum = 0;
	public Sprite damageText = new Sprite ("resources/sprites/config/damage_text.txt"); 
	private int damageFrame = 0;
	int id = 0;
	int num = 0;
	static int publicId = 1;
	double opacity = 1.0;
	DamageText nextDidget;
	DamageText prevDidget;
	int color = 0; // 0 for red 1 for green
	
	public DamageText (double amount, double d, double e, int color){
		
		this.setHitboxAttributes(32, 32);
		
		double amountCopy = amount;
		
		num = (int) amount;
		
		double scaledSize = (16 * (amount/20.0));
		
		if (color == 0) {
			damageText = new Sprite ("resources/sprites/config/damage_text.txt");
		} else {
			damageText = new Sprite ("resources/sprites/config/healing_text.txt");
			this.color = 1;
		}
		
		
		DamageText secondidget;
		int timesAmount = 1; 
		this.setY(this.getY() - 12);
		
		if(amount < 10){
			damageFrame = (int)amount;
		} else {
		double copyOfamount = amount; 
		while (amount > 9) {
			amount = amount/10;
			timesAmount = timesAmount * 10;
		}
		int frameToSet = (int) (Math.floor(amount));
		damageFrame = frameToSet;
		if (frameToSet * timesAmount > 10 && copyOfamount -  frameToSet * timesAmount < 10) {
			int amountOfZeros = 0;
			int copyOfTimesAmount = timesAmount;
			while (timesAmount != 1) {
				timesAmount = timesAmount/10;
				amountOfZeros = amountOfZeros + 1;
			}
			secondidget = new DamageText (amountOfZeros, copyOfamount -(frameToSet * copyOfTimesAmount), d + scaledSize , e, amountCopy);
			
			nextDidget = secondidget;
			secondidget.prevDidget = this;
			
			secondidget.declareIntermedite(d  + scaledSize + 8, e);
			
		} else {
			
		copyOfamount = copyOfamount - frameToSet * timesAmount;
		
		secondidget = new DamageText (copyOfamount, d + scaledSize , e);
		secondidget.scale(scaledSize + 8);
		secondidget.num = (int)amountCopy;
		secondidget.declareIntermedite(d + scaledSize + 8, e);
		
		nextDidget = secondidget;
		secondidget.prevDidget = this;
		
		
		}
		}
		
		x = (int) d;
		y = (int) e;
		
		
		
		this.scale((int) (16 * (amountCopy/20.0) + 8));

	}
	public DamageText (double amount, double d, double e){
		
		this.setHitboxAttributes(32, 32);
		double amountCopy = amount;
		
		DamageText secondidget;
		num = (int) amount;
		int timesAmount = 1; 
		this.setY(this.getY() - 12);
		if(amount < 10){
			damageFrame = (int)amount;
		} else {
		double copyOfamount = amount; 
		while (amount > 9) {
			amount = amount/10;
			timesAmount = timesAmount * 10;
		}
		
		double scaledSize = (16 * (amount/20.0));
		
		int frameToSet = (int) (Math.floor(amount));
		damageFrame = frameToSet;
		if (frameToSet * timesAmount > 10 && copyOfamount -  frameToSet * timesAmount < 10) {
			int amountOfZeros = 0;
			int copyOfTimesAmount = timesAmount;
			while (timesAmount != 1) {
				timesAmount = timesAmount/10;
				amountOfZeros = amountOfZeros + 1;
			}
			
			secondidget = new DamageText (amountOfZeros, copyOfamount -(frameToSet * copyOfTimesAmount), d + scaledSize, e, amountCopy);
			secondidget.num = (int) amountCopy;
			nextDidget = secondidget;
			secondidget.prevDidget = this;
			
			secondidget.declareIntermedite(d + scaledSize + 8, e);
			
		} else {
		copyOfamount = copyOfamount - frameToSet * timesAmount;
		
		secondidget = new DamageText (copyOfamount, d + scaledSize, e);
		secondidget.num = (int) amountCopy;
		nextDidget = secondidget;
		secondidget.prevDidget = this;
		
		secondidget.declareIntermedite(d + scaledSize + 8, e);
		
		}
		}
		
		x = (int) d;
		y = (int) e;
		
		
		this.scale(((16 * (amountCopy/20.0) + 8)));

	}
	public DamageText(double numOfZeros, double endNum, double d, double e, double amount) {
		this.setHitboxAttributes(32, 32);
		double scaledSize = (16 * (amount/20.0));
		
		double amountCopy = amount;
		
		DamageText secondidget;
		if (numOfZeros != 1) {
			damageFrame = 0;
			secondidget = new DamageText (numOfZeros - 1, endNum, d + scaledSize , e, amountCopy);
			secondidget.declareIntermedite(d + scaledSize + 8, e);
			nextDidget = secondidget;
			secondidget.prevDidget = this;
			
			secondidget.num = (int)amountCopy;
		} else {
			damageFrame = (int)endNum;
		}
		
		x = (int) d;
		y = (int) e;
		
		
		AnimationHandler scaler = new AnimationHandler (damageText);
		
		scaler.scale((int)((16 * (amountCopy/20.0) + 8)),(int) (16 * (amountCopy/20.0) + 8));

	}
	public void scale (double scaleVal) {
	 	Sprite.scale(damageText,(int)(scaleVal),(int) (scaleVal));

	}
	public void declareIntermedite (double x,double y) {
		this.id = publicId;
		super.declare(x, y);
		
	}
	@Override
	public void onDeclare () {
		if (id == 0) {
			id = publicId;
			publicId = publicId + 1;
		}
	}
	@Override
	public void frameEvent(){
		opacity = opacity - 0.01;
		if (opacity <= .7) {
			this.forget();
		} else {
			//System.out.println(this.getAnimationHandler().getFrame());
			damageText.setOpacity((float)opacity, damageFrame);
		}
		if (this.isColliding("DamageText")) {
		
			int tempNum = this.num;
			
			ArrayList <GameObject> check = this.getCollisionInfo().getCollidingObjects();
			ArrayList <Integer> comboIDs = new ArrayList <Integer>();
			
			for (int i = 0; i < check.size(); i++) {
				DamageText working = (DamageText) check.get(i);
				if ((working.id != this.id || !comboIDs.isEmpty()) && working.color == this.color) {
					
					if (comboIDs.isEmpty()) {
						comboIDs.add(working.id);
						tempNum = tempNum + working.num;
						i = -1;
						
						while (working.prevDidget != null) {
							working = working.prevDidget;
						}
						while (working != null) {
							working.forget();
							working = working.nextDidget;
						}
					} else {
						
						if (!comboIDs.contains(working.id) && working.id != this.id) {
							comboIDs.add(working.id);
							tempNum = tempNum + working.num;
						}
						
						while (working.prevDidget != null) {
							working = working.prevDidget;
						}
						while (working != null) {
							working.forget();
							working = working.nextDidget;
						}
						
					}
				}
			}
			if (tempNum != this.num) {
				DamageText working = new DamageText (tempNum, this.getX(), this.getY(), this.color);
				working.declare(this.getX(), this.getY());
				this.forget();
			}
		}
		AfterRenderDrawer.drawAfterRender((int)(x- Room.getViewX()), (int)(y - Room.getViewY()), damageText, damageFrame);
	}
}
