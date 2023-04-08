package pl.edu.pja.s28687.factories;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LoadFactory {

    private LocoBase locoBase;

    public LoadFactory(LocoBase locoBase){
        this.locoBase = locoBase;
    }

    public IDeliverable createLoad(LoadType loadType){
        LoadBuilder loadBuilder = new LoadBuilder(locoBase);
        return loadBuilder
                .setFlags(Set.of(loadType))
                .build();
    }

    public IDeliverable createLoad(Set<LoadType> loadType, double weight){
        LoadBuilder loadBuilder = new LoadBuilder(locoBase);
        return loadBuilder
                .setWeight(weight)
                .setFlags(loadType)
                .build();
    }


    public IDeliverable createPassengerLoad(int quantity){
        LoadBuilder loadBuilder = new LoadBuilder(locoBase);
        return loadBuilder
                .setFlags(Set.of(LoadType.PASSENGERS))
                .setQuantity(quantity)
                .build();
    }

    public IDeliverable createRandomLoad(){
        LoadBuilder loadBuilder = new LoadBuilder(locoBase);
        return loadBuilder
                .setRandomProperties()
                .build();
    }

    public IDeliverable createRandomLoadOfType(LoadType loadType){
        LoadBuilder loadBuilder = new LoadBuilder(locoBase);
        return loadBuilder
                .setRandomProperties()
                .setFlags(Set.of(loadType))
                .build();
    }

    public IDeliverable createRandomLoadOfType(Set<LoadType> loadTypes){
        LoadBuilder loadBuilder = new LoadBuilder(locoBase);
        return loadBuilder
                .setRandomProperties()
                .setFlags(loadTypes)
                .build();
    }

    public List<IDeliverable> createRandomLoads(int quantity) {
        List<IDeliverable> loads = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            loads.add(createRandomLoad());
        }
        return loads;
    }

    public List<IDeliverable> createRandomLoadsOfType(int quantity, LoadType loadType) {
        List<IDeliverable> loads = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            loads.add(createRandomLoadOfType(loadType));
        }
        return loads;
    }

    public List<IDeliverable> createRandomLoadsOfType(int quantity, Set<LoadType> loadType) {
        List<IDeliverable> loads = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            loads.add(createRandomLoadOfType(loadType));
        }
        return loads;
    }


}
