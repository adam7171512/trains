package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.IFreightValidator;
import pl.edu.pja.s28687.ILoadValidator;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;

public abstract class BasicFreightCarABC<T extends IDeliverable> extends FreightCarABC<T>{
    static final String shipper = "Siemens";
    static final String securityInfo = "SecInfoBasicFreight";
    static final BigDecimal netWeight = BigDecimal.valueOf(10.4);
    static final BigDecimal grossWeight = BigDecimal.valueOf(446.7);
    static final int numberOfSeats = 4;

    public BasicFreightCarABC(int id, ILoadValidator validator) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id, validator);
    }


}
