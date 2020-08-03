package gameObjects;

import java.util.ArrayList;

import cutsceens.Cutsceen;
import items.Item;
import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;

public class ItemBox extends GameObject {
		private boolean open;
		private Cutsceen cutscene;
		private ArrayList <Item> item = new ArrayList<Item> ();
		private boolean inzialized = false;
		private boolean playing = false;
		public ItemBox () {
			this.setHitboxAttributes(0, 0, 32, 16);
			this.setSprite(new Sprite ("resources/sprites/chestClosed.png"));
		}
		@Override
		public void frameEvent () {
			if (!this.inzialized) {
				if (this.getVariantAttribute("cutsceen") != null ) {
					if (!this.getVariantAttribute("cutsceen").equals("nv")) {
						this.isColliding("PairingObject");
						if (this.getCollisionInfo().getCollidingObjects().size() != 0) {
							PairingObject obj = (PairingObject) this.getCollisionInfo().getCollidingObjects().get(0);
							GameObject [] working = new GameObject [obj.getPairedPairedObjects().size()];
							for (int i =0 ; i<working.length; i++) {
								working[i] = obj.getPairedPairedObjects().get(i);
							}
							cutscene = new Cutsceen (this.getVariantAttribute("cutsceen"), working);
						} else {
							cutscene = new Cutsceen (this.getVariantAttribute("cutsceen"));
						}
					}
				}
				this.isCollidingChildren("Item");
				for (int i = 0; i < this.getCollisionInfo().getCollidingObjects().size(); i++) {
					Item working = (Item)this.getCollisionInfo().getCollidingObjects().get(i);
					item.add(working);
					working.forget();
				}
			}
			if (this.isColliding(Jeffrey.getActiveJeffrey()) && !open && keyDown (10)) {
				open = true;
				if (cutscene == null) {
					this.onOpen();
				} else {
					playing = true;
				}
			}
			if (playing) {
				if (!cutscene.play()) {
					playing = false;
					this.onOpen();
				}
			}
		}
		private void onOpen () {
			this.setSprite(new Sprite ("resources/sprites/chestOpen.png"));
			for (int i = 0; i < item.size(); i ++) {
				double randX = Math.random() * 10;
				double randY = Math.random() * 10;
				if (Math.random() > 0.5) {
					randX = randX * -1;
				}
				item.get(i).declare(this.getX() + randX, this.getY() - randY);
			}
		}
}
