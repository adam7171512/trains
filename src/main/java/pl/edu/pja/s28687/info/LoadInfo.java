package pl.edu.pja.s28687.info;

import pl.edu.pja.s28687.load.Load;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.Passengers;
import pl.edu.pja.s28687.logistics.LocoBase;

public class LoadInfo {
    public static String getBasicInfo(IDeliverable load){
        StringBuilder stringBuilder = new StringBuilder().
                append("Load ID : ").append(load.getId()).
                append(" | Freight type : ").append(load.flags()).
                append(" | Weight : ").append(load.getWeight()).append(" tonnes");

        if (load instanceof Passengers) {
            stringBuilder.append(" | Passengers : ").append(((Passengers) load).getQuantity());
        }
        return stringBuilder.toString();
    }

    public static String getAggregatedBasicInfo(LocoBase locoBase){
        StringBuilder stringBuilder = new StringBuilder();
        for (IDeliverable load : locoBase.getLoadList()) {
            stringBuilder.append(getBasicInfo(load)).append("\n");
        }
        return stringBuilder.toString();
    }
}
