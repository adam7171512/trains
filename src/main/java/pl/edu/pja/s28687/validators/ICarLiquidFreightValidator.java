package pl.edu.pja.s28687.validators;

import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.ILiquid;
import pl.edu.pja.s28687.load.Load;

public interface ICarLiquidFreightValidator extends ICarLoadValidator{

    boolean validateVolume(ILiquid load, ILiquidCarrier receiver);
}
