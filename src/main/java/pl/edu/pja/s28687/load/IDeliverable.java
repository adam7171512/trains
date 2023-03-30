package pl.edu.pja.s28687.load;

import java.math.BigDecimal;
import java.util.Set;

public interface IDeliverable {
    public Set<Flags> flags();
    public BigDecimal getWeight();
}
