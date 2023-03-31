package pl.edu.pja.s28687;

import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.IPassengers;
import pl.edu.pja.s28687.load.Load;

import java.math.BigDecimal;

//public class PassengerValidator implements IPassengerValidator {
//
//    @Override
//    public boolean validateFlags(Load<? extends IDeliverable> component, PassengerCar receiver) {
//        return component.flags().containsAll(receiver.allowedLoadFlags()) ;
//    }
//
//    public boolean validatePassengers(Load<?> component, PassengerCar receiver) {
//        return receiver.getNumberOfPassengers() + component.getQuantity().intValue() <= receiver.getNumberOfSeats();
//    }
//
//    @Override
//    public boolean validate(Load<? extends IPassengers> load, PassengerCar receiver) {
//        return validateFlags(load, receiver) && validatePassengers(load, receiver);
//    }
//}

//public class PassengerValidator implements IPassengerValidator {

//
//    public boolean validateFlags(Load<?> component, PassengerCar receiver) {
//        return component.flags().containsAll(receiver.allowedLoadFlags()) ;
//    }
//
//    public boolean validatePassengers(Load<?> component, PassengerCar receiver) {
//        return receiver.getNumberOfPassengers() + component.getQuantity().intValue() <= receiver.getNumberOfSeats();
//    }
//
//    @Override
//    public <T extends ILoadCarrier<? extends IDeliverable>> boolean validate(Load<?> load, T receiver) {
//        return validateFlags(load, (PassengerCar) receiver) && validatePassengers(load, (PassengerCar) receiver);
//    }
//}

public class PassengerValidator implements IPassengerValidator {

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
        return load.getWeight().add(receiver.getCurrentWeight()).compareTo(receiver.getGrossWeight()) <= 0;
    }

    @Override
    public boolean validateSeats(Load<? extends IDeliverable> load, IPassengerCarrier receiver) {
        return receiver.getNumberOfPassengers() + load.getQuantity().intValue() <= receiver.getNumberOfSeats();
    }
}
