package items;

import main.GameCode;
import main.GameObject;
import main.Tbox;
import main.Textbox;
import resources.Sprite;
import resources.Spritesheet;

public class DropableItem extends GameObject {
	//I'm not sure what you were doing exactly with getVariantData(), but that's gone so now you'll have to use getVariantAttribute() instead
	int amountToAdd;
	boolean coolconstructer = false;
	String type;
	Item itemToAdd;
	Sprite FUKey = new Sprite("resources/sprites/key.png");
	Sprite lemonPacket = new Sprite ("resources/sprites/Lemon_Packet.png");
	//the first constructor is used to spawn dropped objects in the map editor, use the other constructor to drop objects in code 
	public DropableItem () {
		// to add a new item (that can be dropped) write a name for it in the doc then copy and fill in information for your name (first substring is number of charictars second substring is that plus 1)
		// make sure to change sprite and hitbox too
		if (this.getVariantData().substring(0, 8).equals("moneyBag")) {
			this.setSprite(sprites.fallingBag); 
			amountToAdd = Integer.parseInt(this.getVariantData().substring(9));
			this.setHitboxAttributes(0, 0, 16, 15);
		}
		if (this.getVariantData().substring(0, 4).equals("RBPB")) {
			this.setSprite(sprites.paintball); 
			amountToAdd = Integer.parseInt(this.getVariantData().substring(5));
			this.setHitboxAttributes(0, 0, 4, 4);
			type = "ammo";
			itemToAdd = new RedBlackPaintBall (); 
		}
		if (this.getVariantData().substring(0, 4).equals("FUKey")) {
			this.setSprite(FUKey); 
			amountToAdd = Integer.parseInt(this.getVariantData().substring(5));
			this.setHitboxAttributes(0, 0, 9, 5);
			type = "key";
			itemToAdd = new FairUseKey (); 
		}
		if (this.getVariantData().substring(0, 4).equals("lemonPacket")) {
			this.setSprite(lemonPacket); 
			amountToAdd = Integer.parseInt(this.getVariantData().substring(5));
			this.setHitboxAttributes(0, 0, 32, 32);
			type = "consumable";
			itemToAdd = new LemonPacket (); 
		}
	}
	// ItemName = the name of the item in the doc I put on discord (number is not necessary)
	public DropableItem (String ItemName, int ItemAmount) {
		amountToAdd = ItemAmount;
		coolconstructer = true;
		type = "";
		if (ItemName.equals("moneyBag")) {
			this.setSprite(sprites.fallingBag);
			this.setHitboxAttributes(0, 0, 16, 15);
		}
		if (ItemName.equals("RBPB")) {
			this.setSprite(sprites.paintball);
			this.setHitboxAttributes(0, 0, 4, 4);
			type = "ammo";
			itemToAdd = new RedBlackPaintBall ();
		}
		if (ItemName.equals("FUKey")) {
			this.setSprite(FUKey); 
			amountToAdd = ItemAmount;
			this.setHitboxAttributes(0, 0, 9, 5);
			type = "key";
			itemToAdd = new FairUseKey (); 
		}
		if (ItemName.equals("lemonPacket")) {
			this.setSprite(lemonPacket); 
			amountToAdd = ItemAmount;
			this.setHitboxAttributes(0, 0, 32, 32);
			type = "consumables";
			itemToAdd = new LemonPacket (); 
		}
	}
	@Override
	public void frameEvent() {
		this.setY(this.getY() + 1);
		if (this.hitbox () != null) {
			if (!(room.isColliding(this.hitbox()))) {
				this.setY(this.getY() + 3);
			} else {
			this.setY(this.getY() - 1);
			if (this.getSprite().equals(sprites.fallingBag)){
				this.setSprite(sprites.idleBag);
			}
		}
		}
		if (this.isColliding(GameCode.testJeffrey)) {
			if (this.getSprite().equals(sprites.fallingBag) || this.getSprite().equals(sprites.idleBag)) {
				GameCode.testJeffrey.inventory.addMoney(amountToAdd);
				Tbox box = new Tbox (GameCode.testJeffrey.getX(), GameCode.testJeffrey.getY() - 8, 28, 1, "YOU GOT " + Integer.toString(amountToAdd) + " MONEY");
				this.forget();
			}
			if (type.equals("ammo")) {
				if (this.getSprite().equals(sprites.paintball)) {
				Tbox box = new Tbox (GameCode.testJeffrey.getX(), GameCode.testJeffrey.getY() - 8, 28, 1, "YOU GOT " + Integer.toString(amountToAdd) + " PAINTBALLS");
				while (amountToAdd != 0) {
				GameCode.testJeffrey.inventory.addAmmo(itemToAdd);
				amountToAdd = amountToAdd - 1;
				}
				}
				this.forget();
			}
			if (type.equals("key")) {
				if (this.getSprite().equals(this.FUKey)) {
				Tbox box = new Tbox (GameCode.testJeffrey.getX(), GameCode.testJeffrey.getY() - 8, 28, 1, "YOU GOT A FAIR USE KEY");
				GameCode.testJeffrey.inventory.addKeyItem(itemToAdd);
				}
				this.forget();
			}
			if (type.equals("consumables")) {
				if (this.getSprite().equals(this.lemonPacket)) {
					if (amountToAdd != 1) {
					Tbox box = new Tbox (GameCode.testJeffrey.getX(), GameCode.testJeffrey.getY() - 8, 28, 1, "YOU GOT " + Integer.toString(amountToAdd) + " LEMON PACKETS THEY LOOK VERY TANTALIZING");
					while (amountToAdd != 0) {
						GameCode.testJeffrey.inventory.addConsumable(itemToAdd);
						amountToAdd = amountToAdd - 1;
						}
					this.forget();
					} else {
						Tbox box = new Tbox (GameCode.testJeffrey.getX(), GameCode.testJeffrey.getY() - 8, 28, 1, "YOU GOT A LEMON PACKET");
						while (amountToAdd != 0) {
							GameCode.testJeffrey.inventory.addConsumable(itemToAdd);
							amountToAdd = amountToAdd - 1;
							}
						this.forget();
					}
				}
			}
		}
	}
}
