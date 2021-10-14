package gameObjects;

import java.util.Iterator;

import main.GameObject;
import main.ObjectHandler;
import main.RenderLoop;
import map.Room;
import players.Player;
import switches.Activateable;

public class CameraObject extends GameObject implements Activateable {
	boolean inControl = false;
	int panTime;
	boolean panning = false;
	private int endX;
	private int endY;
	private int startX;
	private int startY;
	public CameraObject () {
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override
	public void onDeclare () {
		if (this.getVariantAttribute("panTime") != null) {
			if (!this.getVariantAttribute("panTime").equals("nv")) {
				panTime = Integer.parseInt(this.getVariantAttribute("panTime"));
			}
		} else {
			panTime = 0;
		}
	}
	@Override
	public void frameEvent () {
		if (inControl) {
			if (Room.getViewX() != endX || Room.getViewY() != endY) {
				panning = true;
				startX = Room.getViewX();
				startY = Room.getViewY();
				endX = (int) this.getX();
				endY = (int) this.getY();
			}
			if (panning) {
				if (panTime != 0) {
					this.panLogic();
				} else {
					Room.setView((int)this.getX(), (int)this.getY());
					panning = false;
				}
			} else {
				startX = Room.getViewX();
				startY = Room.getViewY();
				endX = (int) this.getX();
				endY = (int) this.getY();
			}
		}
	}	

	public void panLogic () {
		double x = this.getX();
		double y = this.getY();
		int viewX;
		int viewY;
		viewY = (int) y - 320;
		endY = viewY;	
		viewX = (int) x - 427;
		endX = viewX;
		if (endX < 0) {
			endX = 0;
		}
		if (endY < 0) {
			endY = 0;
		}
		
		if (endX + RenderLoop.window.getResolution()[0] > Room.getWidth() * Room.TILE_WIDTH) {
			endX = Room.getWidth() * Room.TILE_WIDTH - RenderLoop.window.getResolution()[0];
		}
		if (endY + RenderLoop.window.getResolution()[1] > Room.getHeight() * Room.TILE_HEIGHT) {
			endY = (Room.getHeight() * Room.TILE_HEIGHT) - RenderLoop.window.getResolution()[1];
			
		}
		if (startX != endX) {
			if (startX > endX) {
				if (((startX - endX)/panTime) > 15) {
					startX = startX - ((startX - endX)/panTime);
				} else {
					startX = startX - 15;
				}
				if (startX <= endX) {
				
					startX = endX;
				}
			} else {
				if (((endX - startX)/panTime) > 15) {
					startX = startX + ((endX - startX)/panTime);
				} else {
					startX = startX + 15;
				}
				if (startX >= endX) {
					startX = endX;
				}
			}
			Room.setView(startX, Room.getViewY());
		} else {
			if (startY == endY) {
				panning = false;
			}
		}
		if (startY != endY) {
			if (startY > endY) {
				if (((startY - endY)/panTime) > 15) {
					startY = startY - ((startY - endY)/panTime);
				} else {
					startY = startY - 15;
				}
				if (startY <= endY) {
					startY = endY;
				}
			} else {
				if (((startY - endY)/panTime) > 15) {
					startY = startY+ ((endY - startY)/panTime);
				} else {
					startY = startY + 15;
				}
				if (startY >= endY) {
					startY = endY;
				}
			}
			Room.setView(Room.getViewX(), startY);
		}
	}
	@Override
	public void pausedEvent () {
		this.frameEvent();
	}
	
	public boolean inControl () {
		return inControl;
	}
	public void takeControl() {
		Iterator <GameObject> iter = ObjectHandler.getObjectsByName("CameraObject").iterator();
		while (iter.hasNext()) {
			CameraObject working = (CameraObject) iter.next();
			working.giveUpControlToOtherCameraObject();
		}
		Player.getActivePlayer().setScroll(false);
		inControl = true;
	}
	public void giveUpControl () {
		Player.getActivePlayer().setScroll(true);
		Player.getActivePlayer().inzializeCamera();
		inControl = false;
	}
	public void giveUpControlToOtherCameraObject () {
		inControl = false;
	}
	@Override
	public void activate() {
		if (!this.inControl) {
			this.takeControl();
		}
	}
	@Override
	public void deactivate() {
		if (this.inControl) {
			this.giveUpControl();
		}
	}
	@Override 
	public boolean isActivated () {
		return this.inControl;
	}
	@Override
	public void pair() {

	}
}
