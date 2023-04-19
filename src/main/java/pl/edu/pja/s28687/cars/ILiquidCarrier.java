package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.ILiquid;

import java.math.BigDecimal;

public interface ILiquidCarrier extends ILoadCarrier<ILiquid> {
    BigDecimal getVolume();
    BigDecimal getAvailableVolume();
    void openValve();
    void closeValve();
}
