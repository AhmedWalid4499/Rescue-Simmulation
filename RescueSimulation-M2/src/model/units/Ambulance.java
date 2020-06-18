package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
import model.disasters.Collapse;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Ambulance extends MedicalUnit {

	public Ambulance(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		getTarget().getDisaster().setActive(false);

		Citizen target = (Citizen) getTarget();
		if (target.getHp() == 0) {
			jobsDone();
			return;
		} else if (target.getBloodLoss() > 0) {
			target.setBloodLoss(target.getBloodLoss() - getTreatmentAmount());
			if (target.getBloodLoss() == 0)
				target.setState(CitizenState.RESCUED);
		}

		else if (target.getBloodLoss() == 0)

			heal();
		
	}

	public void respond(Rescuable r)throws UnitException {
		if (getTarget() != null && ((Citizen) getTarget()).getBloodLoss() > 0
				&& getState() == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);
		if(getTarget().getDisaster() instanceof Infection){
			throw new CannotTreatException(this,getTarget(),"Ambulance treats injuried citizens not infected one");
		}
//
		if(getTarget().getDisaster() instanceof Infection || getTarget().getDisaster() instanceof Collapse ||
				getTarget().getDisaster() instanceof Fire || getTarget().getDisaster() instanceof GasLeak)
			throw new IncompatibleTargetException(this,getTarget(),"Ambulance treats injuried citizens only");
		if(getTarget().getDisaster()==null){
			throw new CannotTreatException(this,getTarget(),"citizen has no disaster");
		}
		
	}

}
