package pl.edu.pja.s28687.Load;

public class ToxicGasLoad extends Load<IGaseousToxic> {
    public ToxicGasLoad(double weight) {
        super(weight);
    }

    @Override
    public void setFlags() {
        flags.add(Flags.TOXIC);
        flags.add(Flags.GASEOUS);
    }
}
