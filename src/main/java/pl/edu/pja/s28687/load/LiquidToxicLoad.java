package pl.edu.pja.s28687.load;

import java.util.Set;

public class LiquidToxicLoad extends Load implements ILiquidToxic{
    public LiquidToxicLoad(double weight) {
        super(weight);
    }

    public LiquidToxicLoad(int quantity, double weight) {
        super(quantity, weight);
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.LIQUID, LoadType.TOXIC);
    }

}
