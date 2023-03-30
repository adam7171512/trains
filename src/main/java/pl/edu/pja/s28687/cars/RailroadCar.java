package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.Flags;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class RailroadCar {
    private String shipper;
    private String securityInfo;
    private BigDecimal netWeight;
    private BigDecimal grossWeight;
    private BigDecimal currentWeight;
    private int numberOfSeats;
    private int id;
    protected Set<Flags> forbidden;
    protected Set<Flags> allowedLoadFlags;
    private String name;
    private boolean attached = false;

    public RailroadCar(String shipper, String securityInfo, BigDecimal netWeight, BigDecimal grossWeight, int numberOfSeats, int id) {
        this.shipper = shipper;
        this.securityInfo = securityInfo;
        this.netWeight = netWeight;
        this.currentWeight = netWeight;
        this.grossWeight = grossWeight;
        this.numberOfSeats = numberOfSeats;
        this.id = id;
        currentWeight = currentWeight.setScale(2, RoundingMode.CEILING);
        grossWeight = grossWeight.setScale(2, RoundingMode.CEILING);
        netWeight = netWeight.setScale(2, RoundingMode.CEILING);

        forbidden = new HashSet<>(Set.of(
                Flags.REFRIGERATED,
                Flags.GASEOUS,
                Flags.PASSENGERS,
                Flags.BASIC_FREIGHT,
                Flags.HEAVY_FREIGHT,
                Flags.LUGGAGE,
                Flags.LIQUID,
                Flags.TOXIC,
                Flags.EXPLOSIVE));
    }

    public void setName(String name){
        this.name = name;
    }

    public BigDecimal grossWeight(){
        return grossWeight;
    }

    public BigDecimal netWeight(){
        return netWeight;
    }

    public BigDecimal getCurrentWeight() {
        return currentWeight;
    }


    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public void attach(){
        attached = true;
    }

    public void detach(){
        attached = false;
    }

    public boolean isAttached(){
        return attached;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public abstract CarType getCarType();



    //    public void load(T load){
//        if (validateLoad(load)) this.load.add(load);
//    }
//

//    public void load(T load){
//        if (! validatePassengerLimit(load)) throw new IllegalArgumentException("Too many passengers !");
//        if (! validateLoadWeight(load)) throw new IllegalArgumentException("Load too heavy!");
//        currentWeight = currentWeight.add(load.getWeight());
//        // need to fix it
//        this.load.add(load);
//    }
//
//    public void deload(Load load){
//        this.load.remove(load);
//        currentWeight = currentWeight.subtract(load.getWeight());
//    }

//    private boolean validatePassengerLimit(T load){
//        int currentPassengers = this.load.stream().map(T::getPassengers).reduce(Integer::sum).orElse(0);
//        return currentPassengers + load.getPassengers() < numberOfSeats;
//    }
//
//    private boolean validateLoadWeight(Load load){
//        BigDecimal currentFreight = this.load.stream().map(T::getWeight).reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0));
//        return currentFreight.add(this.netWeight).
//                add(load.getWeight()).
//                add(netWeight).
//                compareTo(grossWeight)
//                < 1;
//    }
}
