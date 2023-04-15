package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.load.IDeliverable;

import java.math.BigDecimal;
import java.util.*;

public abstract class LoadableRailroadCar<T extends IDeliverable> extends RailroadCar implements ILoadCarrier<T>{

    List<T> loads;
    Set<LoadType> allowedFlags;
    private BigDecimal currentWeight;

    protected ICarLoadValidator validator;
    public LoadableRailroadCar(String shipper, String securityInfo, BigDecimal netWeight, BigDecimal grossWeight, int numberOfSeats, int id, ICarLoadValidator validator) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id);
        loads = new ArrayList<>();
        currentWeight = this.getNetWeight();
        this.validator = validator;
    }

    @Override
    public boolean load(T load) {
        if (!validator.validateFlags(load, this)){
            Set<LoadType> incompatibleFlags = new HashSet<>(load.flags());
            incompatibleFlags.removeAll(allowedLoadFlags());
            System.err.println("Load type incompatible !" + incompatibleFlags);
            return false;
        }
        if (!validator.validateWeight(load, this)){
            System.err.println("Load too heavy !");
            System.err.println(
                    "\nPayload limit available: " + getGrossWeight().subtract(getCurrentWeight())
                            + " Load weight: " + load.getWeight()

                    );
            return false;
        }
        if (validateLoad(load)){
            loads.add(load);
            load.setLoaded();
            currentWeight = currentWeight.add(load.getWeight());
            return true;
        }
        return false;
    }

    @Override
    public boolean unLoad(T load){
        boolean removed = loads.remove(load);
        if (removed){
            load.setDeloaded();
            currentWeight = currentWeight.subtract(load.getWeight());
        }
        return removed;
    }


    public BigDecimal getCurrentWeight(){
        return currentWeight;
    }

    public boolean validateWeight(IDeliverable load){
        return validator.validateWeight(load, this);
    }

    public boolean validateFlags(IDeliverable load){
        return validator.validateFlags(load, this);
    }

    public Set<LoadType> getAllowedLoadFlags(){
        return allowedFlags != null ? allowedFlags : allowedLoadFlags();
    }

    public void setAllowedFlags(Set<LoadType> flags){
        allowedFlags = flags;
    }
    public abstract Set<LoadType> allowedLoadFlags();

    public List<T> getLoads(){
        return loads;
    }
    @Override
    public boolean validateLoad(IDeliverable load){
        return validator.validate(load, this);
    }


}
