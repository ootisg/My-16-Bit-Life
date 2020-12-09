package vector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import main.GameAPI;
import map.Room;

public class Vector2D {

	public double x;
	public double y;
	
	/**
	 * Creates a new vector with the given x and y values
	 * (offset vector from (0, 0) to (x, y))
	 * @param x the x-coordinate of this vector
	 * @param y the y-coordinate of this vector
	 */
	public Vector2D (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Creates a new vector of the given point
	 * (offset vector from (0, 0) to p)
	 * @param p the point to use
	 */
	public Vector2D (Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	/**
	 * Creates a new offset vector from (xOrigin, yOrigin) to (xTo, yTo)
	 * @param xOrigin the x-coordinate of the origin point
	 * @param yOrigin the y-coordinate of the origin point
	 * @param xTo the x-coordinate of the destination point
	 * @param yTo the y-coordinate of the destination point
	 */
	public Vector2D (double xOrigin, double yOrigin, double xTo, double yTo) {
		this.x = xTo - xOrigin;
		this.y = yTo - yOrigin;
	}
	
	/**
	 * Creates a new offset vector from origin -> to
	 * @param origin the origin point of the vector
	 * @param to the destination point of the vector
	 */
	public Vector2D (Point origin, Point to) {
		this.x = to.x - origin.x;
		this.y = to.y - origin.y;
	}
	
	/**
	 * Creates a new offset vector for the given pair of vectors
	 * @param from the vector to offset from
	 * @param to the vector to offset to
	 */
	public Vector2D (Vector2D from, Vector2D to) {
		this.x = to.x - from.x;
		this.y = to.y - from.y;
	}
	
	/**
	 * Computes the dot product of this vector with the given vector v
	 * @param v the vector to compute the dot product with
	 * @return the dot product of the two vectors
	 */
	public double getDot (Vector2D v) {
		return v.x * this.x + v.y * this.y;
	}
	
	/**
	 * Computes the sum of this vector with the given vector v
	 * @param v the vector to compute the sum with
	 * @return the sum of the two vectors
	 */
	public Vector2D getSum (Vector2D v) {
		return new Vector2D (v.x + this.x, v.y + this.y);
	}
	
	/**
	 * Scales this vector by the given scalar
	 * @param d the amount to scale by
	 * @return the scaled vector
	 */
	public Vector2D getScaled (double d) {
		return new Vector2D (this.x * d, this.y * d);
	}
	
	/**
	 * Gets the magnitude of this vector
	 * @return the magnitude of the vector
	 */
	public double getMagnitude () {
		return Math.sqrt (x * x + y * y);
	}
	
	/**
	 * Returns a normalized version of this vector
	 * @return the normalized vector
	 */
	public Vector2D getNormalized () {
		double magnitude = getMagnitude ();
		return new Vector2D (x / magnitude, y / magnitude);
	}
	
	public void draw (double x, double y) {
		double startX = x - Room.getViewX ();
		double startY = y - Room.getViewY ();
		double endX = this.x + x - Room.getViewX ();
		double endY = this.y + y - Room.getViewY ();
		Graphics2D g = (Graphics2D)GameAPI.getWindow ().getBufferGraphics ();
		g.setColor (new Color (0x0000FF));
		g.drawLine ((int)startX, (int)startY, (int)endX, (int)endY);
	}
	
	@Override
	public String toString () {
		return "[" + x + "," + y + "]";
	}
	
}
