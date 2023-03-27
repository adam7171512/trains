package pl.edu.pja.s28687.Factories;
import pl.edu.pja.s28687.Load.Explosives;
import pl.edu.pja.s28687.Load.*;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.util.Random;

public class LoadFactory {

    public static void makeRandomLoads(int quantity, LocoBase locoBase){
        Random random = new Random();
        for(int i = 0; i < quantity / 3; i++) {
            int r = random.nextInt(80);
            Passengers passengers = new Passengers(r);
            locoBase.registerLoad(passengers);

        }
        for(int i = 0; i < 2 * quantity / 3; i++) {

            int o = random.nextInt(11);
            double r = (random.nextInt(90));

            Load load;
            load = switch (o) {
                case 1, 7 -> new BasicFreightLoad(r);
                case 2, 8 -> new HeavyFreightLoad(r);
                case 3 -> new LiquidLoad(r);
                case 4 -> new Mail(r);
                case 5 -> new GaseousLoad(r);
                case 6 -> new ToxicLoad(r);
                case 9 -> new Explosives(r);
                default -> new LiquidToxicLoad(r);
            };
            locoBase.registerLoad(load);
        }
    }
}
