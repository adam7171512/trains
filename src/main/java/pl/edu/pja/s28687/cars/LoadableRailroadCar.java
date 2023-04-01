package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.Load;
import pl.edu.pja.s28687.load.IDeliverable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public abstract class LoadableRailroadCar<T extends IDeliverable> extends RailroadCar implements ILoadCarrier<T>{

    List<Load<? super T>> loads;
    Set<Flags> allowedFlags;
    private BigDecimal currentWeight;

    protected ICarLoadValidator validator;
    public LoadableRailroadCar(String shipper, String securityInfo, BigDecimal netWeight, BigDecimal grossWeight, int numberOfSeats, int id, ICarLoadValidator validator) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id);
        loads = new ArrayList<>();
        currentWeight = this.getNetWeight();
        this.validator = validator;
    }

    @Override
    public boolean load(Load<? super T> load) {
        if (validateLoad(load)){
            loads.add(load);
            load.setLoaded();
            currentWeight = currentWeight.add(load.getWeight());
            return true;
        }
        return false;
    }

    @Override
    public boolean unLoad(Load<?> load){
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

    public boolean validateWeight(Load<?> load){
        return validator.validateWeight(load, this);
    }

    public boolean validateFlags(Load<?> load){
        return validator.validateFlags(load, this);
    }

    public Set<Flags> getAllowedLoadFlags(){
        return allowedFlags != null ? allowedFlags : allowedLoadFlags();
    }

    public void setAllowedFlags(Set<Flags> flags){
        allowedFlags = flags;
    }
    public abstract Set<Flags> allowedLoadFlags();

    public List<Load<? super T>> getLoads(){
        return loads;
    }

    public boolean validateLoad(Load<? extends IDeliverable> load){
        return validator.validate(load, this);
    }


}
