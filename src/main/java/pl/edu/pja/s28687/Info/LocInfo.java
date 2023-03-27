package pl.edu.pja.s28687.Info;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.Misc.TrainStatus;

public class LocInfo {

    public static String getFullInfo(Locomotive locomotive){
        return getBasicInfo(locomotive) + getLocationInfo(locomotive);
    }

    public static String getLocationInfo(Locomotive locomotive){
        StringBuilder stringBuilder = new StringBuilder();


        if (locomotive.getStatus() == TrainStatus.RUNNING){
            stringBuilder.append("\nRoad info :")
                    .append("\nCurrent speed : ").append(locomotive.getCurrentSpeed()).append(" km/h")
                    .append(" | Current rail segment : ").append(locomotive.getCurrentSegment())
                    .append(" | Segment progress : ").append(locomotive.getCurrentSegmentProgress())
                    .append(" %")
                    .append("\nTrip info : \n")
                    .append("Source  : ").append(locomotive.getSourceStation())
                    .append(" | Destination  : ").append(locomotive.getDestStation())
                    .append(" | Trip distance  : ").append(locomotive.getCurrentTripDistance()).append(" km")
                    .append(" | Trip progress : ").append(locomotive.getCurrentTripProgress()).append(" %")
                    .append("\nSegments : ").append(locomotive.getRoad())
                    .append("\nRecently visited train station: ").append(locomotive.getLastTrainStation())
                    .append("\n");

        }
        return stringBuilder.toString();
    }
    public static String getBasicInfo(Locomotive locomotive){
        StringBuilder stringBuilder = new StringBuilder().
                append("_________________________________________________________________").
                append("\nLocomotive ID : ").append(locomotive.getId()).
                append(" | Loc name : ").append(locomotive.getLocName()).
                append(" | Home station : ").append(locomotive.getHomeStation()).
                append(" | Current payload : ").append(locomotive.getCurrentFreight()).
                append(" | Max payload : ").append(locomotive.getMaxFreight()).
                append(" tonnes").
                append("\nCars attached : ").
                append(" regular : ").append(locomotive.carsOccupied()).
                append(" powered : ").append(locomotive.poweredCarsOccupied()).
                append(" | Car limits | ").
                append(" regular : ").append(locomotive.getMaxCars()).
                append(" powered : ").append(locomotive.getMaxPoweredCars()).
                append(" | Passengers carried : ").append(locomotive.passengersOnBoard()).
                append("\nCurrent status : ").append(locomotive.getStatus()).append("\n");

        return stringBuilder.toString();
    }
}
