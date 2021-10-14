package players;

import java.util.ArrayList;

import gui.Tbox;
import main.GameObject;
import main.ObjectHandler;
import map.Room;

public class PartyManager extends GameObject {
	
	ArrayList <Party> parties = new ArrayList <Party> ();

	private int switchTimer = 0;
	private int nextCharictar = 0;
	private boolean full = false;
	
	public PartyManager () {
		ArrayList <GameObject> shallowCopy = ObjectHandler.getObjectsByName("Party");
		int firstPlayer = 17;
		
		ArrayList <GameObject> inGameParties = new ArrayList <GameObject>();
		
		for (int i = 0; i < shallowCopy.size(); i++) {
			inGameParties.add(shallowCopy.get(i));
		}
		
		boolean takenCareOfActive = false;
		
		for (int i = 0; i < inGameParties.size(); i++) {
			Party party = (Party) inGameParties.get(i);
			parties.add(party);
			if (party.allNulls()) {
				party.inzialize();
			}
			if (party.getVariantAttribute("Active") != null && party.getVariantAttribute("Active").equals("yes")) {
				party.getFirstPlayer().activate(null);
				takenCareOfActive = true;
			} 
			if (party.getFirstPlayer().getPlayerNum() < firstPlayer) {
				firstPlayer = party.getFirstPlayer().getPlayerNum();
			}
			
			
			party.forget();
		}
		
		if (!takenCareOfActive) {
			for (int i = 0; i < parties.size(); i++) {
				if (parties.get(i).getPlayer(firstPlayer) != null) {
					parties.get(i).getPlayer(firstPlayer).activate(null); 
				} 
			}
		}
		for (int i = 0; i < parties.size(); i++) {
			if(!parties.get(i).getFirstPlayer().declared()) {
				parties.get(i).getFirstPlayer().declare(parties.get(i).getX(), parties.get(i).getY());
			}
		}
		
		
		this.updateNextCharictar();
		this.declare();
	}
	@Override
	public void frameEvent () {

		//Switch meter stuff
		//(for now I just made it so you can't switch while in plants and other objects but I might change it later if level design requires it)
		if (keyDown('Q') && !Player.getActivePlayer().isBlackListed()) {
			if (this.checkSwitch()) {
				switchTimer++;
			}
		}
	
		//The "tap Q to toggle" mechanic.
		if (switchTimer > 0 && switchTimer <=5 && !keyDown('Q') && !full) {
			updateNextCharictar ();
		} 
		
		if (!keyDown ('Q')) {
			switchTimer = 0;
			full = false;
		}
	
		
		//System.out.println(nextCharacter);
		
		//Decides what character to switch into. 
		
		if (switchTimer == 30 && !Player.getActivePlayer().isBlackListed()) {
			this.runCharictarSwitchCode();
			switchTimer = 0;
			full = true;
		}
	}
	
	/**
	 * runs the correct code to have the player switch to controling any charictar 
	 * @param witchCharacter the charictar to switch too
	 */
	public void runCharictarSwitchCode() {
		
		Player oldActive = getActiveParty().getActivePlayer();
		oldActive.deactivate();
		getNextParty().getPlayer(nextCharictar).activate(oldActive);
		this.updateNextCharictar();
	}
	
	/**
	 * @return true if it is posible to switch to another charictar false otherwise
	 * 
	 */
	public boolean checkSwitch () {
		return this.getNextParty() != null;
	}
	
	private void updateNextCharictar () {
		
		for (int i = 0; i < 3; i++) {
			nextCharictar = nextCharictar + 1;
			if (nextCharictar % 3 != this.getActiveParty().getActivePlayer().getPlayerNum() && isAlive(nextCharictar % 3)) {
				break;
			}
		}
		nextCharictar = nextCharictar %3;
	}
	private boolean isAlive (int charictarID) {
		for (int i = 0; i < parties.size(); i++) {
			if (parties.get(i).getAlivePlayer(charictarID) != null) {
				return true;
			}
		}
		return false;
	}
	private Party getNextParty () {
		for (int i = 0; i < parties.size(); i++) {
			if (parties.get(i).getAlivePlayer((nextCharictar) % 3) != null) {
				return parties.get(i);
			}
		}
		return null;
	}
	
	private Party getActiveParty () {
		for (int i = 0; i < parties.size(); i++) {
			if (parties.get(i).getActivePlayer() != null) {
				return parties.get(i);
			}
		}
		return null;
	}
	public Player getNextPlayer () {
		return this.getNextParty().getPlayer(nextCharictar);
	}
	
	public int getSwitchTimer() {
		return switchTimer;
	}
	/**
	 * takes the party members from real parties to make a new one with more charictars
	 * discards the old parties those charictars were in and creates the charictars if they were not in any party
	 * @param newParty the party you want formated as a boolean array
	 */
	public void createNewParty (boolean [] newParty) {
		Party joinedParty = new Party ();
		
		
		for (int j = 0; j < newParty.length; j++) {
			if (newParty[j]) {
				boolean looking = true;
				
				for (int i = 0; i < parties.size(); i++) {
					if (parties.get(i).getPlayer(j) != null) {
						Party oldParty = parties.get(i);
						parties.remove(oldParty);
						joinedParty.setPlayer(oldParty.getPlayer(j));
						oldParty.getPlayer(j).setParty(joinedParty);
						looking = false;
					}
				}
				if (looking) {
					switch (j) {
					case 0:
						Jeffrey jeff = new Jeffrey ();
						jeff.setParty(joinedParty);
						joinedParty.setPlayer(jeff);
						break;
					case 1:
						Sam sam = new Sam ();
						sam.setParty(joinedParty);
						joinedParty.setPlayer(sam);
						break;
					case 2:
						Ryan ryan = new Ryan ();
						ryan.setParty(joinedParty);
						joinedParty.setPlayer(ryan);
						break;
					}
				}
			}
		}
		parties.add(joinedParty);
	}
	public Player getPlayer (int playerNum) {
		for (int i = 0; i < parties.size(); i++) {
			if (parties.get(i).getPlayer(playerNum) != null) {
				return parties.get(i).getPlayer(playerNum);
			}
		}
		return null;
	}
	
	/**
	 * returns true if both p1 and p2 are in the same party
	 * @return
	 */
	public boolean sameParty (Player p1, Player p2) {
		return p1.getParty().containsPlayer(p2);
	}
	
	public void resetSwitchTimer () {
		switchTimer = 0;
	}
	public ArrayList<Party> getParties() {
		return parties;
	}
	public void setParties(ArrayList<Party> parties) {
		this.parties = parties;
	}
}
