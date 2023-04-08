package pl.edu.pja.s28687.load;

import java.util.Set;

public class ToxicGasLoad extends Load implements IGaseous {
    public ToxicGasLoad(double weight) {
        super(weight);
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.GASEOUS, LoadType.TOXIC);
    }
}
