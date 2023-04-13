package pl.edu.pja.s28687.consoleInterface.dispatchMenu;

import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.consoleInterface.IBrowsable;
import pl.edu.pja.s28687.info.TrainSetInfo;
import pl.edu.pja.s28687.logistics.BadRouteFinder;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.logistics.NaiveRouteFinder;

import java.util.List;
import java.util.Optional;

public class ManualDispatchMenu extends AbstractLeafMenu implements IBrowsable<TrainSet> {


    @Override
    public void menuSpecificAction() {
        LocoBase locoBase = resourceContainer.getLocoBase();
        List<TrainSet> trainSets = locoBase.getTrainSets();

        if (trainSets.isEmpty()) {
            System.out.println("No train sets registered in the system !");
            return;
        }

        String message = "Enter Train Set ID, ls to list all train sets or 0 to go back";
        Optional<TrainSet> trainSett = preProcessInput(scan, message);
        if (trainSett.isEmpty()) {
            return;
        }
        TrainSet ts = trainSett.get();

        String s = "1. Set route\n" +
                "2. Set route finding algorithm\n";
        System.out.println(s);

        int selection = resourceContainer.parseToInt(scan.nextLine());

        switch (selection) {
            case 1 -> new SetRouteProcess(locoBase, ts).run();
            case 2 -> setRouteAlgorithmMenu(ts);
        }
    }



    @Override
    public String getTitle() {
        return "Manual Dispatch";
    }

    @Override
    public String getDescription() {
        return "";
    }

    private void setRouteAlgorithmMenu(TrainSet trainSet){
        String s = new StringBuilder().append("1. A*\n")
                .append("2. Dijkstra\n")
                .append("3. DFS (bad)\n")
                .toString();
        System.out.println(s);
        int selection = resourceContainer.parseToInt(scan.nextLine());
        switch (selection) {
            case 3 -> trainSet.setAlgorithm(new BadRouteFinder(resourceContainer.getLocoBase()));
            default -> trainSet.setAlgorithm(new NaiveRouteFinder(resourceContainer.getLocoBase()));
        }
    }

    private Optional<TrainStation> readInput(String s){
        System.out.println(s);
        String input;
        do {
            input = scan.nextLine();
            if (input.equals("ls")) {
                listAllStations();
            }
        } while (input.equals("ls"));
        return resourceContainer.getLocoBase().findTrainStation(input);
    }

    private void listAllStations() {
        resourceContainer.getLocoBase().getTrainStations().forEach(System.out::println);
    }

    @Override
    public void listElements() {
        System.out.println(TrainSetInfo.getAggregatedBasicTrainSetsInfo(resourceContainer.getLocoBase()));
    }


    @Override
    public Optional<TrainSet> matchElement(String input) {
        Optional<TrainSet> trainSet = Optional.empty();
        int id = resourceContainer.parseToInt(input);
        if (id == -1) {
            System.out.println("Invalid input!");
        }
        return resourceContainer.getLocoBase().findTrainSet(id);
    }

    public Optional<TrainSet> matchTrainStation(String input) {
        Optional<TrainSet> trainSet = Optional.empty();
        int id = resourceContainer.parseToInt(input);
        if (id == -1) {
            System.out.println("Invalid input!");
        }
        return resourceContainer.getLocoBase().findTrainSet(id);
    }

}
