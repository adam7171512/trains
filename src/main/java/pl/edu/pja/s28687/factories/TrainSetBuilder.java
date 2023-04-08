package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.*;
import pl.edu.pja.s28687.logistics.IRouteFinder;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.validators.locomotive.ILocomotiveLoadValidator;
import pl.edu.pja.s28687.validators.locomotive.LocomotiveLoadValidator;

public class TrainSetBuilder {
    private Conductor conductor;
    private LocoBase locoBase;
    private Locomotive locomotive;
    private IRouteFinder algorithm;
    private ILocomotiveLoadValidator loadValidator;

    public TrainSetBuilder(LocoBase locoBase){
        this.locoBase = locoBase;
    }


    public TrainSetBuilder setLocoBase(LocoBase locoBase){
        this.locoBase = locoBase;
        return this;
    }

    public TrainSetBuilder setAlgorithm(IRouteFinder algorithm){
        this.algorithm = algorithm;
        return this;
    }

    public TrainSetBuilder setLoadValidator(ILocomotiveLoadValidator loadValidator){
        this.loadValidator = loadValidator;
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
        if (conductor == null) {
            conductor = new Conductor(locomotive, locoBase);
        }
        if (algorithm != null){
            conductor.setRouteFindingAlgorithm(algorithm);
        }
        if (loadValidator == null){
            loadValidator = new LocomotiveLoadValidator();
        }


        int id = locoBase.getIdForTrainSet();
        TrainSet t = new TrainSet(locomotive, conductor, locoBase, id, loadValidator);
        locoBase.registerTrainSet(t);
        return t;
    }



}
