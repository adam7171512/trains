package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.math.BigDecimal;

public abstract class AbstractHeavyFreightCar<T extends IDeliverable> extends AbstractLoadCarrier<T> {

    static final BigDecimal netWeight = BigDecimal.valueOf(30);
    static final BigDecimal grossWeight = BigDecimal.valueOf(120);
    static final int numberOfSeats = 4;

    public AbstractHeavyFreightCar(int id, String shipper, String securityInfo, ICarLoadValidator validator) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id, validator);
    }
}
