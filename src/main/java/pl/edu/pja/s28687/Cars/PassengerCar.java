package pl.edu.pja.s28687.Cars;

import pl.edu.pja.s28687.Load.Flags;
import pl.edu.pja.s28687.Load.Load;
import pl.edu.pja.s28687.Load.IPassengers;
import pl.edu.pja.s28687.Load.Passengers;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.math.BigDecimal;

public class PassengerCar extends LoadableRailroadCar<IPassengers> implements IPowered{
    static final String shipper = "Siemens";
    static final String securityInfo = "SecInfoPass";
    static final BigDecimal netWeight = BigDecimal.valueOf(6.4);
    static final BigDecimal grossWeight = BigDecimal.valueOf(46.7);
    static final int numberOfSeats = 82;
    public PassengerCar(LocoBase locoBase){
        super(shipper, securityInfo, netWeight, grossWeight, numberOfSeats, locoBase);
        forbidden.remove(Flags.PASSENGERS);
        setAllowableFlags();
    }


//    @Override
//    public void load (Passengers load) throws IllegalArgumentException{
//        try {
//            String message = validateLoad(load);
//            if (message.isEmpty()) passengersLoads.add(load);
//            else throw new IllegalArgumentException("Could not add passenger load !");
//        } catch (IllegalArgumentException e) {
//            System.err.println("Could not load the passengers ! " + e);
//        }
//    }
//    @Override
//    public String validateLoad(Passengers load) {
//        String message = "";
//        if (! validateSeats(load)) message += "Not enough seats available in the car\n";
//        if (! validateWeight(load)) message += "Load is too heavy and would exceed car's weight limit!\n";
//        return message;
//    }

    public int getNumberOfPassengers(){
        return loads.stream().map(Load::getQuantity).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).intValue();
    }

    public boolean validateSeats(Passengers load) {
        return getNumberOfPassengers()
                + load.getQuantity().intValue()
                <= numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }


    @Override
    public String validateLoad(Load<?> load) {

        String s= "";
        if( ! validateSeats( (Passengers) load) ) s = " ";
        return s;
    }



}
