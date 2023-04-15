package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.IHeavyFreight;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.util.Set;


public class HeavyFreightCar extends HeavyFreightCarABC<IHeavyFreight> {
    private static final String SHIPPER = "URSUS";
    private static final String SECURITY_INFO =
            """
                    Attention: This is a heavy freight car.
                    Please be aware of the size and weight of the cargo being transported
                    and keep a safe distance from the car.
                    In case of an emergency, follow the evacuation procedures
                    and contact trained personnel immediately.
                    """;

    public HeavyFreightCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.HEAVY_FREIGHT);
    }

    @Override
    public CarType getCarType() {
        return CarType.HEAVY_FREIGHT;
    }

    @Override
    public boolean isPowered() {
        return false;
    }
}
