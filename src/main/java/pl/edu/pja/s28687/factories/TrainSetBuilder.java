package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.*;
import pl.edu.pja.s28687.logistics.AStarRouteFinder;
import pl.edu.pja.s28687.logistics.IRouteFinder;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.validators.locomotive.ILocomotiveLoadValidator;
import pl.edu.pja.s28687.validators.locomotive.LocomotiveLoadValidator;

public class TrainSetBuilder {
    private Conductor conductor;
    private final LocoBase locoBase;
    private Locomotive locomotive;
    private IRouteFinder algorithm;

    public TrainSetBuilder(LocoBase locoBase){
        this.locoBase = locoBase;
    }

    public TrainSetBuilder setAlgorithm(IRouteFinder algorithm){
        this.algorithm = algorithm;
        return this;
    }

    public TrainSetBuilder setLocomotive(Locomotive locomotive){
        this.locomotive = locomotive;
        return this;
    }

    public TrainSet build(){
        if (locoBase == null){
            throw new IllegalStateException("LocoBase is not set");
        }
        if (locomotive == null){
            locomotive = new LocomotiveFactory(locoBase).createRandomLocomotive();
        }
        if (algorithm == null){
            algorithm = new AStarRouteFinder(locoBase);
        }
        if (conductor == null) {
            conductor = new Conductor(locomotive, algorithm);
        }

        int id = locoBase.getIdForTrainSet();
        TrainSet t = new TrainSet(locomotive, conductor, id);
        locomotive.setTrainSetId(id);
        locoBase.registerTrainSet(t);
        return t;
    }
}
