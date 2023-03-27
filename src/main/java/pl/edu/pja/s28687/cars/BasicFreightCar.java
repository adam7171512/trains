package pl.edu.pja.s28687.Cars;

import pl.edu.pja.s28687.Load.BasicFreightLoad;
import pl.edu.pja.s28687.Load.Flags;
import pl.edu.pja.s28687.Load.IBasicFreight;
import pl.edu.pja.s28687.Logistics.LocoBase;

public class BasicFreightCar extends BasicFreightCarABC<IBasicFreight>{
    public BasicFreightCar(LocoBase locoBase) {
        super(locoBase);
        forbidden.remove(Flags.BASIC_FREIGHT);
        setAllowableFlags();
    }
}
