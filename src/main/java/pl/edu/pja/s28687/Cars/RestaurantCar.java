package pl.edu.pja.s28687.Cars;

import pl.edu.pja.s28687.Logistics.LocoBase;

import java.math.BigDecimal;

public class RestaurantCar extends RailroadCar implements IPowered{
    static final String shipper = "Siemens";
    static final String securityInfo = "SecInfoRestaurant";
    static final BigDecimal netWeight = BigDecimal.valueOf(6.4);
    static final BigDecimal grossWeight = BigDecimal.valueOf(46.7);
    static final int numberOfSeats = 42;
    public RestaurantCar(LocoBase locoBase) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, locoBase);
    }
}
