package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.ValidationException;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.ILiquidToxic;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.misc.TrainStatus;
import pl.edu.pja.s28687.validators.ICarLiquidFreightValidator;

import java.math.BigDecimal;
import java.util.HashSet;
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

    private static final BigDecimal VOLUME = BigDecimal.valueOf(80);
    private final ICarLiquidFreightValidator validator;

    public LiquidToxicLoadCar(int id, ICarLiquidFreightValidator validator) {
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
        return Set.of(LoadType.LIQUID_TOXIC);
    }

    @Override
    public void safetyCheck() {
        routineProcedure();
        lookForLeaks();
        checkValves();
    }

    @Override
    public void emergencyUnloading() {
        System.out.println("Toxic fluids will not get spilled automatically! Calling the fire department!");
    }

    @Override
    public String getCargoStats() {
        StringBuilder stats = new StringBuilder()
                .append("\nLiquid toxic load total volume: ")
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
                .map(IDeliverable::getBasicInfo)
                .forEach(s -> stats.append(s).append("\n"));
        return stats.toString();
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
        if (getLocomotive().isPresent()) {
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
    public String getBasicInfo() {
        return this.toString() + "\nPayload :  " + getCargoWeight()
                + "  / " + getMaxPayload() + " tonnes, " + getLiquidLoadVolume()
                + " / " + getVolume() + " m3 used," + " compatible load types : " + getAllowedLoadFlags();
    }

    @Override
    public BigDecimal getVolume() {
        return VOLUME;
    }

    @Override
    public BigDecimal getAvailableVolume() {
        return getVolume().subtract(getLiquidLoadVolume());
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

    private void checkVolumeLevel() {
        System.out.println("Checking volume level");
        System.out.println(getVolume());
    }

    private void lookForLeaks() {
        System.out.println("Looking for leaks");
    }

    private void checkValves() {
        System.out.println("Checking valves...");
    }
}
