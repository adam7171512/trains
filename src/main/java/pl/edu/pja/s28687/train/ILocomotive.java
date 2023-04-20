package pl.edu.pja.s28687.train;

import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.load.IDeliverable;

import java.math.BigDecimal;
import java.util.List;

public interface ILocomotive {

    List<ILoadCarrier<? extends IDeliverable>> getLoadCarriers();
    BigDecimal getMaxPayload();
    BigDecimal getCurrentPayload();
    BigDecimal getAvailablePayload();

}
