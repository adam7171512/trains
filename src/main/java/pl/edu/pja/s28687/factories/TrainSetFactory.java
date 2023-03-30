package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.logistics.RouteFindingAlgos;

import java.util.ArrayList;
import java.util.List;

public class TrainSetFactory {

    private LocoBase locoBase;

    public TrainSetFactory(LocoBase locoBase){
        this.locoBase = locoBase;
    }

    public TrainSet createRandomTrainSet(){
        return new TrainSetBuilder(locoBase).build();
    }

    public TrainSet createTrainSetWithAlgorithm(RouteFindingAlgos algorithm){
        return new TrainSetBuilder(locoBase).setAlgorithm(algorithm).build();
    }

    public List<TrainSet> createRandomTrainSets(int count){
        List<TrainSet> trainSets = new ArrayList<>();
        for (int i = 0; i < count; i++){
            trainSets.add(createRandomTrainSet());
        }
        return trainSets;
    }
}
