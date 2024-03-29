	package main;

import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import cutsceens.Cutsceen;
import cutsceens.MoveSlowEvent;
import enemys.BatRat;
import enemys.Bee;
import enemys.BuggyBoi;
import enemys.Cactus;
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
import enemys.FireRextinguisher;
import enemys.FishRing;
import enemys.FlowerEnemy;
import enemys.GreenBlob;
import enemys.ImpatientCar;
import enemys.LazerHoverEnemy;
import enemys.MafiaShooter;
import enemys.Marshan;
import enemys.MissleadingStop;
import enemys.Puncuation;
import enemys.RollingChairCavelry;
import enemys.Sheep;
import enemys.SpearThrower;
import enemys.SplitSlimelet;
import enemys.SplittingSlimelet;
import enemys.TomatoFunction;
import enemys.UFO;
import enemys.WaterBlob;
import enemys.Zombee;
import enemys.ZombeeTreeBoss;
import gameObjects.AnimeTester;
import gameObjects.BreakableObject;
import gameObjects.CameraObject;
import gameObjects.CarSpawner;
import gameObjects.DarkOverlay;
import gameObjects.DashPad;
import gameObjects.FallingSpike;
import gameObjects.FishHook;
import gameObjects.Glider;
import gameObjects.HitboxRightBottomBound;
import gameObjects.Ladder;
import gameObjects.LaunchPlant;
import gameObjects.Leg;
import gameObjects.LightSource;
import gameObjects.Minecart;
import gameObjects.Plant;
import gameObjects.Point;
import gameObjects.SmallProjectileLauncher;
import gameObjects.SwingingVine;
import gameObjects.TemporaryWall;
import graphics3D.VectorCamera;
import gui.Gui;
import gui.ListTbox;
import gui.Tbox;
import gui.Textbox;
import items.Carpet;
import items.FairUseKey;
import items.Jetpack;
import items.LemonPacket;
import items.PogoStick;
import items.Waffle;
import json.JSONException;
import json.JSONObject;
import map.Room;
import mapObjects.Box;
import mapObjects.BubblePlatform;
import mapObjects.Door;
import mapObjects.Fan;
import mapObjects.MoveingPlatform;
import npcs.NonPlayableJeffrey;
import npcs.NonPlayableRyan;
import npcs.NonPlayableSam;
import npcs.PointGuy;
import players.PartyManager;
import players.Player;
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
import switches.Activateable;
import theHeist.Worker;
import triggers.CutsceenTrigger;
import triggers.Trigger;
import weapons.AimableWeapon;
import weapons.Bombs;
import weapons.BubbleGun;
import weapons.FireExtingueser;
import weapons.LaserPointer;
import weapons.LifeVaccum;
import weapons.MagicMarker;
import weapons.MagicMicrophone;
import weapons.NinjaTriangle;
import weapons.SlimeSword;
import weapons.redBlackPaintBallGun;

public class GameCode {
	
	static long frameNum;
	
	//GameObjects
	static TubeRaster raster;
	static AimableWeapon wpn;
	static CreepyButterfly newFly;
	static DuoflyPlus testFly;
	static DuoflyMinus testFly2;
	public static Player J;
	public static SoundPlayer player;
	//static TopDown td;
	Textbox textbox;
	static Celing_boi boi;
	static SmallProjectileLauncher testLauncher;
	static VectorCamera cam;
	ListTbox ltbox;
	static CannonTankEnemy showTank;
	static CyclopesCrab testCrab;
	//static CutsceenTrigger trigger;
	static PointGuy guy;
	static ClostridiumBowtielinea testTie;
	static Status testStatus;
	
	static redBlackPaintBallGun gun;
	static Ladder testLadder;
	static Leg leg;
	static SlimeSword sword;
	static MagicMicrophone microphone;
	static MagicMarker marker;
	static Bombs bomb;
	static TomatoFunction function;
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
	static Door door;
	static AnimeTester bleh;
	static MoveingPlatform platform;
	static Cutsceen testSceen;
	static UFO ufo;
	static SplitSlimelet split;
	static DarkOverlay lay;
	static Fan fan;
	static Sheep sheep;
	static SplittingSlimelet splitting;
	static SpearThrower spearGuy;
	static Glider glider;
	static DashPad pad;
	static FireExtingueser extinguser;
	static LaunchPlant lPlant;
	static FlowerEnemy flower;
	static Minecart cart;
	static Cactus cactus;
	static FireRextinguisher fire;
	static items.FanItem pack;
	static BatRat rat;

	static BubbleGun bubbleGun;
	static BubblePlatform bubble;
	static FallingSpike spike;
	static Bee bee;
	static ZombeeTreeBoss boss;
	static SwingingVine vine;
	
	static FishHook hook;
	
	static FishRing ring;
	
	static Gui gui;
	
	static PartyManager partyManager;

	public static int targetZoomX;
	public static int targetZoomY;
	static int zoomSpeed;
	
	static String jsonTest = ""
			+ "{"
			+ "\"JSON\":\"TRUE\","
			+ "\"OBJ\":{\"JSON\":{\"JSON2\":{\"JSON4\":\"CORRECT\"},\"ARR\":[1,2,{\"JSON5\":\"HELLO WORLD\"},3,4,[2,4,6,7],9]},\"JSON3\":\"TESTING\"}"
			+ "}";
	public static void initialize () {
		//RenderLoop.window.setResolution(960, 540);
		targetZoomX = RenderLoop.window.getResolution()[0];
		targetZoomY = RenderLoop.window.getResolution()[1];
		//zoomTo (540, 320 , 4);
		Room.loadRoom ("resources/maps/figtingJeffreyTest.rmf");
		//GameObject initialization
		player = new SoundPlayer ();
		//fire = new FireRextinguisher ();
		testLauncher = new SmallProjectileLauncher();
		gui = new Gui ();
		extinguser = new FireExtingueser ();
		lameJeffrey = new NonPlayableJeffrey();
		lameSam = new NonPlayableSam();
		lameRyan = new NonPlayableRyan();
		gun = new redBlackPaintBallGun();
		pointer = new LaserPointer ();
		key = new FairUseKey();
		guy = new PointGuy();
		work = new Worker ();
		door = new Door ();
		pack = new items.FanItem();
		fall = new FallingChandleer ();
		test = new BreakableObject ();
		lay = new DarkOverlay();
		packet = new LemonPacket();
		pad = new DashPad ();
		cart = new Minecart ();
		vine = new SwingingVine();
		hook = new FishHook ();
		//bubble = new BubblePlatform();
		//bee = new Bee ();
		//boss = new ZombeeTreeBoss ();
		Player.getInventory().addWeapon(extinguser);
		Player.getInventory().addFreind(lameJeffrey);
		Player.getInventory().addFreind(lameSam);
		Player.getInventory().addFreind(lameRyan);
		Player.getInventory().addFreind(guy);
		//Jeffrey.getInventory().addKeyItem(key);
		Player.getInventory().addConsumable(packet);
		vaccum = new LifeVaccum ();
		Player.getInventory().addWeapon(vaccum);
		Player.getInventory().addWeapon(pointer);
		sword = new SlimeSword();
		microphone = new MagicMicrophone ();
		marker = new MagicMarker ();
		bubbleGun = new BubbleGun();
		bomb = new Bombs ();
		triangle = new NinjaTriangle ();
		Player.getInventory().addWeapon (microphone);
		Player.getInventory().addWeapon(gun);
		Player.getInventory().addWeapon(triangle);
		Player.getInventory().addWeapon(sword);
		Player.getInventory().addWeapon(marker);
		Player.getInventory().addWeapon(bomb);
		Player.getInventory().addWeapon(bubbleGun);
		int x = 0;
		//boi = new Celing_boi();
		waffle = new Waffle ();
		testLadder = new Ladder ();
		//testSceen = new Cutsceen ("resources/cutsceenConfig/breakTest.txt");
		box = new Box();
		plant = new Plant ();
		glider =new Glider();
		lPlant = new LaunchPlant ();
		bubble = new BubblePlatform (-5);
		//shooter = new MafiaShooter();
		//boi = new Celing_boi();
		//waffle = new Waffle (4);
		//puncuation = new Puncuation ();
		//hydrant = new FireHydrant ();
		//slow = new MoveSlowEvent (hydrant, 100, 100, 0, 30, 0, 3, -3);
		//newFly = new CreepyButterfly()
		//candle = new Candle ();
		//box = new Box();
		//plant = new Plant ();
		fan = new Fan();
		ring = new FishRing ();
		bomb.giveAmmo(40);
		gun.giveAmmo(40);
		gun.giveSecondaryAmmo(40);
	//	spike = new FallingSpike ();
		//LightSource.writeLightSourceImage (36, 255, 200, 0, 120, "resources/sprites/overlays/candleOverlay.png"); //This one is yellow-ish
		//LightSource.writeLightSourceImage (36, 0, 200, 255, 120, "resources/sprites/overlays/candleOverlay.png"); //This one is blue-ish
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

		//bleh = new AnimeTester ()
		//function = new TomatoFunction (100,300);
		//bleh.declare (0, 0);
	
		//ufo = new UFO();
		//split = new SplitSlimelet ();
		//new TestObject ().declare (128, 200);
		//new Slimelet ().declare (200, 400);// From when I was messing around with slimelets =P
		//td = new TopDown ();
	
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
		//ufo.declare(200,120);
		//work.declare(100,200);
		//candle.declare(200,200);
		//fall.declare(300, 100);
		//door.declare(100,350);
		//test.declare(300,300);
		//test.Break(new Sprite [] {new Sprite ("resources/sprites/shard.png")}, 7, 1, 2, 0,3.14);
		//plant.declare(320,300);
		//box.declare(300,35);d
		//splitting.declare(100,100);
		//work.declare(100,100);
		//glider.declare(60, 60);
		//pad.declare(100,350);
		//lPlant.declare(100,350);
		//flower.declare(100, 250);
		//cart.declare(400, 100);
		//cactus.declare(100,100);
		//pack.declare(100, 0);
		//fire.declare(100,400);
		//bubble.declare(100, 300);
		//bee.declare (100, 180);
		//boss.declare(850, 530);
		
		//ring.declare(300,350);
		
	}
	public static void beforeGameLogic () {
		
	}
	
	public static void afterGameLogic () {
		
	}
	
	public static void beforeRender () {
		zoom(targetZoomX, targetZoomY, zoomSpeed);
		Room.render ();
	}
	
	public static void afterRender () {
		frameNum = frameNum + 1;
		AfterRenderDrawer.drawAll();
	}
	public static long getFrameNum () {
		return frameNum;
	}
	public static void zoomTo (int x, int y, int speed) {
		targetZoomX = x;
		targetZoomY = y;
		zoomSpeed = speed;
	}
	
	public static PartyManager getPartyManager() {
		return partyManager;
	}
	public static void refreshPartyManager() {
		GameCode.partyManager = new PartyManager ();
	}
	
	public static void zoom (int newResX, int newResY, int speed) {
		if (newResX != RenderLoop.window.getResolution()[0] && newResY != RenderLoop.window.getResolution()[1]) {
			int resDistX = (RenderLoop.window.getResolution()[0] - newResX);
			int resDistY = (RenderLoop.window.getResolution()[1] - newResY);
			
			double resMoveX = 0;
			double resMoveY = 0;
			
			if (resDistX < 0) {
				resDistX = resDistX * -1;
			}
			if (resDistY < 0) {
				resDistY = resDistY * -1;
			}
			
			if (resDistX > resDistY) {
				resMoveX = (resDistX/resDistY) * speed;
				resMoveY = (resDistY/resDistY) * speed;
			} else {
				resMoveX = (resDistX/resDistX) * speed;
				resMoveY = (resDistY/resDistX) * speed;
			}
			
			if (RenderLoop.window.getResolution()[0] > newResX) {
				resMoveX = resMoveX * -1;
			}
			if (RenderLoop.window.getResolution()[1] > newResY) {
				resMoveY = resMoveY * -1;
			}
			RenderLoop.window.setResolution((int)Math.ceil(RenderLoop.window.getResolution()[0] + resMoveX), (int)Math.ceil(RenderLoop.window.getResolution()[1] + resMoveY));
			
			
			if ((resMoveX < 0 && RenderLoop.window.getResolution()[0] < newResX) || (RenderLoop.window.getResolution()[0] > newResX && resMoveX > 0) || (resMoveY < 0 && RenderLoop.window.getResolution()[1] < newResY) || (RenderLoop.window.getResolution()[1] > newResY && resMoveY > 0)) {
				RenderLoop.window.setResolution(newResX, newResY);
			}
		}
	}
	
}
