package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.LocomotivePurpose;
import pl.edu.pja.s28687.logistics.LocoBase;

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


        return new LocomotiveBuilder(locoBase).setName(name)
                .setRegularCars(regularCars)
                .setPoweredCars(poweredCars)
                .setMaxPayload(maxPayload)
                .setDefaultSpeed(speed)
                .build();
    }

    public Locomotive makeRandomLocomotive(){
        return new LocomotiveBuilder(locoBase)
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

    public Locomotive createLocomotiveForPassengerTrain(){
        return new LocomotiveBuilder(locoBase).setDefaultSpeed(BigDecimal.valueOf(120))
                .setMaxPayload(BigDecimal.valueOf(3000))
                .setRegularCars(20)
                .setPoweredCars(20)
                        .build();
    }

    public Locomotive createLocomotiveForBasicFreightTrain(){
        return new LocomotiveBuilder(locoBase).setDefaultSpeed(BigDecimal.valueOf(100))
                .setMaxPayload(BigDecimal.valueOf(6000))
                .setRegularCars(20)
                .setPoweredCars(3)
                .build();
    }

    public Locomotive createLocomotiveForHeavyFreightTrain(){
        return new LocomotiveBuilder(locoBase).setDefaultSpeed(BigDecimal.valueOf(70))
                .setMaxPayload(BigDecimal.valueOf(12000))
                .setRegularCars(20)
                .setPoweredCars(3)
                .build();
    }

    public List<Locomotive> createLocomotivesOfType(int quantity, LocomotivePurpose type){
        List<Locomotive> locomotives = new ArrayList<>();
        for (int i = 0; i < quantity; i++){
            locomotives.add(switch (type){
                case PASSENGER -> createLocomotiveForPassengerTrain();
                case BASIC_FREIGHT -> createLocomotiveForBasicFreightTrain();
                case HEAVY_FREIGHT -> createLocomotiveForHeavyFreightTrain();
            });
        }
        return locomotives;
    }
}
