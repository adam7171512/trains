package pl.edu.pja.s28687.cars;

import java.math.BigDecimal;

public interface IGasCarrier {
    void openValve();
    void closeValve();
    BigDecimal getPressure();
    BigDecimal getTemperature();
}
