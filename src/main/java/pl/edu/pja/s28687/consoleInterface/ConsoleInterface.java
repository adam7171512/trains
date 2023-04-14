package pl.edu.pja.s28687.consoleInterface;
import pl.edu.pja.s28687.ResourceContainer;
import pl.edu.pja.s28687.consoleInterface.dispatchMenu.AutomaticDispatchMenu;
import pl.edu.pja.s28687.consoleInterface.dispatchMenu.DispatchSelectionMenu;
import pl.edu.pja.s28687.consoleInterface.dispatchMenu.ManualDispatchMenu;
import pl.edu.pja.s28687.consoleInterface.freightManagementMenus.*;
import pl.edu.pja.s28687.consoleInterface.infoMenus.*;
import pl.edu.pja.s28687.consoleInterface.objectCreationMenus.SimpleLocoSystemPreparation;
import pl.edu.pja.s28687.consoleInterface.objectCreationMenus.ObjectCreationMethodSelectMenu;
import pl.edu.pja.s28687.consoleInterface.objectCreationMenus.individual.IndividualObjectCreationMenu;
import pl.edu.pja.s28687.consoleInterface.objectCreationMenus.bulk.*;
import pl.edu.pja.s28687.consoleInterface.objectCreationMenus.individual.*;
import pl.edu.pja.s28687.info.AggregateLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleInterface {

    public ConsoleInterface(){
        initialize();
    }
    public static int parseToInt(String s){
        try{
            return Integer.parseInt(s);
        }catch (NumberFormatException e){
            System.err.println("Invalid input, please try again");
            return parseToInt(new Scanner(System.in).nextLine());
        }
    }

    public static double parseToDouble(String s){
        try{
            return Double.parseDouble(s);
        }catch (NumberFormatException e){
            System.err.println("Invalid input, please try again");
            return parseToDouble(new Scanner(System.in).nextLine());
        }
    }
    private final List<IMenu> menus = new ArrayList<>();
    private IMenu root;

    public void show(){
        root.menuFlow();
    }
    public void initialize(){
        MainMenu mainMenu = new MainMenu();
        menus.add(mainMenu);
        root = mainMenu;

        ObjectCreationMethodSelectMenu objectCreationMethodSelectMenu = new ObjectCreationMethodSelectMenu();
        menus.add(objectCreationMethodSelectMenu);
        mainMenu.addSubMenu(objectCreationMethodSelectMenu);

        IndividualObjectCreationMenu individualObjectCreationMenu = new IndividualObjectCreationMenu();
        menus.add(individualObjectCreationMenu);

        objectCreationMethodSelectMenu.addSubMenu(individualObjectCreationMenu);

        BulkObjectCreationMenu bulkObjectCreationMenu = new BulkObjectCreationMenu();
        menus.add(bulkObjectCreationMenu);
        objectCreationMethodSelectMenu.addSubMenu(bulkObjectCreationMenu);

        SimpleLocoSystemPreparation simpleLocoSystemPreparation = new SimpleLocoSystemPreparation();
        menus.add(simpleLocoSystemPreparation);
        objectCreationMethodSelectMenu.addSubMenu(simpleLocoSystemPreparation);

        TrainStationCreation trainStationCreation = new TrainStationCreation();
        menus.add(trainStationCreation);
        individualObjectCreationMenu.addSubMenu(trainStationCreation);

        TrainSetCreation trainSetCreation = new TrainSetCreation();
        menus.add(trainSetCreation);
        individualObjectCreationMenu.addSubMenu(trainSetCreation);

        LocomotiveCreation locomotiveCreation = new LocomotiveCreation();
        menus.add(locomotiveCreation);
        individualObjectCreationMenu.addSubMenu(locomotiveCreation);

        RailroadCarCreation railroadCarCreation = new RailroadCarCreation();
        menus.add(railroadCarCreation);
        individualObjectCreationMenu.addSubMenu(railroadCarCreation);

        LoadCreation loadCreation = new LoadCreation();
        menus.add(loadCreation);
        individualObjectCreationMenu.addSubMenu(loadCreation);

        RailroadCreation railroadCreation = new RailroadCreation();
        menus.add(railroadCreation);
        individualObjectCreationMenu.addSubMenu(railroadCreation);

        BulkTrainSetCreation bulkTrainSetCreation = new BulkTrainSetCreation();
        menus.add(bulkTrainSetCreation);
        bulkObjectCreationMenu.addSubMenu(bulkTrainSetCreation);

        BulkTrainStationCreation bulkTrainStationCreation = new BulkTrainStationCreation();
        menus.add(bulkTrainStationCreation);
        bulkObjectCreationMenu.addSubMenu(bulkTrainStationCreation);

        BulkLocomotiveCreation bulkLocomotiveCreation = new BulkLocomotiveCreation();
        menus.add(bulkLocomotiveCreation);
        bulkObjectCreationMenu.addSubMenu(bulkLocomotiveCreation);

        BulkRailroadCarCreation bulkRailroadCarCreation = new BulkRailroadCarCreation();
        menus.add(bulkRailroadCarCreation);
        bulkObjectCreationMenu.addSubMenu(bulkRailroadCarCreation);

        BulkLoadCreation bulkLoadCreation = new BulkLoadCreation();
        menus.add(bulkLoadCreation);
        bulkObjectCreationMenu.addSubMenu(bulkLoadCreation);

        BulkRailroadCreation bulkRailroadCreation = new BulkRailroadCreation();
        menus.add(bulkRailroadCreation);
        bulkObjectCreationMenu.addSubMenu(bulkRailroadCreation);

        BulkMultiCreation bulkMultiCreation = new BulkMultiCreation();
        menus.add(bulkMultiCreation);
        bulkObjectCreationMenu.addSubMenu(bulkMultiCreation);

        FreightManagementTypeSelectMenu freightManagementTypeSelectMenu = new FreightManagementTypeSelectMenu();
        menus.add(freightManagementTypeSelectMenu);
        mainMenu.addSubMenu(freightManagementTypeSelectMenu);

        AutomaticFreightManagement automaticFreightManagement = new AutomaticFreightManagement();
        menus.add(automaticFreightManagement);
        freightManagementTypeSelectMenu.addSubMenu(automaticFreightManagement);

        ManualFreightManagementSelectMenu manualFreightManagementSelectMenu = new ManualFreightManagementSelectMenu();
        menus.add(manualFreightManagementSelectMenu);
        freightManagementTypeSelectMenu.addSubMenu(manualFreightManagementSelectMenu);

        LocomotiveManagement locomotiveManagement = new LocomotiveManagement();
        menus.add(locomotiveManagement);
        manualFreightManagementSelectMenu.addSubMenu(locomotiveManagement);

        RailroadCarManagement railroadCarManagement = new RailroadCarManagement();
        menus.add(railroadCarManagement);
        manualFreightManagementSelectMenu.addSubMenu(railroadCarManagement);

        InfoSelectMenu infoSelectMenu = new InfoSelectMenu();
        menus.add(infoSelectMenu);
        mainMenu.addSubMenu(infoSelectMenu);

        TrainSetInfoMenu trainSetInfoMenu = new TrainSetInfoMenu();
        menus.add(trainSetInfoMenu);
        infoSelectMenu.addSubMenu(trainSetInfoMenu);

        LocomotiveInfoMenu locomotiveInfoMenu = new LocomotiveInfoMenu();
        menus.add(locomotiveInfoMenu);
        infoSelectMenu.addSubMenu(locomotiveInfoMenu);

        CarInfoMenu carInfoMenu = new CarInfoMenu();
        menus.add(carInfoMenu);
        infoSelectMenu.addSubMenu(carInfoMenu);

        LoadInfoMenu loadInfoMenu = new LoadInfoMenu();
        menus.add(loadInfoMenu);
        infoSelectMenu.addSubMenu(loadInfoMenu);

        DispatchSelectionMenu dispatchSelectionMenu = new DispatchSelectionMenu();
        menus.add(dispatchSelectionMenu);
        mainMenu.addSubMenu(dispatchSelectionMenu);

        Visualisation visualisation = new Visualisation();
        menus.add(visualisation);
        mainMenu.addSubMenu(visualisation);

        Presentation presentation = new Presentation();
        menus.add(presentation);
        mainMenu.addSubMenu(presentation);

        AutomaticDispatchMenu automaticDispatchMenu = new AutomaticDispatchMenu();
        menus.add(automaticDispatchMenu);
        dispatchSelectionMenu.addSubMenu(automaticDispatchMenu);

        ManualDispatchMenu manualDispatchMenu = new ManualDispatchMenu();
        menus.add(manualDispatchMenu);
        dispatchSelectionMenu.addSubMenu(manualDispatchMenu);

        ResourceContainer resourceContainer = new ResourceContainer();
        for(IMenu menu : menus){
            if (menu instanceof ILeafMenu){
            ((ILeafMenu) menu).setResourceContainer(resourceContainer);
            }
        }

        AggregateLogger logger = new AggregateLogger(resourceContainer.getLocoBase());
        logger.start();

    }
}
