package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.load.IRefrigerated;

import java.util.Set;

public class RefrigeratedLoadCar extends BasicFreightCarABC<IRefrigerated> implements IPowered{

    public RefrigeratedLoadCar(int id, ICarLoadValidator validator) {
        super(id, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.REFRIGERATED);
    }

    @Override
    public CarType getCarType() {
        return CarType.REFRIGERATED;
    }

    @Override
    public boolean isPowered() {
        return true;
    }
}
