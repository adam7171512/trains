package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;

public abstract class HeavyFreightCarABC<T extends IDeliverable> extends FreightCarABC<T>{

    static final String shipper = "Siemens";
    static final String securityInfo = "SecInfoHeavyFreight";
    static final BigDecimal netWeight = BigDecimal.valueOf(10.4);
    static final BigDecimal grossWeight = BigDecimal.valueOf(446.7);
    static final int numberOfSeats = 4;
    public HeavyFreightCarABC(String shipper, String securityInfo, BigDecimal netWeight, BigDecimal grossWeight, int numberOfSeats, int id) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id);
    }

    public HeavyFreightCarABC(int id){
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id);
    }
}
