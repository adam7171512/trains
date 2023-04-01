package pl.edu.pja.s28687.load;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class LiquidToxicLoad extends Load<ILiquidToxic> implements ILiquidToxic{
    public LiquidToxicLoad(double weight) {
        super(weight);
    }

    public LiquidToxicLoad(int quantity, double weight) {
        super(quantity, weight);
    }

    @Override
    public Set<Flags> flags() {
        return Set.of(Flags.LIQUID, Flags.TOXIC);
    }

    @Override
    public BigDecimal getVolume() {
        return null;
    }

    @Override
    public BigDecimal getDensity() {
        return null;
    }
}
