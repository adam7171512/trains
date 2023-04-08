package pl.edu.pja.s28687.consoleInterface.infoMenus;

import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.consoleInterface.IBrowsable;
import pl.edu.pja.s28687.info.CarInfo;

import java.util.Optional;

public class CarInfoMenu extends AbstractLeafMenu implements IBrowsable<IRailroadCar> {
    @Override
    public void menuSpecificAction() {
        String message = "Please enter car ID, ls to list out cars or 0 to exit";
        Optional<IRailroadCar> car;
        do {
            car = preProcessInput(scan, message);
            car.ifPresentOrElse(
                    rCar -> System.out.println(CarInfo.getFullInfo(rCar)),
                    () -> System.out.println("No car with this ID "));
        } while (car.isPresent());
        }

    @Override
    public String getTitle() {
        return "Railroad Car info";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void listElements() {
        String s = CarInfo.getAggregatedBasicInfo(resourceContainer.getLocoBase());
        if (s.isEmpty()) {
            System.out.println("No railroad cars registered in the system !");
        } else {
            System.out.println(s);
        }
    }

    @Override
    public Optional<IRailroadCar> matchElement(String input) {
        int id = resourceContainer.parseToInt(input);
        return resourceContainer.getLocoBase().findCar(id);
    }
}
