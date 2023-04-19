package pl.edu.pja.s28687.load;

import java.math.BigDecimal;
import java.util.Set;

public class GaseousLoad extends Load implements IGaseous {
    private final BigDecimal densityAtAtmosphericPressure;
    private final BigDecimal boilingPoint;
    public GaseousLoad(int id, double weight, double density, double boilingPoint) {
        super(id, weight);
        this.densityAtAtmosphericPressure = BigDecimal.valueOf(density);
        this.boilingPoint = BigDecimal.valueOf(boilingPoint);
    }
    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.GASEOUS);
    }

    @Override
    public String getBasicInfo() {
        return "ID : " + getId() + " Gaseous load, Mass: " + getWeight() + " tonnes.";
    }

    @Override
    public String getFullInfo() {
        return getBasicInfo() + "\nDensity at atmospheric pressure : " + getGetDensityAtAtmosphericPressure()
                + " kg / m3" + ", Boiling point : " + getBoilingPoint() + " C" + "\n" + getDescription();
    }

    @Override
    public BigDecimal getGetDensityAtAtmosphericPressure() {
        return densityAtAtmosphericPressure;
    }

    @Override
    public BigDecimal getBoilingPoint() {
        return boilingPoint;
    }
}
