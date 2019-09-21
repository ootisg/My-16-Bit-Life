package gui;


import main.GameObject;
import resources.Sprite;

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
	Sprite font;
	boolean renderBox;
	public Tbox () {
		
	}
	public Tbox (double x, double y, int width, int height, String text, boolean drawBox) {
		//Initialize parameters
		this.declare (x, y);
		this.width = width;
		this.height = height;
		this.text = text;
		this.scrollTime = 2;
		textBorder = new Sprite ("resources/sprites/config/text_border.txt");
		font = new Sprite ("resources/sprites/config/font.txt");
		this.frameCount = 0;
		this.startPos = 0;
		this.letterPos = 0;
		renderBox = drawBox;
	}
	@Override
	public void draw () {
		//Get the x and y coordinates as integers
		int x = (int)this.getX ();
		int y = (int)this.getY ();
		//Draw the top bar, the background, and the bottom bar
		if (renderBox) {
		for (int i = 0; i < this.width; i ++) {
			textBorder.getFrame (0);
			textBorder.draw (x + i * 8 + 1, y);
			textBorder.getFrame (3);
			textBorder.draw (x + i * 8 + 1, y + (this.height + 1) * 8);
			for (int j = 0; j < this.height; j ++) {
				textBorder.getFrame (1);
				textBorder.draw (x + i * 8 + 1, y + j * 8 + 8);
			}
		}
		//Draw the side bars
		for (int i = 0; i < this.height + 1; i ++) {
			textBorder.getFrame (2);
			textBorder.draw (x, y + i * 8);
			textBorder.draw (x + this.width * 8 + 1, y + i * 8);
		}
		}
		//Draw the text in the box
		for (int i = 0; i < letterPos; i ++) {
			try {
				// TODO fix this
			//font.getFrame ((int)text.charAt (startPos + i));
			font.draw (x + (i % width) * 8, y + (i / width) * 8 + 8);
			} catch (StringIndexOutOfBoundsException e) {
				
			}
		}
		//Handles scrolling
		int scrollLimit;
		if (startPos / (width * height) == text.length () / (width * height)) {
			scrollLimit = (text.length () % (width * height)) * scrollTime;
			// not sure why this doesen't work gonna have to fix it later
			//Closes the textbox if A is pressed and all the text has been displayed
			//if (keyPressed((int)'A') && frameCount == scrollLimit) {
				//this.close ();
			//}
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
	public void close () {
		//Adds extensability; this can be overriden to prevent the window from closing when A is pressed
		this.forget ();
	}
	public void scroll () {
		//Adds extensability; this can be overriden to prevent the window from scrolling when A is pressed
		this.frameCount = 0;
		this.startPos += (width * height);
		this.letterPos = 0;
	}
}
