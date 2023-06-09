package pl.edu.pja.s28687.validators;

import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.IPassengers;
import pl.edu.pja.s28687.load.Load;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

public interface ICarPassengerValidator extends ICarLoadValidator {


    boolean validateSeats(IPassengers load, IPassengerCarrier receiver);
}