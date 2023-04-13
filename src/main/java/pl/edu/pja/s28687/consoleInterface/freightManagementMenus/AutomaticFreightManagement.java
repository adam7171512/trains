package pl.edu.pja.s28687.consoleInterface.freightManagementMenus;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.factories.CarAssignmentCenter;
import pl.edu.pja.s28687.factories.LoadAssignmentCenter;
import java.util.List;

public class AutomaticFreightManagement extends AbstractLeafMenu {
    @Override
    public void menuSpecificAction() {
        System.out.println("1. Automatically create train sets from free locomotives");
        System.out.println("2. Automatically assign cars to locomotives");
        System.out.println("3. Automatically assign loads to cars");
        int selection;
        do {
            selection = resourceContainer.parseToInt(scan.nextLine());
        } while (selection < 1 || selection > 3);

        switch (selection) {
            case 1 -> {
                List<Locomotive> freeLocomotives = resourceContainer
                        .getLocoBase()
                        .getLocomotiveList()
                        .stream()
                        .filter(l -> l.getTrainSetId() == null)
                        .toList();
                List<TrainSet> trainSets = resourceContainer
                        .getTrainSetFactory()
                        .createTrainSetsFromLocomotives(freeLocomotives);

                System.out.println("Enter 1 to randomly dispatch the trainSet or any other input to finish" +
                        "\nMore dispatching options available in dispatch menu");
                if (scan.nextLine().equals("1")) {
                    resourceContainer.getDispatchingCenter().dispatchTrainSets(trainSets);
                }
            }
            case 2 -> CarAssignmentCenter.assignCars(resourceContainer.getLocoBase());
            case 3 -> LoadAssignmentCenter.assignLoads(resourceContainer.getLocoBase());
        }
    }

    @Override
    public String getTitle() {
        return "Automatic freight management";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
