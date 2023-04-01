package pl.edu.pja.s28687.validators;
import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.Load;

public interface ICarLoadValidator extends IValidator<Load<? extends IDeliverable>, ILoadCarrier<? extends IDeliverable>> {
 boolean validate(Load<? extends IDeliverable> load,  ILoadCarrier<? extends IDeliverable> receiver);
 boolean validateFlags(Load<? extends IDeliverable> load,  ILoadCarrier<? extends IDeliverable> receiver);
 boolean validateWeight(Load<? extends IDeliverable> load,  ILoadCarrier<? extends IDeliverable> receiver);

}


