package pl.edu.pja.s28687.load;

import java.util.Set;

public class Explosives extends Load implements IExplosive {
    private static final String HAZMAT = "Explosive load. Must be handled with care.";
    private static final String EXPLOSIVE_MATERIAL = "Dynamite";
    public Explosives(double weight) {
        super(weight);
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.EXPLOSIVE);
    }

    @Override
    public String getExplosiveMaterial() {
        return EXPLOSIVE_MATERIAL;
    }

    @Override
    public String getHazMatInstructions() {
        return HAZMAT;
    }
}
