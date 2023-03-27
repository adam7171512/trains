package pl.edu.pja.s28687.Load;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Passengers extends Load<IPassengers> {

    public Passengers(int quantity) {
        super(quantity * 0.08);
        this.quantity = BigDecimal.valueOf(quantity).setScale(2, RoundingMode.CEILING);
    }

    @Override
    public void setFlags() {
        flags.add(Flags.PASSENGERS);
    }
}
