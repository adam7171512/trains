package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.ILoadValidator;
import pl.edu.pja.s28687.load.*;

import java.util.Set;


public class ExplosivesCar extends HeavyFreightCarABC<IExplosive> {

    public ExplosivesCar(int id, ILoadValidator validator) {
        super(id, validator);
    }


    @Override
    public Set<Flags> allowedLoadFlags() {
        return Set.of(Flags.EXPLOSIVE);
    }

    @Override
    public CarType getCarType() {
        return CarType.EXPLOSIVE;
    }

}
