package pl.edu.pja.s28687.load;

import java.util.Set;

public class BasicFreightLoad extends Load implements IBasicFreight, ICountable {

    private boolean fragile;
    private boolean highValue;
    private int count;

    public BasicFreightLoad(int id, double weight) {
        super(id, weight);
        this.count = 1;
    }

    public BasicFreightLoad(int id, double weight, int count) {
        super(id, weight);
        this.count = count;
    }

    public BasicFreightLoad(int id, double weight, int count, boolean fragile, boolean highValue) {
        super(id, weight);
        this.count = count;
        this.fragile = fragile;
        this.highValue = highValue;
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.BASIC_FREIGHT);
    }

    @Override
    public String getBasicInfo() {
        return "ID : " + getId() + " Basic freight load, Weight : " + getWeight() + " tonnes";
    }

    @Override
    public String getFullInfo() {
        return getBasicInfo() + (isFragile() ? " Fragile" : "")
                + (isHighValue() ? " High value" : "") + " Count: " + count + "\n" + getDescription();
    }

    @Override
    public boolean isFragile() {
        return fragile;
    }

    @Override
    public boolean isHighValue() {
        return highValue;
    }

    @Override
    public int getCount() {
        return count;
    }
}
