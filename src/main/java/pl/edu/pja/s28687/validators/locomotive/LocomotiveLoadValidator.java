package pl.edu.pja.s28687.validators.locomotive;

import pl.edu.pja.s28687.train.ILocomotive;
import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.load.IDeliverable;

import java.util.List;

public class LocomotiveLoadValidator implements ILocomotiveLoadValidator {
    @Override
    public boolean validate(IDeliverable component, ILocomotive receiver) {
        return (validateWeight(component, receiver) && ! getCarsThatCouldLoad(component, receiver).isEmpty());
    }

    @Override
    public List<ILoadCarrier<? extends IDeliverable>> getCarsThatCouldLoad(IDeliverable load, ILocomotive receiver) {
        return receiver.getLoadCarriers().stream().filter(car -> car.validateLoad(load)).toList();
    }

    @Override
    public boolean validateWeight(IDeliverable load, ILocomotive receiver) {
        return receiver.getCurrentPayload().add(load.getWeight()).compareTo(receiver.getMaxPayload()) <= 0;
    }
}
