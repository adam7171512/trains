package pl.edu.pja.s28687.Cars;
import pl.edu.pja.s28687.Load.*;
import pl.edu.pja.s28687.Logistics.LocoBase;
import pl.edu.pja.s28687.TrainStation;


public class LiquidsCar extends BasicFreightCarABC<ILiquid> {
    public LiquidsCar(LocoBase locoBase) {
        super(locoBase);
        setName("lol");
        forbidden.remove(Flags.LIQUID);
        forbidden.remove(Flags.TOXIC);
        setAllowableFlags();
    }
}
