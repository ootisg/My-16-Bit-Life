package map;

public class MapTile {
	//Container class for map tiles
	public TileData properties;
	public int x;
	public int y;
	public MapTile (TileData tileId, int x, int y) {
		//tileId is in the format [tile name]
		this.properties = tileId;
		this.x = x;
		this.y = y;
	}
}