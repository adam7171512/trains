package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.IGaseous;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.util.Set;


public class GaseousLoadCar extends BasicFreightCarABC<IGaseous> {
    private static final String SHIPPER = "TOYOTA";
    private static final String SECURITY_INFO =
            """
                    Warning: This is a gaseous load car.
                    Please keep a safe distance and do not approach.
                    Do not attempt to open any valves or containers
                    without proper authorization and training.
                    In case of an emergency, follow the evacuation procedures
                    and contact trained personnel immediately.
                    """;

    public GaseousLoadCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
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
