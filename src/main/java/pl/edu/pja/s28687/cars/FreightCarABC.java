package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.Load;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.util.*;

public abstract class FreightCarABC<T extends IDeliverable> extends LoadableRailroadCar<T> {

    private List<? super T> loads = new ArrayList<>();

    public FreightCarABC(String shipper, String securityInfo, BigDecimal netWeight, BigDecimal grossWeight, int numberOfSeats, int id) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id);
    }

    @Override
    public String validateLoad(Load<? super T> load){
        String message = "";
        if (! validateWeight(load)) message += "Load is too Heavy\n";
        Optional<Set<Flags>> nonCompliantFlags = validateFlags(load);
        if (nonCompliantFlags.isPresent()) message += "Incompatible cargo type!" + nonCompliantFlags.get();
        return message;
    }
}
