package theHeist;

import main.ObjectHandler;
import resources.Sprite;

public class Worker extends CheckableObject {
	boolean itemGot = false;
	public Worker () {
		this.setHitboxAttributes(0, 32, 16, 16);
		this.setSprite(new Sprite ("resources/sprites/the-heist/leners.png"));
		this.adjustHitboxBorders();
	}
	@Override
	public void onCheck () {
		Xavier x = (Xavier) ObjectHandler.getObjectsByName("Xavier").get(0);
		this.writeText("HOW CAN I HELP YOU");
		switch (x.getIQ()){
			case 0:
				this.makeOptions(new String [] {"I AM HERE TO STEAL THE TRAYS .... I MEAN WAIT CRAP", "CAN I HAVE A FROSTY"});
				break;
			case 1:
				this.makeOptions(new String [] {"I AM HERE TO STEAL THE TRAYS .... I MEAN WAIT CRAP","CAN I HAVE A FROSTY", "IM HERE TO \" NOT \" STEAL THE TRAYS WIIIIIIIIIIIIIIIIIINK"});
				break;
			case 2:
				this.makeOptions(new String [] {"I AM HERE TO STEAL THE TRAYS .... I MEAN WAIT CRAP","CAN I HAVE A FROSTY", "IM HERE TO \" NOT \" STEAL THE TRAYS WIIIIIIIIIIIIIIIIIINK","ILL TAKE A WATER, AND NOTHING ELSE"});
				break;
			case 3:
				this.makeOptions(new String [] {"I AM HERE TO STEAL THE TRAYS .... I MEAN WAIT CRAP","CAN I HAVE A FROSTY", "IM HERE TO \" NOT \" STEAL THE TRAYS WIIIIIIIIIIIIIIIIIINK","ILL TAKE A WATER, AND NOTHING ELSE","ILL TAKE A FRY PLEASE"});
				break;
		} 
	}
	@Override
	public void runOption1 () {
		this.writeText("OK WELL WE DON'T SUPPORT THAT KIND OF BEHAVIOR YOUR GONNA HAVE TO COME WITH ME");
		//leads to dungeon
	}
	@Override
	public void runOption2 () {
		this.writeText("WELL THE FUNNY THING IS THE ICE CREAM MACHINE IS KINDA BROKEN BUT WE ARE GIVING OUT A COMPLMENTRY TRAY FOR YOUR TROUBLE");
		
	}
	@Override
	public void runOption3 () {
		this.writeText("OK THEN?");
	}
	@Override
	public void runOption4 () {
		if (!itemGot) {
			Xavier x = (Xavier) ObjectHandler.getObjectsByName("Xavier").get(0);
			this.writeText("HERE YOU GO");
			itemGot = true;
			x.giveItem(new Item ("Water",new Sprite ("resources/sprites/the-heist/water.png")));
		} else {
			this.writeText("SORRY OUR MANAGER WAS COMPLAINIG ABOUT HOW WE WERE MAKEING TOO MUCH MONEY SO WE LIMITED IT TOO ONE ITEM PER CUSTOMER");
		}
	}
	@Override
	public void runOption5 () {
		
		if (!itemGot) {
			Xavier x = (Xavier) ObjectHandler.getObjectsByName("Xavier").get(0);
				if (x.hasItem("Coin")) {
					this.writeText("THANK YOU COME AGAIN");
					itemGot = true;
					x.giveItem(new Item ("Fry",new Sprite ("resources/sprites/the-heist/fry.png")));
				} else {
					this.writeText("SORRY THAT COSTS ONE COIN AND I CAN TELL JUST BY LOOKING AT YOU, YOU DONT HAVE IT");
				}
		} else {
			this.writeText("SORRY OUR MANAGER WAS COMPLAINIG ABOUT HOW WE WERE MAKEING TOO MUCH MONEY SO WE LIMITED IT TOO ONE ITEM PER CUSTOMER");
		}
	}
	
}
