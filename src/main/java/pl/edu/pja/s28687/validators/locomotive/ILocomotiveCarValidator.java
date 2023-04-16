package pl.edu.pja.s28687.validators.locomotive;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.validators.IValidator;

/**
 * An interface for validators that validate whether a railroad car can be added
 * to a locomotive based on the locomotive's car limit, powered car limit, and payload limit.
 * This interface extends the IValidator interface, which specifies a generic type parameter
 * for the objects to be validated and the receiver that will receive the validated objects.
 */
public interface ILocomotiveCarValidator extends IValidator<IRailroadCar, Locomotive> {

    /**
     * Validates whether a given railroad car can be added to the given locomotive
     * based on the locomotive's car limit.
     *
     * @param car      The railroad car to be validated
     * @param receiver The locomotive that will receive the validated railroad car
     * @return true if the car can be added to the locomotive based on its car limit, false otherwise
     */
    boolean validateCarLimit(IRailroadCar car, Locomotive receiver);

    /**
     * Validates whether a given powered railroad car can be added to the given locomotive
     * based on the locomotive's powered car number limit.
     *
     * @param car      The powered railroad car to be validated
     * @param receiver The locomotive that will receive the validated powered railroad car
     * @return true if the powered car can be added to the locomotive based on its powered
     * car limit, false otherwise
     */
    boolean validatePoweredCarLimit(IRailroadCar car, Locomotive receiver);

    /**
     * Validates whether a given railroad car can be added to the given locomotive
     * based on the locomotive's available payload limit.
     *
     * @param car      The railroad car to be validated
     * @param receiver The locomotive that will receive the validated railroad car
     * @return true if the car can be added to the locomotive based on its payload limit, false otherwise
     */
    boolean validatePayloadLimit(IRailroadCar car, Locomotive receiver);


}
