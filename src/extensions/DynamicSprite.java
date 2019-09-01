package extensions;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.Iterator;

import engine.Sprite;

public class DynamicSprite extends Sprite {
	
	public static final String defaultConfig = "offset:0xFF0000,component:0x0000FF";
		
	private Sprite[] components;
	private Sprite meta;
	private FrameMeta[] metadata;
	private String config;
	
	public DynamicSprite (Sprite sprite, Sprite meta, Sprite[] components, String config) {
		super (sprite);
		this.meta = meta;
		this.components = components;
		metadata = new FrameMeta[meta.getFrameCount ()];
		if (sprite.getFrameCount () == meta.getFrameCount ()) {
			for (int i = 0; i < meta.getFrameCount (); i ++) {
				if (sprite.getFrame (i).getWidth () == meta.getFrame (i).getWidth () && sprite.getFrame (i).getHeight () == meta.getFrame (i).getHeight ()) {
					metadata [i] = new FrameMeta (meta.getFrame (i), config);
				} else {
					//Exception?
				}
			}
		} else {
			//Exception?
		}
	}
	
	public DynamicSprite (Sprite sprite, Sprite meta, Sprite[] components) {
		this (sprite, meta, components, defaultConfig);
	}
	
	public DynamicSprite (Sprite sprite, Sprite[] components, String config) {
		this (sprite, new Sprite (getDefaultMetaPath (sprite.getImagePath ()), sprite.getParsePath ()), components, config);
	}
	
	public DynamicSprite (Sprite sprite, Sprite[] components) {
		this (sprite, components, defaultConfig);
	}
	
	public class FrameMeta {
		
		private int offsetX;
		private int offsetY;
		private int[][] componentQueue;
		
		protected FrameMeta (int offsetX, int offsetY, int[][] componentQueue) {
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			this.componentQueue = componentQueue;
		}
		
		public FrameMeta (BufferedImage meta, String config) {
			Raster raster = meta.getData ();
			Color offsetColor = null;
			ArrayList<Color> componentColors = new ArrayList<Color> ();
			String[] configArgs = config.split (",");
			for (int i = 0; i < configArgs.length; i ++) {
				String[] args = configArgs [i].split (":");
				if (args.length == 2) {
					switch (args [0]) {
						case "offset":
							offsetColor = new Color (Integer.decode (args [1]));
							break;
						case "component":
							componentColors.add (new Color (Integer.decode (args [1])));
							break;
						default:
							break;
					}
				}
			}
			if (offsetColor != null) {
				int[] coords = findPixel (raster, offsetColor.getRed (), offsetColor.getGreen (), offsetColor.getBlue (), offsetColor.getAlpha ());
				offsetX = coords [0];
				offsetY = coords [1];
			}
			Iterator<Color> iter = componentColors.iterator ();
			int componentIndex = 0;
			componentQueue = new int[componentColors.size ()][3];
			while (iter.hasNext ()) {
				Color workingColor = iter.next ();
				int[] coords = findPixel (raster, workingColor.getRed (), workingColor.getGreen (), workingColor.getBlue (), workingColor.getAlpha ());
				//Write in the following order: id, x, y
				componentQueue [componentIndex][0] = componentIndex;
				componentQueue [componentIndex][1] = coords [0];
				componentQueue [componentIndex][2] = coords [1];
				componentIndex ++;
			}
		}
		
		public FrameMeta (BufferedImage meta) {
			this (meta, defaultConfig);
		}
		
		private int[] findPixel (Raster usedRaster, int red, int green, int blue, int alpha) {
			int[] pixelData = new int[4];
			for (int i = 0; i < usedRaster.getWidth (); i ++) {
				for (int j = 0; j < usedRaster.getHeight (); j ++) {
					pixelData [0] = 0;
					pixelData [1] = 0;
					pixelData [2] = 0;
					pixelData [3] = 255;
					usedRaster.getPixel (i, j, pixelData);
					if (pixelData [0] == red && pixelData [1] == green && pixelData [2] == blue && pixelData [3] == alpha) {
						return new int[] {i, j};
					}
				}
			}
			return null;
		}
		
		public int getXOffset () {
			return offsetX;
		}
		
		public int getYOffset () {
			return offsetY;
		}
		
		public int[][] getQueuedComponents () {
			return componentQueue;
		}
	}
	
	public static String getDefaultMetaPath (String path) {
		String[] pathArray = path.split ("/");
		if (pathArray.length == 1) {
			pathArray = path.split ("\\");
		}
		pathArray [pathArray.length - 1] = "metadata/" + pathArray [pathArray.length - 1];
		return String.join ("/", pathArray);
	}
	
	public static String getDefaultParsePath (String path) {
		String[] pathArray = path.split ("/");
		if (pathArray.length == 1) {
			pathArray = path.split ("\\");
		}
		String[] splitExt = pathArray [pathArray.length - 1].split (".");
		splitExt [splitExt.length - 1] = "txt";
		String joinExt = String.join (".", splitExt);
		pathArray [pathArray.length - 1] = joinExt;
		pathArray [pathArray.length - 1] = "metadata/" + pathArray [pathArray.length - 1];
		return String.join ("/", pathArray);
	}
	
	@Override
	public void draw (int x, int y, int frame) {
		FrameMeta workingMeta = metadata [frame];
		super.draw (x - workingMeta.getXOffset (), y - workingMeta.getYOffset (), frame);
		int[][] queuedComponents = workingMeta.getQueuedComponents ();
		for (int i = 0; i < queuedComponents.length; i ++) {
			components [queuedComponents [i][0]].draw (x + queuedComponents [i][1] - workingMeta.getXOffset (), y + queuedComponents [i][2] - workingMeta.getYOffset ());
		}
	}
}
