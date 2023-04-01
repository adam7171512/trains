package pl.edu.pja.s28687.cars;
import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.*;

import java.util.Set;


public class LiquidLoadCar extends BasicFreightCarABC<LiquidLoad> {
    public LiquidLoadCar(int id, ICarLoadValidator validator) {
        super(id, validator);
    }

    @Override
    public Set<Flags> allowedLoadFlags() {
        return Set.of(Flags.LIQUID);
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
