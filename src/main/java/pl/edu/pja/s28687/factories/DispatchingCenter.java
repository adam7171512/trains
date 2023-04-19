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
        trainSet.setDestinationStation(destStation);
        if (! trainSet.hasStarted() ){
            trainSet.start();
        }
    }

    public void dispatchTrainSet(TrainSet trainSet){
        if (locoBase.getTrainStations().size() < 2){
            System.err.println("Not enough train stations to dispatch train set");
            return;
        }
        TrainStation sourceStation = getRandomTrainStation();
        TrainStation destStation = getRandomDestinationForTrainStation(sourceStation);
        dispatchTrainSet(trainSet, sourceStation, destStation);
    }

    public void dispatchAllTrainSets(){
        List<TrainSet> trainSets = locoBase.getTrainSets().stream().filter(ts -> ! ts.hasStarted()).toList();
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

    public void dispatchAllTrainSetsWithCars() {
        List<TrainSet> trainSets = locoBase.getTrainSets()
                .stream()
                .filter(trainSet -> trainSet.getCars().size() > 0)
                .filter(trainSet -> ! trainSet.hasStarted())
                .toList();
        for (TrainSet trainSet : trainSets){
            dispatchTrainSet(trainSet);
        }

    }

    public void dispatchTrainSets(List<TrainSet> trainSets) {
        for (TrainSet trainSet : trainSets){
            dispatchTrainSet(trainSet);
        }
    }
}
