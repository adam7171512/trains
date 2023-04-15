package pl.edu.pja.s28687.load;

import java.math.BigDecimal;
import java.util.Set;

public class LiquidToxicLoad extends Load implements ILiquidToxic{
    private BigDecimal volume;
    public LiquidToxicLoad(double weight, double volume) {
        super(weight);
        this.volume = BigDecimal.valueOf(volume);
    }
    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.LIQUID_TOXIC);
    }

}
