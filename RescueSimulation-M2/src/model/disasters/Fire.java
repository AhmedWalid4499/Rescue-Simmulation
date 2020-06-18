package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.DisasterException;
import model.infrastructure.ResidentialBuilding;


public class Fire extends Disaster {

	public Fire(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
		
	}
	@Override
	public void strike()throws DisasterException
	{
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setFireDamage(target.getFireDamage()+10);
		super.strike();
		if(target.getStructuralIntegrity()==0)
			throw new BuildingAlreadyCollapsedException(this,"the building collapses disaster can't be striked");
	}

	@Override
	public void cycleStep() {
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setFireDamage(target.getFireDamage()+10);
		
	}

}
