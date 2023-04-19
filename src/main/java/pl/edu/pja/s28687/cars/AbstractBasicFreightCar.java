package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.math.BigDecimal;

public abstract class AbstractBasicFreightCar<T extends IDeliverable> extends AbstractLoadCarrier<T> {
    private static final BigDecimal NET_WEIGHT = BigDecimal.valueOf(20);
    private static final BigDecimal GROSS_WEIGHT = BigDecimal.valueOf(70);
    private static final int NUMBER_OF_SEATS = 4;

    public AbstractBasicFreightCar(int id, String shipper, String securityInfo, ICarLoadValidator validator) {
        super(shipper, securityInfo, NET_WEIGHT, GROSS_WEIGHT, NUMBER_OF_SEATS, id, validator);
    }


}
