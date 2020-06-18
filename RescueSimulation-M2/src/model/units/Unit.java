package model.units;
import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;
public abstract class Unit implements Simulatable, SOSResponder {
	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;

	public Unit(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		this.unitID = unitID;
		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		this.state = UnitState.IDLE;
		this.worldListener = worldListener;
	}

	public void setWorldListener(WorldListener listener) {
		this.worldListener = listener;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public UnitState getState() {
		return state;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
	}

	@Override
	public void respond(Rescuable r) throws UnitException{
		if(this instanceof Evacuator ){
			if(r.getDisaster()==null){
				throw new CannotTreatException(this,r,"the building has no disaster");
			}
			if(r.getDisaster() instanceof Fire || r.getDisaster() instanceof GasLeak ){
				throw new CannotTreatException(this,r,"evcuator treats collapsed buildings");
			}
			if(r instanceof Citizen){
				throw new IncompatibleTargetException(this,r,"evacuator doesn't treat citizens");
			}
			else{
				if (target != null && state == UnitState.TREATING )
					reactivateDisaster();
				finishRespond(r);
			}
		}
		else if(this instanceof GasControlUnit){
			if(r.getDisaster()==null){
				throw new CannotTreatException(this,r,"the building has no disaster");
			}
			if(r.getDisaster() instanceof Collapse || r.getDisaster() instanceof Fire){
				throw new CannotTreatException(this,r,"gas control unit treats buildings that have gas leak only");
			}
			if(r instanceof Citizen){
				throw new IncompatibleTargetException(this,r,"gas control unit doesn't treat citizens");
			}
			else{
				if (target != null && state == UnitState.TREATING )
					reactivateDisaster();
				finishRespond(r);
			}
		}
		else if(this instanceof FireTruck){
			if(r.getDisaster()== null){
				throw new CannotTreatException(this,r,"the building has no disaster");	
			}
			if(r.getDisaster() instanceof Collapse || r.getDisaster() instanceof GasLeak){
				throw new CannotTreatException(this,r,"firetruck treats buildings on fire only");
			}
			if(r instanceof Citizen){
				throw new IncompatibleTargetException(this,r,"firetruck doesn't treat citizens");
			}
			else{
				if (target != null && state == UnitState.TREATING )
					reactivateDisaster();
				finishRespond(r);
			}

		}

	}

	public void reactivateDisaster() {
		Disaster curr = target.getDisaster();
		curr.setActive(true);
	}

	public void finishRespond(Rescuable r) {
		target = r;
		state = UnitState.RESPONDING;
		Address t = r.getLocation();
		distanceToTarget = Math.abs(t.getX() - location.getX())
				+ Math.abs(t.getY() - location.getY());

	}

	public abstract void treat();

	public void cycleStep() {
		if (state == UnitState.IDLE)
			return;
		if (distanceToTarget > 0) {
			distanceToTarget = distanceToTarget - stepsPerCycle;
			if (distanceToTarget <= 0) {
				distanceToTarget = 0;
				Address t = target.getLocation();
				worldListener.assignAddress(this, t.getX(), t.getY());
			}
		} else {
			state = UnitState.TREATING;
			treat();
		}
	}

	public void jobsDone() {
		target = null;
		state = UnitState.IDLE;

	}
	public boolean canTreat(Rescuable r){
		if(r instanceof Citizen){
			Citizen c=(Citizen)r;
			if(c.getBloodLoss()>0 || c.getToxicity()>0)
				return true;
		}
		if(r instanceof ResidentialBuilding){
			ResidentialBuilding b=(ResidentialBuilding)r;
			if(b.getFireDamage()>0 || b.getFoundationDamage()>0 || b.getGasLevel()>0)
				return true;
		}
		return false;
	}
	public String toString(){

		return "Unit ID: " + this.unitID
				+"\n"+
				"Unit State: " +this.state
				+"\n"+
				"Location: " + this.location+
				"\n"+
				"The Target: " + this.target
				+"\n"+
				"The distance to target: " + this.distanceToTarget
				+"\n" +
				"Steps per cycle: " + this.stepsPerCycle;
		//		private String unitID;
		//		private UnitState state;
		//		private Address location;
		//		private Rescuable target;
		//		private int distanceToTarget;
		//		private int stepsPerCycle;
	}
}
