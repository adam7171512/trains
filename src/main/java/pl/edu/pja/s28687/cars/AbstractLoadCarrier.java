package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.ValidationException;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public abstract class AbstractLoadCarrier<T extends IDeliverable> extends AbstractRailroadCar implements ILoadCarrier<T> {
    protected ICarLoadValidator validator;
    List<T> loads;
    Set<LoadType> allowedFlags;
    private boolean locked = false;

    public AbstractLoadCarrier(String shipper, String securityInfo, BigDecimal netWeight, BigDecimal grossWeight, int numberOfSeats, int id, ICarLoadValidator validator) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id);
        loads = new ArrayList<>();
        this.validator = validator;
    }

    @Override
    public boolean load(T load) {
        if (!validator.validate(load, this)) {
            handleFailedLoading(load);
        }
        logger.log(Level.INFO, "Load " + load + " loaded to " + this);
        loads.add(load);
        load.setLoaded();
        return true;
    }

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
            //todo add more specific exception
            throw new ValidationException("Locomotive can't handle this much payload!");
        }
    }

    @Override
    public boolean unLoad(T load) {
        if (!loads.contains(load)) {
            throw new ValidationException("Load not found in this car!");
        }
        if (isLocked()) {
            throw new ValidationException("Car is locked!");
        }
        loads.remove(load);
        load.setDeloaded();
        logger.log(Level.INFO, "Load " + load + " unloaded from " + this);
        return true;
    }

    @Override
    public BigDecimal getCargoWeight() {
        return loads
                .stream()
                .map(IDeliverable::getWeight)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getAvailablePayload() {
        return getGrossWeight().subtract(getCurrentWeight());
    }

    public BigDecimal getCurrentWeight() {
        return loads
                .stream()
                .map(IDeliverable::getWeight)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(getNetWeight());
    }

    @Override
    public BigDecimal getMaxPayload() {
        return getGrossWeight().subtract(getNetWeight());
    }

    public Set<LoadType> getAllowedLoadFlags() {
        return allowedFlags != null ? allowedFlags : allowedLoadFlags();
    }

    public void setAllowedFlags(Set<LoadType> flags) {
        allowedFlags = flags;
    }

    public abstract Set<LoadType> allowedLoadFlags();

    public List<T> getLoads() {
        return loads;
    }

    @Override
    public String getBasicInfo() {
        return this + "\nPayload :  " + getCargoWeight()
                + "  / " + getMaxPayload() + " tonnes , compatible load types : " + getAllowedLoadFlags();
    }

    @Override
    public String getFullInfo() {
        return getBasicInfo() + getManufacturerInfo() + getCargoStats();
    }

    @Override
    public String getCargoStats() {
        StringBuilder stats = new StringBuilder()
                .append("\nTotal cargo weight: ")
                .append(getCargoWeight())
                .append(" tonnes")
                .append("\nUnits loaded: ")
                .append(getLoads().size());
        getLoads()
                .stream()
                .map(IDeliverable::getBasicInfo)
                .forEach(s -> stats.append(s).append("\n"));
        return stats.toString();
    }

    @Override
    public boolean validateLoad(IDeliverable load) {
        return !isLocked() && validator.validate(load, this);
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void safetyLock() {
        locked = true;
        logger.log(Level.INFO, "Car " + this.getCarType() + " " + this.getId() + " locked");
    }

    @Override
    public void safetyUnlock() {
        locked = false;
        logger.log(Level.INFO, "Car " + this.getCarType() + " " + this.getId() + " unlocked");
    }


}
