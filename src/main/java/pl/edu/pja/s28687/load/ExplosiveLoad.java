package pl.edu.pja.s28687.load;

import java.util.Set;

public class ExplosiveLoad extends Load implements IExplosive {
    private String hazMatInstructions;
    private boolean sensitive;
    public ExplosiveLoad(int id, double weight) {
        super(id, weight);
    }

    public ExplosiveLoad(int id, double weight, boolean sensitive) {
        super(id, weight);
        this.sensitive = sensitive;
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.EXPLOSIVE);
    }

    @Override
    public String getBasicInfo() {
        return "ID : " + getId() + " Explosive load, Weight : " + getWeight() + " tonnes";
    }

    @Override
    public String getFullInfo() {
        return getBasicInfo() + "\n" + getHazMatInstructions()
                + (isSensitive() ? " Sensitive" : "") + "\n" + getDescription();
    }

    public void setHazMatInstructions(String hazMatInstructions) {
        this.hazMatInstructions = hazMatInstructions;
    }

    @Override
    public String getHazMatInstructions() {
        return hazMatInstructions == null ?
                "Explosive load. Must be handled with care."
                : hazMatInstructions;
    }

    @Override
    public boolean isSensitive() {
        return sensitive;
    }
}
