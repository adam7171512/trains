package pl.edu.pja.s28687.Load;

public class ToxicLoad extends Load<IToxic> {
    public ToxicLoad(double weight) {
        super(weight);
    }

    @Override
    public void setFlags() {
        flags.add(Flags.TOXIC);
    }
}
