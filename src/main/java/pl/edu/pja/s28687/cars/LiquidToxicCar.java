package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Set;


public class LiquidToxicCar extends HeavyFreightCarABC<ILiquidToxic>{

    public LiquidToxicCar(int id) {
        super(id);
    }

    @Override
    public Set<Flags> allowedLoadFlags() {
        return Set.of(Flags.LIQUID, Flags.TOXIC);
    }

    @Override
    public CarType getCarType() {
        return CarType.LIQUID_TOXIC;
    }
}
