package pl.edu.pja.s28687.Cars;
import pl.edu.pja.s28687.Load.*;
import pl.edu.pja.s28687.Logistics.LocoBase;


public class ToxicCar extends HeavyFreightCarABC<IToxic>{
    public ToxicCar(LocoBase locoBase) {
        super(locoBase);
        forbidden.remove(Flags.TOXIC);
        setAllowableFlags();
    }

}
