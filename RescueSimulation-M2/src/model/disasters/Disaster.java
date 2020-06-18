package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.DisasterException;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Disaster implements Simulatable{
	private int startCycle;
	private Rescuable target;
	private boolean active;
	public Disaster(int startCycle, Rescuable target) {
		this.startCycle = startCycle;
		this.target = target;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getStartCycle() {
		return startCycle;
	}
	public Rescuable getTarget() {
		return target;
	}
	public void strike() throws DisasterException
	{
		
		target.struckBy(this);
		active=true;
	}
public String toString(){
	String s = "";
	if(target.getDisaster() instanceof Injury){
		s+= "Injury";
	}
	if(target.getDisaster() instanceof Infection){
		s+= "Infection";
	}
	if(target.getDisaster() instanceof Fire){
		s+="Fire";
	}
	if(target.getDisaster() instanceof GasLeak){
		s+="Gas Leak";
	}
	if(target.getDisaster() instanceof Collapse){
		s+="Collapse";
	}
	return s;
	
}
}
