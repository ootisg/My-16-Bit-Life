package main;

import java.io.FileNotFoundException;

import gameObjects.BuggyBoi;
import gameObjects.CannonTankEnemy;
import gameObjects.CarSpawner;
import gameObjects.Celing_boi;
import gameObjects.ClostridiumBowtielinea;
import gameObjects.CreepyButterfly;
import gameObjects.CutsceenTrigger;
import gameObjects.CyclopesCrab;
import gameObjects.DiscoBall;
import gameObjects.DuoflyMinus;
import gameObjects.DuoflyPlus;
import gameObjects.FireHydrant;
import gameObjects.FireRextinguser;
import gameObjects.HitboxRightBottomBound;
import gameObjects.ImpatientCar;
import gameObjects.Ladder;
import gameObjects.Leg;
import gameObjects.MissleadingStop;
import gameObjects.Point;
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
import items.BluePaint;
import items.FairUseKey;
import items.LemonPacket;
import items.RedBlackPaintBall;
import items.Waffle;
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
import weapons.LaserPointer;
import weapons.LifeVaccum;
import weapons.MagicMicrophone;
import weapons.NinjaTriangle;
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
	static BuggyBoi bug1;
	static BuggyBoi bug2;
	static BuggyBoi bug3;
	static BuggyBoi bug4;
	static BuggyBoi bug5;
	static BuggyBoi bug6;
	static BuggyBoi bug7;
	static BuggyBoi bug8;
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
	static Point point;
	static LemonPacket packet;
	static Waffle waffle;
	static NinjaTriangle triangle;
	static BluePaint paint;
	static FairUseKey key;
	static LaserPointer pointer;
	static FireHydrant hydrant;
	static DiscoBall ball;
	static ImpatientCar car;
	static CarSpawner spawner;
	public static void initialize () {
		//Initialize sprites
		//GameObject initialization
		testLaddder = new Ladder ();
		testJeffrey = new Jeffrey ();
		lameJeffrey = new NonPlayableJeffrey();
		lameSam = new NonPlayableSam();
		bug1 = new BuggyBoi();
		bug2 = new BuggyBoi();
		bug3 = new BuggyBoi();
		bug4 = new BuggyBoi();
		bug5 = new BuggyBoi();
		bug6 = new BuggyBoi();
		bug7 = new BuggyBoi();
		spawner = new CarSpawner(true);
		ball = new DiscoBall();
		bug8 = new BuggyBoi();
		lameRyan = new NonPlayableRyan();
		gun = new redBlackPaintBallGun(new Sprite ("resources/sprites/redblack_gun.png"));
		pointer = new LaserPointer ();
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
		testJeffrey.getInventory().addWeapon(pointer, 0);
		sword = new SlimeSword();
		stop = new MissleadingStop();
		slimelet = new SplittingSlimelet ();
		testCrab = new CyclopesCrab();
		//trigger = new CutsceenTrigger();
		microphone = new MagicMicrophone ();
		triangle = new NinjaTriangle (new Sprite ("resources/sprites/config/stationary_ninja_triangle.txt"));
		testJeffrey.getInventory().addWeapon (microphone, 2);
		extinguser = new FireRextinguser ();
		testJeffrey.getInventory().addKill(extinguser);
		testTie = new ClostridiumBowtielinea();
		car = new ImpatientCar();
		testJeffrey.getInventory().addWeapon(gun, 0);
		testJeffrey.getInventory().addWeapon(triangle, 0);
		testJeffrey.getInventory().addWeapon(sword, 1);
		int x = 0;
		//boi = new Celing_boi();
		waffle = new Waffle (4);
		puncuation = new Puncuation ();
		testJeffrey.getInventory().addKill(testTie);
		testJeffrey.getInventory().addKill(puncuation);
		newFly = new CreepyButterfly();
		testLadder = new Ladder ();
		paintball = new RedBlackPaintBall(1);
		paint = new BluePaint (1);
		hydrant = new FireHydrant ();
		while (x <= 40) {
		testJeffrey.inventory.addAmmo(paintball);
		testJeffrey.inventory.addAmmo(paint);
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
		Room.loadRoom ("resources/maps/test.cmf");
		//new TestObject ().declare (128, 200);
		//new Slimelet ().declare (200, 400);// From when I was messing around with slimelets =P
		//td = new TopDown ();
		//leg.declare(150, 200);
	//	boi.declare(40,300);
		//newFly.declare(180,350);
		//trigger.declare(180,350);
		//bound = new HitboxRightBottomBound();
		//bound.declare(180, 300);
		waffle.declare(150, 200);
		//trigger = new Trigger();
		//trigger.declare(60,120);
		//showTank.declare(280, 383);
		//puncuation.declare(300, 200);
		testLaddder.declare(150, 373);
		stop.declare(150, 200);
		ball.declare(200,0);
		//bug1.declare(400, 180);
		/*bug2.declare(425, 175);
		bug3.declare(450, 125);*/
		/*bug4.declare(225, 150);
		bug5.declare(250, 125);
		bug6.declare(450, 200);
		bug7.declare(450, 150);
		bug8.declare(225, 175);*/
		slimelet.declare(180, 300);
		hydrant.declare(40,390);
		//car.declare(300,340);
		spawner.declare (300,340);
		/*point = new Point (418,343);
		point.declare(384,483);
		Point testPoint = new Point (400,180);
		testPoint.declare(185, 380);
		System.out.println(testPoint.generatePath(point));*/
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
