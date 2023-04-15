package pl.edu.pja.s28687.consoleInterface.freightManagementMenus;

import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.consoleInterface.IBrowsable;
import pl.edu.pja.s28687.factories.LoadAssignmentCenter;
import pl.edu.pja.s28687.info.CarInfo;
import pl.edu.pja.s28687.info.LoadInfo;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RailroadCarManagement extends AbstractLeafMenu implements IBrowsable<ILoadCarrier<IDeliverable>> {
    @Override
    public void menuSpecificAction() {

        String message = "Please enter car ID, ls to list out cars or 0 to exit";
        Optional<ILoadCarrier<IDeliverable>> car;
        do {
            car = preProcessInput(scan, message);
            car.ifPresentOrElse(
                    rCar -> {
                        System.out.println(CarInfo.getBasicInfo(rCar));
                        System.out.println("Enter" +
                                "\n1 to see loads loaded to this carrier" +
                                "\n2 to see all validated loads available for this carrier" +
                                "\n3 to see all free loads in the loco base");
                        int selection = resourceContainer.parseToInt(scan.nextLine());
                        switch (selection) {
                            case 1 -> loadedLoadsMenu(rCar);
                            case 2 -> availableLoadsMenu(rCar);
                            case 3 -> allLoadsMenu(rCar);
                            default -> System.out.println("Wrong input");
                        }
                    },
                    () -> System.out.println("No car with this ID "));
        } while (car.isPresent());
    }

    @Override
    public String getTitle() {
        return "Railroad Cars (Load Carriers)";
    }

    @Override
    public String getDescription() {
        return "";
    }

    private void loadedLoadsMenu(ILoadCarrier<IDeliverable> carrier){
        int id;
        do {
            Map<Integer, IDeliverable> loadedLoads =
                    carrier.getLoads()
                            .stream()
                            .collect(Collectors.toMap(IDeliverable::getId, Function.identity()));
            System.out.println("Loaded loads:");
            printLoadedLoads(loadedLoads);
            System.out.println("Enter Load Id to deload or 0 to go back");
            id = resourceContainer.parseToInt(scan.nextLine());
            IDeliverable load = loadedLoads.get(id);
            if (load != null) {
                carrier.unLoad(load);
            } else if(id != 0) {
                System.out.println("Incorrect ID " + id);
            }
        } while (id != 0);
    }

    private void printLoadedLoads(Map<Integer, IDeliverable> loadedLoads){
        if (loadedLoads.isEmpty()){
            System.out.println("No loaded loads");
            return;
        }
        int n = 1;
        for (IDeliverable loaded : loadedLoads.values()){
            System.out.println(n + ". " + LoadInfo.getBasicInfo(loaded));
            n++;
        }
    }

    private void availableLoadsMenu(ILoadCarrier<IDeliverable> carrier){
        int id = -1;
        do {
            Map<Integer, IDeliverable> availableLoads = getAvailableLoads(carrier, resourceContainer.getLocoBase());
            printLoads(availableLoads);
            System.out.println("""
                    Enter either :
                    ID of the load to load it
                    all to load up the car with items from the list\s
                    0 to go back""");
            String input = scan.nextLine();
            if (input.equals("all")) {
                LoadAssignmentCenter.assignLoads(carrier, resourceContainer.getLocoBase());
                System.out.println("Car has been loaded up!");
                return;
            }
            id = resourceContainer.parseToInt(input);
            IDeliverable load = availableLoads.get(id);
            if (load != null) {
                carrier.load(load);
            } else if(id != 0){
                System.out.println("Incorrect ID " + id);
            }
        } while (id != 0);
    }

    private void allLoadsMenu(ILoadCarrier<IDeliverable> carrier){
        int id = -1;
        do {
            Map<Integer, IDeliverable> allLoads = resourceContainer
                    .getLocoBase()
                    .getLoadList()
                    .stream()
                    .filter(l -> !l.isLoaded())
                    .collect(Collectors.toMap(IDeliverable::getId, Function.identity()));

            printLoads(allLoads);
            System.out.println("""
                    Enter either :
                    load ID to try to load it
                    0 to go back""");
            String input = scan.nextLine();
            id = resourceContainer.parseToInt(input);
            IDeliverable load = allLoads.get(id);
            if (load != null) {
                boolean loaded = carrier.load(load);
                if (loaded) {
                    System.out.println("Load has been loaded");
                } else {
                    System.out.println("Load has not been loaded");
                }
                System.out.println("Press Enter to continue browsing");
                scan.nextLine();
            } else if(id != 0){
                System.out.println("Incorrect ID " + id);
            }
        } while (id != 0);
    }

    private void printLoads(Map<Integer, IDeliverable> loads){
        if (loads.isEmpty()){
            System.out.println("No available loads");
            return;
        }
        int n = 1;
        for (IDeliverable ava : loads.values()){
            System.out.println(n + ". " + LoadInfo.getBasicInfo(ava));
            n++;
        }
    }

    public Map<Integer, IDeliverable> getAvailableLoads(ILoadCarrier<IDeliverable> carrier, LocoBase locoBase){
        return locoBase
                .findSuitableLoads(carrier)
                .stream()
                .collect(Collectors.toMap(IDeliverable::getId, Function.identity()));
    }

    @Override
    public void listElements() {
        LocoBase locoBase = resourceContainer.getLocoBase();
        List<ILoadCarrier<IDeliverable>> loadCarriers = locoBase.getLoadCarriers();
        if (loadCarriers.isEmpty()) {
            System.out.println("No load carriers registered in the system !");
            return;
        }
        System.out.println(CarInfo.getAggregatedBasicInfo(loadCarriers));
    }

    @Override
    public Optional<ILoadCarrier<IDeliverable>> matchElement(String input) {
        int id = resourceContainer.parseToInt(input);
        return resourceContainer.getLocoBase().findLoadCarrier(id);
    }
}
