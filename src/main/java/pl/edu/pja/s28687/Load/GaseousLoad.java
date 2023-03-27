package pl.edu.pja.s28687.Load;

public class GaseousLoad extends Load<IGaseous> {
    public GaseousLoad(double weight) {
        super(weight);
    }

    @Override
    public void setFlags() {
        flags.add(Flags.GASEOUS);
    }
}
