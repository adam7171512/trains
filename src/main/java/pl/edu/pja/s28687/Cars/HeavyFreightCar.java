package pl.edu.pja.s28687.Cars;

import pl.edu.pja.s28687.Load.Flags;
import pl.edu.pja.s28687.Load.HeavyFreightLoad;
import pl.edu.pja.s28687.Load.IHeavyFreight;
import pl.edu.pja.s28687.Load.Load;
import pl.edu.pja.s28687.Logistics.LocoBase;


public class HeavyFreightCar extends HeavyFreightCarABC<IHeavyFreight>{

    public HeavyFreightCar(LocoBase locoBase) {
        super(locoBase);
        forbidden.remove(Flags.HEAVY_FREIGHT);
        setAllowableFlags();
    }

}
