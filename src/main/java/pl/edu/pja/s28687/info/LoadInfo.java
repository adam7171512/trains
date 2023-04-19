package pl.edu.pja.s28687.info;

import pl.edu.pja.s28687.load.Load;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.Passengers;
import pl.edu.pja.s28687.logistics.LocoBase;

public class LoadInfo {
    public static String getBasicInfo(IDeliverable load){
        return load.getBasicInfo();
    }

    public static String getAggregatedBasicInfo(LocoBase locoBase){
        StringBuilder stringBuilder = new StringBuilder();
        for (IDeliverable load : locoBase.getLoadList()) {
            stringBuilder.append(getBasicInfo(load)).append("\n");
        }
        return stringBuilder.toString();
    }
}
