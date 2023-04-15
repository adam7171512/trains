package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.IDeliverable;

import java.math.BigDecimal;

public abstract class HeavyFreightCarABC<T extends IDeliverable> extends LoadableRailroadCar<T>{

    static final BigDecimal netWeight = BigDecimal.valueOf(10.4);
    static final BigDecimal grossWeight = BigDecimal.valueOf(446.7);
    static final int numberOfSeats = 4;
    public HeavyFreightCarABC(String shipper, String securityInfo, BigDecimal netWeight, BigDecimal grossWeight, int numberOfSeats, int id, ICarLoadValidator validator) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id, validator);
    }

    public HeavyFreightCarABC(int id, String shipper, String securityInfo,  ICarLoadValidator validator){
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id, validator);
    }
}
