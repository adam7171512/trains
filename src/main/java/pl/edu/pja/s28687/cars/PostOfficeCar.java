package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.IMail;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.math.BigDecimal;
import java.util.Set;
import java.util.logging.Level;

public class PostOfficeCar extends AbstractLoadCarrier<IMail> implements IPowered {

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
    public void emergencyProcedure() {
        logger.log(Level.SEVERE,
                "Attention! Emergency in post office car!" +
                        " Please remain calm and follow the instructions of the staff.");
        if (getLocomotive().isPresent()) {
            getLocomotive().get().raiseAlert("Emergency in post office car!" +
                    getId() + "Please send help");
        }
    }

    @Override
    public void routineProcedure() {
        System.out.println("Sorting mail");
    }

    @Override
    public boolean isPowered() {
        return true;
    }


    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.MAIL);
    }

    @Override
    public void safetyCheck() {
        System.out.println("Scanning selected mail wih xray..");
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
        for (IMail load : loads) {
            unLoad(load);
        }
        safetyLock();
        System.out.println("Emergency unloading complete");
    }

    @Override
    public String getCargoStats() {
        int letters = loads.stream()
                .mapToInt(IMail::getCount)
                .sum();
        StringBuilder stats = new StringBuilder()
                .append("\nTotal Mail estimated weight: ")
                .append(getCargoWeight())
                .append(" tonnes")
                .append("\nLetters carried : ")
                .append(letters);
        getLoads()
                .stream()
                .map(IMail::getBasicInfo)
                .forEach(stats::append);
        return stats.toString();
    }
}
