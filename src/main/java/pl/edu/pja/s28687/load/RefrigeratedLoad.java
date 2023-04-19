package pl.edu.pja.s28687.load;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public class RefrigeratedLoad extends Load implements IRefrigerated {
    private BigDecimal requiredTemperature;
    public RefrigeratedLoad(int id, double weight, double requiredTemperature) {
        super(id, weight);
        this.requiredTemperature = BigDecimal.valueOf(requiredTemperature);
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.REFRIGERATED);
    }

    @Override
    public String getBasicInfo() {
        return "ID : " + getId() + " Refrigerated load, Weight: "
                + getWeight() + " tonnes";
    }

    @Override
    public String getFullInfo() {
        return getBasicInfo() + "\nRequired temperature: "
                + requiredTemperature.setScale(2, RoundingMode.HALF_UP) + "°C" ;
    }

    @Override
    public BigDecimal requiredTemperature() {
        return requiredTemperature;
    }
}
