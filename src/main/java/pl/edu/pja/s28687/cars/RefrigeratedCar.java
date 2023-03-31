package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.IFreightValidator;
import pl.edu.pja.s28687.ILoadValidator;
import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.IRefrigerated;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Set;

public class RefrigeratedCar extends BasicFreightCarABC<IRefrigerated> implements IPowered{

    public RefrigeratedCar(int id, ILoadValidator validator) {
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
}
