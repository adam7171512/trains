package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.IExplosive;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.util.Set;


public class ExplosiveLoadCar extends HeavyFreightCarABC<IExplosive> {
    private static final String SHIPPER = "ALSTOM";
    private static final String SECURITY_INFO =
            """
                    Danger: This railroad car contains explosives.
                    Keep a safe distance and do not approach.
                    Do not use any electronic devices or equipment
                    that may produce sparks or flames in the vicinity of the car.
                    In case of an emergency, follow the evacuation procedures
                    and contact trained personnel immediately.
                    """;

    public ExplosiveLoadCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
    }


    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.EXPLOSIVE);
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
