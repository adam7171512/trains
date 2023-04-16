package pl.edu.pja.s28687.validators.locomotive;

import pl.edu.pja.s28687.ILocomotive;
import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.validators.IValidator;

import java.util.List;

/**
 * An interface for validators that check if a load can be loaded onto a train.
 * It extends the IValidator interface with two additional methods.
 * It checks if loading the load onto the train would not exceed the train's weight limit
 * and if the train has a car that can carry the load.
 * It also provides a method that returns a list of cars that can carry the load.
 */
public interface ILocomotiveLoadValidator extends IValidator<IDeliverable, ILocomotive> {

    /**
     * Returns List of validated Load Carriers that can carry the given load.
     *
     * @param load     The load to be validated
     * @param receiver The locomotive to which the potential load carriers belong
     * @return List of validated Load Carriers that can carry the given load
     */
    List<ILoadCarrier<? extends IDeliverable>> getCarsThatCouldLoad(IDeliverable load, ILocomotive receiver);

    /**
     * Validates whether a given load can be added to one of the cars
     * attached to the given locomotive based on the locomotive's available weight limit.
     *
     * @param load     The load to be validated
     * @param receiver The locomotive to which potential load carrier belongs
     * @return true if the load can be added to the locomotive based on its weight limit, false otherwise
     */
    boolean validateWeight(IDeliverable load, ILocomotive receiver);
}
