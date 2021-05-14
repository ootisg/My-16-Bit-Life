package gui;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import main.AnimationHandler;
import main.GameAPI;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import resources.AfterRenderDrawer;
import resources.Sprite;
import resources.SpriteParser;



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
	private boolean unPause = true;
	public Sprite textBoxSides;
	public Sprite fontSheet;
	String message;
	private boolean unfrezeMenu = false;
	int isScrolled = 0;
	String text1;
	String name;
	int width1;
	int height1;
	int time = -1;
	String [] extentions = {"7Z","JAVA","MP3","AI","AVI","BAS","C","C++","CD","CDF","CLASS","CMD","CSV","CSPROJ","D","D64","DAF","DAT","DB","DCI","DEV","DFL","DHP","DLC","DMO","DMP","DOC","DOG","E","EXE","EXP","EXS","F01","F4V","FA","FLV","GBR","GGB","GIF","GO","GPX","H!","H","H++","HACK","HDMP","HTA","HTML","HUM","ICO","IGC","ISO","IT","JAR","JNLP","JPEG","JS","JSON","LISP","LUA","LZ","M","MDI","MDG","MDS","MEX","MID","MOB","MOD","MOV","MP2","MP4","MPEG","MPG","MSI","NC","NEO","NPR","NUMBERS","O","OBJ","OBS","OXT","OWL","OST","P","PAL","PACK","PAK","PAM","PAS","PDF","PDN","PHP","PIE","PIT","PMA","PPTX","PSD","PTF","PS1","PUP","PY","QT","RAD","RAM","RAR","RB","RBXM","RBXL","RC","RES","RTF","RUN","SAV","SB3","SEQ","SIG","SM","SPIN","ST","STD","SWF","SWIFT","TAK","TORRENT","TAR","TSF","TTF","UI","UT!","V","V64","VB","VFD","VMG","VOB","WAV","WMA","XAR","XCF","XEX","XLS","XP","XYZ","ZIP","ZS"};
	boolean renderBox;
	// put filepath of fontsheet to use as the font
	public Textbox (String textToDisplay){
	renderBox = true;
	ArrayList <String> parserQuantitys = new ArrayList<String> ();
	parserQuantitys.add("grid 8 8");
	ArrayList <String> parserQuantitiys2 = new ArrayList<String>();
	parserQuantitiys2.add("rectangle 0 0 8 8");
	ArrayList <String> parserQuantitiys3 = new ArrayList<String>();
	parserQuantitiys3.add("rectangle 24 0 8 1");
	ArrayList <String> parserQuantitiys4 = new ArrayList<String>();
	parserQuantitiys4.add("rectangle 16 0 1 8");
	ArrayList <String> parserQuantitiys5 = new ArrayList<String>();
	parserQuantitiys5.add("rectangle 8 0 8 8");
	textBoxTop = new Sprite ("resources/sprites/Text/windowspritesBlack.png", new SpriteParser(parserQuantitiys2));
	fontSheet = new Sprite ("resources/sprites/Text/normal.png", new SpriteParser(parserQuantitys));
	textBoxBottum = new Sprite ("resources/sprites/Text/windowspritesBlack.png", new SpriteParser(parserQuantitiys3));
	textBoxSides = new Sprite ("resources/sprites/Text/windowspritesBlack.png", new SpriteParser(parserQuantitiys4));
	textBoxBackground = new Sprite ("resources/sprites/Text/windowspritesBlack.png", new SpriteParser(parserQuantitiys5));
	isFinished = false;
	spaceManipulation = 0;
	text1 = textToDisplay;
	width1 = 200;
	height1 = 100;
	name = "null";
	}
	//makes the textbox not get automaticly forgoten
	public void remember (boolean whateverMan) {
		remember = whateverMan;
	}
	public void chagePause() {
		if (!ObjectHandler.isPaused()) {
		ObjectHandler.pause(true);
		} else {
		ObjectHandler.pause(false);
		}
	}
	//changes wheather or not to unpause the game after the textbox is done
	public void changeUnpause () {
		unPause = !unPause;
	}
	public void changeWidth (int newWidth) {
		width1 = newWidth;
	}
	public void unfrezeMenu() {
		unfrezeMenu = true;
	}
	public void changeHeight(int newHeigh) {
		height1 = newHeigh;
	}
	public void changeBoxVisability () {
		renderBox = !renderBox;
	}
	public void changeText(String newText) {
		text1 = newText;
	}
	public void setFont (String fontName) {
		ArrayList <String> parserQuantitys = new ArrayList<String> ();
		parserQuantitys.add("grid 8 8");
		fontSheet = new Sprite ("resources/sprites/Text/" + fontName + ".png", new SpriteParser(parserQuantitys));
	}
	public void giveName (String boxName) {
		if (!boxName.equals("null")) {
			name = boxName;
			Random rand = new Random();
			name = name + "." +extentions[rand.nextInt(extentions.length)];
		}
	}
	public void setBox (String color) {
		ArrayList <String> parserQuantitiys2 = new ArrayList<String>();
		parserQuantitiys2.add("rectangle 0 0 8 8");
		ArrayList <String> parserQuantitiys3 = new ArrayList<String>();
		parserQuantitiys3.add("rectangle 24 0 8 1");
		ArrayList <String> parserQuantitiys4 = new ArrayList<String>();
		parserQuantitiys4.add("rectangle 16 0 1 8");
		ArrayList <String> parserQuantitiys5 = new ArrayList<String>();
		parserQuantitiys5.add("rectangle 8 0 8 8");
		textBoxTop = new Sprite ("resources/sprites/Text/windowsprites" + color + ".png", new SpriteParser(parserQuantitiys2));
		textBoxBottum = new Sprite ("resources/sprites/Text/windowsprites" + color + ".png", new SpriteParser(parserQuantitiys3));
		textBoxSides= new Sprite ("resources/sprites/Text/windowsprites" + color + ".png", new SpriteParser(parserQuantitiys4));
		textBoxBackground = new Sprite ("resources/sprites/Text/windowsprites" + color + ".png", new SpriteParser(parserQuantitiys5));
	}
	public void setTime (int time) {
		this.time = time;
	}
	// text = the message thats displayed width is the width of the box height is the height of the box 
	//x_orign is the x start point of the box y_orign is the y start point of the box
	
public void drawBox (){
	if ((finalCheck && isFinished && (keyPressed(65) || keyPressed (97) || isDone)) || keyPressed (88) || time == 0){
		isDone = true;
		if (unfrezeMenu) {
			Gui.getGui().menu.frozen = false;
		}
		if (unPause) {
		ObjectHandler.pause(false);
		}
		if (!remember) {
		this.forget();
		}
	}
	else {
	time = time -1;
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
	if (!name.equals("null")) {
		int nameX = x_origin;
		int nameY = y_origin - 8;
		for (int i = 0; i < name.length(); i++) {
			AfterRenderDrawer.drawAfterRender(nameX,nameY, textBoxBackground);
			AfterRenderDrawer.drawAfterRender(nameX,nameY, fontSheet, name.charAt(i));
			nameX = nameX + 8;
		}
	}
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
					int nextWordSpace = 0;
					for (int i = charictarNumber; i < message.length(); i++) {
						nextWordSpace = nextWordSpace + 1;
						try {
						if (KeyEvent.getExtendedKeyCodeForChar(message.charAt(i)) == 32) {
							break;
						}
						} catch (StringIndexOutOfBoundsException e) {
							break;
						}
					}
					int lineSpace =  (width1 - (x_beginning - x_origin))/8;
					if (nextWordSpace > lineSpace) {
						y_origin = y_origin + 8;
						x_beginning = x_origin;
						width_basis = width_beginning;
					}
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
public void draw () {
		this.drawBox();
}
}