package pl.edu.pja.s28687.validators;

import pl.edu.pja.s28687.ILocomotive;
import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.Load;

import java.util.List;

public interface ILocomotiveLoadValidator extends IValidator<Load<? extends IDeliverable>, ILocomotive> {

    boolean validate(Load<? extends IDeliverable> load, ILocomotive receiver);
    List<ILoadCarrier<? extends IDeliverable>> getCarsThatCouldLoad(Load<? extends IDeliverable> load, ILocomotive receiver);
    boolean validateWeight(Load<? extends IDeliverable> load, ILocomotive receiver);
}
