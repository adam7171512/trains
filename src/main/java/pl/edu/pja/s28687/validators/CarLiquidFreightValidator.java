package pl.edu.pja.s28687.validators;

import pl.edu.pja.s28687.cars.ILiquidCarrier;
import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.ILiquid;

public class CarLiquidFreightValidator extends CarFreightValidator implements ICarLiquidFreightValidator {

    @Override
    public boolean validate(IDeliverable load, ILoadCarrier<? extends IDeliverable> receiver) {
        if (! (load instanceof ILiquid)){
            return false;
        }
        else return validateVolume((ILiquid) load, (ILiquidCarrier) receiver)
                && validateFlags(load, receiver)
                && validateWeight(load, receiver);
    }

    @Override
    public boolean validateVolume(ILiquid load, ILiquidCarrier receiver) {
        return load.getVolume().compareTo(receiver.getAvailableVolume()) <= 0;
    }
}
