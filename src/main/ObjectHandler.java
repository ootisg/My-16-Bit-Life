package main;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

/**
 * Stores and organizes references to all GameObjects, includes methods for searching and object interaction
 * @author nathan
 *
 */
public class ObjectHandler {
	
	/**
	 * Set to true when objects can be removed without an error; false otherwise
	 */
	private static boolean mutable = true;
	/**
	 * Stores all the classes currently in use, and their respective objects
	 */
	private static HashIndexedTree<String, LinkedList<GameObject>> classTrees = new HashIndexedTree <String, LinkedList<GameObject>> ("GameObject", null);
	/**
	 * The elements to add
	 */
	private static LinkedList<GameObject> addQueue = new LinkedList<GameObject> ();
	/**
	 * The elements to remove
	 */
	private static LinkedList<GameObject> removeQueue = new LinkedList<GameObject> ();
	/**
	 * ObjectHandler cannot be constructed.
	 */
	private ObjectHandler () {
		
	}

	/**
	 * Gets a list of all the objects of the given type.
	 * @param objName The name of the object's class, as given by getClass().getSimpleName() by default
	 * @return All the objects of the given type, as a linked list
	 */
	public static LinkedList<GameObject> getObjectsByName (String objName) {
		return classTrees.get (objName);
	}
	
	/**
	 * Gets a list of all the objects that are children of the given type.
	 * @param objName The name of the parent's class, as given by getClass().getSimpleName() by default
	 * @return All the objects which are children of the given type, in a two-dimensional linked list, grouped by type
	 */
	public static LinkedList<LinkedList<GameObject>> getChildrenByName (String objName) {
		return classTrees.getAllChildren (objName);
	}
	
	/**
	 * Inserts the given object into the object handler.
	 * @param obj The object to insert
	 */
	public static void insert (GameObject obj) {
		insert (obj, obj.getClass ().getSimpleName ());
	}
	
	/**
	 * Inserts an object with the given name into the object handler. Saves time by avoiding reflection.
	 * @param obj The object to insert
	 * @param name The type of the object, as a string
	 */
	public static void insert (GameObject obj, String name) {
		if (mutable) {
			LinkedList<GameObject> objList = getObjectsByName (name);
			if (objList == null) {
				addClass (obj);
				objList = getObjectsByName (name);
			}
			objList.add (obj);
		} else {
			addQueue.add (obj);
		}
	}
	
	/**
	 * Removes the given object from the object handler.
	 * @param obj The object to remove
	 * @return true of the object was successfully removed; false otherwise
	 */
	public static boolean remove (GameObject obj) {
		return remove (obj, obj.getClass ().getSimpleName ());
	}
	
	/**
	 * Removes the object with the given name from the object handler. Saves time by avoiding reflection.
	 * @param obj The object to remove
	 * @param name The type of the object, as a string
	 * @return true if the object was successfully removed; false otherwise
	 */
	private static boolean remove (GameObject obj, String name) {
		LinkedList<GameObject> objList = getObjectsByName (name);
		if (objList == null) {
			return false;
		}
		if (mutable) {
			return objList.remove (obj);
		} else {
			removeQueue.add (obj);
			return false;
		}
	}
	
	/**
	 * Checks for collision with all objects of a given type
	 * @param objType The type of object to check for collision with (given by getClass().getSimpleName() by default)
	 * @param object The object to check collision against
	 * @return A CollisionInfo object describing the collision, or lack thereof
	 */
	public static CollisionInfo checkCollision (String objType, GameObject object) {
		//Make a CollisionInfo object
		return new CollisionInfo (getColliding (objType, object));
	}
	
	//Helper method for collision checking
	private static LinkedList<GameObject> getColliding (String objType, GameObject object) {
		LinkedList<GameObject> checkList = getObjectsByName (objType);
		return getColliding (checkList, object);
	}
	
	//Helper method for collision checking
	private static CollisionInfo checkCollision (LinkedList<GameObject> objects, GameObject object) {
		//Make a CollisionInfo object
		return new CollisionInfo (getColliding (objects, object));
	}
	
	//Helper method for collision checking
	private static LinkedList<GameObject> getColliding (LinkedList<GameObject> objects, GameObject object) {
		LinkedList<GameObject> result = new LinkedList<GameObject> ();
		if (objects == null) {
			return result;
		}
		Iterator<GameObject> iter = objects.iterator ();
		while (iter.hasNext ()) {
			GameObject working = iter.next ();
			if (working.isColliding (object) && working != object) {
				result.add (working);
			}
		}
		return result;
	}
	
	/**
	 * Checks for collision against all objects which are children of the given type.
	 * @param parentType The type of the parent, as given by getClass().getSimpleName() by default
	 * @param object The object to check for collision against
	 * @return The CollisionInfo object generated by the collision
	 */
	public static CollisionInfo checkCollisionChildren (String parentType, GameObject object) {
		return new CollisionInfo (getCollidingChildren (parentType, object));
	}
	
	//Helper method for collision checking with children
	private static LinkedList<GameObject> getCollidingChildren (String parentType, GameObject object) {
		LinkedList<LinkedList<GameObject>> lists = getChildrenByName (parentType);
		LinkedList<GameObject> result = new LinkedList<GameObject> ();
		if (lists == null) {
			return result;
		}
		Iterator<LinkedList<GameObject>> iter = lists.iterator ();
		while (iter.hasNext ()) {
			result.addAll (getColliding (iter.next (), object));
		}
		return result;
	}
	
	/**
	 * Adds the class of obj to the class hierarchy stored in ObjectHandler.
	 * @param obj The GameObject whose class to add
	 */
	private static void addClass (GameObject obj) {
		Class<?> workingClass = obj.getClass ();
		Stack<Class<?>> toAdd = new Stack<Class<?>> ();
		while (!workingClass.getName ().equals ("main.GameObject") && getObjectsByName (workingClass.getSimpleName ()) == null) {
			toAdd.push (workingClass);
			workingClass = workingClass.getSuperclass ();
		}
		while (!toAdd.isEmpty ()) {
			Class<?> topClass = toAdd.pop ();
			LinkedList<GameObject> usedList;
			if (toAdd.isEmpty ()) {
				usedList = new LinkedList<GameObject> ();
			} else {
				usedList = null;
			}
			classTrees.addChild (topClass.getSuperclass ().getSimpleName (), topClass.getSimpleName (), usedList);
		}
	}
	
	/**
	 * Calls the frameEvent method of all GameObjects in ObjectHandler
	 */
	public static void callAll () {
		LinkedList<LinkedList<GameObject>> allObjs = getChildrenByName ("GameObject");
		LinkedList<GameObject> allObjsList = new LinkedList<GameObject> ();
		Iterator<LinkedList<GameObject>> allObjsIter = allObjs.iterator ();
		while (allObjsIter.hasNext ()) {
			allObjsList.addAll (allObjsIter.next ());
		}
		GameObject[] allObjsArray = allObjsList.toArray (new GameObject[0]);
		for (int i = 0; i < allObjsArray.length; i ++) {
			allObjsArray [i].frameEvent ();
		}
	}
	
	/**
	 * Calls the draw method of all GameObjects in ObjectHandler
	 */
	public static void renderAll () {
		lock ();
		LinkedList<LinkedList<GameObject>> allObjs = getChildrenByName ("GameObject");
		LinkedList<GameObject> allObjsList = new LinkedList<GameObject> ();
		Iterator<LinkedList<GameObject>> allObjsIter = allObjs.iterator ();
		while (allObjsIter.hasNext ()) {
			allObjsList.addAll (allObjsIter.next ());
		}
		GameObject[] allObjsArray = allObjsList.toArray (new GameObject[0]);
		unlock ();
		for (int i = 0; i < allObjsArray.length; i ++) {
			allObjsArray [i].draw ();
		}
	}
	
	/**
	 * Returns whether or not objects can be removed safely
	 * @return current mutability of ObjectHandler
	 */
	public static boolean isMutable () {
		return mutable;
	}
	
	/**
	 * Sets the mutability of this ObjectHandler to false
	 */
	public static void lock () {
		mutable = false;
	}
	
	/**
	 * Sets the mutability of this ObjectHandler to true. May throw an exception if interrupted.
	 */
	public static void unlock () {
		mutable = true;
		Iterator<GameObject> iter = addQueue.iterator ();
		while (iter.hasNext ()) {
			insert (iter.next ());
			iter.remove ();
		}
		iter = removeQueue.iterator ();
		while (iter.hasNext ()) {
			remove (iter.next ());
			iter.remove ();
		}
	}
}
