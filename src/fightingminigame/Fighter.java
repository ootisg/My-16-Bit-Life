package fightingminigame;

import main.GameObject;
import map.MapTile;
import map.Room;
import resources.Sprite;

public class Fighter extends GameObject {
	
	//lag is stored in this order
	/*
	 * 0 = jab
	 * 1 = nutral special
	 * 2 = side spcial
	 * 3 = up special
	 * 4 = down special
	 * 5 = nutral air
	 * 6 = side air
	 * 7 = up air
	 * 8 = down air
	 * 9 = down ground
	 * 10 = up ground
	 * 11 = side ground
	 */
	int [] startLags = new int[12];
	int [] endLags = new int[12];
	
	boolean usingMove = false;
	
	int curLag = 0;
	
	int curStocks = 3;
	
	double damage = 0;
	
	double speed = 1;
	
	double jumpHeight = 1;
	
	double vx = 0;
	double vy = 0;
	
	int waitingOn = -1;
	
	boolean usedDoubleJump = false;
	boolean jumpReleased = false;
	
	long stagertill;
	
	
	
	public Fighter () {
		
	}
	
	@Override
	public void frameEvent () {
		if (this.isColliding("FightingObject")) {
			//TODO do some sort of damage calculatons here
		}
		
		if (!this.goY(this.getY() + vy)) {
			vy = 0;
		}
		
		//check if the player is trying to start any moves
		if (!usingMove && curLag == 0 && System.currentTimeMillis() > stagertill) {
			boolean jumpDown = keyPressed(32);
			
			boolean leftDown = keyDown('A');
			boolean rightDown = keyDown('D');
			boolean downDown = keyDown('S');
			boolean upDown = keyDown('W');
			
			boolean regularDown = mouseButtonPressed(0);
			boolean specialDown = mouseButtonPressed(2);
			
			boolean onGround = (vy == 0); //TODO detect if the fighter is on the ground
			boolean inAir = !onGround;
			
			if (!inAir) {
				usedDoubleJump = false;
			}
			
			//use ground moves
			if (regularDown && onGround) {
				if (leftDown && !rightDown) {
					waitingOn = 11;
					curLag = startLags[11];
				}
				else if (!leftDown && rightDown) {
					waitingOn = 14;
					curLag = startLags[11];
				}
				else if (upDown) {
					waitingOn = 10;
					curLag = startLags[10];
				}
				else if (downDown) {
					waitingOn = 9;
					curLag = startLags[9];
				} else {
					waitingOn = 0;
					curLag = startLags[0];
				}
			}
		
		//use air moves
		if (regularDown && inAir) {
			if (leftDown && !rightDown) {
				waitingOn = 6;
				curLag = startLags[6];
			}
			else if (!leftDown && rightDown) {
				waitingOn = 13;
				curLag = startLags[6];
			}
			else if (upDown) {
				waitingOn = 7;
				curLag = startLags[7];
			}
			else if (downDown) {
				waitingOn = 8;
				curLag = startLags[8];
			} else {
				waitingOn = 5;
				curLag = startLags[5];
			}
		}
		
		//use special moves
		if (specialDown) {
			if (leftDown && !rightDown) {
				waitingOn = 2;
				curLag = startLags[2];
			}
			else if (!leftDown && rightDown) {
				waitingOn = 12;
				curLag = startLags[2];
			}
			else if (upDown) {
				waitingOn = 3;
				curLag = startLags[3];
			}
			else if (downDown) {
				waitingOn = 4;
				curLag = startLags[4];
			} else {
				waitingOn = 1;
				curLag = startLags[1];
			}
		}
		
		//jumps
		if (jumpDown && !inAir && waitingOn == -1) {
			vy = -(12.15625 * jumpHeight);
			jumpReleased = false;
		}
		
		if (!jumpDown) {
			jumpReleased = true;
		}
		
		if (jumpReleased && jumpDown && !usedDoubleJump) {
			vy = -(12.15625 * jumpHeight);
			usedDoubleJump = true;
		}
		
		vx = 0;
		//moves
		if (leftDown && !rightDown && waitingOn == -1) {
			vx = -5 * speed;
			this.getAnimationHandler().setFlipHorizontal(true);
		}
		
		if (!leftDown && rightDown && waitingOn == -1) {
			vx = 5 * speed;
			this.getAnimationHandler().setFlipHorizontal(false);
		}
		
	}
		
		//uses the attack once the timer has ended
		if (curLag == 0) {
			if (waitingOn != -1) {
				usingMove = true;
				switch(waitingOn) {
				case 0:
					useJab();
					break;
				case 1:
					useNutralS();
					break;
				case 2:
					useSideS(true);
					break;
				case 12:
					useSideS(false);
					break;
				case 3:
					useUpS();
					break;
				case 4:
					useDownS();
					break;
				case 5:
					useNutralAir();
					break;
				case 6:
					useSideAir(true);
					break;
				case 13:
					useSideAir(false);
					break;
				case 7:
					useUpAir();
					break;
				case 8:
					useDownAir();
					break;
				case 9:
					useDownG();
					break;
				case 10:
					useUpG();
					break;
				case 11:
					useSideG(true);
					break;
				case 14:
					useSideG(false);
					break;
				}
				waitingOn = -1;
				
			}
		} else {
			curLag = curLag-1;
		}
		
		vy = vy + Room.getGravity();
		this.goX(this.getX() + vx);
	}
	
	//stagers this fighter for a certain amount of miliseconds making them unable to fight back
	public void stager (int til) {
		stagertill = System.currentTimeMillis() + til;
	}
	
	public boolean isUsingMove() {
		return usingMove;
	}
	public void stopUsingMove(int witchMove) {
		usingMove = false;
		curLag = endLags[witchMove];
	}
	
	public void useNutralS() {
		System.out.println("using nutral special");
	}
	public void useSideS(boolean direction) {
		if (direction) {
			System.out.println("using side special left");
		} else {
			System.out.println("using side special right");
		}
	}
	public void useDownS() {
		System.out.println("using down special");
	}
	public void useUpS() {
		System.out.println("using up special");
	}

	public void useNutralAir() {
		System.out.println("using nutral air");
	}
	public void useSideAir(boolean direction) {
		if (direction) {
			System.out.println("using side air left");
		} else {
			System.out.println("using side air right");
		}
	}
	public void useDownAir() {
		System.out.println("using down air");
	}
	public void useUpAir() {
		System.out.println("using up air");
	}
	
	public void useNutralG() {
		System.out.println("using nutral ground");
	}
	public void useSideG(boolean direction) {
		if (direction) {
			System.out.println("using side ground left");
		} else {
			System.out.println("using side ground right");
		}
	}
	public void useDownG() {
		System.out.println("using down ground");
	}
	public void useUpG() {
		System.out.println("using up ground");
	}
	
	public void useJab() {
		System.out.println("using jab");
	}
	
	public void damage (double amount) {
		damage = damage + amount;
	}
	public void launch (double direction, double velocity) {
		
	}
	public void interuptAttack () {
		
	}
}
