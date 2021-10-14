package players;

import main.GameObject;
import main.ObjectHandler;

public class Party extends GameObject {
	
	private Player [] party = {null,null,null};
	
	public Party () {

	}
	
	public Party (Jeffrey j, Sam s, Ryan r) {
		party[0] = j;
		party[1] = s;
		party[2] = r;
		
		j.setParty(this);
		s.setParty(this);
		r.setParty(this);
	
	}
	
	public void inzialize () {
		if (this.getVariantAttribute("Jeffrey") == null || !this.getVariantAttribute("Jeffrey").equals("no")) {
			party[0] = new Jeffrey ();
			party[0].setParty(this);
		}
		if (this.getVariantAttribute("Sam") == null || !this.getVariantAttribute("Sam").equals("no")) {
			party[1] = new Sam ();
			party[1].setParty(this);
		}
		if (this.getVariantAttribute("Ryan") == null || !this.getVariantAttribute("Ryan").equals("no")) {
			party[2] = new Ryan ();
			party[2].setParty(this);
		}
		
		for (int i = 0; i < party.length; i++) {
			if (party[i] != null) {
				party[i].setX(this.getX());
				party[i].setY(this.getY());
			}
		}
	}
	
	public Player getNextPlayer (int startPlayer) {
		for (int i = startPlayer; i < startPlayer + 3; i++) {
			if (party[i %3] != null) {
				return party [i % 3];
			}
		}
		return null;
	}
	
	public Player getFirstPlayer () {
			for (int i = 0; i < 3; i++) {
				if (party[i] != null) {
					return party[i];
				}
			}
		return null;
	}
	
	public Player getActivePlayer () {
		for (int i = 0; i < 3; i++) {
			if (party[i] != null && party[i].isActive()) {
				return party[i];
			}
		}
		return null;
	}
	
	
	public Player getAlivePlayer (int playerID) {
		if (party [playerID] != null) {
			if (party [playerID].getHealth() > 0) {
				return party[playerID];
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public Player getPlayer(int playerID) {
		return party[playerID];
	}
	
	public boolean containsPlayer (Player player) {
		if (party[player.getPlayerNum()] != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setPlayer (Player newP) {
		party[newP.getPlayerNum()] = newP;
	}
	
	public boolean allNulls() {
		for (int i = 0; i < party.length; i++) {
			if (party[i] != null) {
				return false;
			}
		}
		return true;
	}
}
