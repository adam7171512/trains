package pl.edu.pja.s28687.Cars;

import pl.edu.pja.s28687.Load.Load;
import pl.edu.pja.s28687.Load.ILuggage;
import pl.edu.pja.s28687.Load.Luggage;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.math.BigDecimal;

public class MailAndLuggageCar extends LoadableRailroadCar<ILuggage> {
    static final String shipper = "Siemens";
    static final String securityInfo = "SecInfoMail";
    static final BigDecimal netWeight = BigDecimal.valueOf(6.4);
    static final BigDecimal grossWeight = BigDecimal.valueOf(96.7);
    static final int numberOfSeats = 0;
    public MailAndLuggageCar(LocoBase locoBase) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, locoBase);
        setAllowableFlags();
    }

    @Override
    public String validateLoad(Load<?> load) {
        return "";
    }
}
