package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.ILiquidToxic;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.util.Set;


public class LiquidToxicLoadCar extends HeavyFreightCarABC<ILiquidToxic> {
    private static final String SHIPPER = "POLMOS";
    private static final String SECURITY_INFO =
            """
                    Danger: This railroad car contains toxic liquid materials.
                    Keep a safe distance and do not approach.
                    In case of a spill or leak, immediately evacuate the area
                    and contact trained personnel for assistance.
                    """;

    public LiquidToxicLoadCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.LIQUID_TOXIC);
    }

    @Override
    public CarType getCarType() {
        return CarType.LIQUID_TOXIC;
    }

    @Override
    public boolean isPowered() {
        return false;
    }
}
