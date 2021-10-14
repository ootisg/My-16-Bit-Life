package enemys;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import main.GameObject;
import main.RenderLoop;
import map.Room;
import players.Player;
import resources.Sprite;

public class FishRing extends GameObject  {
	Fish [] fish = new Fish [7];
	Point [] veiw = new Point [fish.length + 1];
	
	double displacement = 0;
	
	public FishRing () {
		
	}
	@Override
	public void onDeclare() {
		for (int i = 0; i < fish.length; i++) {
			fish[i] = new Fish();
			fish[i].declare(this.getX() + 40 * (Math.cos((2 * Math.PI/(fish.length + 1)) * i) ), this.getY() + 40 * (Math.sin((2 * Math.PI/(fish.length + 1)) * i)));
			veiw[i] = new Point ((int)(this.getX() + 40 * (Math.cos((2 * Math.PI/(fish.length + 1)) * i))), (int) (this.getY() + 40 * (Math.sin((2 * Math.PI/(fish.length + 1)) * i))));
			if (i != 0) {
				fish[i].lookTowards(veiw[i-1]);
			}
		}
		veiw [fish.length] = new Point ((int) (this.getX() + 40 * (Math.cos((2 * Math.PI/(fish.length + 1)) * fish.length))), (int) (this.getY() + 40 * (Math.sin((2 * Math.PI/(fish.length + 1)) * fish.length))));
		fish[0].lookTowards(veiw[fish.length]);
	}
	@Override
	public void frameEvent () {
		displacement = displacement + 0.05;
		for (int i = 0; i < fish.length; i++) {
			fish[i].setX(this.getX() + 40 * (Math.cos((2 * Math.PI/(fish.length + 1)) * i + displacement)));
			fish[i].setY(this.getY() + 40 * (Math.sin((2 * Math.PI/(fish.length + 1)) * i + displacement)));
			if (i != 0) {
				fish[i].lookTowards(veiw[i-1]);
			}
			veiw[i] = new Point ((int)(this.getX() + 40 * (Math.cos((2 * Math.PI/(fish.length + 1)) * i + displacement))), (int) (this.getY() + 40 * (Math.sin((2 * Math.PI/(fish.length + 1)) * i + displacement))) );
		 }
		veiw [fish.length] = new Point ((int) (this.getX() + 40 * (Math.cos((2 * Math.PI/(fish.length + 1)) * fish.length + displacement))), (int) (this.getY() + 40 * (Math.sin((2 * Math.PI/(fish.length + 1)) * fish.length + displacement))));
		fish[0].lookTowards(veiw[fish.length]);
	}
	
	
	public class Fish extends Enemy {
		public Fish () {
			
			this.setSprite(new Sprite ("resources/sprites/fucking fish dude.png"));	
			this.setHitboxAttributes(0, 0, 30, 19);
			

			this.setHealth(30);
			
		}
	
	}
}
