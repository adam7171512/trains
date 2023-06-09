package pl.edu.pja.s28687.load;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public class Passengers extends Load implements IPassengers {

    private int count;

    public Passengers(int id, int count) {
        super(id, count * 0.08); //80 kg per passenger
        this.count = count;
    }
    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.PASSENGERS);
    }

    @Override
    public String getBasicInfo() {
        return "ID : " + getId() + " Passengers load,  Count: " + count;
    }

    @Override
    public String getFullInfo() {
        return getBasicInfo() + "\n" + getDescription();
    }

    @Override
    public int getCount() {
        return count;
    }
}
