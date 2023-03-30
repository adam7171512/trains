package pl.edu.pja.s28687.load;

import java.util.Set;

public class HeavyFreightLoad extends Load<IHeavyFreight> {
    public HeavyFreightLoad(double weight) {
        super(weight);
    }

    @Override
    public Set<Flags> flags() {
        return Set.of(Flags.HEAVY_FREIGHT);
    }


}
