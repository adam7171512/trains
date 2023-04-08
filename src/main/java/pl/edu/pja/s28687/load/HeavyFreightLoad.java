package pl.edu.pja.s28687.load;

import java.util.Set;

public class HeavyFreightLoad extends Load implements IHeavyFreight {
    public HeavyFreightLoad(double weight) {
        super(weight);
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.HEAVY_FREIGHT);
    }


}
