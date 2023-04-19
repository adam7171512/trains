package pl.edu.pja.s28687.load;

import java.math.BigDecimal;
import java.util.Set;

public class LiquidToxicLoad extends Load implements ILiquidToxic{
    BigDecimal combustionTemperature;
    private static final String HAZMAT = "Liquid Toxic load. Must be handled with care.";
    private final BigDecimal volume;
    public LiquidToxicLoad(int id, double weight, double volume, double combustionTemperature) {
        super(id, weight);
        this.volume = BigDecimal.valueOf(volume);
        this.combustionTemperature = BigDecimal.valueOf(combustionTemperature);
    }
    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.LIQUID_TOXIC);
    }

    @Override
    public String getBasicInfo() {
        return "ID : " + getId() + " Liquid Toxic load, Weight: " + getWeight()
                + " tonnes. Volume: " + volume + " m3";
    }

    @Override
    public String getFullInfo() {
        return getBasicInfo() + "\n" + getHazMatInstructions() + "\nCombustion temperature: " + combustionTemperature
                + "°C\n" + getDescription();
    }

    @Override
    public String getHazMatInstructions() {
        return HAZMAT;
    }

    @Override
    public BigDecimal getVolume() {
        return volume;
    }

    @Override
    public BigDecimal getCombustionTemperature() {
        return combustionTemperature;
    }
}
