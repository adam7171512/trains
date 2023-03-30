package pl.edu.pja.s28687.load;

import java.util.HashSet;
import java.util.Set;

public class ToxicGasLoad extends Load<IGaseousToxic> {
    public ToxicGasLoad(double weight) {
        super(weight);
    }

    @Override
    public Set<Flags> flags() {
        return Set.of(Flags.GASEOUS, Flags.TOXIC);
    }
}
