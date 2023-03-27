package pl.edu.pja.s28687.Cars;

import pl.edu.pja.s28687.Load.IBasicFreight;
import pl.edu.pja.s28687.Load.IDeliverable;
import pl.edu.pja.s28687.Load.Load;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.math.BigDecimal;

public abstract class BasicFreightCarABC<T extends IDeliverable> extends FreightCarABC<T>{
    static final String shipper = "Siemens";
    static final String securityInfo = "SecInfoBasicFreight";
    static final BigDecimal netWeight = BigDecimal.valueOf(10.4);
    static final BigDecimal grossWeight = BigDecimal.valueOf(446.7);
    static final int numberOfSeats = 4;

    public BasicFreightCarABC(LocoBase locoBase) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, locoBase);
    }


}
