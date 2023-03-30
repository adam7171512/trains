package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.Load;
import pl.edu.pja.s28687.load.IPassengers;
import pl.edu.pja.s28687.load.Passengers;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.util.Set;

public class PassengerCar extends LoadableRailroadCar<IPassengers> implements IPowered{
    static final String shipper = "Siemens";
    static final String securityInfo = "SecInfoPass";
    static final BigDecimal netWeight = BigDecimal.valueOf(6.4);
    static final BigDecimal grossWeight = BigDecimal.valueOf(46.7);
    static final int numberOfSeats = 82;
    public PassengerCar(int id){
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, id);
    }


    @Override
    public void load (Load<? super IPassengers> load) throws IllegalArgumentException{
        try {
            String message = validateLoad(load);
            if (message.isEmpty()){
                loads.add(load);
                load.setLoaded();
            }
            else throw new IllegalArgumentException("Could not add passenger load !");
        } catch (IllegalArgumentException e) {
            System.err.println("Could not load the passengers ! " + e);
        }
    }
    @Override
    public String validateLoad(Load<? super IPassengers> load) {
        String message = "";
        if (validateFlags(load).isPresent()) message += "Load is not allowed in this car!\n";
        if (! validateSeats(load)) message += "Not enough seats available in the car\n";
        if (! validateWeight(load)) message += "Load is too heavy and would exceed car's weight limit!\n";
        return message;
    }

    public int getNumberOfPassengers(){
        return loads.stream().map(Load::getQuantity).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).intValue();
    }

    public boolean validateSeats(Load<? super IPassengers> load) {
        return getNumberOfPassengers()
                + load.getQuantity().intValue()
                <= numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    @Override
    public Set<Flags> allowedLoadFlags() {
        return Set.of(Flags.PASSENGERS);
    }

    @Override
    public CarType getCarType() {
        return CarType.PASSENGERS;
    }
}
