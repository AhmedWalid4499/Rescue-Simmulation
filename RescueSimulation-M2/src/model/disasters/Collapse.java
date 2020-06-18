package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.DisasterException;
import model.infrastructure.ResidentialBuilding;


public class Collapse extends Disaster {

	public Collapse(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
		
	}
	public void strike() throws DisasterException
	{
		ResidentialBuilding target= (ResidentialBuilding)getTarget();	
		target.setFoundationDamage(target.getFoundationDamage()+10);
		super.strike();
		if(target.getStructuralIntegrity()==0)
			throw new BuildingAlreadyCollapsedException(this,"the building collapses disaster can't be striked");
	}
	public void cycleStep()
	{
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setFoundationDamage(target.getFoundationDamage()+10);
	}

}
