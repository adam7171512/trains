package pl.edu.pja.s28687.info;

import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.cars.PassengerCar;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.List;

public class CarInfo {
    public static String getBasicInfo(IRailroadCar car){
        StringBuilder stringBuilder = new StringBuilder().
                append("Car ID : ").append(car.getId()).
                append(" | Car type : ").append(car.getCarType()).
                append(" | Payload : ").
                append(car.getCurrentWeight()).
                append(" out of ").
                append(car.getGrossWeight()).append(" tonnes");

        if (car instanceof PassengerCar) {
            stringBuilder
                    .append(" | Passengers : ")
                    .append(((PassengerCar) car)
                            .getNumberOfPassengers())
                    .append(" / ")
                    .append(((PassengerCar) car)
                            .getNumberOfSeats());
        }

        car.getLocomotive().ifPresent(locomotive -> stringBuilder
                .append(" | Attached to locomotive ID : ")
                .append(locomotive.getId()));


        return stringBuilder.toString();
    }

    public static String getFullInfo(IRailroadCar car){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getBasicInfo(car));
        if (car instanceof ILoadCarrier<? extends IDeliverable>) {
            for(IDeliverable deliverable :  ((ILoadCarrier<?>) car).getLoads()){
            stringBuilder.append("\n").append(LoadInfo.getBasicInfo(deliverable));
        }
        car.getLocomotive().ifPresent(locomotive -> stringBuilder
                .append(" | Attached to locomotive ID : ")
                .append(locomotive.getId()));
        }
        return stringBuilder.toString();
    }

    public static String getAggregatedBasicInfo(List<? extends IRailroadCar> cars){
        StringBuilder stringBuilder = new StringBuilder();
        for (IRailroadCar car : cars) {
            stringBuilder.append(getBasicInfo(car)).append("\n");
        }
        return stringBuilder.toString();
    }

    public static String getAggregatedBasicInfo(LocoBase locoBase){
        return getAggregatedBasicInfo(locoBase.getRailroadCarsList());
    }
}
