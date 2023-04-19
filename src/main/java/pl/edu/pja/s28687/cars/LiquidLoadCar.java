package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.ValidationException;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.ILiquid;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLiquidFreightValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
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

    private static final BigDecimal VOLUME = BigDecimal.valueOf(80);
    private final ICarLiquidFreightValidator validator;

    public LiquidLoadCar(int id, ICarLiquidFreightValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
        this.validator = validator;
    }

    @Override
    protected void handleFailedLoading(IDeliverable load) {
        if (!validator.validateFlags(load, this)) {
            Set<LoadType> incompatibleFlags = new HashSet<>(load.flags());
            incompatibleFlags.removeAll(allowedLoadFlags());
            throw new ValidationException("Load type incompatible !" + incompatibleFlags);
        } else if (!validator.validateWeight(load, this)) {
            throw new ValidationException(
                    "Load too heavy !" + "\nPayload limit available: "
                            + getGrossWeight().subtract(getCurrentWeight())
                            + " Load weight: " + load.getWeight());
        } else {
            throw new ValidationException("Volume too high!");
        }
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
        logger.log(INFO, "Checking valves");
    }

    private void lookForLeaks() {
        logger.log(INFO, "Looking for leaks");
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
        StringBuilder stats = new StringBuilder()
                .append("\nLiquid load total volume: ")
                .append(getLiquidLoadVolume())
                .append(" m3 (tank capacity: ")
                .append(VOLUME)
                .append(" m3)")
                .append("\nLiquid load total weight: ")
                .append(getCargoWeight())
                .append(" tonnes")
                .append("\nunits loaded: ")
                .append(getLoads().size())
                .append("\n");
        getLoads()
                .stream()
                .map(ILiquid::getBasicInfo)
                .forEach(s -> stats.append(s).append("\n"));
        return stats.toString();
    }

    @Override
    public CarType getCarType() {
        return CarType.LIQUID;
    }

    @Override
    public void emergencyProcedure() {
        logger.log(SEVERE, "Starting emergency procedure in liquid load car! " + getId());
        closeValve();
        if (getLocomotive().isPresent()) {
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
    public String getBasicInfo() {
        return this + "\nPayload :  " + getCargoWeight()
                + "  / " + getMaxPayload() + " tonnes, " + getLiquidLoadVolume()
                + " / " + getVolume() + " m3 used," + " compatible load types : " + getAllowedLoadFlags();
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
        logger.log(INFO, "Closing the valve!");
        safetyLock();
    }
}
