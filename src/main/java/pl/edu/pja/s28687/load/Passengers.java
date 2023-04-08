package pl.edu.pja.s28687.load;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public class Passengers extends Load implements IPassengers {

    public Passengers(int quantity) {
        super(quantity * 0.08);
        this.quantity = BigDecimal.valueOf(quantity).setScale(2, RoundingMode.CEILING);
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.PASSENGERS);
    }

    @Override
    public BigDecimal getPassengersCount() {
        return quantity;
    }
}
