package pl.edu.pja.s28687.Load;

public class LiquidToxicLoad extends Load<ILiquidToxic>{
    public LiquidToxicLoad(double weight) {
        super(weight);
        setFlags();
    }

    public LiquidToxicLoad(int quantity, double weight) {
        super(quantity, weight);
        setFlags();
    }

    @Override
    public void setFlags(){
        flags.add(Flags.LIQUID);
        flags.add(Flags.TOXIC);
    }
}
