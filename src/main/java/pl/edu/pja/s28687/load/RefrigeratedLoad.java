package pl.edu.pja.s28687.load;

import java.util.Set;

public class RefrigeratedLoad extends Load implements IRefrigerated {
    public RefrigeratedLoad(double weight) {
        super(weight);
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.REFRIGERATED);
    }

}
