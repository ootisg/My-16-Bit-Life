package main;

import java.util.Comparator;

public class XposComparator implements Comparator<GameObject> {
	
	@Override
	public int compare(GameObject o1, GameObject o2) {
		return (int)(o2.getX() - o1.getX ());
	}

}
