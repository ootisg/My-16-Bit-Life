package gameObjects;

import triggers.Checkpoint;

public class CheckpointSystem {
	private static Checkpoint current;
	
	public static Checkpoint getNewestCheckpoint () {
		return current;
	}
	public static void setNewestCheckpoint (Checkpoint newPoint) {
		current = newPoint;
	}
	public static void loadNewestCheckpoint () {
		
		current.load();
	}
}