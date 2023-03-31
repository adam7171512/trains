package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.IFreightValidator;
import pl.edu.pja.s28687.ILoadValidator;
import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.IBasicFreight;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Set;

public class BasicFreightCar extends BasicFreightCarABC<IBasicFreight>{
    public BasicFreightCar(int id, ILoadValidator validator) {
        super(id, validator);
    }

    @Override
    public Set<Flags> allowedLoadFlags() {
        return Set.of(Flags.BASIC_FREIGHT);
    }

    @Override
    public CarType getCarType() {
        return CarType.BASIC_FREIGHT;
    }
}
