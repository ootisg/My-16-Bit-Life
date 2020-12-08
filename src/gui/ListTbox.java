package gui;

import java.util.ArrayList;

import items.Item;
import items.LemonPacket;
import resources.AfterRenderDrawer;
import resources.Sprite;
import resources.SpriteParser;

public class ListTbox extends Tbox {
	
	public static final Sprite selector = new Sprite ("resources/sprites/selector.png");
	String[] options;
	int selected;
	Sprite textBorder;
	Sprite font;
	private boolean complete;
	public ListTbox (double x, double y, String[] options) {
		//Initialize parameters
		this.declare (x, y);
		ArrayList <String> parserQuantitys = new ArrayList<String> ();
		parserQuantitys.add("grid 8 8");
		textBorder = new Sprite ("resources/sprites/Text/windowspritesBlack.png", new SpriteParser(parserQuantitys));
		font = new Sprite ("resources/sprites/Text/normal.png", new SpriteParser(parserQuantitys));
		int longestWidth = 0;
		for (int i = 0; i < options.length; i ++) {
			if (options [i].length () > longestWidth) {
				longestWidth = options [i].length ();
			}
		}
		this.width = longestWidth + 1;
		this.height = options.length;
		this.options = options;
	}
	public ListTbox (double x, double y, String[] options, String color, String fontName) {
		//Initialize parameters
		this.declare (x, y);
		ArrayList <String> parserQuantitys = new ArrayList<String> ();
		parserQuantitys.add("grid 8 8");
		textBorder = new Sprite ("resources/sprites/Text/windowsprites"+ color +".png", new SpriteParser(parserQuantitys));
		font = new Sprite ("resources/sprites/Text/" + fontName +".png", new SpriteParser(parserQuantitys));
		int longestWidth = 0;
		for (int i = 0; i < options.length; i ++) {
			if (options [i].length () > longestWidth) {
				longestWidth = options [i].length ();
			}
		}
		this.width = longestWidth + 1;
		this.height = options.length;
		this.options = options;
	}
	@Override
	public void draw () {
		//Draws the top bar, bottom bar, and background
		int x = (int)this.getX ();
		int y = (int)this.getY ();
		for (int i = 0; i < this.width; i ++) {
			AfterRenderDrawer.drawAfterRender (x + i * 8 + 1, y,textBorder, 0);
			AfterRenderDrawer.drawAfterRender (x + i * 8 + 1, y + (this.height + 1) * 8,textBorder, 3);
			for (int j = 0; j < this.height; j ++) {
				AfterRenderDrawer.drawAfterRender (x + i * 8 + 1, y + j * 8 + 8, textBorder, 1);
			}
		}
		//Draws the two side bars
		for (int i = 0; i < this.height + 1; i ++) {
			AfterRenderDrawer.drawAfterRender (x, y + i * 8, textBorder, 2);
			AfterRenderDrawer.drawAfterRender (x + this.width * 8 + 1, y + i * 8,textBorder, 2);
		}
		//Draws the text
		for (int i = 0; i < height; i ++) {
			for (int j = 0; j < options [i].length (); j ++) {
				AfterRenderDrawer.drawAfterRender (x + j * 8 + 8, y + i * 8 + 8, font,(int)options [i].charAt (j));
			}
		}
		//Draws the selector
		AfterRenderDrawer.drawAfterRender (x + 1, y + selected * 8 + 8, selector);
	}
	@Override
	public void frameEvent () {
		//Handles key presses
		if (!complete) {
			if (keyPressed ((int)'W')) {
				selected --;
			}
			if (keyPressed ((int)'S')) {
				selected ++;
			}
			if (selected < 0) {
				selected = height - 1;
			}
			if (selected >= height) {
				selected = 0;
			}
			if (keyPressed ((int)'A')) {
				this.select ();
			}
		}
	}
	//@Override TODO IMPLEMENT FRICKIN PAUSING
	public void pausedEvent () {
		//Handles key presses
		if (!complete) {
			if (keyPressed ('W')) {
				selected --;
			}
			if (keyPressed ('S')) {
				selected ++;
			}
			if (selected < 0) {
				selected = height - 1;
			}
			if (selected >= height) {
				selected = 0;
			}
			if (keyPressed ('A')) {
				this.select ();
			}
		}
	}
	public void select () {
		//Doesn't close the window, but sets it ready to be closed
		complete = true;
	}
	public void deselect () {
		//Deselects the currently selected option
		complete = false;
	}
	@Override
	public void close () {
		this.forget ();
	}
	public int getSelected () {
		//Closes the window and returns the option selected if the window is ready to be closed
		//Returns -1 if the window is not ready to be closed
		if (!complete) {
			return -1;
		} else {
			return selected;
		}
	}
	public String getSelectedValue () {
		if (!complete) {
			return "NULL";
		} else {
			return options [selected];
		}
	}
}
