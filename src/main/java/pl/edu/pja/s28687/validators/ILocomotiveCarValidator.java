package pl.edu.pja.s28687.validators;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.cars.RailroadCar;

public interface ILocomotiveCarValidator extends IValidator<RailroadCar, Locomotive> {

    boolean validateCarLimit(RailroadCar car, Locomotive receiver);
    boolean validatePoweredCarLimit(RailroadCar car, Locomotive receiver);
    boolean validatePayloadLimit(RailroadCar car, Locomotive receiver);



}
