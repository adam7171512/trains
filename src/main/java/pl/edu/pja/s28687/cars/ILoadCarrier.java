package pl.edu.pja.s28687.Cars;

import pl.edu.pja.s28687.Load.Flags;
import pl.edu.pja.s28687.Load.Load;
import pl.edu.pja.s28687.Load.IDeliverable;

import java.util.Set;

public interface ILoadCarrier<T> {

    void load(Load<? super T> load);

    String validateLoad(Load<?> load);

    Set<Flags> forbiddenLoadFlags();
}
