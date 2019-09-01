package gui;

import resources.Sprite;

public class ListTbox extends Tbox {
	
	public static final Sprite selector = new Sprite ("resources/sprites/selector.png");
	
	String[] options;
	int selected;
	private boolean complete;
	public ListTbox (double x, double y, String[] options) {
		//Initialize parameters
		this.declare (x, y);
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
			textBorder.draw (x + i * 8 + 1, y, 0);
			textBorder.draw (x + i * 8 + 1, y + (this.height + 1) * 8, 3);
			for (int j = 0; j < this.height; j ++) {
				textBorder.draw (x + i * 8 + 1, y + j * 8 + 8, 1);
			}
		}
		//Draws the two side bars
		for (int i = 0; i < this.height + 1; i ++) {
			textBorder.draw (x, y + i * 8, 2);
			textBorder.draw (x + this.width * 8 + 1, y + i * 8, 2);
		}
		//Draws the text
		for (int i = 0; i < height; i ++) {
			for (int j = 0; j < options [i].length (); j ++) {
				font.draw (x + j * 8 + 8, y + i * 8 + 8, (int)options [i].charAt (j));
			}
		}
		//Draws the selector
		selector.draw (x + 1, y + selected * 8 + 8);
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
