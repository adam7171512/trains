package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.Locomotive;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractRailroadCar implements IRailroadCar {
    //todo: find info on weight etc of typical cars and locomotives
    protected static final Logger logger = Logger.getLogger(AbstractRailroadCar.class.getName());

    static {
        logger.setLevel(Level.SEVERE);
    }

    private final String shipper;
    private final String securityInfo;
    private final BigDecimal netWeight;
    private final BigDecimal grossWeight;
    private final int numberOfSeats;
    private final int id;
    private BigDecimal currentWeight;
    private Optional<Locomotive> locomotive = Optional.empty();

    public AbstractRailroadCar(String shipper, String securityInfo, BigDecimal netWeight, BigDecimal grossWeight, int numberOfSeats, int id) {
        this.shipper = shipper;
        this.securityInfo = securityInfo;
        this.netWeight = netWeight;
        this.currentWeight = netWeight;
        this.grossWeight = grossWeight;
        this.numberOfSeats = numberOfSeats;
        this.id = id;
    }

    public static void setLogLevel(java.util.logging.Level level) {
        logger.setLevel(level);
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

    public String getShipper() {
        return shipper == null ? "Unknown" : shipper;
    }

    public String getSecurityInfo() {
        return securityInfo == null ? "No security info" : securityInfo;
    }

    public abstract String getBasicInfo();

    public String getFullInfo() {
        return getBasicInfo() + getManufacturerInfo();
    }

    public String getManufacturerInfo() {
        return String.format(
                "\nShipper: %s\nSecurity info: \n%s",
                getShipper(), getSecurityInfo());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().
                append("Car ID : ").append(getId()).
                append(" | Car type : ").append(getCarType())
                .append(" | Current Weight : ").append(getCurrentWeight())
                .append(" tonnes");
        if (isPowered()) {
            stringBuilder.append(" | Powered");
        } else {
            stringBuilder.append(" | Not powered");
        }
        locomotive.ifPresent(value -> stringBuilder.append(" | Attached to locomotive ID : ")
                .append(value.getId()));
        return stringBuilder.toString();
    }
}
