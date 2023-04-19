package pl.edu.pja.s28687.load;

public interface IHeavyFreight extends IDeliverable {
    boolean isHighValue();
    boolean isOverSized();
}
