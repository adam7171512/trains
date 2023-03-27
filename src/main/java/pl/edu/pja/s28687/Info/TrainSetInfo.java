package pl.edu.pja.s28687.Info;
import pl.edu.pja.s28687.Cars.RailroadCar;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TrainSetInfo {
    public static String getTrainSetInfo(Locomotive locomotive){

        StringBuilder loco = new StringBuilder()
                .append("\nLocomotive ID : ").append(locomotive.getId())
                .append(" | Loc name : ").append(locomotive.getLocName())
                .append(" | Current status : ").append(locomotive.getStatus()).append("\n")
                .append("Home station : ").append(locomotive.getHomeStation())
                .append(" | Current payload : ").append(locomotive.getCurrentFreight())
                .append(" | Max payload : ").append(locomotive.getMaxFreight())
                .append(" tonnes")
                .append("\nCars attached : ")
                .append(" regular : ").append(locomotive.carsOccupied())
                .append(" powered : ").append(locomotive.poweredCarsOccupied())
                .append(" | Car limits : ")
                .append(" regular : ").append(locomotive.getMaxCars())
                .append(" powered : ").append(locomotive.getMaxPoweredCars())
                .append(" | Passengers carried : ").append(locomotive.passengersOnBoard())
                .append("\nSegment info | ")
                .append(" Current speed : ").append(locomotive.getCurrentSpeed()).append(" km/h")
                .append(" | Current rail segment : ").append(locomotive.getCurrentSegment())
                .append(" | Segment progress : ").append(locomotive.getCurrentSegmentProgress())
                .append(" %")
                .append("\nTrip info |  ")
                .append("Source  : ").append(locomotive.getSourceStation())
                .append(" | Destination  : ").append(locomotive.getDestStation())
                .append(" | Trip distance  : ").append(locomotive.getCurrentTripDistance()).append(" km")
                .append(" | Stops  : ").append(locomotive.getRoad().size())
                .append(" | Trip progress : ").append(locomotive.getCurrentTripProgress()).append(" %")
//                .append("\nTrip segments : ").append(locomotive.getRoad())
                .append("\nRailroad cars attached :\n");

        List<RailroadCar> cars = locomotive.getCars();
        if (cars.size() == 0) {
            loco.append("No cars ");
            return loco.toString();
        }

        loco.append(
                    cars.
                    stream().
                    sorted
                            (Comparator.comparingDouble
                            (car -> car.getCurrentWeight().
                            doubleValue())).
                    map(CarInfo::getBasicInfo).collect(Collectors.joining("\n")));
        loco.append("\n").append("_".repeat(140));
        return loco.toString();
    }

    public static String getAggregatedTrainSetsInfo(LocoBase locoBase){
        List<Locomotive> locs = new ArrayList<>(locoBase.
                getLocomotiveList().
                stream().
                sorted(Comparator.comparingDouble
                        (loc -> loc.getCurrentTripDistance()
                                .doubleValue()))
                .toList());
        Collections.reverse(locs);

        StringBuilder stringBuilder = new StringBuilder();
        for (Locomotive locomotive : locs){
            stringBuilder.append(getTrainSetInfo(locomotive));
            stringBuilder.append("\n").append("_".repeat(140));
            stringBuilder.append("\n").append("_".repeat(140));
            stringBuilder.append("\n").append("_".repeat(140));
        }
        return stringBuilder.toString();
    }

}
