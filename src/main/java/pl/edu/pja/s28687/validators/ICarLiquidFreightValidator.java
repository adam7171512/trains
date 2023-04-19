package pl.edu.pja.s28687.validators;

import pl.edu.pja.s28687.cars.ILiquidCarrier;
import pl.edu.pja.s28687.load.ILiquid;

public interface ICarLiquidFreightValidator extends ICarLoadValidator{
    boolean validateVolume(ILiquid load, ILiquidCarrier receiver);
}
