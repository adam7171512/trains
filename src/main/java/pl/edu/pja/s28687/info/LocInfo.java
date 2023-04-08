package pl.edu.pja.s28687.info;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.misc.TrainStatus;

import java.util.List;

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
                append(" | Current payload : ").append(locomotive.getCurrentPayload()).
                append(" | Max payload : ").append(locomotive.getMaxPayload()).
                append(" tonnes").
                append("\nCars attached : ").
                append(" regular : ").append(locomotive.getCurrentCarNumber()).
                append(" powered : ").append(locomotive.getPoweredCarsNumber()).
                append(" | Car limits | ").
                append(" regular : ").append(locomotive.getCarLimit()).
                append(" powered : ").append(locomotive.getPoweredCarLimit()).
                append(" | Passengers carried : ").append(locomotive.passengersOnBoard()).
                append("\nCurrent status : ").append(locomotive.getStatus()).append("\n");
        return stringBuilder.toString();
    }

    public static String getAggregatedBasicInfo(LocoBase locoBase){
        StringBuilder stringBuilder = new StringBuilder();
        for(Locomotive loc: locoBase.getLocomotiveList()){
            stringBuilder.append(getBasicInfo(loc));
        }
        return stringBuilder.toString();
    }

    public static String getAggregatedBasicInfo(List<Locomotive> locomotives){
        StringBuilder stringBuilder = new StringBuilder();
        for(Locomotive loc: locomotives){
            stringBuilder.append(getBasicInfo(loc));
        }
        return stringBuilder.toString();
    }
}
