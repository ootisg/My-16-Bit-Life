package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Stack;

import main.GameObject;
import main.RenderLoop;
import map.Room;

public class DarkOverlay extends GameObject {
	public static ArrayList <LightSource> lightSources = new ArrayList <LightSource> ();
	public DarkOverlay () {
		this.setRenderPriority(69);
	}
	public static void addLightSources (LightSource newObject) {
		lightSources.add(newObject);
	}
	public static void removeLightSources (LightSource newObject) {
		lightSources.remove(newObject);
	}
	@Override
	public void draw () {
		Stack <PP> ppList= new Stack <PP> ();
		for (int i = 0; i < lightSources.size();i++) {
			ppList.add(new PP ((int)lightSources.get(i).getVeiwport().getY(),lightSources.get(i)));
			ppList.add(new PP ((int)lightSources.get(i).getVeiwport().getY() + lightSources.get(i).getVeiwport().height,lightSources.get(i)));
		}
		Collections.sort(ppList);
		Graphics g = RenderLoop.window.getBufferGraphics();
		g.setColor(new Color (0x000000));
		int currentPosition = 0;
		ArrayList <PP>temporaryList = new ArrayList <PP> ();
		if (!ppList.isEmpty()) {
		if (ppList.peek().pointPos != 0) {
			/// ha ha it diden't work(?)
			g.fillRect(0, 0, Room.getWidth()*16, ppList.peek().pointPos);
		}
		} else {
			g.fillRect(0, 0, Room.getWidth()*16,Room.getHeight()*16);
		}
		while (!ppList.isEmpty()) {
			
			currentPosition = ppList.peek().pointPos;
			PP working;
			do {
			
				working = ppList.pop();
				boolean feelingItNowMrKrabs = false;
				for (int i = 0; i < temporaryList.size(); i++) {
					if (temporaryList.get(i).pairedObject == working.pairedObject) {
						temporaryList.remove(i);
						feelingItNowMrKrabs = true;
						break;
					}
				}
				if (!feelingItNowMrKrabs) {
					temporaryList.add(working);
				} 
				if (ppList.isEmpty()) {
					break;
				}
			} while (ppList.peek().pointPos == currentPosition);
			ArrayList <PP> perminteListNotReally = new ArrayList<PP>();
			while (perminteListNotReally.size() != temporaryList.size()) {
				int lowestNum = 42000000;
				int index = 0;
				for (int i = 0; i < temporaryList.size(); i++) {
					if (temporaryList.get(i).pairedObject.getVeiwport().x < lowestNum && !perminteListNotReally.contains(temporaryList.get(i))) {
						lowestNum = temporaryList.get(i).pairedObject.getVeiwport().x;
						index = i;
					}
				}
				perminteListNotReally.add(temporaryList.get(index));
			}
			int endPoint;
			if (!perminteListNotReally.isEmpty()) {
				endPoint = perminteListNotReally.get(0).pairedObject.getVeiwport().y + perminteListNotReally.get(0).pairedObject.getVeiwport().height;
				g.fillRect(0, currentPosition, perminteListNotReally.get(0).pairedObject.getVeiwport().x, endPoint);
			} else {
				endPoint = Room.getHeight() * 16;
				g.fillRect(0, currentPosition, Room.getWidth()*16, endPoint);
			}
			for (int i = 0; i < perminteListNotReally.size(); i++) {
				if (!perminteListNotReally.get(i).isDrawn) {
				g.drawImage(perminteListNotReally.get(i).pairedObject.getOverlay(), perminteListNotReally.get(i).pairedObject.getVeiwport().x, currentPosition, null);
				perminteListNotReally.get(i).isDrawn = true;
				}
				if (i != perminteListNotReally.size() -1) {
					g.fillRect((int) (perminteListNotReally.get(i).pairedObject.getVeiwport().x + perminteListNotReally.get(i).pairedObject.getVeiwport().getWidth()) , currentPosition, perminteListNotReally.get(i + 1).pairedObject.getVeiwport().x, endPoint);
				} else {
					g.fillRect((int) (perminteListNotReally.get(i).pairedObject.getVeiwport().x + perminteListNotReally.get(i).pairedObject.getVeiwport().getWidth()) , currentPosition, Room.getWidth()*16, endPoint);
				}
			}
		}
	}
	// short for "paired point" (or penis)
	public class PP implements Comparable {
		int pointPos;
		LightSource pairedObject;
		boolean isDrawn = false;
		public PP (int pointPos, LightSource pairedObject) {
			this.pointPos = pointPos;
			this.pairedObject = pairedObject;
		}
		@Override
		public int compareTo(Object o) {
			PP working = null;
			if (o instanceof PP) {
			working = (PP) o;
			}
			return  working.pointPos - this.pointPos ;
		}
	}
}
