package pl.edu.pja.s28687.consoleInterface.dispatchMenu;

import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.consoleInterface.IBrowsable;
import pl.edu.pja.s28687.info.TrainSetInfo;
import pl.edu.pja.s28687.logistics.*;

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

        String s = """
                1. Set route
                2. Set route finding algorithm
                3. Set conductor parameters (time interval, distance multiplier etc)
                """;
        System.out.println(s);

        int selection = resourceContainer.parseToInt(scan.nextLine());

        switch (selection) {
            case 1 -> new SetRouteProcess(locoBase, ts).run();
            case 2 -> setRouteAlgorithmMenu(ts);
            case 3 -> setTimeIntervalAndDistanceMultiplier(ts);
        }
    }

    private void setTimeIntervalAndDistanceMultiplier(TrainSet ts) {
        System.out.println("Enter time interval in milliseconds");
        int timeInterval = resourceContainer.parseToInt(scan.nextLine());
        System.out.println("Enter distance multiplier");
        int distanceMultiplier = resourceContainer.parseToInt(scan.nextLine());
        ts.setTimeUpdateInterval(timeInterval);
        ts.setDistanceMultiplier(distanceMultiplier);

        System.out.println("Enter station stoppage time in milliseconds");
        int selection = resourceContainer.parseToInt(scan.nextLine());
        ts.setStationStoppageTime(selection);
        System.out.println("Enter destination station stoppage time in milliseconds");
        selection = resourceContainer.parseToInt(scan.nextLine());
        ts.setDestinationStoppageTime(selection);

    }

    @Override
    public String getTitle() {
        return "Manual Dispatch";
    }

    @Override
    public String getDescription() {
        return "";
    }

    private void setRouteAlgorithmMenu(TrainSet trainSet) {
        String s = new StringBuilder().append("1. A*\n")
                .append("2. Dijkstra\n")
                .append("3. DFS (bad)\n")
                .append("4. A* reversed (bad)\n")
                .toString();
        System.out.println(s);
        int selection = resourceContainer.parseToInt(scan.nextLine());
        switch (selection) {
            case 1 -> trainSet.setAlgorithm(new AStarRouteFinder(resourceContainer.getLocoBase()));
            case 2 -> trainSet.setAlgorithm(new DijkstraRouteFinder(resourceContainer.getLocoBase()));
            case 3 -> trainSet.setAlgorithm(new DepthFirstSearchRouteFinder(resourceContainer.getLocoBase()));
            case 4 -> trainSet.setAlgorithm(new ReverseAStarRouteFinder(resourceContainer.getLocoBase()));
            default -> System.out.println("Invalid input!");
        }
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
