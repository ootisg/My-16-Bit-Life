package map;

import java.awt.image.BufferedImage;

import main.GameObject;

public class TileEntitiy {
	
	
	TileData accociatedData; 
	
	BufferedImage texture;
	
	
	public TileEntitiy (TileData data, BufferedImage image) {
	accociatedData = data;
	image = texture;
	}
	/**
	 * gets the image of the map entity
	 */
	public BufferedImage getTexture () {
		return texture;
	}
	/**
	 * run code for the specfied tileEnitiy
	 * @param o the object collideing with this tileEnity
	 */
	public void onCollision(GameObject o) {
		
	}
	/**
	 * override to make this tile entity not solid sometimes
	 * @param o the object colliding with this tile Enitiy
	 * @return whether or not a collision should occur
	 */
	public boolean doesColide (GameObject o) {
		return true;
	}
	/**
	 * returns the tileData associated with this tile
	 * @return
	 */
	public TileData getData () {
		return accociatedData;
	}
	/**
	 * returns witch type of tile entiy
	 * ex "Convaier tile"
	 * @return the typeID of the tile eneity 
	 */
	public String getType() {
		return accociatedData.getName();
	}
}
