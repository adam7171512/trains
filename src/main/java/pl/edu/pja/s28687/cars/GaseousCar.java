package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.ILoadValidator;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Set;


public class GaseousCar extends BasicFreightCarABC<IGaseous> {
    public GaseousCar(int id, ILoadValidator validator) {
        super(id, validator);
    }

    @Override
    public Set<Flags> allowedLoadFlags() {
        return Set.of(Flags.GASEOUS);
    }

    @Override
    public CarType getCarType() {
        return CarType.GASEOUS;
    }
}
