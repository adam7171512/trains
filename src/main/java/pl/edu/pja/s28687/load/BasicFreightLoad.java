package pl.edu.pja.s28687.load;

import java.util.Set;

public class BasicFreightLoad extends Load implements IBasicFreight {
    public BasicFreightLoad(double weight) {
        super(weight);
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.BASIC_FREIGHT);
    }

}
