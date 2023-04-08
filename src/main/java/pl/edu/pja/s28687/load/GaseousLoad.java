package pl.edu.pja.s28687.load;

import java.util.Set;

public class GaseousLoad extends Load implements IGaseous {
    public GaseousLoad(double weight) {
        super(weight);
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.GASEOUS);
    }

}
