package gameObjects;

import main.GameObject;
import map.Room;
import map.TileData;

public class KinematicObject extends GameObject {
	private double vx;
	private double vy;
	private double ax;
	private double ay;
	public void physicsFrame () {
		ay = Room.getGravity ();
		vy += ay;
		double[] newCoords = Room.doHitboxVectorCollison (this.hitbox (), this.getX () + vx, this.getY () + vy);
		boolean collisionDetected = true; //set to true for initial loop condition
		if (newCoords != null) {
			while (collisionDetected && newCoords != null && !(this.vx == 0 && this.vy == 0)) {
				collisionDetected = false;
				byte direction = -1;
				//-1 is no direction
				//0 is up
				//1 is left
				//2 is down
				//3 is right
				if (newCoords [0] % 16 != 0 || newCoords [1] % 16 != 0) {
					if (Room.tileBuffer.collisionX % 16 == 0 && (int)Room.tileBuffer.collisionX / 16 == Room.tileBuffer.mapTile.x) {
						direction = 3;
					} else if (Room.tileBuffer.collisionX % 16 == 0 && (int)Room.tileBuffer.collisionX / 16 - 1 == Room.tileBuffer.mapTile.x) {
						direction = 1;
					} else if (Room.tileBuffer.collisionY % 16 == 0 && (int)Room.tileBuffer.collisionY / 16 == Room.tileBuffer.mapTile.y) {
						direction = 2;
					} else if (Room.tileBuffer.collisionY % 16 == 0 && (int)Room.tileBuffer.collisionY - 1 / 16 == Room.tileBuffer.mapTile.y) {
						direction = 0;
					}
					if (vx == 0) {
						if (vy < 0) {
							direction = 0;
						} else if (vy > 0) {
							direction = 2;
						}
					} else if (vy == 0) {
						if (vx < 0) {
							direction = 1;
						} else if (vx > 0) {
							direction = 3;
						}
					}
				} else {
					int tileXOffset = 1;
					int tileYOffset = 1;
					if (vx > 0) {
						tileXOffset = -1;
					}
					if (vy > 0) {
						tileYOffset = -1;
					}
					boolean tileVert = Room.isSolid (Room.tileBuffer.mapTile.x, Room.tileBuffer.mapTile.y + tileYOffset);
					boolean tileHoriz = Room.isSolid (Room.tileBuffer.mapTile.x + tileXOffset, Room.tileBuffer.mapTile.y);
					if (!tileVert && !tileHoriz) {
						System.out.println("Something hit a corner");
						direction = -1;
					}
					if (!tileVert && tileHoriz) {
						if (tileYOffset == 1) {
							direction = 2;
						} else {
							direction = 0;
						}
					}
					if (tileVert && !tileHoriz) {
						//System.out.print(tileXOffset);
						//System.out.print(", ");
						//System.out.println(tileYOffset);
						if (tileXOffset == 1) {
							direction = 3;
						} else {
							direction = 1;
						}
					}
					if (tileVert && tileHoriz) {
						System.out.println("YOYOYOYO");
						if (vy == 0) {
							if (vx < 0) {
								direction = 1;
							} else {
								direction = 3;
							}
						} else {
							if (vy < 0) {
								direction = 0;
							} else {
								direction = 2;
							}
						}
						/*if (tileXOffset == -1 && tileYOffset == -1) {
							direction = 4;
						}
						if (tileXOffset == 1 && tileYOffset == -1) {
							direction = 5;
						}
						if (tileXOffset == -1 && tileYOffset == 1) {
							direction = 6;
						}
						if (tileXOffset == 1 && tileYOffset == 1) {
							direction = 7;
						}*/
					}
				}
				//System.out.println(direction);
				if (direction == 3 || direction == 1) {
					TileData workingData = Room.getTileAttributesList ().getTile (Room.tileBuffer.mapTile.tileId);
					double frictionCoefficient;
					if (workingData != null) {
						frictionCoefficient = workingData.frictionCoefficient;
					} else {
						frictionCoefficient = 1.0;
					}
					this.setX ((newCoords [0]));
					this.vx = 0;
					if (this.vy < 0) {
						this.vy += ax * frictionCoefficient;
						if (this.vy > 0) {
							this.vy = 0;
						}
					} else {
						this.vy -= ax * frictionCoefficient;
						if (this.vy < 0) {
							this.vy = 0;
						}
					}
					collisionDetected = true;
				}
				if (Room.tileBuffer.collisionX == (double)(Room.tileBuffer.mapTile.x * 16 - 16)) {
					
				}
				if (direction == 0 || direction == 2) {
					TileData workingData = Room.getTileAttributesList ().getTile (Room.tileBuffer.mapTile.tileId);
					double frictionCoefficient;
					if (workingData != null) {
						frictionCoefficient = workingData.frictionCoefficient;
					} else {
						frictionCoefficient = 1.0;
					}
					this.setY (newCoords [1]);
					this.vy = 0;
					if (this.vx < 0) {
						this.vx += ay * frictionCoefficient;
						if (this.vx > 0) {
							this.vx = 0;
						}
					} else {
						this.vx -= ay * frictionCoefficient;
						if (this.vx < 0) {
							this.vx = 0;
						}
					}
					collisionDetected = true;
				}
				if (Room.tileBuffer.collisionY == (double)(Room.tileBuffer.mapTile.y * 16 - 16)) {
						
				}
				/*System.out.print(vx);
				System.out.print(", ");
				System.out.println(vy);*/
				//collisionDetected = false;
				newCoords = Room.doHitboxVectorCollison (this.hitbox (), this.getX () + vx, this.getY () + vy);
			}
		}
		this.setX (this.getX () + vx);
		this.setY (this.getY () + vy);
	}
	public double getVelocityX () {
		return vx;
	}
	public double getVelocityY () {
		return vy;
	}
	public double getAccelerationX () {
		return ax;
	}
	public double getAccelerationY () {
		return ay;
	}
	public void setVelocityX (double vx) {
		this.vx = vx;
	}
	public void setVelocityY (double vy) {
		this.vy = vy;
	}
	public void setAccelerationX (double ax) {
		this.ax = ax;
	}
	public void setAccelerationY (double ay) {
		this.ay = ay;
	}
}
