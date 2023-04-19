package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.ValidationException;
import pl.edu.pja.s28687.load.IPassengers;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.validators.ICarPassengerValidator;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class PassengerCar extends AbstractLoadCarrier<IPassengers> implements IPowered, IPassengerCarrier {
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
    private final ICarPassengerValidator validator;

    public PassengerCar(int id, ICarPassengerValidator validator) {
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
    protected void handleFailedLoading(IPassengers load) {
        if (!validator.validateFlags(load, this)) {
            Set<LoadType> incompatibleFlags = new HashSet<>(load.flags());
            incompatibleFlags.removeAll(allowedLoadFlags());
            throw new ValidationException("Load type incompatible !" + incompatibleFlags);
        } else if (!validator.validateWeight(load, this)) {
            throw new ValidationException(
                    "Load too heavy !" + "\nPayload limit available: "
                            + getGrossWeight().subtract(getCurrentWeight())
                            + " Load weight: " + load.getWeight());
        } else if (!validator.validateSeats(load, this)) {
            throw new ValidationException("Not enough passenger seats!");
        }
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
        logger.log(Level.WARNING,"Checking tickets..");
    }

    private void checkFirstAidKit() {
        logger.log(Level.INFO,"Checking first aid kit..");
    }

    private void checkFireExtinguisher() {
        logger.log(Level.INFO,"Checking fire extinguisher..");
    }
}
