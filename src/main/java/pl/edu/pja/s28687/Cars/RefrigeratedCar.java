package pl.edu.pja.s28687.Cars;

import pl.edu.pja.s28687.Load.Flags;
import pl.edu.pja.s28687.Load.IRefrigerated;
import pl.edu.pja.s28687.Logistics.LocoBase;

public class RefrigeratedCar extends BasicFreightCarABC<IRefrigerated> implements IPowered{

    public RefrigeratedCar(LocoBase locoBase) {
        super(locoBase);
        forbidden.remove(Flags.REFRIGERATED);
        setAllowableFlags();
    }


}
