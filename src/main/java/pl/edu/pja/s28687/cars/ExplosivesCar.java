package pl.edu.pja.s28687.Cars;

import pl.edu.pja.s28687.Load.*;
import pl.edu.pja.s28687.Logistics.LocoBase;


public class ExplosivesCar extends HeavyFreightCarABC<IExplosive> {

    public ExplosivesCar(LocoBase locoBase) {
        super(locoBase);
        forbidden.remove(Flags.EXPLOSIVE);
        setAllowableFlags();
    }


}
