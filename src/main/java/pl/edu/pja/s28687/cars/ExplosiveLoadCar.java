package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.load.IExplosive;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.misc.TrainStatus;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.util.Set;


public class ExplosiveLoadCar extends AbstractHeavyFreightCar<IExplosive> {
    private static final String SHIPPER = "ALSTOM";
    private static final String SECURITY_INFO =
            """
                    Danger: This railroad car contains explosives.
                    Keep a safe distance and do not approach.
                    Do not use any electronic devices or equipment
                    that may produce sparks or flames in the vicinity of the car.
                    In case of an emergency, follow the evacuation procedures
                    and contact trained personnel immediately.
                    """;

    public ExplosiveLoadCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
    }


    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.EXPLOSIVE);
    }

    @Override
    public void safetyCheck() {
        for (IExplosive load : loads) {
            System.out.println("Carefully examining explosive load: " + load);
        }
    }

    @Override
    public void emergencyUnloading() {
        System.out.println("Can't start emergency unloading! Calling fire department for help!");
    }

    @Override
    public CarType getCarType() {
        return CarType.EXPLOSIVE;
    }

    @Override
    public void emergencyProcedure() {
        safetyLock();
        if (getLocomotive().isPresent()){
            Locomotive loco = getLocomotive().get();
            loco.raiseAlert("Explosive load car " + getId() + " is in danger of exploding!");
            loco.setStatus(TrainStatus.EMERGENCY);
            loco.detach(this);
        }
    }

    @Override
    public void routineProcedure() {
        safetyCheck();
    }

    @Override
    public boolean isPowered() {
        return false;
    }

}
