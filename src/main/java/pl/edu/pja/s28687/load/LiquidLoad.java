package pl.edu.pja.s28687.load;

import java.math.BigDecimal;
import java.util.Set;

public class LiquidLoad extends Load implements ILiquid {
    private BigDecimal volume;
    public LiquidLoad(int id, double weight, double volume) {
        super(id, weight);
        this.volume = BigDecimal.valueOf(volume);
    }
    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.LIQUID);
    }

    @Override
    public String getBasicInfo() {
        return "ID : " + getId() + " Liquid load, Weight: " + getWeight()
                + " tonnes. Volume: " + volume + " m3";
    }

    @Override
    public String getFullInfo() {
        return getBasicInfo() + "\n" + getDescription();
    }

    @Override
    public BigDecimal getVolume() {
        return volume;
    }
}
