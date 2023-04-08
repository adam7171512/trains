package pl.edu.pja.s28687.validators;
import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.Load;

public interface ICarLoadValidator extends IValidator<IDeliverable, ILoadCarrier<? extends IDeliverable>> {
 boolean validate(IDeliverable load,  ILoadCarrier<? extends IDeliverable> receiver);
 boolean validateFlags(IDeliverable load,  ILoadCarrier<? extends IDeliverable> receiver);
 boolean validateWeight(IDeliverable load,  ILoadCarrier<? extends IDeliverable> receiver);

}


