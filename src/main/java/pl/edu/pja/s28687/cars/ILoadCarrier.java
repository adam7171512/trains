package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.Load;

import java.util.Set;

public interface ILoadCarrier<T> {

    void load(Load<? super T> load);

    String validateLoad(Load<? super T> load);

    Set<Flags> allowedLoadFlags();
}
