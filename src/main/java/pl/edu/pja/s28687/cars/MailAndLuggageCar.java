package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.ILuggage;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.math.BigDecimal;
import java.util.Set;

public class MailAndLuggageCar extends AbstractLoadCarrier<ILuggage> {
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
        return Set.of(LoadType.LUGGAGE, LoadType.MAIL);
    }

    @Override
    public void safetyCheck() {
        if (loads.isEmpty()) {
            System.out.println("No loads to check");
            return;
        }
        for (ILuggage load : loads) {
            System.out.println("Examining luggage: " + load);
        }
    }

    @Override
    public void emergencyUnloading() {
        if (loads.isEmpty()) {
            System.out.println("No loads to unload");
            return;
        }
        System.out.println("Emergency unloading of " + loads.size() + " loads");
        if (isLocked()) {
            safetyUnlock();
        }
        for (ILuggage load : loads) {
            unLoad(load);
        }
        safetyLock();
        System.out.println("Emergency unloading complete");
    }

    @Override
    public CarType getCarType() {
        return CarType.LUGGAGE;
    }

    @Override
    public void emergencyProcedure() {
        System.out.println("Emergency situation in luggage car !!" + getId());
        if (getLocomotive().isPresent()) {
            getLocomotive()
                    .get()
                    .raiseAlert("Emergency situation in luggage car !!" + getId()
                            + " Please send help immediately");
        }
    }

    @Override
    public void routineProcedure() {
        safetyCheck();
    }

    @Override
    public boolean isPowered() {
        return false;
    }

}
