package pl.edu.pja.s28687.load;

import java.util.Set;

public class Explosives extends Load implements IExplosive {
    public Explosives(double weight) {
        super(weight);
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.EXPLOSIVE);
    }
}
