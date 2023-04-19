package pl.edu.pja.s28687.load;

import java.math.BigDecimal;

public interface IGaseous extends IDeliverable{
    BigDecimal getGetDensityAtAtmosphericPressure();
    BigDecimal getBoilingPoint();
}
