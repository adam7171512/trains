package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.IDeliverable;

import java.math.BigDecimal;
import java.util.*;

public abstract class FreightCarABC<T extends IDeliverable> extends LoadableRailroadCar<T> {

    private List<? super T> loads = new ArrayList<>();

    public FreightCarABC(String shipper, String securityInfo, BigDecimal netWeight, BigDecimal grossWeight, int numberOfSeats, int id, ICarLoadValidator validator) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id, validator);
    }
}
