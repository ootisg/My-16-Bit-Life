package items;

import resources.Sprite;

public class CashMoney extends Item {
	public CashMoney () {
		this.setSprite(new Sprite ("resources/sprites/cashMoney.png")); 
		this.setHitboxAttributes(0, 0, 9, 5);
	}
	@Override
	public String checkName () {
		return "CASH MONEY";
	}
	@Override 
	public String getItemType() {
		return "Key";
	}
	@Override
	public String checkEnetry() {
		return "CASH MONEY BABY!";
	}
}
