package pl.edu.pja.s28687;

import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.cars.AbstractLoadCarrier;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.logistics.IRouteFinder;

import java.math.BigDecimal;
import java.util.List;

public class TrainSet {
    private final int id;
    private final Locomotive locomotive;
    private final Conductor conductor;
    boolean started = false;

    public TrainSet(Locomotive locomotive, Conductor conductor, int id)  {
        this.locomotive = locomotive;
        this.conductor = conductor;
        this.id = id;
    }

    public void start(){
        conductor.start();
        started = true;
    }

    public void stop(){
        conductor.interrupt();
    }

    public boolean hasStarted(){
        return started;
    }

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public void setAlgorithm(IRouteFinder algorithm){
        conductor.setRouteFindingAlgorithm(algorithm);
    }

    public void setDestinationStation(TrainStation destStation){
        locomotive.setDestinationStation(destStation);
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

    public List<IRailroadCar> getCars(){
        return locomotive.getCars();
    }

    public List<ILoadCarrier<? extends IDeliverable>> getLoadCarriers(){
        return locomotive.getLoadCarriers();
    }

    public ILoadCarrier<? extends IDeliverable> load(IDeliverable load){
        if (! validateLoadWeight(load)){
            throw new IllegalArgumentException("Load is too heavy for this train");
        }

        ILoadCarrier<? extends IDeliverable> carToLoad =
                getCarsThatCouldLoad(load)
                        .stream()
                        .findFirst()
                        .orElseThrow(() -> new ValidationException("No car could load this load"));
        ((ILoadCarrier<IDeliverable>) carToLoad).load(load);
        return carToLoad;
    }

    public boolean validateLoadWeight(IDeliverable load){
        return locomotive.getLoadValidator().validateWeight(load, this.locomotive);
    }

    public boolean validateLoad(IDeliverable load){
        return locomotive.getLoadValidator().validate(load, this.locomotive);
    }

    public List<ILoadCarrier<? extends IDeliverable>> getCarsThatCouldLoad(IDeliverable load){
        if (!validateLoadWeight(load)){
            return List.of();
        }
        return locomotive.getLoadValidator().getCarsThatCouldLoad(load, this.locomotive);
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
        for (IRailroadCar car : locomotive.getCars()){
            if (car instanceof AbstractLoadCarrier){
                loadsNumber += ((AbstractLoadCarrier<?>) car).getLoads().size();
                stringBuilder.append(((AbstractLoadCarrier<?>) car).getLoads());
            }
        }
        stringBuilder.append("\nloadsNumber = ").append(loadsNumber);
        return stringBuilder.toString();
    }

    public BigDecimal getMaxPayload() {
        return locomotive.getMaxPayload();
    }

    public BigDecimal getCurrentPayload() {
        return locomotive.getCurrentPayload();
    }
}
