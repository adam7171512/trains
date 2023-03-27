package pl.edu.pja.s28687.Load;

public class Mail extends Load<ILuggage> {
    public Mail(double weight) {
        super(weight);
    }

    @Override
    public void setFlags() {
        flags.add(Flags.LUGGAGE);
    }
}
