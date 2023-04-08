package pl.edu.pja.s28687.validators.locomotive;

import pl.edu.pja.s28687.ILocomotive;
import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.Load;
import pl.edu.pja.s28687.validators.IValidator;

import java.util.List;

public interface ILocomotiveLoadValidator extends IValidator<IDeliverable, ILocomotive> {

    boolean validate(IDeliverable load, ILocomotive receiver);
    <T extends IDeliverable> List<ILoadCarrier<T>> getCarsThatCouldLoad(IDeliverable load, ILocomotive receiver);
    boolean validateWeight(IDeliverable load, ILocomotive receiver);
}
