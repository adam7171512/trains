package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.util.Set;

public class RestaurantCar extends RailroadCar implements IPowered{
    static final String shipper = "Siemens";
    static final String securityInfo = "SecInfoRestaurant";
    static final BigDecimal netWeight = BigDecimal.valueOf(6.4);
    static final BigDecimal grossWeight = BigDecimal.valueOf(46.7);
    static final int numberOfSeats = 42;
    public RestaurantCar(int id) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id);
    }

    @Override
    public CarType getCarType() {
        return CarType.RESTAURANT;
    }

    @Override
    public boolean isPowered() {
        return true;
    }
}
