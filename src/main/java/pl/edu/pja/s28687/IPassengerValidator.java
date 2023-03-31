package pl.edu.pja.s28687;

import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.IPassengers;
import pl.edu.pja.s28687.load.Load;

public interface IPassengerValidator extends ILoadValidator{


    boolean validateSeats(Load<? extends IDeliverable> load, IPassengerCarrier receiver);
}


//public interface IPassengerValidator extends IValidator{
//
//    boolean validateFlags(Load<?> component, PassengerCar receiver);
//    boolean validatePassengers(Load<?> component, PassengerCar receiver);
//}