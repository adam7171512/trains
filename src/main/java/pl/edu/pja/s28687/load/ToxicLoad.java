package pl.edu.pja.s28687.load;

import java.util.HashSet;
import java.util.Set;

public class ToxicLoad extends Load<IToxic> {
    public ToxicLoad(double weight) {
        super(weight);
    }

    @Override
    public Set<Flags> flags() {
        return Set.of(Flags.TOXIC);
    }

}
