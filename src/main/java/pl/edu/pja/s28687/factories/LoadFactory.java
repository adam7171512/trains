package pl.edu.pja.s28687.factories;
import pl.edu.pja.s28687.load.Explosives;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class LoadFactory {

    private LocoBase locoBase;

    public LoadFactory(LocoBase locoBase){
        this.locoBase = locoBase;
    }

    public Load<? extends IDeliverable> createLoad(Flags loadType){
        LoadBuilder loadBuilder = new LoadBuilder(locoBase);
        return loadBuilder
                .setFlags(Set.of(loadType))
                .build();
    }

    public Load<? extends IDeliverable> createRandomLoad(){
        LoadBuilder loadBuilder = new LoadBuilder(locoBase);
        return loadBuilder
                .setRandomProperties()
                .build();
    }

    public Load<? extends IDeliverable> createRandomLoadOfType(Flags loadType){
        LoadBuilder loadBuilder = new LoadBuilder(locoBase);
        return loadBuilder
                .setRandomProperties()
                .setFlags(Set.of(loadType))
                .build();
    }

    public Load<? extends IDeliverable> createRandomLoadOfType(Set<Flags> loadTypes){
        LoadBuilder loadBuilder = new LoadBuilder(locoBase);
        return loadBuilder
                .setRandomProperties()
                .setFlags(loadTypes)
                .build();
    }

    public List<Load<? extends IDeliverable>> createRandomLoads(int quantity) {
        List<Load<? extends IDeliverable>> loads = new ArrayList<>();
        for (int i = 0; i < quantity / 3; i++) {
            loads.add(createRandomLoad());
        }
        return loads;
    }

    public List<Load<? extends IDeliverable>> createRandomLoadsOfType(int quantity, Flags loadType) {
        List<Load<? extends IDeliverable>> loads = new ArrayList<>();
        for (int i = 0; i < quantity / 3; i++) {
            loads.add(createRandomLoadOfType(loadType));
        }
        return loads;
    }

    public List<Load<? extends IDeliverable>> createRandomLoadsOfType(int quantity, Set<Flags> loadType) {
        List<Load<? extends IDeliverable>> loads = new ArrayList<>();
        for (int i = 0; i < quantity / 3; i++) {
            loads.add(createRandomLoadOfType(loadType));
        }
        return loads;
    }


}
