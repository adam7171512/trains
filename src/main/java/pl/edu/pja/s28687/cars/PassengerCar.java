package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.*;

import java.math.BigDecimal;
import java.util.Set;

public class PassengerCar extends LoadableRailroadCar<IPassengers> implements IPowered, IPassengerCarrier{
    static final String shipper = "Siemens";
    static final String securityInfo = "SecInfoPass";
    static final BigDecimal netWeight = BigDecimal.valueOf(6.4);
    static final BigDecimal grossWeight = BigDecimal.valueOf(46.7);
    static final int numberOfSeats = 82;
    private final ICarLoadValidator validator;
    public PassengerCar(int id, ICarLoadValidator validator){
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id, validator);
        this.validator = validator;
    }

    public int getNumberOfPassengers(){
        return loads.stream().map(IPassengers::getPassengersCount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).intValue();
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.PASSENGERS);
    }

    @Override
    public CarType getCarType() {
        return CarType.PASSENGERS;
    }

    @Override
    public boolean isPowered() {
        return true;
    }
}
