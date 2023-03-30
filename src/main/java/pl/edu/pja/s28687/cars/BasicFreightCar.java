package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.load.IBasicFreight;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Set;

public class BasicFreightCar extends BasicFreightCarABC<IBasicFreight>{
    public BasicFreightCar(int id) {
        super(id);
    }

    @Override
    public Set<Flags> allowedLoadFlags() {
        return Set.of(Flags.BASIC_FREIGHT);
    }

    @Override
    public CarType getCarType() {
        return CarType.BASIC_FREIGHT;
    }
}
