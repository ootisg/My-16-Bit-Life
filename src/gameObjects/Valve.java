package gameObjects;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import main.GameObject;
import players.Jeffrey;

public class Valve extends GameObject {
	int waterLevel;
	BubbleWater waterToManage;
	boolean inzialized = false;
	PairingObject working;
	public Valve () {
		this.setHitboxAttributes(0,0, 10, 32);
		this.adjustHitboxBorders();
	}
	@Override
	public void frameEvent () {
		if (!inzialized) {
			int count = 0;
			if (this.isColliding("PairingObject")) { 
				working = (PairingObject) this.getCollisionInfo().getCollidingObjects().get(0);
				ArrayList <PairingObject> pairedList = working.getPairedParingObjects();
				PairingObject pairedLevel = pairedList.get(0);
				working = pairedLevel;
				waterLevel = (int) pairedLevel.getY();
			}
			while (true) {
				count = count + 1;
				working.setY(working.getY() + 1);
				if (working.isColliding("BubbleWater")) {
					waterToManage = (BubbleWater) working.getCollisionInfo().getCollidingObjects().get(0);
					break;
				}
				if (count == 10000) {
					System.out.println("bruh you diden't place the valve pairingObject over a bubble water");
					waterToManage = null;
					break;
				}
			}
			inzialized = true;
		}
		if (this.isColliding(Jeffrey.getActiveJeffrey()) && keyPressed(KeyEvent.VK_ENTER)){
			waterToManage.setWaterLevel(waterLevel);
		}
	}	
}
