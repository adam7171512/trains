package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.load.IBasicFreight;

import java.util.Set;

public class BasicFreightCar extends BasicFreightCarABC<IBasicFreight>{
    public BasicFreightCar(int id, ICarLoadValidator validator) {
        super(id, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.BASIC_FREIGHT);
    }

    @Override
    public CarType getCarType() {
        return CarType.BASIC_FREIGHT;
    }

    @Override
    public boolean isPowered() {
        return false;
    }
}
