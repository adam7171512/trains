package pl.edu.pja.s28687.load;

import java.util.HashSet;
import java.util.Set;

public class BasicFreightLoad extends Load<IBasicFreight> {
    public BasicFreightLoad(double weight) {
        super(weight);
    }

    @Override
    public Set<Flags> flags() {
        return Set.of(Flags.BASIC_FREIGHT);
    }

}
