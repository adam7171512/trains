package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.ILiquid;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.util.Set;


public class LiquidLoadCar extends BasicFreightCarABC<ILiquid> {
    private static final String SHIPPER = "POLMOS";
    private static final String SECURITY_INFO =
            """
                    Caution: This railroad car contains liquid materials.
                    Keep a safe distance and do not approach.
                    In case of a spill or leak, follow proper procedures for containment
                    and contact trained personnel immediately.
                    """;

    public LiquidLoadCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.LIQUID);
    }

    @Override
    public CarType getCarType() {
        return CarType.LIQUID;
    }

    @Override
    public boolean isPowered() {
        return false;
    }
}
