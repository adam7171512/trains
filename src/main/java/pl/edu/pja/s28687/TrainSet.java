package pl.edu.pja.s28687;

import pl.edu.pja.s28687.cars.LoadableRailroadCar;
import pl.edu.pja.s28687.cars.RailroadCar;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.LiquidLoad;
import pl.edu.pja.s28687.load.Load;
import pl.edu.pja.s28687.logistics.IRouteFinder;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.logistics.RouteFindingAlgos;

import java.util.List;
import java.util.Optional;

public class TrainSet {
    private int id;
    private Locomotive locomotive;
    private Conductor conductor;
    private LocoBase locoBase;
    private IFreightValidator freightValidator;

    public TrainSet(Locomotive locomotive, Conductor conductor, LocoBase locoBase, int id){
        this.locomotive = locomotive;
        this.conductor = conductor;
        this.locoBase = locoBase;
        this.id = id;
        this.freightValidator = new FreightValidator();
        freightValidator.validate(new LiquidLoad(2), getLoadableCars().get(0));
    }

    public void start(){
        conductor.start();
    }

    public void stop(){
        conductor.interrupt();
    }

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public void setAlgorithm(IRouteFinder algorithm){
        conductor.setRouteFindingAlgorithm(algorithm);
    }

    public void setDestStation(TrainStation destStation){
        locomotive.setDestStation(destStation);
    }

    public void setSourceStation(TrainStation sourceStation){
        locomotive.setSourceStation(sourceStation);
    }

    public void setHomeStation(TrainStation homeStation){
        locomotive.setHomeStation(homeStation);
    }


    public int getId() {
        return this.id;
    }

    public List<RailroadCar> getCars(){
        return locomotive.getCars();
    }

    public List<LoadableRailroadCar<? extends IDeliverable>> getLoadableCars(){
        return locomotive.getLoadableCars();
    }

    public Optional<LoadableRailroadCar<? extends IDeliverable>> load(Load<? extends IDeliverable> load){

        List<LoadableRailroadCar<?>> loadableCars = findCarsForLoad(load);
        LoadableRailroadCar<? extends IDeliverable> carToLoad;

        Optional<LoadableRailroadCar<? extends IDeliverable>> carToLoadOpt = Optional.empty();

        if (!loadableCars.isEmpty()) {
            carToLoad = loadableCars.get(0);
            carToLoad.load((Load<IDeliverable>) load);
            load.setLoaded();
            carToLoadOpt = Optional.of(carToLoad);
        }
        return carToLoadOpt;
    }

    public String validateLoadWeight(Load<? extends IDeliverable> load){
        String s = "";
        if (load.getWeight().add(locomotive.getCurrentFreight()).compareTo(locomotive.getMaxFreight()) > 0){
            s += "Load is too heavy and can't be loaded because locomotive's max freight would be exceeded"
                    + "\nCurrent freight: " + locomotive.getCurrentFreight()
                    + "\nMax freight: " + locomotive.getMaxFreight()
                    + "\nLoad weight: " + load.getWeight()
                    + "\nToo heavy by : "
                    + load.getWeight()
                    .add(locomotive.getCurrentFreight())
                    .subtract(locomotive.getMaxFreight());
        }
        return s;
    }

    public List<LoadableRailroadCar<? extends IDeliverable>> findCarsForLoad(Load<? extends IDeliverable> load){

        if (! validateLoadWeight(load).isEmpty()){
            return List.of();
        }
        else {
            return getLoadableCars().stream()
                    .filter(car -> car
                            .validateLoad
                                    ((Load<IDeliverable>) (load)).isEmpty()).toList();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder()
                .append("TrainSet : ")
                .append("id = ")
                .append(id)
                .append("\nlocomotive = ")
                .append(locomotive)
                .append("\nconductor = ")
                .append(conductor)
                .append("\ncars : ")
                .append(locomotive.getCars())
                .append("\nloads : ");

        int loadsNumber = 0;
        for (RailroadCar car : locomotive.getCars()){
            if (car instanceof LoadableRailroadCar){
                loadsNumber += ((LoadableRailroadCar<?>) car).getLoads().size();
                stringBuilder.append(((LoadableRailroadCar<?>) car).getLoads());
            }
        }
        stringBuilder.append("\nloadsNumber = ").append(loadsNumber);
        return stringBuilder.toString();
    }
}
