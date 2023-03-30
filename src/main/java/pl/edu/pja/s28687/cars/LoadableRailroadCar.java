package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.Load;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public abstract class LoadableRailroadCar<T extends IDeliverable> extends RailroadCar implements ILoadCarrier<T>{

    List<Load<? super T>> loads;
    Set<Flags> allowedFlags;
    public LoadableRailroadCar(String shipper, String securityInfo, BigDecimal netWeight, BigDecimal grossWeight, int numberOfSeats, int id){
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id);
        loads = new ArrayList<>();
    }

    @Override
    public  void load(Load<? super T> load) {
        String message = "";
        try {
            message = validateLoad(load);
        } catch (ClassCastException e) {
            System.err.println("Incompatible load type!" + e.getMessage());
        }
        try {
            if (message.isEmpty()) loads.add(load);
            else throw new IllegalArgumentException("Could not load the Car!\n");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage() + message);
        }
        load.setLoaded();
    }

    public void deLoad(Load<? super T> load){
//        loads.remove(loads.stream().filter(l -> l.equals(load)).findFirst().or);
        loads.remove(load);
        load.setDeloaded();
    }


    public BigDecimal getCurrentWeight(){
        return loads.stream()
                .map(Load::getWeight)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .add(netWeight())
                .setScale(2, RoundingMode.CEILING);
    }

    public boolean validateWeight(Load<?> load){
        return loads.
                stream().
                map(Load::getWeight).
                reduce(BigDecimal::add).
                orElse(BigDecimal.valueOf(0)).
                add(netWeight()).
                add(load.getWeight()).
                compareTo(grossWeight()) <= 0;
    }

    public Optional<Set<Flags>> validateFlags(Load<?> load){
        Set<Flags> loadFlags = new HashSet<>(load.flags());
        Set<Flags> carFlags = getAllowedLoadFlags();
        loadFlags.removeAll(carFlags);
        return loadFlags.isEmpty() ? Optional.empty() : Optional.of(loadFlags);
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

    public abstract String validateLoad(Load<? super T> load);
}
