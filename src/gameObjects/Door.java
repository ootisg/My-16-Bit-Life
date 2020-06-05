package gameObjects;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import cutsceens.Cutsceen;
import items.Item;
import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;

public class Door extends MapObject {
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
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
	public Door () {
		
	}
	@Override
	public void frameEvent () {
		System.out.println(open);
		if (!inizialized) {
			inizialized = true;
			if (this.getVariantAttribute("keyName") != null && !this.getVariantAttribute("keyName").equals("nv")) {
				keyName = this.getVariantAttribute("keyName");
			} else {
				keyName = "Key";
			}
			if (this.getVariantAttribute("startSceen") != null && !this.getVariantAttribute("startSceen").equals("nv")) {
				startSceen = new Cutsceen ("resources/cutsceenConfig/" + this.getVariantAttribute("startSceen"));
			}else {
				startSceen = null;
			}
			if (this.getVariantAttribute("endSceen") != null && !this.getVariantAttribute("endSceen").equals("nv")) {
				endSceen = new Cutsceen ("resources/cutsceenConfig/" + this.getVariantAttribute("endSceen"),new GameObject [] {this});
			} else {
				endSceen = null;
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
				Class<?> c;
				try {
					c = Class.forName("items." + keyName);
					Jeffrey.getInventory().removeItem((Item)c.getConstructor().newInstance());
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				open = true;
				this.reverseCollision();
			}
		}
		if (!open) {
		super.frameEvent();
			if (this.isColliding(j)) {
				try {
					Class <?> c = Class.forName("items." + keyName);
					if (Jeffrey.getInventory().checkKey((Item)c.getConstructor().newInstance())){
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
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
						if (endSceen != null) {
							if (!endPause) {
								if (!endSceen.play()) {
									endSceen = null;
								}
							} else {
								sceen = true;
								ObjectHandler.pause(true);
								doStuff = true;
							}
					}  else {
						open = true;
						this.reverseCollision();
					}
				}
			}
		}
	}
	@Override 
	public void pausedEvent () {
		if (doStuff) {
			if (sceen) {
				if (!endSceen.play()) {
					endSceen = null;
					ObjectHandler.pause(false);
					doStuff = false;
					Class<?> c;
					try {
						c = Class.forName("items." + keyName);
						Jeffrey.getInventory().removeItem((Item)c.getConstructor().newInstance());
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
	public void setKeyType (String keyType) {
		this.keyName = keyType;
	}
}
