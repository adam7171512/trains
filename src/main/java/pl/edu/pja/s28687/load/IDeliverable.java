package pl.edu.pja.s28687.load;

import java.math.BigDecimal;
import java.util.Set;

public interface IDeliverable {
    public Set<LoadType> flags();
    public BigDecimal getWeight();

    void setLoaded();

    void setDeloaded();

    void setId(int id);
    int getId();
    boolean isLoaded();
}
