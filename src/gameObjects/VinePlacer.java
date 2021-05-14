package gameObjects;

import java.util.ArrayList;

import main.GameObject;
import main.ObjectHandler;

public class VinePlacer extends GameObject{

	public VinePlacer () {
		
	}
	
	@Override
	public void frameEvent ()
	{
		String pairableValue = this.getVariantAttribute("partner");
		
		ArrayList <GameObject> bottoms = ObjectHandler.getObjectsByName("VinePlacer");
		
		for (int i = 0; i < bottoms.size(); i++) {
			GameObject obj = bottoms.get(i);
			if (obj.getVariantAttribute("partner").equals(pairableValue) && !this.equals(obj)) {
				SwingingVine vine = new SwingingVine((int) Math.abs(this.getY() - obj.getY()) );
				obj.forget();
				this.forget();
				if (obj.getY() > this.getY()) {
					vine.declare(this.getX(), this.getY());
				} else {
					vine.declare(bottoms.get(i).getX(), bottoms.get(i).getY());
				}
				return;
			}
		}
		
	}
	
}
