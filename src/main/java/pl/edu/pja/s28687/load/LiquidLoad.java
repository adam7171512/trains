package pl.edu.pja.s28687.load;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

public class LiquidLoad extends Load<ILiquid> {
    private BigDecimal volume;
    public LiquidLoad(double weight, double volume) {
        super(weight);
        this.volume = BigDecimal.valueOf(volume);
    }

    @Override
    public Set<Flags> flags() {
        return Set.of(Flags.LIQUID);
    }

}
