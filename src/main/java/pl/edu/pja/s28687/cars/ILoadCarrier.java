package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.Load;

import java.math.BigDecimal;
import java.util.Set;

public interface ILoadCarrier<T>  extends IRailroadCar{

    boolean load(Load<? super T> load);
    boolean unLoad(Load<?> load);

    boolean validateLoad(Load<? extends IDeliverable> load);

    Set<Flags> allowedLoadFlags();


}
