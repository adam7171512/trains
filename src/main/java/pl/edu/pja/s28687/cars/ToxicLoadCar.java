package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.train.Locomotive;
import pl.edu.pja.s28687.load.IToxic;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.misc.TrainStatus;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.util.Set;
import java.util.logging.Level;


public class ToxicLoadCar extends AbstractHeavyFreightCar<IToxic> {
    private static final String SHIPPER = "POLMOS";
    private static final String SECURITY_INFO =
            """
                    Danger: This railroad car contains toxic materials.
                    Keep a safe distance and do not approach.
                    In case of an emergency, follow evacuation procedures
                    and contact trained personnel immediately.""";

    public ToxicLoadCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.TOXIC);
    }

    @Override
    public void safetyCheck() {
        if (loads.isEmpty()) {
            System.out.println("No loads to check in safety procedure!");
            return;
        }
        for (IToxic load : loads) {
            System.out.println("Carefully examining toxic load: " + load);
        }
    }

    @Override
    public void emergencyUnloading() {
        if (loads.isEmpty()) {
            System.out.println("No loads to unload");
            return;
        }
        System.out.println("Emergency unloading of " + loads.size() + " loads");
        if (isLocked()) {
            safetyUnlock();
        }
        for (IToxic load : loads) {
            unLoad(load);
        }
        safetyLock();
        System.out.println("Emergency unloading complete");
    }

    @Override
    public String getCargoStats() {
        return null;
    }

    @Override
    public CarType getCarType() {
        return CarType.TOXIC;
    }

    @Override
    public void emergencyProcedure() {
        logger.log(Level.SEVERE, "Emergency situation in toxic load car !!" + getId());
        safetyLock();
        if (getLocomotive().isPresent()) {
            Locomotive loco = getLocomotive().get();
            loco.raiseAlert(
                    "Toxic load car " + getId() + " is in emergency mode." +
                            " Please call the fire department immediately");
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
        return true;
    }

}
