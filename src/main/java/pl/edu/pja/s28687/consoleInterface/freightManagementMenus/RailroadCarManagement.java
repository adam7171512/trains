package pl.edu.pja.s28687.consoleInterface.freightManagementMenus;

import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.consoleInterface.IBrowsable;
import pl.edu.pja.s28687.info.CarInfo;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.List;
import java.util.Optional;

public class RailroadCarManagement extends AbstractLeafMenu implements IBrowsable<IRailroadCar> {
    @Override
    public void menuSpecificAction() {

        String message = "Please enter car ID, ls to list out cars or 0 to exit";
        Optional<IRailroadCar> car;
        do {
            car = preProcessInput(scan, message);
            car.ifPresentOrElse(
                    rCar -> {
                        System.out.println(rCar.getBasicInfo());
                        System.out.println("""
                                Enter
                                1 to perform routine procedure
                                2 to perform safety check
                                3 to perform emergency procedure
                                0 to go back to previous menu""");
                        int selection;
                        do {
                            selection = resourceContainer.parseToInt(scan.nextLine());
                            switch (selection) {
                                case 1 -> rCar.routineProcedure();
                                case 2 -> rCar.safetyCheck();
                                case 3 -> rCar.emergencyProcedure();
                                case 0 -> System.out.println("Going back to previous menu");
                                default -> System.out.println("Invalid selection");
                            }
                        } while (selection != 0);
                    },
                    () -> System.out.println("No car with this ID "));
        } while (car.isPresent());
    }

    @Override
    public String getTitle() {
        return "Railroad Cars - special actions";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void listElements() {
        LocoBase locoBase = resourceContainer.getLocoBase();
        List<IRailroadCar> cars = locoBase.getRailroadCarsList();
        if (cars.isEmpty()) {
            System.out.println("No cars registered in the system !");
            return;
        }
        System.out.println(CarInfo.getAggregatedBasicInfo(cars));
    }

    @Override
    public Optional<IRailroadCar> matchElement(String input) {
        int id = resourceContainer.parseToInt(input);
        return resourceContainer.getLocoBase().findCar(id);
    }
}
