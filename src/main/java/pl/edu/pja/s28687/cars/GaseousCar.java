package pl.edu.pja.s28687.Cars;

import pl.edu.pja.s28687.Load.*;
import pl.edu.pja.s28687.Logistics.LocoBase;


public class GaseousCar extends BasicFreightCarABC<IGaseous> {
    public GaseousCar(LocoBase locoBase) {
        super(locoBase);
        forbidden.remove(Flags.GASEOUS);
        setAllowableFlags();
    }

}
