package pl.edu.pja.s28687;

import pl.edu.pja.s28687.IFreightValidator;
import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.Load;

public class FreightValidator implements IFreightValidator {

    @Override
    public boolean validate(Load<? extends IDeliverable> load, ILoadCarrier<? extends IDeliverable> receiver) {
        return false;
    }

    @Override
    public boolean validateFlags(Load<? extends IDeliverable> load, ILoadCarrier<? extends IDeliverable> receiver) {
        return false;
    }

    @Override
    public boolean validateWeight(Load<? extends IDeliverable> load, ILoadCarrier<? extends IDeliverable> receiver) {
        return false;
    }
}