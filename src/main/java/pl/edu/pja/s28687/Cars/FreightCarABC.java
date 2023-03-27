package pl.edu.pja.s28687.Cars;

import pl.edu.pja.s28687.Load.Flags;
import pl.edu.pja.s28687.Load.Load;
import pl.edu.pja.s28687.Load.IDeliverable;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.math.BigDecimal;
import java.util.*;

public abstract class FreightCarABC<T extends IDeliverable> extends LoadableRailroadCar<T> {

    private List<? super T> loads = new ArrayList<>();

    public FreightCarABC(String shipper, String securityInfo, BigDecimal netWeight, BigDecimal grossWeight, int numberOfSeats, LocoBase locobase) {
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, locobase);
    }

    @Override
    public String validateLoad(Load<?> load){
        String message = "";
        if (! validateWeight(load)) message += "Load is too Heavy\n";
        Optional<Set<Flags>> nonCompliantFlags = validateFlags(load);
        if (nonCompliantFlags.isPresent()) message += "Incompatible cargo type!" + nonCompliantFlags.get();
        return message;
    }

    public Optional<Set<Flags>> validateFlags(Load<?> load) {
        Optional<Set<Flags>> intersection = Optional.empty();
        Set<Flags> inters = new HashSet<>(forbidden);
        inters.retainAll(load.flags());
        if (! inters.isEmpty()) intersection = Optional.of(inters);
        return intersection;
    }


}
