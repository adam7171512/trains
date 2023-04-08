package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.*;

import java.util.Set;


public class GaseousLoadCar extends BasicFreightCarABC<IGaseous> {
    public GaseousLoadCar(int id, ICarLoadValidator validator) {
        super(id, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.GASEOUS);
    }

    @Override
    public CarType getCarType() {
        return CarType.GASEOUS;
    }

    @Override
    public boolean isPowered() {
        return false;
    }
}
