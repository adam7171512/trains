package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.load.ILiquid;
import pl.edu.pja.s28687.load.ILiquidToxic;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.misc.TrainStatus;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.math.BigDecimal;
import java.util.Set;


public class LiquidToxicLoadCar extends AbstractHeavyFreightCar<ILiquidToxic> implements ILiquidToxicCarrier {
    private static final String SHIPPER = "POLMOS";
    private static final String SECURITY_INFO =
            """
                    Danger: This railroad car contains toxic liquid materials.
                    Keep a safe distance and do not approach.
                    In case of a spill or leak, immediately evacuate the area
                    and contact trained personnel for assistance.
                    """;

    private static final BigDecimal VOLUME = BigDecimal.valueOf(80);

    public LiquidToxicLoadCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.LIQUID_TOXIC);
    }

    @Override
    public void safetyCheck() {
        routineProcedure();
        performResidueCleaning();
    }

    @Override
    public void emergencyUnloading() {
        System.out.println("Toxic fluids will not get spilled automatically! Calling the fire department!");
    }

    @Override
    public String getCargoStats() {
        return new StringBuilder()
                .append("\n Liquid load total volume: ")
                .append(getLiquidLoadVolume())
                .append(" m3 (tank capacity: ")
                .append(VOLUME)
                .append(" m3)")
                .append("\n Liquid load total weight: ")
                .append(getCargoWeight())
                .append(" tons")
                .append("\n units loaded: ")
                .append(getLoads().size()).toString();
    }

    private BigDecimal getLiquidLoadVolume() {
        return getLoads().stream()
                .map(ILiquidToxic::getVolume)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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
        lookForLeaks();
    }

    @Override
    public boolean isPowered() {
        return false;
    }

    @Override
    public BigDecimal getVolume() {
        return VOLUME;
    }

    @Override
    public BigDecimal getAvailableVolume() {
        return VOLUME.subtract(getLiquidLoadVolume());
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

    @Override
    public void performResidueCleaning() {
        System.out.println("Cleaning the toxic residue");
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
