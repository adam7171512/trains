package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.Load;
import pl.edu.pja.s28687.load.ILuggage;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.util.Set;

public class MailAndLuggageCar extends LoadableRailroadCar<ILuggage> {
    static final String shipper = "Siemens";
    static final String securityInfo = "SecInfoMail";
    static final BigDecimal netWeight = BigDecimal.valueOf(6.4);
    static final BigDecimal grossWeight = BigDecimal.valueOf(96.7);
    static final int numberOfSeats = 0;
    public MailAndLuggageCar(int id) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id);
    }

    @Override
    public String validateLoad(Load<? super ILuggage> load) {
        String message = "";
        if (validateFlags(load).isPresent()) message += "Load is not allowed in this car!\n";
        if (! validateWeight(load)) message += "Load is too heavy and would exceed car's weight limit!\n";
        return message;
    }

    @Override
    public Set<Flags> allowedLoadFlags() {
        return Set.of(Flags.LUGGAGE);
    }

    @Override
    public CarType getCarType() {
        return CarType.LUGGAGE;
    }
}
