package main;

import java.io.FileNotFoundException;

import gameObjects.CannonTankEnemy;
import gameObjects.ClostridiumBowtielinea;
import gameObjects.CreepyButterfly;
import gameObjects.CyclopesCrab;
import gameObjects.DuoflyMinus;
import gameObjects.DuoflyPlus;
import gameObjects.Ladder;
import graphics3D.VectorCamera;
import gui.Gui;
import gui.ListTbox;
import gui.Tbox;
import gui.Textbox;
import items.RedBlackPaintBall;
import map.Room;
import players.Jeffrey;
import players.TopDown;
import players.TubeRaster;
import resources.Sprite;
import statusEffect.Status;
import weapons.AimableWeapon;

public class GameCode {
	private GameWindow gameWindow;
	//GameObjects
	static TubeRaster raster;
	static AimableWeapon wpn;
	static CreepyButterfly newFly;
	static DuoflyPlus testFly;
	static DuoflyMinus testFly2;
	public static Jeffrey testJeffrey;
	//static TopDown td;
	Textbox textbox;
	static Gui gui;
	Tbox tbox;
	static VectorCamera cam;
	ListTbox ltbox;
	static CannonTankEnemy showTank;
	static CyclopesCrab testCrab;
	static ClostridiumBowtielinea testTie;
	static Status testStatus;
	static Ladder testLaddder;
	static RedBlackPaintBall paintball;
	static Ladder testLadder;
	public static void initialize () {
		//Initialize sprites
		//GameObject initialization
		testLaddder = new Ladder ();
		testJeffrey = new Jeffrey ();
		showTank = new CannonTankEnemy();
		gui = new Gui ();
		testCrab = new CyclopesCrab();
		testTie = new ClostridiumBowtielinea();
		int x = 0;
		while (x <= 5) {
		paintball = new RedBlackPaintBall();
		testJeffrey.inventory.addAmmo(paintball);
		testLadder = new Ladder ();
		x = x + 1;
		}
		//testTie.declare (32, 32);
		//cam = new VectorCamera (0, 0);
		//Uncomment the above line if you want to see them
		//GameObject declaration
		//textbox = new Textbox ();
		//tbox = new Tbox (0, 32, 16, 2, "HELLOTHISISTHEAWESOMEJEFFREY1234567890ANDTHEREARELOTSOFLINESTOTHISTEXT!");
		//ltbox = new ListTbox (0, 128, new String[] {"OPTION A", "OPTION B", "OPTION C"});
		//WARNING: LOADING A ROOM PURGES ALL THE OBJECTS USING THE FORGET METHOD
		//Add the following to an object to a class to keep it around: @Override public void forget () {}
		Room.loadRoom ("resources/maps/snowmap.cmf");
		//new TestObject ().declare (128, 200);
		//new Slimelet ().declare (200, 400);// From when I was messing around with slimelets =P
		//td = new TopDown ();
		testLaddder.declare(150, 373);
		testLadder.declare(150, 388);
	}
	
	public static void beforeGameLogic () {
		
	}
	
	public static void afterGameLogic () {
		
	}
	
	public static void beforeRender () {
		Room.frameEvent ();
	}
	
	public static void afterRender () {
		
	}
}
