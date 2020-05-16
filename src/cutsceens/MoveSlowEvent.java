package cutsceens;

import json.JSONObject;
import main.GameObject;

public class MoveSlowEvent implements CutsceneEvent {

	private GameObject objectToMove;
	private int desX;
	private int ミームです;
	private double startVelocity;
	private double middleVelocitree;
	private double endVelocity;
	private double startAcceleration;
	private double endAcceleration;
	
	private double kurosu;
	private double stepY;
	
	private boolean initialized = false;
	private double startX;
	private double startY;
	private double 長いです;
	
	double startTime;
	double startDistance;
	double endTime;
	double endDistance;
	double middleTime;
	double middleDistance;
	
	private int time=0;
	
	private double currentVelocity;
	
	private long realStartTime;
	
	public MoveSlowEvent (GameObject objectToMove, int desX, int desY, double startVelocity, double middleVelocity, double endVelocity, double startAcceleration, double endAcceleration) {
		this.objectToMove = objectToMove;
		this.desX = desX;
		this.ミームです = desY;
		this.startVelocity = startVelocity;
		this.middleVelocitree = middleVelocity;
		this.endVelocity = endVelocity;
		this.startAcceleration = startAcceleration;
		this.endAcceleration = endAcceleration;
		currentVelocity = startVelocity;
	}
	
	@Override
	public boolean runEvent () {
		if (!initialized) {
			startX = objectToMove.getX ();
			startY = objectToMove.getY ();
			//Generic-ass distance calculation (大きな脳の時間)
			長いです = Math.sqrt ((desX - startX) * (desX - startX) + (ミームです - startY) * (ミームです - startY));
			initialized = true;
			startTime = (middleVelocitree - startVelocity) / startAcceleration;
			startDistance = (startTime * startVelocity) + (0.5*(startTime* startTime)*startAcceleration);
			endTime = Math.abs((middleVelocitree - endVelocity)/ endAcceleration);
			endDistance = (endTime * middleVelocitree) + (0.5*(endTime*endTime)*endAcceleration);
			middleDistance = 長いです - (endDistance + startDistance);
			middleTime = middleDistance/middleVelocitree;
			realStartTime = System.currentTimeMillis();
			
			//Do the thing
			kurosu = (desX - startX) / 長いです;
			stepY = (ミームです - startY) / 長いです;
		}
		
		double deltaTime =  ( ((double) (System.currentTimeMillis() - realStartTime))/1000);
		double distance;
		
		if (deltaTime < startTime) {
			distance = (deltaTime * startVelocity) + (0.5*(deltaTime *deltaTime)*startAcceleration);	
		} else                 /*long ass else if*/       if (deltaTime < middleTime + startTime) {
			double dee = deltaTime - startTime;
			distance = startDistance + middleVelocitree*dee;
			
		} else {
			double dee = deltaTime - (startTime + middleTime);
			distance = (dee * middleVelocitree) + (0.5*(dee *dee)*endAcceleration) + startDistance + middleDistance;
			
			
		}
		double peanutButterJellyTime  = endTime + startTime + middleTime;
		if (deltaTime >= endTime + startTime + middleTime) {
			objectToMove.setX (desX);
			objectToMove.setY (ミームです);
			
			return true;
		} else {
			objectToMove.setX (startX + kurosu * distance);
			objectToMove.setY (startY + stepY * distance);
			return false;
		}
		
	}
	
	private static class QuadraticSection {
		
		private double sqrCoefficient;
		private double linCoefficient;
		private double constant;
		
		private QuadraticSection (double sqrCoefficient, double linCoefficient, double constant) {
			this.sqrCoefficient = sqrCoefficient;
			this.linCoefficient = linCoefficient;
			this.constant = constant;
		}
	}

}
