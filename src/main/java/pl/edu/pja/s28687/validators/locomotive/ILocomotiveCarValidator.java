package pl.edu.pja.s28687.validators.locomotive;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.cars.RailroadCar;
import pl.edu.pja.s28687.validators.IValidator;

public interface ILocomotiveCarValidator extends IValidator<IRailroadCar, Locomotive> {

    boolean validateCarLimit(IRailroadCar car, Locomotive receiver);
    boolean validatePoweredCarLimit(IRailroadCar car, Locomotive receiver);
    boolean validatePayloadLimit(IRailroadCar car, Locomotive receiver);



}
