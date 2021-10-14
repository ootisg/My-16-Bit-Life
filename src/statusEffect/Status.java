package statusEffect;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;


public class Status {
	public ArrayList <StatusData> status;
	
	public Status() {
		status = new ArrayList <StatusData>();
		
		try {
			Scanner s = new Scanner (new File ("resources/statusList.txt"));
			
			while (s.hasNextLine()) {
				String statusType = s.nextLine();
				StatusData working = new StatusData (statusType);
				
				
				try {
					Class <?> c = Class.forName("statusEffect." + statusType);
					working.setStatusEffect((StatusType)c.getConstructor().newInstance());
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				
				status.add(working);
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void applyStatus (String statusName, int time) throws IllegalArgumentException {
		for (int i = 0; i < status.size(); i++) {
			if (status.get(i).getName().equals(statusName)) {
				status.get(i).setTime(time);
				return;
			}
		}
		throw new IllegalArgumentException (statusName + "is not the name of a status check resouces/statusList.txt for a list of statuses");
	}
	
	public boolean checkStatus (String statusName) throws IllegalArgumentException {
		for (int i = 0; i < status.size(); i++) {
			if (status.get(i).getName().equals(statusName)) {
				return status.get(i).getTime() != 0;
			}
		}
		throw new IllegalArgumentException (statusName + "is not the name of a status check resouces/statusList.txt for a list of statuses");
	}
	
	public void cureStatus (String statusName) throws IllegalArgumentException {
		for (int i = 0; i < status.size(); i++) {
			if (status.get(i).getName().equals(statusName)) {
				status.get(i).setTime(0);
				if (status.get(i).getStatusEffect() != null) {
					status.get(i).getStatusEffect().onCure();
				}
				return;
			}
		}
		throw new IllegalArgumentException (statusName + "is not the name of a status check resouces/statusList.txt for a list of statuses");
	}
	
	
	
	public void statusFrame () {
		for (int i = 0; i < status.size(); i++) {
			if (status.get(i).getTime() > 0) {
				status.get(i).decrementTime();
				if (status.get(i).getStatusEffect() != null) {
					status.get(i).getStatusEffect().onStatus();
					if (status.get(i).getTime() == 0) {
						status.get(i).getStatusEffect().onCure();
					}
				}
			}
		}
	}
	
	
	private class StatusData {
		
		int timeLeft = 0;
		
		String statusName = "NULL";
		
		private StatusType statusEffect = null;
		
		public StatusData (String statusName) {
			this.statusName = statusName;
		}
		
		public String getName () {
			return statusName;
		}
		
		public void setTime (int time) {
			timeLeft = time;
		}
		
		public void decrementTime () {
			timeLeft = timeLeft - 1;
		}
		
		public int getTime () {
			return timeLeft;
		}

		public StatusType getStatusEffect() {
			return statusEffect;
		}

		public void setStatusEffect(StatusType statusEffect) {
			this.statusEffect = statusEffect;
		}
		
	}
}
