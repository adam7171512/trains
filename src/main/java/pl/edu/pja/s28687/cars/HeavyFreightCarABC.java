package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.math.BigDecimal;

public abstract class HeavyFreightCarABC<T extends IDeliverable> extends LoadableRailroadCar<T> {

    static final BigDecimal netWeight = BigDecimal.valueOf(30);
    static final BigDecimal grossWeight = BigDecimal.valueOf(120);
    static final int numberOfSeats = 4;

    public HeavyFreightCarABC(int id, String shipper, String securityInfo, ICarLoadValidator validator) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id, validator);
    }
}
