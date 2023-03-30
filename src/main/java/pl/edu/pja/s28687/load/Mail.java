package pl.edu.pja.s28687.load;

import java.util.HashSet;
import java.util.Set;

public class Mail extends Load<ILuggage> {
    public Mail(double weight) {
        super(weight);
    }

    @Override
    public Set<Flags> flags() {
        return Set.of(Flags.LUGGAGE);
    }
}
