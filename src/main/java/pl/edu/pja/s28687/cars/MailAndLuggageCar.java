package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.ILuggage;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.math.BigDecimal;
import java.util.Set;

public class MailAndLuggageCar extends LoadableRailroadCar<ILuggage> {
    private static final String SHIPPER = "ALSTOM";
    private static final String SECURITY_INFO =
            """
                    Notice: This is a mail car.
                    Please handle mail and packages with care
                    and refrain from tampering with any materials.
                    In case of suspicious activity, report it immediately to the authorities.
                    """;
    private static final BigDecimal NET_WEIGHT = BigDecimal.valueOf(15);
    private static final BigDecimal GROSS_WEIGHT = BigDecimal.valueOf(40);
    private static final int NUMBER_OF_SEATS = 0;

    public MailAndLuggageCar(int id, ICarLoadValidator validator) {
        super(SHIPPER, SECURITY_INFO, NET_WEIGHT, GROSS_WEIGHT, NUMBER_OF_SEATS, id, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.LUGGAGE);
    }

    @Override
    public CarType getCarType() {
        return CarType.LUGGAGE;
    }

    @Override
    public boolean isPowered() {
        return false;
    }
}
