package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.Conductor;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.logistics.RailroadLink;
import pl.edu.pja.s28687.logistics.RouteFindingAlgos;
import pl.edu.pja.s28687.TrainStation;

import java.math.BigDecimal;
import java.util.*;

public class LocomotiveFactory {




    private LocoBase locoBase;

    public LocomotiveFactory(LocoBase locoBase){
        this.locoBase = locoBase;
    }

    public Locomotive makeLocomotive( String name,
                                      int regularCars,
                                      int poweredCars,
                                      BigDecimal maxPayload,
                                      BigDecimal speed){


        return new LocoBuilder(locoBase).setName(name)
                .setRegularCars(regularCars)
                .setPoweredCars(poweredCars)
                .setMaxPayload(maxPayload)
                .setDefaultSpeed(speed)
                .build();
    }

    public Locomotive makeRandomLocomotive(){
        return new LocoBuilder(locoBase)
                .setRandomProperties()
                .build();
    }

    public List<Locomotive> makeRandomLocomotives(int quantity){
        List<Locomotive> locomotives = new ArrayList<>();
        for (int i = 0; i < quantity; i++){
            locomotives.add(makeRandomLocomotive());
        }
        return locomotives;
    }
}
