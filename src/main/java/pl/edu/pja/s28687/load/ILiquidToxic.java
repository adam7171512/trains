package pl.edu.pja.s28687.load;

import java.math.BigDecimal;

public interface ILiquidToxic extends IDeliverable, IHazMat, ILiquid{
    BigDecimal getVolume();
    BigDecimal getCombustionTemperature();
}
