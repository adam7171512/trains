package pl.edu.pja.s28687.load;

import java.util.Set;

public class ToxicLoad extends Load implements IToxic {
    public ToxicLoad(double weight) {
        super(weight);
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.TOXIC);
    }

}
