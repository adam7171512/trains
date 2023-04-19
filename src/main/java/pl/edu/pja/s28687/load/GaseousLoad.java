package pl.edu.pja.s28687.load;

import java.math.BigDecimal;
import java.util.Set;

public class GaseousLoad extends Load implements IGaseous {
    private BigDecimal densityAtAtmosphericPressure;
    public GaseousLoad(double weight, double density) {
        super(weight);
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.GASEOUS);
    }

    @Override
    public BigDecimal getGetDensityAtAtmosphericPressure() {
        return densityAtAtmosphericPressure;
    }
}
