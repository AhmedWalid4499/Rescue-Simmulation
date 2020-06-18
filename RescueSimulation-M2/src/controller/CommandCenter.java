package controller;

import java.util.ArrayList;

import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import simulation.Rescuable;
import simulation.Simulator;

public class CommandCenter implements SOSListener {

	private Simulator engine;
	public ArrayList<ResidentialBuilding> visibleBuildings;
	public ArrayList<Citizen> visibleCitizens;
	
	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();

	}

	public ArrayList<ResidentialBuilding> getVisibleBuildings() {
		return visibleBuildings;
	}

	public Simulator getEngine() {
		return engine;
	}

	public ArrayList<Citizen> getVisibleCitizens() {
		return visibleCitizens;
	}

	public ArrayList<Unit> getEmergencyUnits() {
		return emergencyUnits;
	}

	
	private ArrayList<Unit> emergencyUnits;

	@Override
	public void receiveSOSCall(Rescuable r) {
		System.out.println("sos call received");
		if (r instanceof ResidentialBuilding) {
			
			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);
			
		} else {
			
			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}

		System.out.println(visibleCitizens.size());
		System.out.println(visibleBuildings.size());

	}
	public static void main(String[] args) throws Exception {
		CommandCenter c = new CommandCenter();
		int x =c.visibleCitizens.size();
		System.out.println(x);
	}

}
