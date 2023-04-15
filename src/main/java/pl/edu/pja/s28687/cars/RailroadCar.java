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
}
