package items;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

import gui.Gui;
import gui.ListTbox;
import gui.Tbox;
import gui.Textbox;
import main.GameCode;
import main.ObjectHandler;
import main.RenderLoop;
import map.Room;
import players.Jeffrey;
import projectiles.DirectionBullet;
import projectiles.Projectile;
import resources.Sprite;

public class Item extends Projectile {
	Boolean activeBox = false;
	ListTbox box;
	LemonPacket packet;
	double direction;
	double speed;
	int timer = 0;
	boolean unaturally;
	protected BufferedImage img;
	private double rotation;
	private double renderedRotation;
	static Tbox tbox = new Tbox();
	boolean maganatized = false;
	public Item (Sprite sprite) {
		this.setHitboxAttributes(0, 0, 8, 8);
		this.keep();
		img = new BufferedImage (sprite.getFrame (0).getWidth (), sprite.getFrame (0).getHeight (), sprite.getFrame (0).getType ());
	}
	public Item () {
		this.setHitboxAttributes(0, 0, 8, 8);
		this.keep();
		this.setSpeed(0);
	}
	/*public Item () {
		Class<?> c = this.getClass ();
		try {
			setSprite (new Sprite ("resources/sprites/" + c.getSimpleName () + ".png"));
		} catch (Exception e) {
			return;
		}
	} */
	//override to set effect
	public void useItem(int witchCharictar) {
		Textbox box;
		box = new Textbox("YOU CANT USE THAT");
		box.changeUnpause();
		box.unfrezeMenu();
		box.declare(100,120);
	}
	public String checkName () {
		return "";	
	}
	public Item getAmmoType () {
		return null;
	}
	public int getAmmoAmount() {
		return 0;
	}
	/**
	 * run whenver weapons are switch overriden in wepon classes (if nessasary)
	 */
	public void onSwitch () {
		
	}
	//override to set entry
	public String checkEnetry() {
		return "";
	}
	//overriden in classes to clarify what type of item the item is
	public String getItemType() {
		return "";
	}
	//override in weapon classes
	public String [] getUpgrades () {
		String [] returnArray;
		returnArray = new String [] {"DEFULT", "DEFULT", "DEFULT", "DEFULT"};
		return returnArray;
	}
	//override in weapon classes
	public int [] getTierInfo () {
		int [] returnArray;
		returnArray = new int [] {0,0,0,0};
		return returnArray;
	}
	public void allwaysRunItemStuff (int witchCharitar) {
		if (Jeffrey.getActiveJeffrey().checkIfSomeoneIsLemoney(witchCharitar) && !activeBox && this.getItemType().equals("Consumable") && !this.checkName().equals("LEMON PACKET")) {
			Tbox twobox2furios;
			activeBox = true;
			packet = new LemonPacket ();
			if (Jeffrey.getInventory().checkConsumable(packet)) {
			twobox2furios = new Tbox (100, 80, 24, 8, "HE DOESEN'T WANT THAT HE WANTS LEMON PACKETS HE WILL ONLY EAT IT IF HE CAN HAVE LEMON PACKETS WITH IT", true);
			twobox2furios.setScrollRate(0);
			box = new ListTbox (290,400, new String []{"WHATEVER TAKE IT DUDE", "SCREW THAT"});
			} else {
				twobox2furios = new Tbox (100, 80, 24, 8, "HE DOESEN'T WANT THAT HE WANTS LEMON PACKETS HE WILL ONLY EAT IT IF HE CAN HAVE LEMON PACKETS WITH IT BUT YOU DONT HAVE ANY SO YOUR OUTTA LUCK", true);
				twobox2furios.setScrollRate(0);
				Gui.getGui().menu.frozen = false;
				activeBox = false;
			}
			twobox2furios.declare();
		} 
		try {
			if(box.getSelected() == 0) {
				this.useItem(witchCharitar);
				packet.useItem(witchCharitar);
				Jeffrey.getInventory().removeItem(this);
				Jeffrey.getInventory().removeItem(packet);
				box.close();
			}
			if (box.getSelected() == 1) {
				Gui.getGui().menu.frozen = false;
				activeBox = false;
				box.close();
			}
		} catch (NullPointerException e) {
			
		}
		if (!this.getItemType().equals("Consumable") && !activeBox) {
			this.useItem(witchCharitar);
			activeBox = true;
		}
		if ((!Jeffrey.getActiveJeffrey().checkIfSomeoneIsLemoney(witchCharitar) || this.checkName().equals("LEMON PACKET")) && this.getItemType().equals("Consumable")) {
			this.useItem(witchCharitar);
			Jeffrey.getInventory().removeItem(this);
		}
	}
	@Override
	public void projectileFrame () {
		this.goY(this.getY() + 1);
		if (!maganatized) {
			if (Jeffrey.getActiveJeffrey().getX() < this.getX() + 50 && Jeffrey.getActiveJeffrey().getX() > this.getX() - 50 && Jeffrey.getActiveJeffrey().getY() < this.getY() + 50 && Jeffrey.getActiveJeffrey().getY() > this.getY() - 50) {
				maganatized = true;
			}
			if (this.getDirection() % 6.28 > 1.57 && this.getDirection() %6.28 < 4.7) {
				this.rotation = this.rotation - (this.getSpeed()/1800.0 + 0.001);
			} else {
				this.rotation = this.rotation + (this.getSpeed()/1800.0 + 0.002);
			}
			if (!checkIfGoingIntoWall()) {
			if (this.getSpeed() != 0) {
			if (this.getDirection() % 6.28 > 0 && this.getDirection() %6.28 < 3.14 && this.speed < 15) {
				this.setSpeed(this.getSpeed() + 0.2);
			} else {
				if (this.getSpeed() < 15) {
					this.setSpeed(this.getSpeed() -0.3);
					if (this.getSpeed() < 0) {
						this.setSpeed(0);
					}
					if (this.getDirection() > 4.7) {
						this.setDirection(this.getDirection() - 0.01);
					} else {
						this.setDirection(this.getDirection() + 0.01);
					}
				}
			}
			} else {
				if (!(this.getDirection() % 6.28 > 0 && this.getDirection() %6.28 < 3.14)) {
					this.setDirection(1.57);
					this.setSpeed(this.getSpeed() + 3);
				}
			}
			}else {
				if (timer == 0) {
				this.setSpeed(this.getSpeed()/4);
				}
				this.setSpeed(this.getSpeed() - 0.1);
				timer = timer + 1;
				this.goY( this.getY() + 3);
			}
			} else {
				if (!(Jeffrey.getActiveJeffrey().getX() < this.getX() + 50 && Jeffrey.getActiveJeffrey().getX() > this.getX() - 50 && Jeffrey.getActiveJeffrey().getY() < this.getY() + 50 && Jeffrey.getActiveJeffrey().getY() > this.getY() - 50)) {
					maganatized = false;
				}
				DirectionBullet bullet = new DirectionBullet (this.getX(),this.getY());
				this.setDirection(bullet.findDirection(Jeffrey.getActiveJeffrey()));
				this.setSpeed(5);
			}
		if (this.isColliding(Jeffrey.getActiveJeffrey()) && !this.getClass().getSimpleName().equals("Item")) {
			if (tbox.declared()) {
				String [] words = tbox.getContent().split(" ");
				int length = 0;
				boolean broke = false;
				for (int i = 0; i <words.length; i++) {
					 length = length + words[i].length() + 1;
					 if (words[i].equals(this.checkName())) {
						 
						 tbox.setContent(tbox.getContent().substring(0, length) + "X " + Integer.toString (Integer.parseInt(words[i + 2]) + 1) + tbox.getContent().substring(length + words[i + 1].length() + words[i+2].length() + 1) );
						 broke = true;
						 break;
					 }
				 }
				if (!broke) {
					tbox.setContent(tbox.getContent() + " AND A " + this.checkName() + " X 1");
				}
			} else {
				tbox = new Tbox (Jeffrey.getActiveJeffrey().getX(), Jeffrey.getActiveJeffrey().getY() - 8, 28, 2, "YOU GOT A " + this.checkName() + " X 1", true);
			}
			this.onPickup();
			this.forget();
		}
		}
	@Override
	public void draw () {
		if (img != null) {
		if (rotation != renderedRotation) {
			
			BufferedImage startImg = this.getSprite().getFrame (0);
			AffineTransform transform = new AffineTransform ();
			transform.rotate (rotation, 4, 4);
			AffineTransformOp operation = new AffineTransformOp (transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			img = new BufferedImage (this.getSprite().getFrame (0).getWidth (), this.getSprite().getFrame (0).getHeight (), this.getSprite().getFrame (0).getType ());
			operation.filter (startImg, img);
			this.setSprite (new Sprite (img));
			renderedRotation = rotation;
		}
		if (this.hiboxBorders) {
			if (this.hitbox() != null) {
				Graphics g = RenderLoop.window.getBufferGraphics();
				g.setColor(new Color(0xFFFFFF));
				g.drawRect((int)(this.getX() + this.getHitboxXOffset() - Room.getViewX()),(int) (this.getY() + this.getHitboxYOffset() - Room.getViewY()), this.hitbox().width, this.hitbox().height);
				}
			}
		getSprite ().draw ((int) getX () - Room.getViewX (), (int) getY () - Room.getViewY (), getAnimationHandler ().flipHorizontal (), false, 0);
		} else {
			super.draw();
		}
	}
 	//overriden in AimableWeapon
	public Sprite getUnrotatedSprite () {
		return this.getSprite();
	}
	public Item clone () {
		Class <?> working = this.getClass();
		try {
			return (Item) working.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	//override to specify different behavior
	public void onPickup() {
		Jeffrey.inventory.addItem(this);
	}
	//override to specify a behavior
	public void onRemove () {
		
	}
}
