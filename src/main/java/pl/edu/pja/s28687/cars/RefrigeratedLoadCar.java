package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.IRefrigerated;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.util.Set;

public class RefrigeratedLoadCar extends AbstractBasicFreightCar<IRefrigerated> implements IPowered {
    private static final String SHIPPER = "GENERAL ELECTRIC";
    private static final String SECURITY_INFO =
            """
                    Caution: This is a refrigerated cargo car.
                    Please do not open the doors or tamper with the refrigeration unit.
                    Any unauthorized access may compromise the safety and quality of the goods being transported.
                    In case of an emergency, follow the evacuation procedures
                    and contact trained personnel immediately.
                    """;

    public RefrigeratedLoadCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.REFRIGERATED);
    }

    @Override
    public void safetyCheck() {
        checkCoolantLevel();
        checkChamberTemperature();
        scanChamberWithThermalVision();
    }

    @Override
    public void emergencyUnloading() {
        safetyLock();
        System.out.println("Emergency unloading in refrigerated car" + getId() + " started");
        if (getLocomotive().isPresent()) {
            getLocomotive().get().raiseAlert("Refrigerated car " + getId() + " is in emergency mode");
        }
    }

    @Override
    public CarType getCarType() {
        return CarType.REFRIGERATED;
    }

    @Override
    public void emergencyProcedure() {
        safetyLock();
        System.out.println("Emergency procedure in refrigerated car" + getId() + " started");
        if (getLocomotive().isPresent()) {
            getLocomotive().get().raiseAlert("Refrigerated car " + getId() + " is in emergency mode");
        }
    }

    @Override
    public void routineProcedure() {
        checkChamberTemperature();
    }

    @Override
    public boolean isPowered() {
        return true;
    }


    private void checkCoolantLevel() {
        System.out.println("Checking the coolant level..");
    }

    private void checkChamberTemperature() {
        System.out.println("Checking the chamber temperature..");
    }

    private void scanChamberWithThermalVision() {
        System.out.println("Scanning the chamber with thermal vision..");
    }
}
