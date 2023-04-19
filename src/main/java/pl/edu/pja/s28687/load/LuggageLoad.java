package pl.edu.pja.s28687.load;

import java.util.Set;

public class LuggageLoad extends Load implements ILuggage {
    private final int count;
    private boolean fragile;
    private boolean overSized;
    private boolean priority;

    public LuggageLoad(int id, double weight) {
        super(id, weight);
        this.count = 1;

    }

    public LuggageLoad(int id, double weight, int count) {
        super(id, weight);
        this.count = count;
    }

    public LuggageLoad(int id, double weight, int count, boolean fragile, boolean overSized, boolean priority) {
        super(id, weight);
        this.count = count;
        this.fragile = fragile;
        this.overSized = overSized;
        this.priority = priority;
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.LUGGAGE);
    }

    @Override
    public String getBasicInfo() {
        return "ID : " + getId() + " Luggage load, Weight : " + getWeight() + " Count: " + count;
    }

    @Override
    public String getFullInfo() {
        return getBasicInfo() + " Fragile: " + fragile + " Oversized: " + overSized + " Priority: " + priority + "\n" + getDescription();
    }

    @Override
    public boolean isFragile() {
        return fragile;
    }

    @Override
    public boolean isOverSized() {
        return overSized;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isPriority() {
        return priority;
    }
}
