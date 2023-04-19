package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.load.ILiquid;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;


public class LiquidLoadCar extends AbstractBasicFreightCar<ILiquid> implements ILiquidCarrier {
    private static final String SHIPPER = "POLMOS";
    private static final String SECURITY_INFO =
            """
                    Caution: This railroad car contains liquid materials.
                    Keep a safe distance and do not approach.
                    In case of a spill or leak, follow proper procedures for containment
                    and contact trained personnel immediately.
                    """;

    private static final BigDecimal VOLUME = BigDecimal.valueOf(110);

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
        logger.log(INFO,"Checking valves");
    }

    private void lookForLeaks() {
        logger.log(INFO,"Looking for leaks");
    }

    @Override
    public void emergencyUnloading() {
        logger.log(SEVERE, "Starting emergency spillage procedure");
        openValve();
        for (ILiquid liquid : getLoads()) {
            unLoad(liquid);
        }
        closeValve();
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

    @Override
    public CarType getCarType() {
        return CarType.LIQUID;
    }

    @Override
    public void emergencyProcedure() {
        logger.log(SEVERE, "Starting emergency procedure in liquid load car! " + getId());
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
        logger.log(INFO, "Checking volume level");
        logger.log(INFO, "\nVolume: " + getLiquidLoadVolume() + " m3");
    }

    @Override
    public boolean isPowered() {
        return false;
    }

    @Override
    public BigDecimal getVolume() {
        return VOLUME.setScale(2, RoundingMode.FLOOR);
    }

    @Override
    public BigDecimal getAvailableVolume() {
        return getVolume().subtract(getLiquidLoadVolume());
    }

    public BigDecimal getLiquidLoadVolume() {
        return loads
                .stream()
                .map(ILiquid::getVolume)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void openValve() {
        logger.log(INFO, "Opening the valve!");
        safetyUnlock();
    }

    @Override
    public void closeValve() {
        logger.log(INFO,"Closing the valve!");
        safetyLock();
    }
}
