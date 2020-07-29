package gameObjects;

import java.util.ArrayList;

import main.GameObject;
import main.XposComparator;
import map.Room;
import resources.Sprite;

public class Rail extends GameObject {
	private int direction = 0;
	private boolean inzialized = false;
	
	private static ArrayList <Rail> sortedRailList = new ArrayList<Rail>();
	public Rail () {
		this.setHitboxAttributes(0, 0, 16, 16);
		this.enablePixelCollisions();
		//this.adjustHitboxBorders();
		this.setSprite(new Sprite ("resources/sprites/railStrate.png"));
		
	}
	@Override 
	public void frameEvent () {
		if (!inzialized) {
			sortedRailList.add(this);
			if (this.getVariantAttribute("Direction") != null) {
				switch (this.getVariantAttribute("Direction")) {
				case "Strate":
					this.direction = 0;
					this.setHitboxAttributes(0, 0, 16, 16);
					this.setSprite(new Sprite ("resources/sprites/railStrate.png"));
					break;
				case "UpLeft":
					this.direction = 1;
					this.setSprite(new Sprite ("resources/sprites/railDownRight.png"));
					break;
				case "UpRight":
					this.direction = 2;
					this.setSprite(new Sprite ("resources/sprites/railUpRight.png"));
					break;
				}
			} else {
				this.direction = 0;
				this.setHitboxAttributes(0, 0, 16, 16);
				this.setSprite(new Sprite ("resources/sprites/railStrate.png"));
			}
			inzialized  = true;
			this.getAnimationHandler().scale(16, 16);
		}
}
	/** zero indicates a strate rail
	* one indicates a rail that goes up and to the left
	* two indicates a rail that goes up and to the right
	* 
	* @return the proper direction
	 */
	public int getDirection () {
		return direction;
	}
	public static void sortRails () {
		XposComparator comp = new XposComparator ();
		sortedRailList.sort(comp);
	}
	public static int getRailIndex (int xPos) {
		int startIndex = 0;
		int endIndex = sortedRailList.size();
		int breakIndex = -1;
		while (true) {
			int middleIndex = (startIndex +endIndex)/2;
			int middleXPos = (int) sortedRailList.get(middleIndex).getX();
			if (middleXPos == xPos) {
				breakIndex =middleIndex;
				break;
			} 
			if (xPos > middleXPos) {
				if (startIndex != middleIndex) {
				startIndex = middleIndex;
				} else {
					if (sortedRailList.get(startIndex).getX() == xPos) {
						breakIndex = startIndex;
						break;
					} else {
						return breakIndex;
					}
				}
			} else {
				if (endIndex != middleIndex) {
					endIndex = middleIndex;
					} else {
						if (sortedRailList.get(endIndex).getX() == xPos) {
							breakIndex = endIndex;
							break;
						} else {
							return breakIndex;
						}
					}
			}
			if (startIndex == endIndex) {
				if (sortedRailList.get(startIndex).getX() == xPos) {
					breakIndex = startIndex;
					break;
				} else {
					return breakIndex;
				}
			}	
		}
		while (sortedRailList.get(breakIndex).getX() == xPos) {
			breakIndex = breakIndex - 1;
			if (breakIndex == -1) {
				return 0;
			}
		}
		return ++breakIndex;
	}
	public static ArrayList <Rail>  getSortedList (){
		return sortedRailList;
	}
	public class railFinder {
		
		private int direction;
		
		private int steps = 0;
		
		private boolean xDirection;
		
		private static final int STRATE_RAIL_DIRCTION = 0;
		private static final int UPRIGHT_RAIL_DIRECTION = 2;
		private static final int DOWN_IF_RIGHT_UP_OTHERWISE_RAIL_DIRECTION = 1;
		
		private static final boolean LEFT = true;
		private static final boolean RIGHT = false;		
		
		private static final int STEPS = 5;
		

		public railFinder (int direction, boolean xDirection, int steps) {
			this.direction = direction;
			this.xDirection = xDirection;
			this.steps  = steps;
		}
		public RailSegment getFirstRail (double startX, double startY) {
			int startIndex = Rail.getRailIndex((int) startX);
			ArrayList <Rail> releventRails = getRelventRailsForwards(startIndex);
			if (xDirection) {
				startIndex = startIndex - 1;
			}
			double x = startX;
			double y = startY; 
			while (true) {
				int count = 0;
				ArrayList <Rail> reeeee = new ArrayList <Rail> ();
				for (int i = 0; i < releventRails.size(); i++) {
					if (y > releventRails.get(i).getY() - Room.TILE_HEIGHT && y < releventRails.get(i).getY() + Room.TILE_HEIGHT ) {
						count = count + 1;
						reeeee.add(releventRails.get(i));
					}
				}
				if (count == 0) {
					return new RailSegment (new Point (startX,startY), new Point (x,y));	
				}
				if (count > 1  || reeeee.get(0).getDirection() != this.direction) {
					return new RailSegment (new Point (startX,startY), new Point (x,y));
				} else {
					if (xDirection) {
						releventRails = this.getRelventRailsBackwards(startIndex);
						startIndex = startIndex - releventRails.size();
						x = x - Room.TILE_WIDTH;
						if (this.direction == DOWN_IF_RIGHT_UP_OTHERWISE_RAIL_DIRECTION) {
							y = y - Room.TILE_HEIGHT;
						} else if (this.direction == UPRIGHT_RAIL_DIRECTION) {
							y = y + Room.TILE_HEIGHT;
						}
					} else {
						startIndex = startIndex + releventRails.size();
						releventRails = this.getRelventRailsForwards(startIndex);
						x = x + Room.TILE_WIDTH;
						if (this.direction == DOWN_IF_RIGHT_UP_OTHERWISE_RAIL_DIRECTION) {
							y = y + Room.TILE_HEIGHT;
						} else if (this.direction == UPRIGHT_RAIL_DIRECTION) {
							y = y - Room.TILE_HEIGHT;
						}
					}
				}
			}
		}
		private ArrayList<Rail> getRelventRailsBackwards (int startIndex){
			int x = (int) Rail.sortedRailList.get(startIndex).x;
			ArrayList <Rail> working = new ArrayList <Rail>();
			startIndex = startIndex - 1;
			if (startIndex != -1) { //dont do that please 
				while (Rail.sortedRailList.get(startIndex).getX() == x) {
					working.add(Rail.sortedRailList.get(startIndex));
					startIndex = startIndex - 1;
					if (startIndex == -1) {
						break;
					}
				}
			}
			return working;
		}
		private ArrayList<Rail> getRelventRailsForwards (int startIndex){
			if (startIndex < sortedRailList.size()) {
				int x = (int) Rail.sortedRailList.get(startIndex).x;
				ArrayList <Rail> working = new ArrayList <Rail>();
				while (Rail.sortedRailList.get(startIndex).getX() == x) {
					working.add(Rail.sortedRailList.get(startIndex));
					startIndex = startIndex + 1;
					if (startIndex == Rail.sortedRailList.size()) {
						break;
					}
				}
				return working;
			} 
			return new ArrayList<Rail> ();
		}
		public void trackRail (RailSegment startSemgent) {
			int startIndex = Rail.getRailIndex((int) startSemgent.getEnd().x);
			if (startIndex == -1) {
				return;
			}
			if (steps == 0) {
				return;
			}
			ArrayList <Rail> releventRails = getRelventRailsForwards(startIndex);
			if (xDirection) {
				startIndex = startIndex - 1;
			}
			double y = startSemgent.getEnd().y;
			
			while (true) {
				int count = 0;
				ArrayList <Rail> reeeee = new ArrayList <Rail> ();
				for (int i = 0; i < releventRails.size(); i++) {
					if (y > releventRails.get(i).getY() - Room.TILE_HEIGHT && y < releventRails.get(i).getY() + Room.TILE_HEIGHT ) {
						count = count + 1;
						reeeee.add(releventRails.get(i));
					}
				}
				if (count == 0) {
					break;
				}
				if (count > 1  || reeeee.get(0).getDirection() != this.direction) {
					RailSegment generatedSegment = new RailSegment (startSemgent, new Point (releventRails.get(0).getX(),y));
					for (int i = 0; i <reeeee.size(); i++) {
						
						if (reeeee.size() == 1) {
							startSemgent.setNext(generatedSegment);
							railFinder find = new railFinder (reeeee.get(i).getDirection(),this.xDirection,this.steps -1 );
							find.trackRail(generatedSegment);
						} else {
							switch (this.direction) {
							case STRATE_RAIL_DIRCTION:
								if (reeeee.get(i).getDirection() == STRATE_RAIL_DIRCTION) {
									if (!(keyDown ('W') || keyDown ('D'))) {
										railFinder find = new railFinder (reeeee.get(i).getDirection(),this.xDirection,this.steps -1 );
										find.trackRail(generatedSegment);
									} else if (reeeee.get(i).getDirection() == UPRIGHT_RAIL_DIRECTION) {
										if (keyDown ('W')) {
											railFinder find = new railFinder (reeeee.get(i).getDirection(),this.xDirection,this.steps -1 );
											find.trackRail(generatedSegment);
										}
									} else {
										if (keyDown ('D')) {
											railFinder find = new railFinder (reeeee.get(i).getDirection(),this.xDirection,this.steps -1 );
											find.trackRail(generatedSegment);
										}
									}
								}
								break;
							case UPRIGHT_RAIL_DIRECTION:
								if (reeeee.get(i).getDirection() == STRATE_RAIL_DIRCTION) {
									if ((keyDown ('W') && !xDirection) || (keyDown ('D') && xDirection)) {
										railFinder find = new railFinder (reeeee.get(i).getDirection(),this.xDirection,this.steps -1 );
										find.trackRail(generatedSegment);
									} else if (reeeee.get(i).getDirection() == UPRIGHT_RAIL_DIRECTION) {
										if (!(keyDown ('W') || keyDown ('D'))) {
											railFinder find = new railFinder (reeeee.get(i).getDirection(),this.xDirection,this.steps -1 );
											find.trackRail(generatedSegment);
										}
									}	
								}
								break;
							case DOWN_IF_RIGHT_UP_OTHERWISE_RAIL_DIRECTION:
								if (reeeee.get(i).getDirection() == STRATE_RAIL_DIRCTION) {
									if ((keyDown ('W') && xDirection) || (keyDown ('D') && !xDirection)) {
										railFinder find = new railFinder (reeeee.get(i).getDirection(),this.xDirection,this.steps -1 );
										find.trackRail(generatedSegment);
									} else if (reeeee.get(i).getDirection() == DOWN_IF_RIGHT_UP_OTHERWISE_RAIL_DIRECTION) {
										if (!(keyDown ('W') || keyDown ('D'))) {
											railFinder find = new railFinder (reeeee.get(i).getDirection(),this.xDirection,this.steps -1 );
											find.trackRail(generatedSegment);
										}
									}	
								}
								break;
							}
						}
					}
				} else {
					if (xDirection) {
						releventRails = this.getRelventRailsBackwards(startIndex);
						startIndex = startIndex - releventRails.size();
						if (this.direction == DOWN_IF_RIGHT_UP_OTHERWISE_RAIL_DIRECTION) {
							y = y - Room.TILE_HEIGHT;
						} else if (this.direction == UPRIGHT_RAIL_DIRECTION) {
							y = y + Room.TILE_HEIGHT;
						}
					} else {
						startIndex = startIndex + releventRails.size();
						releventRails = this.getRelventRailsForwards(startIndex);
						if (this.direction == DOWN_IF_RIGHT_UP_OTHERWISE_RAIL_DIRECTION) {
							y = y + Room.TILE_HEIGHT;
						} else if (this.direction == UPRIGHT_RAIL_DIRECTION) {
							y = y - Room.TILE_HEIGHT;
						}
					}
				}
			}
		}
	}
}
