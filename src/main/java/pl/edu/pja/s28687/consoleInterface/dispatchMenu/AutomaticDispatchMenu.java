package pl.edu.pja.s28687.consoleInterface.dispatchMenu;

import pl.edu.pja.s28687.train.TrainSet;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.factories.DispatchingCenter;

import java.util.Optional;

public class AutomaticDispatchMenu extends AbstractLeafMenu {
    private DispatchingCenter dispatchingCenter;
    @Override
    public void menuSpecificAction() {
        System.out.println("1. Dispatch randomly all trains");
        System.out.println("2. Dispatch randomly all trains with cars attached");
        System.out.println("3. Dispatch randomly selected Train Set");
        System.out.println("4. Change time intervals and other parameters for all existing trainSets");

        dispatchingCenter = resourceContainer.getDispatchingCenter();
        int input;
        do {
            input = resourceContainer.parseToInt(scan.nextLine());
        } while (input < 1 || input > 4);

        switch (input) {
            case 1 -> dispatchingCenter.dispatchAllTrainSets();
            case 2 -> dispatchingCenter.dispatchAllTrainSetsWithCars();
            case 3 -> dispatchSelectedTrainSet();
            case 4 -> setDynamicParameters();
        }

    }

    private void setDynamicParameters() {
        int timeInterval;
        do {
            System.out.println("Enter speed time update interval in milliseconds (more than 1)");
            timeInterval = resourceContainer.parseToInt(scan.nextLine());
        } while (timeInterval < 1);
        int distanceMultiplier;
        do {
            System.out.println("Enter distance multiplier (more than 1) " +
                    "\nhigher value means trains cover higher distance in given time interval");
            distanceMultiplier = resourceContainer.parseToInt(scan.nextLine());
        } while (distanceMultiplier < 1);
        int stationStoppageTime;
        do {
            System.out.println("Enter station stoppage time in milliseconds (more than 1)");
            stationStoppageTime = resourceContainer.parseToInt(scan.nextLine());
        } while (stationStoppageTime < 1);
        int destinationStoppageTime;
        do {
            System.out.println("Enter destination station stoppage time in milliseconds (more than 1)");
            destinationStoppageTime = resourceContainer.parseToInt(scan.nextLine());
        } while (destinationStoppageTime < 1);

        for (TrainSet ts : resourceContainer.getLocoBase().getTrainSets()) {
            ts.setStationStoppageTime(stationStoppageTime);
            ts.setTimeUpdateInterval(timeInterval);
            ts.setDistanceMultiplier(distanceMultiplier);
            ts.setDestinationStoppageTime(destinationStoppageTime);
        }
    }

    private void dispatchSelectedTrainSet(){
        System.out.println("Please enter Train Set ID or ls to list all train sets");
        String input;
        Optional<TrainSet> trainSet = Optional.empty();
        do {
            input = scan.nextLine();
            if (input.equals("ls")) {
                resourceContainer.getLocoBase().getTrainSets().forEach(System.out::println);
            }
            else {
                int id = resourceContainer.parseToInt(input);
                trainSet = resourceContainer.getLocoBase().findTrainSet(id);
                if (trainSet.isEmpty()) {
                    System.out.println("No such train set");
                }
                else {
                    dispatchingCenter.dispatchTrainSet(trainSet.get());
                }
            }
        } while (trainSet.isEmpty());
    }


    @Override
    public String getTitle() {
        return "Automatic";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
