package pl.edu.pja.s28687.load;

import java.util.Set;

public class Luggage extends Load implements ILuggage {
    public Luggage(double weight) {
        super(weight);
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.LUGGAGE);
    }


}
