package pl.edu.pja.s28687.validators;

import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.IPassengers;


public class CarPassengerValidator implements ICarPassengerValidator {

    @Override
    public boolean validate(IDeliverable load, ILoadCarrier<? extends IDeliverable> receiver) {
        if (load instanceof IPassengers) {
            return validateSeats((IPassengers) load,
                    (IPassengerCarrier) receiver)
                    && validateFlags(load, receiver)
                    && validateWeight(load, receiver);
        }
        else return false;
    }

    @Override
    public boolean validateFlags(IDeliverable load, ILoadCarrier<? extends IDeliverable> receiver) {
        return load.flags().containsAll(receiver.allowedLoadFlags());
    }

    @Override
    public boolean validateWeight(IDeliverable load, ILoadCarrier<? extends IDeliverable> receiver) {
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


    public boolean validateSeats(IPassengers load, IPassengerCarrier receiver) {
        return receiver.getNumberOfPassengers() + load.getPassengersCount().intValue()
                <= receiver.getNumberOfSeats();
    }
}
