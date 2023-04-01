package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.util.Set;

public class PostOfficeCar extends RailroadCar implements IPowered{
    static final String shipper = "General Electric";
    static final String securityInfo = "SecInfoPostOffice";
    static final BigDecimal netWeight = BigDecimal.valueOf(4.4);
    static final BigDecimal grossWeight = BigDecimal.valueOf(32.7);
    static final int numberOfSeats = 22;
    public PostOfficeCar(int id) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id);
    }

    @Override
    public CarType getCarType() {
        return CarType.POST_OFFICE;
    }

    @Override
    public boolean isPowered() {
        return true;
    }
}
