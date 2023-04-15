package pl.edu.pja.s28687.cars;
import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.*;

import java.util.Set;


public class LiquidLoadCar extends BasicFreightCarABC<ILiquid> {
    public LiquidLoadCar(int id, ICarLoadValidator validator) {
        super(id, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.LIQUID);
    }

    @Override
    public CarType getCarType() {
        return CarType.LIQUID;
    }

    @Override
    public boolean isPowered() {
        return false;
    }
}
