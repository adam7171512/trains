package pl.edu.pja.s28687.consoleInterface.dispatchMenu;

import pl.edu.pja.s28687.train.TrainSet;
import pl.edu.pja.s28687.logistics.TrainStation;
import pl.edu.pja.s28687.consoleInterface.IBrowsable;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Optional;
import java.util.Scanner;

public class SetRouteProcess implements IBrowsable<TrainStation> {
    private LocoBase locoBase;
    private Scanner scan;
    private TrainSet trainSet;

    public SetRouteProcess(LocoBase locoBase, TrainSet trainSet) {
        this.locoBase = locoBase;
        this.scan = new Scanner(System.in);
        this.trainSet = trainSet;
    }

    public void run(){
        Optional<TrainStation> source = preProcessInput(scan, "Enter source station name, ls to list all stations or 0 to go back");
        if (source.isEmpty()) {
            return;
        }
        Optional<TrainStation> destination = preProcessInput(scan, "Enter destination station name, ls to list all stations or 0 to go back");
        if (destination.isEmpty()) {
            return;
        }
        trainSet.setSourceStation(source.get());
        trainSet.setDestinationStation(destination.get());
        if (!trainSet.hasStarted()) {
            trainSet.start();
        }
    }

    @Override
    public void listElements() {
        locoBase.getTrainStations().forEach(ts -> System.out.println(ts.getName()));
    }

    @Override
    public Optional<TrainStation> matchElement(String input) {
        return locoBase.findTrainStation(input);
    }
}
