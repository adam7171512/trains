package pl.edu.pja.s28687.Load;

public class LiquidLoad extends Load<ILiquid> {
    public LiquidLoad(double weight) {
        super(weight);
    }

    @Override
    public void setFlags() {
        flags.add(Flags.LIQUID);
    }
}
