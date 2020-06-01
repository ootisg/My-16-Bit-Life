	package main;

import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import cutsceens.Cutsceen;
import cutsceens.MoveSlowEvent;
import enemys.BuggyBoi;
import enemys.Candle;
import enemys.CannonTankEnemy;
import enemys.Celing_boi;
import enemys.ClostridiumBowtielinea;
import enemys.CreepyButterfly;
import enemys.CyclopesCrab;
import enemys.DiscoBall;
import enemys.DuoflyMinus;
import enemys.DuoflyPlus;
import enemys.Enemy;
import enemys.FallingChandleer;
import enemys.FireHydrant;
import enemys.FireRextinguser;
import enemys.ImpatientCar;
import enemys.LazerHoverEnemy;
import enemys.MafiaShooter;
import enemys.Marshan;
import enemys.MissleadingStop;
import enemys.Puncuation;
import enemys.SplitSlimelet;
import enemys.SplittingSlimelet;
import enemys.TomatoFunction;
import enemys.UFO;
import gameObjects.AnimeTester;
import gameObjects.Box;
import gameObjects.BreakableObject;
import gameObjects.CarSpawner;
import gameObjects.HitboxRightBottomBound;
import gameObjects.Ladder;
import gameObjects.Leg;
import gameObjects.MoveingPlatform;
import gameObjects.Plant;
import gameObjects.Point;
import gameObjects.TemporaryWall;
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
import json.JSONException;
import json.JSONObject;
import map.Room;
import npcs.NonPlayableJeffrey;
import npcs.NonPlayableRyan;
import npcs.NonPlayableSam;
import npcs.PointGuy;
import players.Jeffrey;
import players.TopDown;
import players.TubeRaster;
import resources.AfterRenderDrawer;
import resources.SoundPlayer;
import resources.Sprite;
import spriteParsers.HitboxFilter;
import spriteParsers.JigsawFilter;
import spriteParsers.ParsedFrame;
import spriteParsers.PixelParser;
import statusEffect.Status;
import theHeist.Worker;
import triggers.CutsceenTrigger;
import triggers.Trigger;
import weapons.AimableWeapon;
import weapons.Bombs;
import weapons.LaserPointer;
import weapons.LifeVaccum;
import weapons.MagicMarker;
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
	public static Jeffrey J;
	public static SoundPlayer player;
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
	static MagicMicrophone microphone;
	static MagicMarker marker;
	static Bombs bomb;
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
	static LazerHoverEnemy laser;
	static CarSpawner spawner;
	static MafiaShooter shooter;
	static Box box;
	static MoveSlowEvent slow;
	static Marshan marsh;
	static FallingChandleer fall;
	static Plant plant;
	static BreakableObject test;
	static Worker work;
	static Candle candle;
	static AnimeTester bleh;
	static MoveingPlatform platform;
	static Cutsceen testSceen;
	static UFO ufo;
	static SplitSlimelet split;
	static String jsonTest = ""
			+ "{"
			+ "\"JSON\":\"TRUE\","
			+ "\"OBJ\":{\"JSON\":{\"JSON2\":{\"JSON4\":\"CORRECT\"},\"ARR\":[1,2,{\"JSON5\":\"HELLO WORLD\"},3,4,[2,4,6,7],9]},\"JSON3\":\"TESTING\"}"
			+ "}";
	public static void initialize () {
		
		//Initialize sprites
		//GameObject initialization
		J = new Jeffrey ();
		testLaddder = new Ladder ();
		lameJeffrey = new NonPlayableJeffrey();
		lameSam = new NonPlayableSam();
		lameRyan = new NonPlayableRyan();
		gun = new redBlackPaintBallGun(	new Sprite ("resources/sprites/redblack_gun.png"));
		pointer = new LaserPointer ();
		key = new FairUseKey(1);
		//guy = new PointGuy();
		work = new Worker ();
		fall = new FallingChandleer ();
		test = new BreakableObject ();
		packet = new LemonPacket(1);
		Jeffrey.getInventory().addFreind(lameJeffrey);
		Jeffrey.getInventory().addFreind(lameSam);
		Jeffrey.getInventory().addFreind(lameRyan);
		Jeffrey.getInventory().addFreind(guy);
		Jeffrey.getInventory().addKeyItem(key);
		Jeffrey.getInventory().addConsumable(gun);
		Jeffrey.getInventory().addConsumable(packet);
		vaccum = new LifeVaccum (new Sprite ("resources/sprites/config/lifeVaccum.txt"));
		Jeffrey.getInventory().addWeapon(vaccum, 1);
		Jeffrey.getInventory().addWeapon(pointer, 0);
		sword = new SlimeSword();
		microphone = new MagicMicrophone ();
		marker = new MagicMarker (new Sprite ("resources/sprites/config/marker_weapon_red.txt"));
		bomb = new Bombs (new Sprite ("resources/sprites/config/bomb_active_blue.txt"));
		triangle = new NinjaTriangle (new Sprite ("resources/sprites/config/stationary_ninja_triangle.txt"));
		Jeffrey.getInventory().addWeapon (microphone, 2);
		Jeffrey.getInventory().addWeapon(gun, 0);
		Jeffrey.getInventory().addWeapon(triangle, 0);
		Jeffrey.getInventory().addWeapon(sword, 1);
		Jeffrey.getInventory().addWeapon(marker, 2);
		Jeffrey.getInventory().addWeapon(bomb, 2);
		int x = 0;
		//boi = new Celing_boi();
		waffle = new Waffle (4);
		testLadder = new Ladder ();
		paintball = new RedBlackPaintBall(1);
		testSceen = new Cutsceen ("resources/cutsceenConfig/breakTest.txt");
		box = new Box();
		plant = new Plant ();
		//shooter = new MafiaShooter();
		//boi = new Celing_boi();
		//waffle = new Waffle (4);
		//puncuation = new Puncuation ();
		//hydrant = new FireHydrant ();
		//slow = new MoveSlowEvent (hydrant, 100, 100, 0, 30, 0, 3, -3);
		//newFly = new CreepyButterfly();
		testLadder = new Ladder ();
		paintball = new RedBlackPaintBall(1);
		testSceen = new Cutsceen ("resources/cutsceenConfig/breakTest.txt");
		//candle = new Candle ();
		//box = new Box();
		//plant = new Plant ();
		paint = new BluePaint (1);
		while (x <40) {
		Jeffrey.inventory.addAmmo(paintball);
		Jeffrey.inventory.addAmmo(paint);
		x = x + 1;
		}
		//testTie.declare (32, 32);
	//	Jeffrey.getInventory().addKill(boi);
		//Jeffrey.getInventory().addKill(puncuation);
		//cam = new VectorCamera (0, 0);
		//Uncomment the above line if you want to see them
		//GameObject declaration
		//textbox = new Textbox ();
		//tbox = new Tbox (0, 32, 16, 2, "HELLOTHISISTHEAWESOMEJEFFREY1234567890ANDTHEREARELOTSOFLINESTOTHISTEXT!");
		//ltbox = new ListTbox (0, 128, new String[] {"OPTION A", "OPTION B", "OPTION C"});
		//WARNING: LOADING A ROOM PURGES ALL THE OBJECTS USING THE FORGET METHOD
		//Add the following to an object to a class to keep it around: @Override public void forget () {}
		Room.loadRoom ("resources/maps/PitStuff.rmf");
		//bleh = new AnimeTester ();
		//bleh.declare (0, 0);
		gui = new Gui ();
		//ufo = new UFO();
		//split = new SplitSlimelet ();
		//new TestObject ().declare (128, 200);
		//new Slimelet ().declare (200, 400);// From when I was messing around with slimelets =P
		//td = new TopDown ();
		testLaddder.declare(150, 373);
		//stop.declare(150, 200);
	//	ball.declare(200,0);
		//bug1.declare(400, 180);
		/*bug2.declare(425, 175);
		//leg.declare(150, 200);
	//	boi.declare(40,300);
		//newFly.declare(180,350);
		//trigger.declare(180,350);
		//bound = new HitboxRightBottomBound();
		//bound.declare(180, 300);
		//waffle.declare(150, 200);
		//trigger = new Trigger();
		//trigger.declare(60,120);
		//showTank.declare(280, 383);
		//puncuation.declare(300, 200);
		bleh.declare(0,0);
		bug3.declare(450, 125);*/
		/*bug4.declare(225, 150);
		bug5.declare(250, 125);
		bug6.declare(450, 200);
		bug7.declare(450, 150);
		bug8.declare(225, 175);*/
		//slimelet.declare(180, 300);
		//hydrant.declare(40,390);
		//car.declare(300,340);
		//spawner.declare (300,320);
		//testCrab.declare (20,300);
		//shooter.declare(250,300);
		/*point = new Point (418,343);
		point.declare(384,483);
		Point testPoint = new Point (400,180);
		testPoint.declare(185, 380);
		System.out.println(testPoint.generatePath(point));*/
		//function.declare(120, 300);
		//marsh.declare(200,320);
		//ufo.declare(200,120);
		//work.declare(100,200);
		//candle.declare(200,200);
		fall.declare(300, 100);
		//test.declare(300,300);
		//test.Break(new Sprite [] {new Sprite ("resources/sprites/shard.png")}, 7, 1, 2, 0,3.14);
		//plant.declare(320,300);
		//box.declare(300,35);
	}
	
	public static void beforeGameLogic () {
	}
	
	public static void afterGameLogic () {
	}
	
	public static void beforeRender () {
		Room.render ();
		
	}
	
	public static void afterRender () {
		AfterRenderDrawer.drawAll();
	}
}
