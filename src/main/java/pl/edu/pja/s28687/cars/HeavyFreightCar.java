package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.IHeavyFreight;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Set;


public class HeavyFreightCar extends HeavyFreightCarABC<IHeavyFreight>{

    public HeavyFreightCar(int id) {
        super(id);
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
