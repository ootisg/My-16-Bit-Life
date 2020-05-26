package statusEffect;

import gui.Textbox;
import main.GameCode;
import main.GameObject;
import players.Jeffrey;

public class Lemoney extends GameObject {
	int charictar; 
	int timer = 0;
	int time = 54000;
	
	Textbox box;
	public Lemoney ( int charictarToModerate) {
	charictar = charictarToModerate;
	}
	@Override
	public void frameEvent () {
		if (charictar == 0) {
			Jeffrey.status.statusAppliedOnJeffrey[2] = true;
		}
		if (charictar == 1) {
			Jeffrey.status.statusAppliedOnSam[2] = true;
		}
		if (charictar == 2) {
			Jeffrey.status.statusAppliedOnRyan[2] = true;
		}
		timer = timer + 1;
		if (timer == time) {
			if (charictar == 0) {
				box = new Textbox ("JEFFREY GOT OVER HIS LEMON PACKET ADDICTION BUT HE IS EXPERINCEING WITHDRAWL");
				Jeffrey.status.statusAppliedOnJeffrey[4] = true;
				Slowness slow = new Slowness(charictar);
				slow.setTime(4500);
				slow.declare();
				Jeffrey.status.statusAppliedOnJeffrey[3] = true;
				Jeffrey.status.statusAppliedOnJeffrey[2] = false;
			}
			if (charictar == 1) {
				box = new Textbox ("SAM GOT OVER HIS LEMON PACKET ADDICTION BUT HE IS EXPERINCEING WITHDRAWL");
				Jeffrey.status.statusAppliedOnSam[4] = true;
				Slowness slow = new Slowness(charictar);
				slow.setTime(4500);
				slow.declare();
				Jeffrey.status.statusAppliedOnSam[3] = true;
				Jeffrey.status.statusAppliedOnSam[2] = false;
			}
			if (charictar == 2) {
				box = new Textbox ("RYAN GOT OVER HIS LEMON PACKET ADDICTION BUT HE IS EXPERINCEING WITHDRAWL");
				Jeffrey.status.statusAppliedOnRyan[4] = true;
				Slowness slow = new Slowness(charictar);
				slow.setTime(4500);
				slow.declare();
				Jeffrey.status.statusAppliedOnRyan[3] = true;
				Jeffrey.status.statusAppliedOnRyan[2] = false;
			}
			this.forget();
		}
	}
	public void setTime (int timeYouWant) {
		time = timeYouWant;
	}
}
