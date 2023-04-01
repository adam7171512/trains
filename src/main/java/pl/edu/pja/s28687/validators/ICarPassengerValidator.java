package pl.edu.pja.s28687.validators;

import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.Load;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

public interface ICarPassengerValidator extends ICarLoadValidator {


    boolean validateSeats(Load<? extends IDeliverable> load, IPassengerCarrier receiver);
}


//public interface IPassengerValidator extends IValidator{
//
//    boolean validateFlags(Load<?> component, PassengerCar receiver);
//    boolean validatePassengers(Load<?> component, PassengerCar receiver);
//}