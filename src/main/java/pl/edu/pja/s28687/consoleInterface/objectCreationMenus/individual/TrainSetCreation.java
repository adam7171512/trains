package pl.edu.pja.s28687.consoleInterface.objectCreationMenus.individual;
import pl.edu.pja.s28687.train.Locomotive;
import pl.edu.pja.s28687.train.LocomotivePurpose;
import pl.edu.pja.s28687.train.TrainSet;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.factories.TrainSetFactory;
import pl.edu.pja.s28687.info.LocInfo;
import pl.edu.pja.s28687.logistics.AStarRouteFinder;

import java.util.Map;
import java.util.stream.Collectors;


public class TrainSetCreation extends AbstractLeafMenu {
    @Override
    public void menuSpecificAction() {
        String menu = """
                Please select Type of TrainSet
                1. Random without Railroad Cars
                2. Random with Railroad Cars
                3. Passenger Train
                4. Basic Freight train
                5. Heavy Freight train
                6. Use existing Locomotive""";
        System.out.println(menu);

        int type;
        do {
            type = resourceContainer.parseToInt(scan.nextLine());
        } while (type < 1 || type > 6);

        TrainSetFactory trainSetFactory = resourceContainer.getTrainSetFactory();

        TrainSet trainSet;

        switch (type) {
            case 1 -> trainSet = trainSetFactory.createRandomTrainSet();
            case 2 -> trainSet = trainSetFactory.createRandomTrainSetWithCars();
            case 3 -> trainSet = trainSetFactory.createTrainSetOfType(LocomotivePurpose.PASSENGER);
            case 4 -> trainSet = trainSetFactory.createTrainSetOfType(LocomotivePurpose.BASIC_FREIGHT);
            case 5 -> trainSet = trainSetFactory.createTrainSetOfType(LocomotivePurpose.HEAVY_FREIGHT);
            case 6 -> {

                Map<Integer, Locomotive> freeLocomotives = resourceContainer
                        .getLocoBase()
                        .getLocomotiveList()
                        .stream()
                        .filter(l -> l.getTrainSetId() == null)
                        .collect(Collectors.toMap(Locomotive::getId, l -> l));

                if (freeLocomotives.isEmpty()) {
                    System.out.println("No free locomotives available");
                    return;
                }
                else {
                    trainSet = createTrainSetOufOfExistingLocomotive(freeLocomotives);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

        System.out.println("Enter 1 to randomly dispatch the trainSet or any other input to finish + " +
                "\nMore dispatching options available in dispatch menu");
        if (scan.nextLine().equals("1")) {
            resourceContainer.getDispatchingCenter().dispatchTrainSet(trainSet);
        }


    }

    private TrainSet createTrainSetOufOfExistingLocomotive(Map<Integer, Locomotive> freeLocomotives){
        TrainSet trainSet;
        Locomotive locomotive = null;
            int id;
            do {
                System.out.println("Please enter id of existing Locomotive or ls to list out all free locomotives");
                String input = scan.nextLine();
                if (input.equals("ls")) {
                    System.out.println(
                            LocInfo.getAggregatedBasicInfo(
                                    freeLocomotives
                                            .values()
                                            .stream()
                                            .toList()));
                    continue;
                }
                id = resourceContainer.parseToInt(input);
                locomotive = freeLocomotives.get(id);
            } while (locomotive == null);

       trainSet = resourceContainer.getTrainSetFactory().createTrainSetFromLocomotive(locomotive);

        System.out.println("Set route finding algorithm");
        String s = "1. A*\n" +
                "2. Dijkstra\n" +
                "3. DFS (bad)\n";
        System.out.println(s);
        int selection = resourceContainer.parseToInt(scan.nextLine());
        //todo: add more algorithms
        switch (selection) {
            case 3 -> trainSet.setAlgorithm(new AStarRouteFinder(resourceContainer.getLocoBase()));
            default -> trainSet.setAlgorithm(new AStarRouteFinder(resourceContainer.getLocoBase()));
        }
        return trainSet;
    }

    @Override
    public String getTitle() {
        return "Train Set Creation";
    }

    @Override
    public String getDescription() {
        return "For more customisation options please use locomotive creation menu" +
                "to create Locomotive and then create a train set from it using option 6.";
    }
}
