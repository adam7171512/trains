package pl.edu.pja.s28687.load;

import java.util.Set;

public class ToxicLoad extends Load implements IToxic {
    private boolean flammable;
    public ToxicLoad(int id, double weight) {
        super(id, weight);
    }

    public ToxicLoad(int id, double weight, boolean flammable) {
        super(id, weight);
        this.flammable = flammable;
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.TOXIC);
    }

    @Override
    public String getBasicInfo() {
        return "ID : " + getId() + " Toxic load. Weight: " + getWeight() + " tonnes";
    }

    @Override
    public String getFullInfo() {
        return getBasicInfo() + ((flammable) ? " Flammable ! " : "") + "\n" + getHazMatInstructions();
    }

    @Override
    public String getHazMatInstructions() {
        return "Toxic load. Must be handled with care.";
    }

    @Override
    public boolean isFlammable() {
        return flammable;
    }
}
