package pl.edu.pja.s28687.Info;

import pl.edu.pja.s28687.Load.Load;
import pl.edu.pja.s28687.Load.IDeliverable;
import pl.edu.pja.s28687.Load.Passengers;

public class LoadInfo {
    public static String getBasicInfo(Load<? extends IDeliverable> load){
        StringBuilder stringBuilder = new StringBuilder().
                append("Load ID : ").append(load.getId()).
                append(" | Load name : ").append(load.getName()).
                append(" | Freight type : ").append(load.flags()).
                append(" | Weight : ").append(load.getWeight()).append(" tonnes");

        if (load instanceof Passengers) {
            stringBuilder.append(" | Passengers : ").append(((Passengers) load).getQuantity());
        }
        return stringBuilder.toString();
    }
}
