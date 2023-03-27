package pl.edu.pja.s28687.Load;

public class Luggage extends Load<ILuggage> {
    public Luggage(double weight) {
        super(weight);
    }

    @Override
    public void setFlags() {
        flags.add(Flags.LUGGAGE);
    }

}
