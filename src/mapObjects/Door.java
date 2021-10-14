package mapObjects;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import cutsceens.Cutsceen;
import items.Item;
import main.GameObject;
import main.ObjectHandler;
import players.Player;
import resources.Sprite;
import switches.Activateable;

public class Door extends MapObject implements Activateable {
	boolean open = false;
	boolean inizialized = false;
	String keyName;
	Cutsceen startSceen;
	Cutsceen endSceen;
	boolean sceen = false;
	boolean startPause;
	boolean endPause;
	boolean endPlaying = false;
	boolean startPlaying = false;
	boolean doStuff = false;
	
	boolean paired = false;
	boolean queened = false;
	boolean active = false;
	
	public Door () {
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	//start scene usally refers to the cutscene that plays when you don't have the key to open the door howevver if using this with a switch
	// it instead represents the cutscene that plays when the door closes
	@Override
	public void frameEvent () {
		super.frameEvent();
		if (!inizialized) {
			inizialized = true;
			if (this.getVariantAttribute("keyName") != null && !this.getVariantAttribute("keyName").equals("nv")) {
				keyName = this.getVariantAttribute("keyName");
			} else {
				keyName = "Key";
			}
			if (this.getVariantAttribute("startSceen") != null && !this.getVariantAttribute("startSceen").equals("nv")) {
				startSceen = new Cutsceen ("resources/cutsceenConfig/" + this.getVariantAttribute("startSceen"),new GameObject [] {this} );
			}else {
				startSceen =  new Cutsceen ("resources/cutsceenConfig/DoorDown.txt",new GameObject [] {this});
			}
			if (this.getVariantAttribute("endSceen") != null && !this.getVariantAttribute("endSceen").equals("nv")) {
				endSceen = new Cutsceen ("resources/cutsceenConfig/" + this.getVariantAttribute("endSceen"),new GameObject [] {this});
			} else {
				endSceen = new Cutsceen ("resources/cutsceenConfig/DoorUp.txt",new GameObject [] {this});
			}
			try {
				if (this.getVariantAttribute("startPause").equals("yes")) {
					startPause = true;
				} else {
					startPause = false;
				}
			} catch (NullPointerException e) {
				startPause = false;
			}
			try {
				if (this.getVariantAttribute("endPause").equals("yes")) {
					endPause = true;
				} else {
					endPause = false;
				}
			} catch (NullPointerException e) {
				endPause = false;
			}
			if (this.getVariantAttribute("sprite") != null && !this.getVariantAttribute("sprite").equals("nv")) {
				this.setSprite(new Sprite ("resources/sprites/" + this.getVariantAttribute("sprite")));
				this.setHitboxAttributes(0, 0, this.getSprite().getWidth(), this.getSprite().getHeight());
			} else {
				this.setSprite( new Sprite ("resources/sprites/Door.png"));
				this.setHitboxAttributes(0, 0, this.getSprite().getWidth(), this.getSprite().getHeight());
			}
			
		}
		if (!paired) {
			if (startPlaying && startSceen != null) {
				if (!startSceen.play()) {
					startSceen = null;
					startPlaying = false;
				}
			}
			if (endPlaying && endSceen != null) {
				if (!endSceen.play()) {
					endSceen = null;
					endPlaying = false;
					Player.getInventory().removeItem(keyName);
					open = true;
					this.reverseCollision();
				}
			}
			if (!open) {
			super.frameEvent();
				if (this.isColliding(Player.getActivePlayer())) {
						if (Player.getInventory().checkItem(keyName)){
								if (endSceen != null) {
									if (!endPause) {
										endPlaying = true;
									} else {
										sceen = true;
										ObjectHandler.pause(true);
										doStuff = true;
									}
								} 
							} else {
								if (startSceen != null) {
									if (!startPause) {
										startPlaying = true;
									} else {
										sceen = false;
										ObjectHandler.pause(true);
										doStuff = true;
									}
								} 
							}
						}
					}
		} else {
			if (endPlaying) {
				if (!endSceen.play()) {
					endSceen = null;
					open = true;
					if (queened) {
						queened = false;
						startPlaying = true;
					}
					endPlaying = false;
					if (this.getVariantAttribute("startSceen") != null && !this.getVariantAttribute("startSceen").equals("nv")) {
						startSceen = new Cutsceen ("resources/cutsceenConfig/" + this.getVariantAttribute("startSceen"),new GameObject [] {this});
					}else {
						startSceen = new Cutsceen ("resources/cutsceenConfig/DoorDown.txt",new GameObject [] {this});
					}
					//this.reverseCollision();
				}
			}
			if (startPlaying) {
				if (!startSceen.play()) {
					startSceen = null;
					open = false;
					if (queened) {
						queened = false;
						endPlaying = true;
					}
					startPlaying = false;
					if (this.getVariantAttribute("endSceen") != null && !this.getVariantAttribute("endSceen").equals("nv")) {
						endSceen = new Cutsceen ("resources/cutsceenConfig/" + this.getVariantAttribute("endSceen"),new GameObject [] {this});
					} else {
						endSceen  = new Cutsceen ("resources/cutsceenConfig/DoorUp.txt",new GameObject [] {this});
					}
					//this.reverseCollision();
				} 
			}
		}
	}
	@Override 
	public void pausedEvent () {
		if (!paired) {
			if (doStuff) {
				if (sceen) {
					if (!endSceen.play()) {
						endSceen = null;
						ObjectHandler.pause(false);
						doStuff = false;
						Class<?> c;
						try {
							c = Class.forName("items." + keyName);
							Player.getInventory().removeItem((Item)c.getConstructor().newInstance());
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
						open = true;
						this.reverseCollision();
					}
				} else {
					if (!startSceen.play()) {
						startSceen = null;
						ObjectHandler.pause(false);
						doStuff = false;
					}
				}
			}
		}
	}
	public void setKeyType (String keyType) {
		this.keyName = keyType;
	}
	@Override
	public void activate() {
		active = true;
		queened = false;
		if (!startPlaying) {
			endPlaying = true;
		} else {
			queened = true;
		}
	}
	@Override
	public void deactivate() {
		active = false;
		queened = false;
		if (!endPlaying) {
			startPlaying = true;
		} else {
			queened = true;
		}
	}
	@Override
	public boolean isActivated() {
		return active;
	}
	@Override
	public void pair() {
		paired = true;
	}
}
