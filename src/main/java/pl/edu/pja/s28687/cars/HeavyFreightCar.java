package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.IFreightValidator;
import pl.edu.pja.s28687.ILoadValidator;
import pl.edu.pja.s28687.IValidator;
import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.IHeavyFreight;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Set;


public class HeavyFreightCar extends HeavyFreightCarABC<IHeavyFreight>{

    public HeavyFreightCar(int id, ILoadValidator validator) {
        super(id, validator);
    }

    @Override
    public Set<Flags> allowedLoadFlags() {
        return Set.of(Flags.HEAVY_FREIGHT);
    }

    @Override
    public CarType getCarType() {
        return CarType.HEAVY_FREIGHT;
    }
}
