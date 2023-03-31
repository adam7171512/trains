package pl.edu.pja.s28687;

import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.load.IBasicFreight;
import pl.edu.pja.s28687.load.IDeliverable;

public interface IFreightCarrier extends ILoadCarrier<IBasicFreight> {
}
