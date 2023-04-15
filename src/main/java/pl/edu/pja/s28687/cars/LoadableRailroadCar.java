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

public abstract class LoadableRailroadCar<T extends IDeliverable> extends RailroadCar implements ILoadCarrier<T> {

    protected ICarLoadValidator validator;
    List<T> loads;
    Set<LoadType> allowedFlags;
    private BigDecimal currentWeight;

    public LoadableRailroadCar(String shipper, String securityInfo, BigDecimal netWeight, BigDecimal grossWeight, int numberOfSeats, int id, ICarLoadValidator validator) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id);
        loads = new ArrayList<>();
        currentWeight = this.getNetWeight();
        this.validator = validator;
    }

    @Override
    public boolean load(T load) {
        if (!validator.validate(load, this)) {
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
        loads.add(load);
        load.setLoaded();
        currentWeight = currentWeight.add(load.getWeight());
        return true;
    }

    @Override
    public boolean unLoad(T load) {
        if (!loads.contains(load)) {
            throw new ValidationException("Load not found in this car!");
        }
        loads.remove(load);
        load.setDeloaded();
        currentWeight = currentWeight.subtract(load.getWeight());
        return true;
    }


    public BigDecimal getCurrentWeight() {
        return currentWeight;
    }

    public boolean validateWeight(IDeliverable load) {
        return validator.validateWeight(load, this);
    }

    public boolean validateFlags(IDeliverable load) {
        return validator.validateFlags(load, this);
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
    public boolean validateLoad(IDeliverable load) {
        return validator.validate(load, this);
    }


}
