package pl.edu.pja.s28687.Load;

public class HeavyFreightLoad extends Load<IHeavyFreight> {
    public HeavyFreightLoad(double weight) {
        super(weight);
    }

    @Override
    public void setFlags() {
        flags.add(Flags.HEAVY_FREIGHT);
    }


}
