package pl.edu.pja.s28687.validators;

import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.load.IDeliverable;

/**
 * An interface for Cargo validators for Load Carriers.
 * It extends the IValidator interface with two additional methods.
 * Provides methods to validate whether the load can be added to the carrier
 * based on load flags, and weight.
 */
public interface ICarLoadValidator extends IValidator<IDeliverable, ILoadCarrier<? extends IDeliverable>> {

    /**
     * Validates whether a given load can be added to the given load carrier
     * based on the load carrier's load flags.
     *
     * @param load     The load to be validated
     * @param receiver The load carrier that will receive the validated load
     * @return true if the load can be added to the load carrier based on its load flags, false otherwise
     */
    boolean validateFlags(IDeliverable load, ILoadCarrier<? extends IDeliverable> receiver);

    /**
     * Validates whether a given load can be added to the given load carrier
     * based on the load carrier's available weight limit.
     *
     * @param load     The load to be validated
     * @param receiver The load carrier that will receive the validated load
     * @return true if the load can be added to the load carrier based on its weight limit, false otherwise
     */
    boolean validateWeight(IDeliverable load, ILoadCarrier<? extends IDeliverable> receiver);

}


