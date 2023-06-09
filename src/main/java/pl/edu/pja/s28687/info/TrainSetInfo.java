package pl.edu.pja.s28687.info;
import pl.edu.pja.s28687.train.TrainSet;
import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.train.Locomotive;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TrainSetInfo {
    public static String getTrainSetInfo(TrainSet trainSet){

        Locomotive locomotive = trainSet.getLocomotive();

        StringBuilder loco = new StringBuilder()
                .append("\nTrainSet ID : ").append(trainSet.getId())
                .append("\nLocomotive ID : ").append(locomotive.getId())
                .append(" | Loc name : ").append(locomotive.getName())
                .append(" | Current status : ").append(locomotive.getStatus()).append("\n")
                .append("Home station : ").append(locomotive.getHomeStation())
                .append(" | Current payload : ").append(locomotive.getCurrentPayload())
                .append(" / ").append(locomotive.getMaxPayload())
                .append(" tonnes")
                .append("\nCars attached : ")
                .append(" total : ").append(locomotive.getCurrentCarNumber())
                .append(" powered : ").append(locomotive.getPoweredCarsNumber())
                .append(" | Car limits : ")
                .append(" regular : ").append(locomotive.getCarLimit())
                .append(" powered : ").append(locomotive.getPoweredCarLimit())
                .append(" | Passengers carried : ").append(locomotive.getPassengerNumber())
                .append("\nSegment info | ")
                .append(" Current speed : ").append(locomotive.getCurrentSpeed()).append(" km/h")
                .append(" Nominal speed : ").append(locomotive.getNominalSpeed()).append(" km/h")
                .append(" | Current rail segment : ").append(locomotive.getCurrentSegment())
                .append(" | Segment progress : ")
                .append(locomotive.getCurrentSegmentProgress().multiply(BigDecimal.valueOf(100)))
                .append(" %")
                .append("\nTrip info |  ")
                .append("Source  : ").append(locomotive.getSourceStation())
                .append(" | Destination  : ").append(locomotive.getDestinationStation())
                .append(" | Trip distance  : ").append(locomotive.getCurrentTripDistance()).append(" km")
                .append(" | Stops  : ").append(locomotive.getRoute().size())
                .append(" | Trip progress : ").append(locomotive.getCurrentTripProgress().multiply(BigDecimal.valueOf(100))).append(" %");

        List<IRailroadCar> cars = locomotive.getCars();
        if (cars.size() == 0) {
            loco.append("\nNo cars attached");
            return loco.toString();
        }
        else {
            loco.append("\nRailroad cars attached :\n");
            loco.append(
                    cars.
                            stream().
                            sorted
                                    (Comparator.comparingDouble
                                            (car -> car.getCurrentWeight().
                                                    doubleValue())).
                            map(IRailroadCar::getBasicInfo).collect(Collectors.joining("\n")));
            loco.append("\n").append("_".repeat(140));
        }
        return loco.toString();
    }

    public static String getTrainSetBasicInfo(TrainSet trainSet) {
        Locomotive locomotive = trainSet.getLocomotive();
        return new StringBuilder()
                .append("\nTrainSet ID : ").append(trainSet.getId())
                .append(" | Current status : ").append(locomotive.getStatus())
                .append(" | Current payload : ").append(locomotive.getCurrentPayload())
                .append(" / ").append(locomotive.getMaxPayload())
                .append(" tonnes")
                .append("\nCars attached : ")
                .append(locomotive.getCurrentCarNumber())
                .append(" | Passengers carried : ").append(locomotive.getPassengerNumber())
                .append(" | Destination  : ").append(locomotive.getDestinationStation())
                .append(" | Trip distance  : ").append(locomotive.getCurrentTripDistance()).append(" km")
                .append(" | Trip progress : ").append(locomotive.getCurrentTripProgress().multiply(BigDecimal.valueOf(100))).append(" %").toString();
    }

    public static String getAggregatedBasicTrainSetsInfo(LocoBase locoBase){
        List<TrainSet> trainSets = new ArrayList<>(locoBase.
                getTrainSets().
                stream().
                sorted(Comparator.comparingDouble
                        (trainSet -> trainSet.getLocomotive().
                                getCurrentTripDistance()
                                .doubleValue()))
                .toList());
        Collections.reverse(trainSets);

        StringBuilder stringBuilder = new StringBuilder();
        for (TrainSet trainSet : trainSets){
            stringBuilder.append(getTrainSetBasicInfo(trainSet));
        }
        return stringBuilder.toString();
    }

    public static String getAggregatedTrainSetsInfo(LocoBase locoBase){

        List<TrainSet> trainSets = new ArrayList<>(locoBase.
                getTrainSets().
                stream().
                sorted(Comparator.comparingDouble
                        (trainSet -> trainSet.getLocomotive().
                                getCurrentTripDistance()
                                .doubleValue()))
                .toList());
        Collections.reverse(trainSets);

        StringBuilder stringBuilder = new StringBuilder();

        for (TrainSet trainSet : trainSets){
            stringBuilder.append(getTrainSetInfo(trainSet));
            stringBuilder.append("\n").append("_".repeat(200));
            stringBuilder.append("\n").append("_".repeat(200));
            stringBuilder.append("\n").append("_".repeat(200));
        }
        return stringBuilder.toString();
    }

}
