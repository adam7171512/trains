package pl.edu.pja.s28687.cars;
import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.*;

import java.util.Set;


public class ToxicLoadCar extends HeavyFreightCarABC<IToxic>{
    public ToxicLoadCar(int id, ICarLoadValidator validator) {
        super(id, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.TOXIC);
    }

    @Override
    public CarType getCarType() {
        return CarType.TOXIC;
    }

    @Override
    public boolean isPowered() {
        return true;
    }
}
