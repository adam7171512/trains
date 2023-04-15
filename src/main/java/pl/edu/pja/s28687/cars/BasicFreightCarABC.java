package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.IDeliverable;

import java.math.BigDecimal;

public abstract class BasicFreightCarABC<T extends IDeliverable> extends LoadableRailroadCar<T>{
    private static final BigDecimal NET_WEIGHT = BigDecimal.valueOf(20);
    private static final BigDecimal GROSS_WEIGHT = BigDecimal.valueOf(70);
    private static final int NUMBER_OF_SEATS = 4;

    public BasicFreightCarABC(int id, String shipper, String securityInfo, ICarLoadValidator validator) {
        super(shipper, securityInfo, NET_WEIGHT, GROSS_WEIGHT, NUMBER_OF_SEATS, id, validator);
    }


}
