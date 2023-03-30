package pl.edu.pja.s28687.load;

import java.util.HashSet;
import java.util.Set;

public class Explosives extends Load<IExplosive> {
    public Explosives(double weight) {
        super(weight);
    }

    @Override
    public Set<Flags> flags() {
        return Set.of(Flags.EXPLOSIVE);
    }
}
