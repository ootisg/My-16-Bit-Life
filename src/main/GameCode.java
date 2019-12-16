package main;

import java.io.FileNotFoundException;

import gameObjects.CannonTankEnemy;
import gameObjects.Celing_boi;
import gameObjects.ClostridiumBowtielinea;
import gameObjects.CreepyButterfly;
import gameObjects.CutsceenTrigger;
import gameObjects.CyclopesCrab;
import gameObjects.DuoflyMinus;
import gameObjects.DuoflyPlus;
import gameObjects.FireRextinguser;
import gameObjects.HitboxRightBottomBound;
import gameObjects.Ladder;
import gameObjects.Leg;
import gameObjects.MissleadingStop;
import gameObjects.Puncuation;
import gameObjects.SplittingSlimelet;
import gameObjects.TemporaryWall;
import gameObjects.TomatoFunction;
import gameObjects.Trigger;
import graphics3D.VectorCamera;
import gui.Gui;
import gui.ListTbox;
import gui.Tbox;
import gui.Textbox;
import items.FairUseKey;
import items.LemonPacket;
import items.RedBlackPaintBall;
import map.Room;
import npcs.NonPlayableJeffrey;
import npcs.NonPlayableRyan;
import npcs.NonPlayableSam;
import npcs.PointGuy;
import players.Jeffrey;
import players.TopDown;
import players.TubeRaster;
import resources.AfterRenderDrawer;
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
	public static Gui gui;
	Tbox tbox;
	static VectorCamera cam;
	ListTbox ltbox;
	static CannonTankEnemy showTank;
	static CyclopesCrab testCrab;
	//static CutsceenTrigger trigger;
	static PointGuy guy;
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
	static Trigger trigger;
	static TemporaryWall wall;
	static HitboxRightBottomBound bound;
	static Puncuation puncuation;
	static NonPlayableJeffrey lameJeffrey;
	static MissleadingStop stop;
	static NonPlayableSam lameSam;
	static NonPlayableRyan lameRyan;
	static LemonPacket packet;
	static FairUseKey key;
	public static void initialize () {
		//Initialize sprites
		//GameObject initialization
		testLaddder = new Ladder ();
		testJeffrey = new Jeffrey ();
		lameJeffrey = new NonPlayableJeffrey();
		lameSam = new NonPlayableSam();
		lameRyan = new NonPlayableRyan();
		gun = new redBlackPaintBallGun(new Sprite ("resources/sprites/redblack_gun.png"));
		key = new FairUseKey(1);
		guy = new PointGuy();
		packet = new LemonPacket(1);
		testJeffrey.getInventory().addFreind(lameJeffrey);
		testJeffrey.getInventory().addFreind(lameSam);
		testJeffrey.getInventory().addFreind(lameRyan);
		testJeffrey.getInventory().addFreind(guy);
		gui = new Gui ();
		testJeffrey.getInventory().addKeyItem(key);
		testJeffrey.getInventory().addConsumable(gun);
		testJeffrey.getInventory().addConsumable(packet);
		vaccum = new LifeVaccum (new Sprite ("resources/sprites/config/lifeVaccum.txt"));
		testJeffrey.getInventory().addWeapon(vaccum, 1);
		sword = new SlimeSword();
		stop = new MissleadingStop();
		slimelet = new SplittingSlimelet ();
		testCrab = new CyclopesCrab();
		//trigger = new CutsceenTrigger();
		microphone = new MagicMicrophone ();
		testJeffrey.getInventory().addWeapon (microphone, 2);
		extinguser = new FireRextinguser ();
		testJeffrey.getInventory().addKill(extinguser);
		testTie = new ClostridiumBowtielinea();
		testJeffrey.getInventory().addWeapon(gun, 0);
		testJeffrey.getInventory().addWeapon(sword, 1);
		int x = 0;
		//boi = new Celing_boi();
		packet = new LemonPacket (4);
		puncuation = new Puncuation ();
		testJeffrey.getInventory().addKill(testTie);
		testJeffrey.getInventory().addKill(puncuation);
		newFly = new CreepyButterfly();
		testLadder = new Ladder ();
		paintball = new RedBlackPaintBall(1);
		while (x <= 40) {
		testJeffrey.inventory.addAmmo(paintball);
		x = x + 1;
		}
		//testTie.declare (32, 32);
	//	testJeffrey.getInventory().addKill(boi);
		//testJeffrey.getInventory().addKill(puncuation);
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
	//	boi.declare(40,300);
		//newFly.declare(180,350);
		//trigger.declare(180,350);
		//bound = new HitboxRightBottomBound();
		//bound.declare(180, 300);
		packet.declare(150, 200);
		//trigger = new Trigger();
		//trigger.declare(60,120);
		//showTank.declare(280, 383);
		//puncuation.declare(300, 200);
		testLaddder.declare(150, 373);
		stop.declare(150, 200);
		slimelet.declare(180, 300);
		
	}
	
	public static void beforeGameLogic () {
		
	}
	
	public static void afterGameLogic () {
		
	}
	
	public static void beforeRender () {
		Room.frameEvent ();
		
	}
	
	public static void afterRender () {
		AfterRenderDrawer.drawAll();
	}
}
