package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.IHeavyFreight;

import java.util.Set;


public class HeavyFreightCar extends HeavyFreightCarABC<IHeavyFreight>{

    public HeavyFreightCar(int id, ICarLoadValidator validator) {
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

    @Override
    public boolean isPowered() {
        return false;
    }
}
