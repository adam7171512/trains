package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.load.LoadType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Set;


public abstract class RailroadCar implements IRailroadCar {
    //todo: find info on weight etc of typical cars and locomotives
    private final String shipper;
    private final String securityInfo;
    private final BigDecimal netWeight;
    private final BigDecimal grossWeight;
    private final int numberOfSeats;
    private final int id;
    private BigDecimal currentWeight;
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

    @Override
    public BigDecimal getGrossWeight() {
        return grossWeight.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public BigDecimal getNetWeight() {
        return netWeight.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public BigDecimal getCurrentWeight() {
        return currentWeight.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public int getId() {
        return id;
    }

    public void setAttachedTo(Locomotive locomotive) {
        this.locomotive = Optional.of(locomotive);
    }

    public Optional<Locomotive> getLocomotive() {
        return locomotive;
    }

    public void setDetached() {
        this.locomotive = Optional.empty();
    }

    public boolean isAttached() {
        return locomotive.isPresent();
    }

    public abstract CarType getCarType();

    public abstract boolean isPowered();
}
