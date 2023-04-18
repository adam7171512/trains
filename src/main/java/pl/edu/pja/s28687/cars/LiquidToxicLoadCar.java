package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.load.ILiquidToxic;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.misc.TrainStatus;
import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.validators.ILiquidCarrier;

import java.math.BigDecimal;
import java.util.Set;


public class LiquidToxicLoadCar extends AbstractHeavyFreightCar<ILiquidToxic> implements ILiquidCarrier {
    private static final String SHIPPER = "POLMOS";
    private static final String SECURITY_INFO =
            """
                    Danger: This railroad car contains toxic liquid materials.
                    Keep a safe distance and do not approach.
                    In case of a spill or leak, immediately evacuate the area
                    and contact trained personnel for assistance.
                    """;

    public LiquidToxicLoadCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.LIQUID_TOXIC);
    }

    @Override
    public void safetyCheck() {
        lookForLeaks();
        checkValves();
    }

    @Override
    public void emergencyUnloading() {
        System.out.println("Toxic fluids will not get spilled automatically! Calling the fire department!");
    }

    @Override
    public String getCargoStats() {
        return null;
    }

    @Override
    public CarType getCarType() {
        return CarType.LIQUID_TOXIC;
    }

    @Override
    public void emergencyProcedure() {
        closeValve();
        if (getLocomotive().isPresent()){
            Locomotive loco = getLocomotive().get();
            loco.raiseAlert("Emergency in liquid toxic car !! " + getId() + "!");
            loco.setStatus(TrainStatus.EMERGENCY);
            System.out.println("Detaching car from locomotive!");
            loco.detach(this);
        }
    }

    @Override
    public void routineProcedure() {
        checkVolumeLevel();
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
        System.out.println("Opening valve");
        safetyUnlock();
    }

    @Override
    public void closeValve() {
        System.out.println("Closing valve");
        safetyLock();
    }

    private void checkVolumeLevel(){
        System.out.println("Checking volume level");
        System.out.println(getVolume());
    }

    private void lookForLeaks(){
        System.out.println("Looking for leaks");
    }

    private void checkValves(){
        System.out.println("Checking valves...");
    }
}
