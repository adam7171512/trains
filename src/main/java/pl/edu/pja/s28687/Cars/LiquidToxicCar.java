package pl.edu.pja.s28687.Cars;

import pl.edu.pja.s28687.Load.*;
import pl.edu.pja.s28687.Logistics.LocoBase;


public class LiquidToxicCar extends HeavyFreightCarABC<ILiquidToxic>{

    public LiquidToxicCar(LocoBase locoBase) {
        super(locoBase);
        forbidden.remove(Flags.LIQUID);
        forbidden.remove(Flags.TOXIC);
        setAllowableFlags();
    }

}


// <T extends IDeliverable & ILiquid & IToxic>