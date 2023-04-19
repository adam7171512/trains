package pl.edu.pja.s28687.load;

import java.math.BigDecimal;
import java.util.Set;

public interface IDeliverable {
    public Set<LoadType> flags();
    public BigDecimal getWeight();

    public void setLoaded();
    public void setDeloaded();

    int getId();
    boolean isLoaded();
    public String getBasicInfo();

    public String getFullInfo();
}
