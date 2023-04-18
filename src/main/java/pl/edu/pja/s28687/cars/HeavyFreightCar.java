package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.load.IHeavyFreight;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.util.Set;


public class HeavyFreightCar extends AbstractHeavyFreightCar<IHeavyFreight> {
    private static final String SHIPPER = "URSUS";
    private static final String SECURITY_INFO =
            """
                    Attention: This is a heavy freight car.
                    Please be aware of the size and weight of the cargo being transported
                    and keep a safe distance from the car.
                    In case of an emergency, follow the evacuation procedures
                    and contact trained personnel immediately.
                    """;

    public HeavyFreightCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.HEAVY_FREIGHT);
    }

    @Override
    public void safetyCheck() {
        System.out.println("Examining car compartments for any damage");
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
        for (IHeavyFreight load : loads) {
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
        return CarType.HEAVY_FREIGHT;
    }

    @Override
    public void emergencyProcedure() {
        System.out.println("Emergency procedure in heavy freight car !");
        safetyLock();
        if (getLocomotive().isPresent()){
            Locomotive loco = getLocomotive().get();
            loco.raiseAlert("Heavy freight car " + getId() + " is in emergency mode" +
                    "+ please send staff to help");
        }

    }

    @Override
    public void routineProcedure() {
        System.out.println("Quick look at the cargo..");
    }

    @Override
    public boolean isPowered() {
        return false;
    }
}
