package pl.edu.pja.s28687.validators;

import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.Load;

public class CarFreightValidator implements ICarFreightValidator {

    @Override
    public boolean validate(Load<? extends IDeliverable> load, ILoadCarrier<? extends IDeliverable> receiver) {
        return validateFlags(load, receiver) && validateWeight(load, receiver);
    }

    @Override
    public boolean validateFlags(Load<? extends IDeliverable> load, ILoadCarrier<? extends IDeliverable> receiver) {
        return receiver.allowedLoadFlags().containsAll(load.flags());
    }

    @Override
    public boolean validateWeight(Load<? extends IDeliverable> load, ILoadCarrier<? extends IDeliverable> receiver) {
        if (receiver.getLocomotive().isPresent()
                && receiver
                .getLocomotive()
                .get()
                .getAvailablePayload()
                .compareTo(load.getWeight()) < 0)
            return false;
        else {
            return receiver.getCurrentWeight().add(load.getWeight()).compareTo(receiver.getGrossWeight()) <= 0;
        }
    }
}