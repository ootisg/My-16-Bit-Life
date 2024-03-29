package gui;


import java.util.ArrayList;

import main.GameObject;
import map.Room;
import resources.AfterRenderDrawer;
import resources.Sprite;
import resources.SpriteParser;

public class Tbox extends GameObject {
	//Lightweight textbox object
	//Alternatively, Textbox can be used
	protected int width;
	protected int height;
	protected String text;
	protected int frameCount;
	protected int scrollTime;
	protected int letterPos;
	protected int startPos;
	Sprite textBorder;
	private int timerCloseing = 0;
	Sprite font;
	boolean renderBox;
	int timer;
	boolean setPlace = false;
	boolean keepOpen = false;
	public Tbox () {
		timer = 0;
		ArrayList <String> parserQuantitys = new ArrayList<String> ();
		parserQuantitys.add("grid 8 8");
		textBorder = new Sprite ("resources/sprites/Text/windowspritesBlack.png", new SpriteParser(parserQuantitys));
		font = new Sprite ("resources/sprites/Text/normal.png", new SpriteParser(parserQuantitys));
	}
	public Tbox (double x, double y, int width, int height, String text, boolean drawBox) {
		//Initialize parameters
		this.declare (x, y);
		this.width = width;
		this.height = height;
		this.text = text;
		this.scrollTime = 2;
		ArrayList <String> parserQuantitys = new ArrayList<String> ();
		parserQuantitys.add("grid 8 8");
		textBorder = new Sprite ("resources/sprites/Text/windowspritesBlack.png", new SpriteParser(parserQuantitys));
		font = new Sprite ("resources/sprites/Text/normal.png", new SpriteParser(parserQuantitys));
		this.frameCount = 0;
		this.startPos = 0;
		this.letterPos = 0;
		timer = 0;
		renderBox = drawBox;
		
	}
	@Override
	public void draw () {  
		if (timer == timerCloseing && timerCloseing != 0) {	
			this.forget();
		}
		//Get the x and y coordinates as integers
		int x = (int)this.getX ();
		int y = (int)this.getY ();
		//Draw the top bar, the background, and the bottom bar
		if (renderBox) {
		for (int i = 0; i < this.width; i ++) {
			if (setPlace) {
				AfterRenderDrawer.drawAfterRender (x + i * 8 + 1, y, textBorder, 0);
				AfterRenderDrawer.drawAfterRender(x + i * 8 + 1, y + (this.height + 1) * 8, textBorder, 3);
			} else {
				AfterRenderDrawer.drawAfterRender (x + i * 8 + 1 - Room.getViewX(), y - Room.getViewY(), textBorder, 0);
				AfterRenderDrawer.drawAfterRender(x + i * 8 + 1 - Room.getViewX(), y + (this.height + 1) * 8 - Room.getViewY(), textBorder, 3);
			}
			for (int j = 0; j < this.height; j ++) {
				if (setPlace) {
					AfterRenderDrawer.drawAfterRender(x + i * 8 + 1, y +j*8 + 8, textBorder, 1);
				} else {
					AfterRenderDrawer.drawAfterRender(x + i * 8 + 1 - Room.getViewX(), y +j*8 + 8 - Room.getViewY(), textBorder, 1);
				}
			}
		}
		//Draw the side bars
		for (int i = 0; i < this.height + 1; i ++) {
			if (setPlace) {
				AfterRenderDrawer.drawAfterRender(x, y + i * 8, textBorder, 2);
				AfterRenderDrawer.drawAfterRender(x + this.width * 8 + 1, y + i * 8,textBorder, 2);
			} else {
				AfterRenderDrawer.drawAfterRender(x - Room.getViewX(), y + i * 8 - Room.getViewY(), textBorder, 2);
				AfterRenderDrawer.drawAfterRender(x + this.width * 8 + 1 - Room.getViewX(), y + i * 8 - Room.getViewY(),textBorder, 2);
			}
		}
		}
		//Draw the text in the box
		int yi = -1;
		int xi = -1;
		int calcwidth = width;
		int oldvalue = 0;
		for (int i = 0; i < letterPos; i ++) {
			try {
				yi = yi + 1;
				xi = xi + 1;
				if ( (yi / calcwidth) > oldvalue) {
					oldvalue = oldvalue +1;
					calcwidth = width;
				}
				try {
					if ((int)text.charAt(startPos + i + 1) != 32 ||(int)text.charAt(startPos + i - 1) != 32 ) {
						int icopy = i;
						int yicopy = yi;
						while (icopy != text.length() - 1 && (int)text.charAt(startPos + icopy) != 32 ) {
							icopy = icopy + 1;
							if (icopy > text.length() -1) {
								icopy = text.length() -1;
							}
							yicopy = yicopy +1;
						}
						if (yicopy/calcwidth != oldvalue) {
							xi = 0;
							yi = calcwidth * ((yi/calcwidth) + 1);
							calcwidth = width;
							oldvalue = oldvalue + 1;
						}
				}
				} catch (StringIndexOutOfBoundsException e) {
					if ((int)text.charAt(startPos + i) != 32 ) {
						int icopy = i;
						int yicopy = yi;
						while (icopy != text.length() - 1 && (int)text.charAt(startPos + icopy) != 32 ) {
							icopy = icopy + 1;
							if (icopy > text.length() -1) {
								icopy = text.length() -1;
							}
							yicopy = yicopy +1;
						}
						if (yicopy/calcwidth != oldvalue) {
							xi = 0;
							yi = calcwidth * ((yi/calcwidth) + 1);
							calcwidth = width;
							oldvalue = oldvalue + 1;
						}
				}
				}
				if (setPlace) {
					AfterRenderDrawer.drawAfterRender((x + (xi % width) * 8), y + (yi / calcwidth) * 8 + 8,font, (int)text.charAt(startPos + i)); 
				} else {
					AfterRenderDrawer.drawAfterRender((x + (xi % width) * 8) - Room.getViewX(), y + (yi / calcwidth) * 8 + 8 - Room.getViewY(),font, (int)text.charAt(startPos + i)); 
				}
			if ((int)text.charAt(startPos + i) == 10) {
				xi = -3;
				calcwidth = calcwidth + 3;
				yi = calcwidth * ((yi/calcwidth) + 1);
				oldvalue = oldvalue + 1;
				}
			} catch (StringIndexOutOfBoundsException e) {
			}
		}
		timer = timer + 1;
		//Handles scrolling
		int scrollLimit;
		if (startPos / (width * height) == text.length () / (width * height)) {
			scrollLimit = (text.length () % (width * height)) * scrollTime;
			//Closes the textbox if A is pressed and all the text has been displayed
			//TODO the game doesen't like keyPressed when the game just started so this timer is a temporary solution
			if (timer > 180) {
			if (keyPressed((int)'A') && frameCount == scrollLimit) {
				this.close ();
			}
			}
		} else {
			scrollLimit = width * height * scrollTime;
			//Scrolls the textbox if A is pressed and not all the text has been displayed
			if (keyPressed((int)'A') && frameCount == scrollLimit) {
				this.scroll ();
			}
		}
		//Applies the letter limit
		if (frameCount < scrollLimit) {
			frameCount ++;
		}
		//Sets the letter to draw to
		if (scrollTime != 0) {
			letterPos = frameCount / scrollTime;
		} else {
			if (startPos / (width * height) == text.length () / (width * height)) {
				letterPos = text.length () - startPos;
			} else {
				letterPos = width * height;
			}
		}
	}
	public void setContent (String text) {
		this.text = text;
	}
	public void setWidth (int width) {
		this.width = width;
	}
	public void setHeight (int height) {
		this.height = height;
	}
	public void setFrames (int frames) {
		this.frameCount = frames;
	}
	public void setScrollRate (int rate) {
		this.scrollTime = rate;
	}
	public String getContent () {
		return this.text;
	}
	public int getWidth () {
		return this.width;
	}
	public int getHeight () {
		return this.height;
	}
	public int getFrames () {
		return this.frameCount;
	}
	public int getScrollRate () {
		return this.scrollTime;
	}
	public void setFont (String fontName) {
		ArrayList <String> parserQuantitys = new ArrayList<String> ();
		parserQuantitys.add("grid 8 8");
		font = new Sprite ("resources/sprites/Text/" +fontName +".png", new SpriteParser(parserQuantitys));
	}
	public void setBox (String color) {
		ArrayList <String> parserQuantitys = new ArrayList<String> ();
		parserQuantitys.add("grid 8 8");
		textBorder = new Sprite ("resources/sprites/Text/windowsprites" + color +".png", new SpriteParser(parserQuantitys));
	}
	public void keepOpen (boolean shouldItStayOpen) {
		keepOpen = shouldItStayOpen;
	}
	public void renderBox (boolean toRenderOrNotToRender) {
		renderBox = toRenderOrNotToRender;
	}
	public void close () {
		//Adds extensability; this can be overriden to prevent the window from closing when A is pressed
		if (!keepOpen) {
		this.forget ();
		}
	}
	public void configureTimerCloseing (int timeToClose) {
		timerCloseing = timeToClose;
	}
	// turn on to make Tbox remain in the same place regardless of scrolling keep off to not have that happen
	public void setPlace () {
		setPlace = true;
	}
	public void scroll () {
		//Adds extensability; this can be overriden to prevent the window from scrolling when A is pressed
		this.frameCount = 0;
		this.startPos += (width * height);
		this.letterPos = 0;
	}
}
