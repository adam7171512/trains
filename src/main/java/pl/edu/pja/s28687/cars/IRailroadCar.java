package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.train.Locomotive;

import java.math.BigDecimal;
import java.util.Optional;

public interface IRailroadCar {
    Optional<Locomotive> getLocomotive();

    void setAttachedTo(Locomotive locomotive);

    void setDetached();

    boolean isPowered();

    boolean isAttached();

    CarType getCarType();

    int getId();

    BigDecimal getNetWeight();

    BigDecimal getCurrentWeight();

    BigDecimal getGrossWeight();

    void emergencyProcedure();

    void routineProcedure();

    void safetyCheck();

    String getBasicInfo();

    String getFullInfo();
}
