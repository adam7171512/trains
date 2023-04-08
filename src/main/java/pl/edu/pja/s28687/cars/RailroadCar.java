package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.load.LoadType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public abstract class RailroadCar implements IRailroadCar{
    private String shipper;
    private String securityInfo;
    private BigDecimal netWeight;
    private BigDecimal grossWeight;
    private BigDecimal currentWeight;
    private int numberOfSeats;
    private int id;
    protected Set<LoadType> forbidden;
    protected Set<LoadType> allowedLoadFlags;
    private String name;
    private Optional<Locomotive> locomotive = Optional.empty();

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
                LoadType.REFRIGERATED,
                LoadType.GASEOUS,
                LoadType.PASSENGERS,
                LoadType.BASIC_FREIGHT,
                LoadType.HEAVY_FREIGHT,
                LoadType.LUGGAGE,
                LoadType.LIQUID,
                LoadType.TOXIC,
                LoadType.EXPLOSIVE));
    }

    public void setName(String name){
        this.name = name;
    }

    public BigDecimal getMaxWeight(){
        return grossWeight.setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal getNetWeight(){
        return netWeight.setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal getCurrentWeight() {
        return currentWeight.setScale(2, RoundingMode.CEILING);
    }


    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public void setAttachedTo(Locomotive locomotive){
        this.locomotive = Optional.of(locomotive);
    }

    public Optional<Locomotive> getLocomotive(){
        return locomotive;
    }

    public void setDetached(){
        this.locomotive = Optional.empty();
    }

    public boolean isAttached(){
        return locomotive.isPresent();
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public abstract CarType getCarType();

    public abstract boolean isPowered();



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
