package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.LoadType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ILoadCarrier<T extends IDeliverable> extends IRailroadCar {

    boolean load(T load);

    boolean unLoad(T load);

    boolean validateLoad(IDeliverable load);

    Set<LoadType> allowedLoadFlags();

    List<T> getLoads();

    void emergencyUnloading();

    boolean isLocked();

    void safetyLock();

    void safetyUnlock();

    BigDecimal getCargoWeight();

    BigDecimal getAvailablePayload();

    BigDecimal getMaxPayload();

    String getCargoStats();
}
