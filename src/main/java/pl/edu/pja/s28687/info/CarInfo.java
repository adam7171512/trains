package pl.edu.pja.s28687.info;

import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.List;

public class CarInfo {

    public static String getAggregatedBasicInfo(List<? extends IRailroadCar> cars) {
        StringBuilder stringBuilder = new StringBuilder();
        for (IRailroadCar car : cars) {
            stringBuilder.append(car.getBasicInfo()).append("\n");
        }
        return stringBuilder.toString();
    }

    public static String getAggregatedBasicInfo(LocoBase locoBase) {
        return getAggregatedBasicInfo(locoBase.getRailroadCarsList());
    }
}
