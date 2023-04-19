package pl.edu.pja.s28687.load;

import java.util.Set;

public class HeavyFreightLoad extends Load implements IHeavyFreight {
    boolean highValue;
    boolean overSized;

    public HeavyFreightLoad(int id, double weight) {
        super(id, weight);
    }

    public HeavyFreightLoad(int id, double weight, boolean highValue, boolean overSized) {
        super(id, weight);
        this.highValue = highValue;
        this.overSized = overSized;
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.HEAVY_FREIGHT);
    }

    @Override
    public String getBasicInfo() {
        return "ID : " + getId() + " Heavy freight load, Weight : " + getWeight() + " tonnes";
    }

    @Override
    public String getFullInfo() {
        return getBasicInfo() + (isHighValue() ? " High value" : "")
                + (isOverSized() ? " Over sized" : "") + "\n" + getDescription();
    }


    @Override
    public boolean isHighValue() {
        return highValue;
    }

    @Override
    public boolean isOverSized() {
        return overSized;
    }
}
