package pl.edu.pja.s28687.factories;
import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.logistics.LocoBase;
import java.util.List;
import java.util.Random;


public class DispatchingCenter{
    private LocoBase locoBase;
    private Random random;

    public DispatchingCenter(LocoBase locoBase){
        this.locoBase = locoBase;
        this.random = new Random();
    }

    public void dispatchTrainSet(TrainSet trainSet, TrainStation sourceStation, TrainStation destStation){
        trainSet.setHomeStation(sourceStation);
        trainSet.setSourceStation(sourceStation);
        trainSet.setDestStation(destStation);
        trainSet.start();
    }

    public void dispatchTrainSet(TrainSet trainSet){
        TrainStation sourceStation = getRandomTrainStation();
        TrainStation destStation = getRandomDestinationForTrainStation(sourceStation);
        dispatchTrainSet(trainSet, sourceStation, destStation);
    }

    public void dispatchAllTrainSets(){
        List<TrainSet> trainSets = locoBase.getTrainSets().stream().toList();
        for (TrainSet trainSet : trainSets){
            dispatchTrainSet(trainSet);
        }
    }


    public TrainStation getRandomTrainStation(){
        List<TrainStation> trainStationList = locoBase.getTrainStations().stream().toList();
        return trainStationList.stream().toList().get(random.nextInt(trainStationList.size()));
    }

    public TrainStation getRandomDestinationForTrainStation(TrainStation trainStation){
        List<TrainStation> trainStations = locoBase.getTrainStations().stream().toList();
        return trainStations
                .stream()
                .filter(station -> station != trainStation)
                .toList()
                .get(random.nextInt(trainStations.size() - 1));
    }
}
