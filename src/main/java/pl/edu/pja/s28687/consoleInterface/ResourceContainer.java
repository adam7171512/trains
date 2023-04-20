package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Scanner;

public class ResourceContainer {

    private LocoBase locoBase;
    private TrainStationFactory trainStationFactory;
    private TrainSetFactory trainSetFactory;
    private LocomotiveFactory locomotiveFactory;
    private CarsFactory carsFactory;
    private LoadFactory loadFactory;
    private RailroadsFactory railroadsFactory;

    private DispatchingCenter dispatchingCenter;


    public ResourceContainer(){
        this.locoBase = new LocoBase();
        this.trainStationFactory = new TrainStationFactory(locoBase);
        this.locomotiveFactory = new LocomotiveFactory(locoBase);
        this.carsFactory = new CarsFactory(locoBase);
        this.loadFactory = new LoadFactory(locoBase);
        this.railroadsFactory = new RailroadsFactory(locoBase);
        this.trainSetFactory = new TrainSetFactory(locoBase, locomotiveFactory, carsFactory);
        this.dispatchingCenter = new DispatchingCenter(locoBase);
    }

    public int parseToInt(String s){
        try{
            return Integer.parseInt(s);
        }catch (NumberFormatException e){
        }
        System.err.println("Invalid input !");
//        return parseToInt(new Scanner(System.in).nextLine());
        return -1;
    }

    public double parseToDouble(String s){
        try{
            return Double.parseDouble(s);
        }catch (NumberFormatException e){
        }
        System.err.println("Invalid input !");
        return parseToDouble(new Scanner(System.in).nextLine());
    }

    public LocoBase getLocoBase() {
        return locoBase;
    }

    public DispatchingCenter getDispatchingCenter() {
        return dispatchingCenter;
    }

    public TrainStationFactory getTrainStationFactory() {
        return trainStationFactory;
    }

    public TrainSetFactory getTrainSetFactory() {
        return trainSetFactory;
    }

    public LocomotiveFactory getLocomotiveFactory() {
        return locomotiveFactory;
    }

    public CarsFactory getCarsFactory() {
        return carsFactory;
    }

    public LoadFactory getLoadFactory() {
        return loadFactory;
    }

    public RailroadsFactory getRailroadsFactory() {
        return railroadsFactory;
    }
}
