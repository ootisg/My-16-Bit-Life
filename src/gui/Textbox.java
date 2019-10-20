package gui;
import java.awt.event.KeyEvent;

import main.AnimationHandler;
import main.GameAPI;
import main.GameObject;
import main.ObjectHandler;
import resources.AfterRenderDrawer;
import resources.Sprite;



public class Textbox extends GameObject {
	//Jeffrey please comment your code
	//Alternatively, Tbox can be used
	int timer;
	int amountToDraw = 1;
	boolean isFinished;
	boolean finalCheck = false;
	public boolean isDone = false;
	boolean remember = false;
	int spaceManipulation;
	public Sprite textBoxTop;
	public Sprite textBoxBottum;
	public Sprite textBoxBackground;
	public Sprite textBoxSides;
	public Sprite fontSheet;
	String message;
	int isScrolled = 0;
	String text1;
	int width1;
	int height1;
	boolean renderBox;
	// put filepath of fontsheet to use as the font
	public Textbox (String textToDisplay){
	renderBox = true ;
	fontSheet = new Sprite ("resources/sprites/config/font.txt");
	textBoxTop = new Sprite ("resources/sprites/config/text_border_top.txt"); 
	textBoxBottum = new Sprite ("resources/sprites/config/text_border_bottom.txt");
	textBoxSides = new  Sprite ("resources/sprites/config/text_border_sides.txt");
	textBoxBackground = new Sprite ("resources/sprites/config/text_background.txt");
	isFinished = false;
	spaceManipulation = 0;
	text1 = textToDisplay;
	width1 = 200;
	height1 = 100;
	}
	//makes the textbox not get automaticly forgoten
	public void remember (boolean whateverMan) {
		remember = whateverMan;
	}
	public void chagePause() {
		if (ObjectHandler.isPaused()) {
		ObjectHandler.pause(true);
		} else {
		ObjectHandler.pause(false);
		}
	}
	public void changeWidth (int newWidth) {
		width1 = newWidth;
	}
	public void changeHeight(int newHeigh) {
		height1 = newHeigh;
	}
	// font = a config file with the font you want 
	public void changeFont (String font) {
		fontSheet = new Sprite (font);
	}
	public void changeBoxVisability () {
		renderBox = !renderBox;
	}
	// text = the message thats displayed width is the width of the box height is the height of the box 
	//x_orign is the x start point of the box y_orign is the y start point of the box
	@Override
public void pausedEvent (){
	if ((finalCheck && isFinished && (keyPressed(65) || keyPressed (97) || isDone)) || keyPressed (88)){
		isDone = true;
		ObjectHandler.pause(false);
		if (!remember) {
		this.forget();
		}
	}
	else {
	String text;
	int width;
	int height;
	int x_origin;
	int y_origin;
	text = text1;
	width = width1;
	height = height1;
	x_origin = (int)this.getX();
	y_origin = (int)this.getY();
	int space = 0;
	timer = timer + 1;
	int textLength = text.length();
	width = width/8;
	height = height/8;
	int width_start = width;
	int width_beginning = width;
	int width_basis = width;
	int height_start = height;
	height_start = height_start - 2;
	int x_start = x_origin;
	int x_beginning = x_origin;
	int x_basis = x_origin;
	int y_start = y_origin;
	while (width > 1){
	if (renderBox) {
	AfterRenderDrawer.drawAfterRender(x_start, y_start, textBoxTop);
	}
	width = width - 1;
	x_start = x_start + 8;
		}
	while (height > 1){
	if (renderBox) {
	AfterRenderDrawer.drawAfterRender(x_origin, y_origin, textBoxSides);
	AfterRenderDrawer.drawAfterRender(x_start, y_origin, textBoxSides);
		}
	height = height - 1;
	y_origin = y_origin + 8;
		}
	while (width_start > 1){
		if (renderBox) {
		AfterRenderDrawer.drawAfterRender(x_origin, y_origin, textBoxBottum);
		}
		width_start = width_start - 1;
		x_origin = x_origin + 8;
			}
	y_origin = y_start;
	x_origin = x_basis;
	int x = 0;
	while (x < height_start){
		width_beginning = width_basis;
		y_start = y_start + 8;
		x_beginning = x_basis;
		x = x + 1;
		while (width_beginning > 1){
			if (renderBox) {
			AfterRenderDrawer.drawAfterRender(x_beginning, y_start, textBoxBackground);
			}
			width_beginning = width_beginning - 1;
			x_beginning = x_beginning + 8;
			space = space + 1;
			}
		}
	x_beginning = x_origin;
	int spaceBasis = space;
	int charictarNumber = 0;
	int spaceManipulationPlusSpace = 0;
	int textLengthAtBeginning = textLength;
	width_beginning = width_basis;
	if (space < text.substring (spaceManipulation, text.length ()).length ()) {
		message = text.substring(spaceManipulation,spaceManipulation + spaceBasis);
	} else {
		//Here's the fix
		message = text.substring (spaceManipulation, text.length ());
	}
	y_origin = y_origin + 8;
	if (((keyPressed(65) || keyPressed(97)) && isFinished) || message.length () == 0){
		
		amountToDraw = 0;
		if (spaceManipulation <= textLength - spaceManipulation && isFinished) {
			spaceManipulation = spaceManipulation + spaceBasis;
		}
		isFinished = false;
		spaceManipulationPlusSpace = spaceManipulation + spaceBasis;
		if (spaceManipulationPlusSpace >= textLengthAtBeginning){
			spaceManipulationPlusSpace = textLengthAtBeginning;
			isScrolled = space;
			}
		if (text.substring (spaceManipulation, text.length () - 1).length () >= 30) {
			spaceBasis = 30;
		} else {
			spaceBasis = text.length () - spaceManipulation;
		}
		message = text.substring(spaceManipulation,spaceManipulationPlusSpace);
}
		textLength = textLength - isScrolled;
		if (timer == 2 || keyDown (65)) {
			timer = 0;
			amountToDraw = amountToDraw + 1;
		}
		int amountToDrawBasis = amountToDraw;
		if ((spaceManipulation + spaceBasis) >= textLength) {
			finalCheck = true;
			space = message.length() - 1;
		}
	for (int n = 0; n < textLength; textLength = textLength - 1){
		if (width_basis == 1){
			y_origin = y_origin + 8;
			x_beginning = x_origin;
			width_basis = width_beginning;
		}
		if (space <= 0 && amountToDraw >= message.length()) {
			isFinished = true;
		}	
		// translates the charictar in the message to a askii value that is used to specify position on the
		// text sheet run for every for every charitar in the message every frame
		if (amountToDrawBasis > 0) {
			amountToDrawBasis = amountToDrawBasis - 1;
			if (charictarNumber < message.length()){
				char charictarInQuestion = message.charAt(charictarNumber);
				charictarNumber = charictarNumber + 1;
				width_basis = width_basis - 1;
				int charitarCode = KeyEvent.getExtendedKeyCodeForChar(charictarInQuestion);
				if (charitarCode == 32) {
					x_beginning = x_beginning + 8;
					space = space - 1;
				}
		// uses the askii value to draw the charictar in the box
				else{
				AfterRenderDrawer.drawAfterRender(x_beginning, y_origin, fontSheet, charitarCode);
				x_beginning = x_beginning + 8;
				space = space - 1;
						}
					}
				}
			}
		}
	}
@Override
public void frameEvent () {
	if (!ObjectHandler.isPaused()) {
		this.pausedEvent();
	}
}
}