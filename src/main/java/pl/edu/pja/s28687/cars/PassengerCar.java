package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.IPassengers;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.math.BigDecimal;
import java.util.Set;

public class PassengerCar extends LoadableRailroadCar<IPassengers> implements IPowered, IPassengerCarrier {
    private static final String SHIPPER = "SIEMENS";
    private static final String SECURITY_INFO =
            """
                    Attention: This is a passenger car.
                    Please keep your belongings with you at all times\s
                    and be aware of your surroundings.\s
                    In case of an emergency, remain calm and follow the instructions of the staff.
                    """;
    private static final BigDecimal NET_WEIGHT = BigDecimal.valueOf(15);
    private static final BigDecimal GROSS_WEIGHT = BigDecimal.valueOf(40);
    private static final int NUMBER_OF_SEATS = 82;
    private final ICarLoadValidator validator;

    public PassengerCar(int id, ICarLoadValidator validator) {
        super(SHIPPER, SECURITY_INFO, NET_WEIGHT, GROSS_WEIGHT, NUMBER_OF_SEATS, id, validator);
        this.validator = validator;
    }

    public int getNumberOfPassengers() {
        return loads.stream().map(IPassengers::getPassengersCount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).intValue();
    }

    public int getNumberOfSeats() {
        return NUMBER_OF_SEATS;
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.PASSENGERS);
    }

    @Override
    public void safetyCheck() {
        checkFirstAidKit();
        checkFireExtinguisher();
    }

    @Override
    public void emergencyUnloading() {
        if(isLocked()){
            safetyUnlock();
        }
        System.out.println("Attention! All passengers must evacuate the car immediately!");
        for (IPassengers load : loads) {
           unLoad(load);
        }
        safetyLock();
    }

    @Override
    public String getCargoStats() {
        return null;
    }

    @Override
    public CarType getCarType() {
        return CarType.PASSENGERS;
    }

    @Override
    public void emergencyProcedure() {
        System.out.println(
                "Attention! Emergy procedure started! in car " +
                        getId() +
                        " Please don't panic! " +
                        "Follow the instructions of the staff!");
        if (isLocked()) {
            safetyUnlock();
        }
    }

    @Override
    public void routineProcedure() {
        checkTickets();
    }

    @Override
    public boolean isPowered() {
        return true;
    }

    private void checkTickets() {
        System.out.println("Checking passenger tickets..");
    }

    private void checkFirstAidKit() {
        System.out.println("Checking first aid kit..");
    }

    private void checkFireExtinguisher() {
        System.out.println("Checking fire extinguisher..");
    }
}
