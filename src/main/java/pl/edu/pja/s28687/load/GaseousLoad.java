package pl.edu.pja.s28687.load;

import java.util.Set;

public class GaseousLoad extends Load<IGaseous> {
    public GaseousLoad(double weight) {
        super(weight);
    }

    @Override
    public Set<Flags> flags() {
        return Set.of(Flags.GASEOUS);
    }

}
