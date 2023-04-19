package pl.edu.pja.s28687.load;

import java.math.BigDecimal;

public interface IRefrigerated extends IDeliverable{
    BigDecimal requiredTemperature();
}
