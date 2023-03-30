package pl.edu.pja.s28687.cars;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Set;


public class ToxicCar extends HeavyFreightCarABC<IToxic>{
    public ToxicCar(int id) {
        super(id);
    }

    @Override
    public Set<Flags> allowedLoadFlags() {
        return Set.of(Flags.TOXIC);
    }

    @Override
    public CarType getCarType() {
        return CarType.TOXIC;
    }
}
