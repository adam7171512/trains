package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.FreightValidator;
import pl.edu.pja.s28687.IFreightValidator;
import pl.edu.pja.s28687.ILoadValidator;
import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.Load;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.util.*;

public abstract class FreightCarABC<T extends IDeliverable> extends LoadableRailroadCar<T> {

    private List<? super T> loads = new ArrayList<>();

    public FreightCarABC(String shipper, String securityInfo, BigDecimal netWeight, BigDecimal grossWeight, int numberOfSeats, int id, ILoadValidator validator) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id, validator);
    }

    @Override
    public String validateLoad(Load<?> load){
        validator.validate(load, this);
        String message = "";
        if (! validateWeight(load)) message += "Load is too Heavy\n";
        Optional<Set<Flags>> nonCompliantFlags = validateFlags(load);
        if (nonCompliantFlags.isPresent()) message += "Incompatible cargo type!" + nonCompliantFlags.get();
        return message;
    }
}
