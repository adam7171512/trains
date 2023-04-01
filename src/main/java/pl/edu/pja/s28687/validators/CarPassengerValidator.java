package pl.edu.pja.s28687.validators;

import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.Load;


public class CarPassengerValidator implements ICarPassengerValidator {

    @Override
    public boolean validate(Load<? extends IDeliverable> load, ILoadCarrier<? extends IDeliverable> receiver) {
        return validateSeats(load, (IPassengerCarrier) receiver) && validateFlags(load, receiver) && validateWeight(load, receiver);
    }

    @Override
    public boolean validateFlags(Load<? extends IDeliverable> load, ILoadCarrier<? extends IDeliverable> receiver) {
        return load.flags().containsAll(receiver.allowedLoadFlags());
    }

    @Override
    public boolean validateWeight(Load<? extends IDeliverable> load, ILoadCarrier<? extends IDeliverable> receiver) {
        if (receiver.getLocomotive().isPresent()
                && receiver
                .getLocomotive()
                .get()
                .getAvailablePayload()
                .compareTo(load.getWeight()) < 0){
            return false;
        }
        else return load
                .getWeight()
                .add(receiver.getCurrentWeight())
                .compareTo(receiver.getGrossWeight()) <= 0;
    }

    @Override
    public boolean validateSeats(Load<? extends IDeliverable> load, IPassengerCarrier receiver) {
        return receiver.getNumberOfPassengers() + load.getQuantity().intValue()
                <= receiver.getNumberOfSeats();
    }
}
