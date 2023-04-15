package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.IToxic;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.util.Set;


public class ToxicLoadCar extends HeavyFreightCarABC<IToxic> {
    private static final String SHIPPER = "POLMOS";
    private static final String SECURITY_INFO =
            """
                    Danger: This railroad car contains toxic materials.
                    Keep a safe distance and do not approach.
                    In case of an emergency, follow evacuation procedures
                    and contact trained personnel immediately.""";

    public ToxicLoadCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.TOXIC);
    }

    @Override
    public CarType getCarType() {
        return CarType.TOXIC;
    }

    @Override
    public boolean isPowered() {
        return true;
    }
}
