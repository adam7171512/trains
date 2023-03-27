package pl.edu.pja.s28687.Load;

public class BasicFreightLoad extends Load<IBasicFreight> {
    public BasicFreightLoad(double weight) {
        super(weight);
    }

    @Override
    public void setFlags() {
        flags.add(Flags.BASIC_FREIGHT);
    }
}
