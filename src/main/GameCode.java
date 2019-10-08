package main;

import java.io.FileNotFoundException;

import gameObjects.CannonTankEnemy;
import gameObjects.Celing_boi;
import gameObjects.ClostridiumBowtielinea;
import gameObjects.CreepyButterfly;
import gameObjects.CyclopesCrab;
import gameObjects.DuoflyMinus;
import gameObjects.DuoflyPlus;
import gameObjects.FireRextinguser;
import gameObjects.Ladder;
import gameObjects.Leg;
import gameObjects.SplittingSlimelet;
import gameObjects.TomatoFunction;
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
import weapons.LifeVaccum;
import weapons.MagicMicrophone;
import weapons.SlimeSword;
import weapons.redBlackPaintBallGun;

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
	static Celing_boi boi;
	static Gui gui;
	Tbox tbox;
	static VectorCamera cam;
	ListTbox ltbox;
	static CannonTankEnemy showTank;
	static CyclopesCrab testCrab;
	static ClostridiumBowtielinea testTie;
	static Status testStatus;
	static Ladder testLaddder;
	static redBlackPaintBallGun gun;
	static RedBlackPaintBall paintball;
	static Ladder testLadder;
	static Leg leg;
	static SlimeSword sword;
	static SplittingSlimelet slimelet;
	static MagicMicrophone microphone;
	static TomatoFunction function;
	static FireRextinguser extinguser;
	static LifeVaccum vaccum;
	public static void initialize () {
		//Initialize sprites
		//GameObject initialization
		testLaddder = new Ladder ();
		testJeffrey = new Jeffrey ();
		gui = new Gui ();
		vaccum = new LifeVaccum (new Sprite ("resources/sprites/config/lifeVaccum.txt"));
		testJeffrey.getInventory().addWeapon(vaccum, 1);
		sword = new SlimeSword();
		slimelet = new SplittingSlimelet ();
		testCrab = new CyclopesCrab();
		microphone = new MagicMicrophone ();
		testJeffrey.getInventory().addWeapon (microphone, 2);
		extinguser = new FireRextinguser ();
		gun = new redBlackPaintBallGun(new Sprite ("resources/sprites/redblack_gun.png"));
		testTie = new ClostridiumBowtielinea();
		testJeffrey.getInventory().addWeapon(gun, 0);
		testJeffrey.getInventory().addWeapon(sword, 1);
		int x = 0;
		boi = new Celing_boi();
		testLadder = new Ladder ();
		while (x <= 5) {
		paintball = new RedBlackPaintBall();
		testJeffrey.inventory.addAmmo(paintball);
		x = x + 1;
		}
		System.out.println("YEET");
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
		//leg.declare(150, 200);
		boi.declare(40,300);
		extinguser.declare(180,350);
		//showTank.declare(280, 383);
		testLaddder.declare(150, 373);
		//slimelet.declare(180, 300);
		
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
