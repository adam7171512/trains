package pl.edu.pja.s28687.load;

import java.util.HashSet;
import java.util.Set;

public class RefrigeratedLoad extends Load<IRefrigerated> {
    public RefrigeratedLoad(double weight) {
        super(weight);
    }

    @Override
    public Set<Flags> flags() {
        return Set.of(Flags.REFRIGERATED);
    }

}
