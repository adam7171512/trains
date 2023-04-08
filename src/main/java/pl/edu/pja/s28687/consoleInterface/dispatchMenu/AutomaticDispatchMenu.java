package pl.edu.pja.s28687.consoleInterface.dispatchMenu;

import pl.edu.pja.s28687.TrainSet;
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

        dispatchingCenter = resourceContainer.getDispatchingCenter();
        int input;
        do {
            input = resourceContainer.parseToInt(scan.nextLine());
        } while (input < 1 || input > 3);

        switch (input) {
            case 1 -> dispatchingCenter.dispatchAllTrainSets();
            case 2 -> dispatchingCenter.dispatchAllTrainSetsWithCars();
            case 3 -> dispatchSelectedTrainSet();
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
        return "Automatic dispatch";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
