package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.ILiquidToxic;

import java.math.BigDecimal;

public interface ILiquidToxicCarrier extends ILoadCarrier<ILiquidToxic>{
    BigDecimal getVolume();
    BigDecimal getAvailableVolume();
    void openValve();
    void closeValve();
    void performResidueCleaning();
}
