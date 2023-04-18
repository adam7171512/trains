package pl.edu.pja.s28687.cars;

import java.math.BigDecimal;
import java.util.logging.Level;

public class RestaurantCar extends RailroadCar implements IPowered {
    private static final String SHIPPER = "SIEMENS";
    private static final String SECURITY_INFO =
            """
                    Notice: This is a restaurant car.
                    Please follow safety guidelines and be mindful of hot surfaces,
                    sharp objects, and food allergens.
                    In case of an emergency, remain calm and follow the instructions of the staff.
                    """;
    private static final BigDecimal NET_WEIGHT = BigDecimal.valueOf(15);
    private static final BigDecimal GROSS_WEIGHT = BigDecimal.valueOf(45);
    private static final int NUMBER_OF_SEATS = 42;

    public RestaurantCar(int id) {
        super(SHIPPER, SECURITY_INFO, NET_WEIGHT, GROSS_WEIGHT, NUMBER_OF_SEATS, id);
    }

    @Override
    public CarType getCarType() {
        return CarType.RESTAURANT;
    }

    @Override
    public void emergencyProcedure() {
        logger.log(Level.INFO,
                "Attention! Emergency in restaurant car!" +
                        " Please remain calm and follow the instructions of the staff.");
    }

    @Override
    public void routineProcedure() {
        logger.log(Level.INFO, "Cleaning staff is cleaning the car");
    }

    @Override
    public void safetyCheck() {
        logger.log(Level.INFO, "Checking the fire extinguisher");
    }

    @Override
    public boolean isPowered() {
        return true;
    }
}
