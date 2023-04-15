package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.load.ILuggage;

import java.math.BigDecimal;
import java.util.Set;

public class MailAndLuggageCar extends LoadableRailroadCar<ILuggage> {
    static final String shipper = "Siemens";
    static final String securityInfo = "SecInfoMail";
    static final BigDecimal netWeight = BigDecimal.valueOf(6.4);
    static final BigDecimal grossWeight = BigDecimal.valueOf(96.7);
    static final int numberOfSeats = 0;
    public MailAndLuggageCar(int id, ICarLoadValidator validator) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.LUGGAGE);
    }

    @Override
    public CarType getCarType() {
        return CarType.LUGGAGE;
    }

    @Override
    public boolean isPowered() {
        return false;
    }
}
