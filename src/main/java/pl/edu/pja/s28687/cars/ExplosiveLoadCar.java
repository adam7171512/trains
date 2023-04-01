package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.load.*;

import java.util.Set;


public class ExplosiveLoadCar extends HeavyFreightCarABC<IExplosive> {

    public ExplosiveLoadCar(int id, ICarLoadValidator validator) {
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

    @Override
    public boolean isPowered() {
        return false;
    }

}
