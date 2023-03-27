package pl.edu.pja.s28687.Cars;

import pl.edu.pja.s28687.Logistics.LocoBase;

import java.math.BigDecimal;

public class PostOfficeCar extends RailroadCar implements IPowered{
    static final String shipper = "General Electric";
    static final String securityInfo = "SecInfoPostOffice";
    static final BigDecimal netWeight = BigDecimal.valueOf(4.4);
    static final BigDecimal grossWeight = BigDecimal.valueOf(32.7);
    static final int numberOfSeats = 22;
    public PostOfficeCar(LocoBase locoBase) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, locoBase);
    }

}
