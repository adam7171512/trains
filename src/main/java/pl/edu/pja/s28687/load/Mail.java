package pl.edu.pja.s28687.load;

import java.util.Set;

public class Mail extends Load implements ILuggage {
    public Mail(double weight) {
        super(weight);
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.LUGGAGE);
    }
}
