package gameObjects;

import main.GameCode;
import projectiles.Period;
import resources.Sprite;

public class Puncuation extends Enemy {
	public Sprite idleExclamation;
	public Sprite idleQuestion;
	public Sprite bendingExclamation;
	public Sprite bendingQuestion;
	public Sprite sharpExclamation;
	public Sprite sharpQuestion;
	public Sprite noPeriodQuestion;
	public Sprite noPeriodExclamation;
	boolean chargeLineAquired;
	boolean question;
	boolean period;
	Period projectile;
	int timer;
	boolean shooting;
	public Puncuation () {
		this.health = 300;
		timer = 0;
		this.defence = 0;
		period = true;
		noPeriodQuestion = new Sprite ("resources/sprites/config/idleQuestionMarkNoPeriod.txt");
		noPeriodExclamation = new Sprite ("resources/sprites/config/idleExclamationMarkNoPeriod.txt");
		chargeLineAquired = false;
		shooting = false;
		this.setHitboxAttributes(6, 1, 14, 25);
		idleExclamation = new Sprite ("resources/sprites/config/exclamation_Mark.txt");
		idleQuestion = new Sprite ("resources/sprites/config/question_Mark.txt");
		bendingExclamation = new Sprite ("resources/sprites/config/bending_exclamation_Mark.txt");
		bendingQuestion = new Sprite ("resources/sprites/config/bending_question_Mark.txt");
		sharpExclamation = new Sprite ("resources/sprites/config/sharp_exclamation_Mark.txt");
		sharpQuestion = new Sprite ("resources/sprites/config/sharp_question_Mark.txt");
		this.setSprite(idleQuestion);
		question = true;
		this.getAnimationHandler().setFrameTime(150);
		//if (this.getVariantAttribute("puncuation").equals("question")) {
			//question = true;
		//	this.setHitboxAttributes(6, 1, 14, 25);
		//} else {
		//	question = false;
		//	this.setHitboxAttributes(11, 0, 6, 28);
		//}
	}
	@Override 
	public String checkName () {
		return "PUNCUATION";
	}
	@Override
	public String checkEntry () {
		return "I KNEW IT I FREAKIN KNEW IT AS SOON AS I LEARNED ABOUT THIS CRAP IN THIRD GRADE I WAS LIKE IM GONNA HAVE TO BE THE GUY THAT PUTS A STOP TO THIS AND AS IT TURRNED OUT I WAS RIGHT";
	}
	@Override 
	public void enemyFrame () {
		this.Charge(40);
		if (!chargeLineAquired && (this.isNearPlayerX(0, 200, 0, 200) && this.isNearPlayerY(0, 200, 0, 200)) && !shooting) {
			if (question) {
				this.setSprite(sharpQuestion);
			} else {
				this.setSprite(sharpExclamation);
			}
			this.getAnimationHandler().setRepeat(false);
		}
		if ((this.isNearPlayerX(0, 200, 0, 200) && this.isNearPlayerY(0, 200, 0, 200)) && !chargeing && !shooting) {
			if (!chargeLineAquired) {
				chargeLineAquired = true;
			}
			this.getChargeLine();
		}
		if (!(this.isNearPlayerX(0, 200, 0, 200) && this.isNearPlayerY(0, 200, 0, 200)) && !chargeing && !shooting) {
			if (question) {
				this.setSprite(idleQuestion);
			} else {
				this.setSprite(idleExclamation);
			}
			chargeLineAquired = false;
		}
		if (period && !(this.isNearPlayerX(0, 200, 0, 200) && this.isNearPlayerY(0, 200, 0, 200)) && !shooting) {
			if (question) {
				this.setSprite(bendingQuestion);
			} else {
				this.setSprite(bendingExclamation);
			}
			shooting = true;
		}
		if ((this.getSprite().equals(bendingExclamation) && this.getAnimationHandler().getFrame() == 3)  || (this.getSprite().equals(bendingQuestion) && this.getAnimationHandler().getFrame() == 3) ) {
			shooting = false;
			if (this.getX()> GameCode.testJeffrey.getX()) {
				projectile = new Period (3.14);
				projectile.declare(this.getX(),this.getY());
			} else {
				projectile = new Period (0);
				projectile.declare(this.getX(),this.getY());
			}
			if (question) {
				this.setSprite(idleQuestion);
			} else {
				this.setSprite(idleExclamation);
			}
			period = false;
		}
		if (!period) {
			timer = timer + 1;
				idleQuestion = noPeriodQuestion;
				idleExclamation = noPeriodExclamation;
				if (timer == 600) {
					period = true;
					timer = 0;
				}
		} else {
			idleExclamation = new Sprite ("resources/sprites/config/exclamation_Mark.txt");
			idleQuestion = new Sprite ("resources/sprites/config/question_Mark.txt");
		}
		
	}
}
