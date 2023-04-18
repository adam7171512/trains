package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.load.IBasicFreight;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.util.Set;
import java.util.logging.Level;

public class BasicFreightCar extends AbstractBasicFreightCar<IBasicFreight> {
    private static final String SHIPPER = "URSUS";
    private static final String SECURITY_INFO =
            """
                    Attention: This is a basic freight car.
                    Please be aware of the cargo being transported
                    and keep a safe distance from the car.
                    In case of an emergency, follow the evacuation procedures
                    and contact trained personnel immediately.
                    """;

    public BasicFreightCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.BASIC_FREIGHT);
    }

    @Override
    public void safetyCheck() {
        System.out.println("Examining car compartments for any damage");
    }

    @Override
    public void emergencyUnloading() {
        logger.log(Level.SEVERE, "Emergency unloading of basic freight car started! " + getId());
        if (loads.isEmpty()) {
            logger.log(Level.SEVERE, "No loads to unload in basic freight car! " + getId());
            return;
        }
        logger.log(Level.SEVERE, loads.size() + " loads to unload! ");
        if (isLocked()) {
            safetyUnlock();
        }
        for (IBasicFreight load : loads) {
            unLoad(load);
        }
        safetyLock();
        logger.log(Level.SEVERE, "Emergency unloading complete! ");
    }

    @Override
    public String getCargoStats() {
        return null;
    }

    @Override
    public CarType getCarType() {
        return CarType.BASIC_FREIGHT;
    }

    @Override
    public void emergencyProcedure() {
        logger.log(Level.SEVERE, "Emergency procedure in basic freight car started ! " + getId());
        safetyLock();
        if (getLocomotive().isPresent()){
            Locomotive loco = getLocomotive().get();
            loco.raiseAlert("Emergency in basic freight car! " + getId()
            + "please send staff to check the car!");
            }
        }

    @Override
    public void routineProcedure() {
        System.out.println("Quick look at the car..");
    }

    @Override
    public boolean isPowered() {
        return false;
    }
}
