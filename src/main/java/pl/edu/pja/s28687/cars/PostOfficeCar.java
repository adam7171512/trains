package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.IMail;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.math.BigDecimal;
import java.util.Set;

public class PostOfficeCar extends LoadableRailroadCar<IMail> implements IPowered {

    private static final String SHIPPER = "GENERAL ELECTRIC";
    private static final String SECURITY_INFO =
            """
                    Warning: This is a post office car.
                    Please handle mail and packages with care
                    and do not attempt to tamper with any materials.
                    Any suspicious activity should be reported immediately to the authorities.
                    """;
    private static final BigDecimal NET_WEIGHT = BigDecimal.valueOf(15);
    private static final BigDecimal GROSS_WEIGHT = BigDecimal.valueOf(40);
    private static final int NUMBER_OF_SEATS = 22;

    public PostOfficeCar(int id, ICarLoadValidator loadValidator) {
        super(SHIPPER, SECURITY_INFO, NET_WEIGHT, GROSS_WEIGHT, NUMBER_OF_SEATS, id, loadValidator);
    }

    @Override
    public CarType getCarType() {
        return CarType.POST_OFFICE;
    }

    @Override
    public boolean isPowered() {
        return true;
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.MAIL);
    }
}
