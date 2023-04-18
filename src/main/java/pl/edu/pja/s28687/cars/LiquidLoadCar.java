package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.load.ILiquid;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.validators.ILiquidCarrier;

import java.math.BigDecimal;
import java.util.Set;


public class LiquidLoadCar extends BasicFreightCarABC<ILiquid> implements ILiquidCarrier {
    private static final String SHIPPER = "POLMOS";
    private static final String SECURITY_INFO =
            """
                    Caution: This railroad car contains liquid materials.
                    Keep a safe distance and do not approach.
                    In case of a spill or leak, follow proper procedures for containment
                    and contact trained personnel immediately.
                    """;

    public LiquidLoadCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.LIQUID);
    }

    @Override
    public void safetyCheck() {
        lookForLeaks();
        checkValves();
    }

    private void checkValves() {
        System.out.println("Checking valves");
    }

    private void lookForLeaks() {
        System.out.println("Looking for leaks");
    }

    @Override
    public void emergencyUnloading() {
        System.out.println("Starting emergency spillage procedure");
        openValve();
        for (ILiquid liquid : getLoads()) {
            unLoad(liquid);
        }
        closeValve();
    }

    @Override
    public String getCargoStats() {
        return null;
    }

    @Override
    public CarType getCarType() {
        return CarType.LIQUID;
    }

    @Override
    public void emergencyProcedure() {
        System.out.println("Starting emergency procedure in liquid load car! " + getId());
        closeValve();
        if (getLocomotive().isPresent()){
            Locomotive loco = getLocomotive().get();
            loco.raiseAlert("Emergency in liquid car !! " + getId() + "!"
            + " Please send help to examine the car immediately!");
        }

    }

    @Override
    public void routineProcedure() {
        checkVolumeLevel();
    }

    private void checkVolumeLevel() {
        System.out.println("Checking volume level");
        System.out.println("\nVolume: " + getVolume());
    }

    @Override
    public boolean isPowered() {
        return false;
    }

    @Override
    public BigDecimal getVolume() {
        return null;
    }

    @Override
    public void openValve() {
        System.out.println("Opening the valve!");
        safetyUnlock();
    }

    @Override
    public void closeValve() {
        System.out.println("Closing the valve!");
        safetyLock();
    }
}
