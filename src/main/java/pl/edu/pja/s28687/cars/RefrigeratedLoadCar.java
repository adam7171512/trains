package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.IRefrigerated;

import java.util.Set;

public class RefrigeratedLoadCar extends BasicFreightCarABC<IRefrigerated> implements IPowered{

    public RefrigeratedLoadCar(int id, ICarLoadValidator validator) {
        super(id, validator);
    }

    @Override
    public Set<Flags> allowedLoadFlags() {
        return Set.of(Flags.REFRIGERATED);
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
