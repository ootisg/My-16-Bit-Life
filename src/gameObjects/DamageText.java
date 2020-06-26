package gameObjects;

import main.GameObject;
import map.Room;
import resources.AfterRenderDrawer;
import resources.Sprite;


public class DamageText extends GameObject {
	int momentum = 0;
	public Sprite damageText = new Sprite ("resources/sprites/config/damage_text.txt"); 
	private int x;
	private int y;
	private int damageFrame = 0;
	//false for red true for green
	public DamageText (double amount, double d, double e, boolean color){
		if (color == false) {
			damageText = new Sprite ("resources/sprites/config/damage_text.txt");
		} else {
			damageText = new Sprite ("resources/sprites/config/healing_text.txt");
		}
		DamageText secondidget;
		int timesAmount = 1; 
		this.setY(this.getY() - 12);
		if (this.getX() < 508){
		this.setX(this.getX() + 4);
		}
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
			secondidget = new DamageText (amountOfZeros, copyOfamount -(frameToSet * copyOfTimesAmount), d + 8, e);
			secondidget.declare(d, e);
		} else {
		copyOfamount = copyOfamount - frameToSet * timesAmount;
		secondidget = new DamageText (copyOfamount, d + 8, e);
		secondidget.declare(d, e);
		}
		}
		x = (int) d;
		y = (int) e;
	}
	public DamageText (double amount, double d, double e){
		DamageText secondidget;
		int timesAmount = 1; 
		this.setY(this.getY() - 12);
		if (this.getX() < 508){
		this.setX(this.getX() + 4);
		}
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
			secondidget = new DamageText (amountOfZeros, copyOfamount -(frameToSet * copyOfTimesAmount), d + 8, e);
			secondidget.declare(d, e);
		} else {
		copyOfamount = copyOfamount - frameToSet * timesAmount;
		secondidget = new DamageText (copyOfamount, d + 8, e);
		secondidget.declare(d, e);
		}
		}
		x = (int) d;
		y = (int) e;
	}
	public DamageText(double numOfZeros, double endNum, double d, double e) {
		DamageText secondidget;
		if (numOfZeros != 1) {
			damageFrame = 0;
			secondidget = new DamageText (numOfZeros - 1, endNum, d + 8, e);
			secondidget.declare(d, e);
		} else {
			damageFrame = (int)endNum;
		}
		x = (int) d;
		y = (int) e;
	}
	@Override
	public void frameEvent(){
		AfterRenderDrawer.drawAfterRender((x- Room.getViewX()), y - Room.getViewY(), damageText, damageFrame);
		momentum = momentum + 1;
		if (momentum < 6){
			y = y + 2;
		}
		if (momentum > 6 && momentum < 12){
			y = y + 4;
		}
		if (momentum > 6 && momentum < 18){
			y = y + 6;
		}
		if (momentum > 18 && momentum < 24){
			y = y + 8;
		}
		if (momentum > 24){
			y = y + 10;
		}	
		if (y > 512){
			this.forget();
		}
	}
}
