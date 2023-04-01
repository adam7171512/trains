package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.factories.TrainStationFactory;
import pl.edu.pja.s28687.logistics.LocoBase;

public class TrainStationCreationMenu2 extends Menu{

    static String name = "Train Station Creation Menu";
    static String description = "Train Station Creation Menu";


    public TrainStationCreationMenu2() {
        super(name, description);
    }

    @Override
    public void createSubMenus() {}

    @Override
    public void menuAction(){
        System.out.println("Please enter Train Station City Name");
        String name = scan.nextLine();
        System.out.println("Please enter Train Station x Coordinate");
        int x = scan.nextInt();
        System.out.println("Please enter Train Station y Coordinate");
        int y = scan.nextInt();
        new TrainStationFactory(new LocoBase()).createTrainStation(name, x, y);
        parentMenu.menuAction();
    }

    @Override
    public void handleInput(String input) {}

}
