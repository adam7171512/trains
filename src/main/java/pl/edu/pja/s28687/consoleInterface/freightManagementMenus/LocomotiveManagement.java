package pl.edu.pja.s28687.consoleInterface.freightManagementMenus;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.consoleInterface.IBrowsable;
import pl.edu.pja.s28687.factories.CarAssignmentCenter;
import pl.edu.pja.s28687.info.CarInfo;
import pl.edu.pja.s28687.info.LocInfo;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LocomotiveManagement extends AbstractLeafMenu implements IBrowsable<Locomotive> {
    @Override
    public void menuSpecificAction() {

        Optional<Locomotive> locomotive;
        do {
            locomotive = preProcessInput(scan, "Please enter locomotive ID, ls to list out locomotives or 0 to exit");
            locomotive.ifPresentOrElse(
                    loc -> {
                        String s = new StringBuilder().append("1. See attached cars\n")
                                .append("2. See cars available for this locomotive\n")
                                .toString();
                        System.out.println(s);
                        int selection = resourceContainer.parseToInt(scan.nextLine());
                        switch (selection) {
                            case 1 -> loadedCarsMenu(loc);
                            case 2 -> availableCarsMenu(loc);
                            default -> System.out.println("Wrong input");
                        }
                    },
                    () -> System.out.println("No locomotive with this ID "));
        } while (locomotive.isPresent());
    }

    @Override
    public String getTitle() {
        return "Locomotives";
    }

    @Override
    public String getDescription() {
        return "";
    }

    private void loadedCarsMenu(Locomotive loc){
        int id;
        do {
            Map<Integer, IRailroadCar> loadedCars =
                    loc.getCars()
                            .stream()
                            .collect(Collectors.toMap(IRailroadCar::getId, Function.identity()));
            System.out.println("Loaded cars:");
            printLoadedCars(loadedCars);
            System.out.println("Enter Car Id to detach or 0 to go back");
            id = resourceContainer.parseToInt(scan.nextLine());
            IRailroadCar car = loadedCars.get(id);
            if (car != null) {
                loc.detach(car);
            } else if(id != 0) {
                System.out.println("Incorrect ID " + id);
            }
        } while (id != 0);
    }

    private void printLoadedCars(Map<Integer, IRailroadCar> loadedCars){
        if (loadedCars.isEmpty()){
            System.out.println("No loaded cars");
            return;
        }
        int n = 1;
        for (IRailroadCar loaded : loadedCars.values()){
            System.out.println(n + ". " + CarInfo.getBasicInfo(loaded));
            n++;
        }
    }

    private void availableCarsMenu(Locomotive loc){
        int id;
        do {
            Map<Integer, IRailroadCar> availableCars = getAvailableCars(loc, resourceContainer.getLocoBase());
            printAvailableCars(availableCars);
            System.out.println("""
                    Enter either :
                    Car ID to attach it
                    all to attach all possible cars from the list\s
                    0 to go back""");
            String input = scan.nextLine();
            if (input.equals("all")) {
                CarAssignmentCenter.assignCarsToLocomotive(loc, resourceContainer.getLocoBase());
                System.out.println("All possible cars attached");
                return;
            }
            id = resourceContainer.parseToInt(scan.nextLine());
            IRailroadCar car = availableCars.get(id);
            if (car != null) {
                loc.attach(car);
            } else if(id != 0){
                System.out.println("Incorrect ID " + id);
            }
        } while (id != 0);
    }

    private void printAvailableCars(Map<Integer, IRailroadCar> availableCars){
        if (availableCars.isEmpty()){
            System.out.println("No available cars");
            return;
        }
        int n = 1;
        for (IRailroadCar ava : availableCars.values()){
            System.out.println(n + ". " + CarInfo.getBasicInfo(ava));
            n++;
        }
    }

    public Map<Integer, IRailroadCar> getAvailableCars(Locomotive loc, LocoBase locoBase){
        return locoBase
                        .findSuitableCars(loc)
                        .stream()
                        .collect(Collectors.toMap(IRailroadCar::getId, Function.identity()));
    }

    @Override
    public void listElements() {
        LocoBase locoBase = resourceContainer.getLocoBase();
        List<Locomotive> locomotives = locoBase.getLocomotiveList();
        if (locomotives.isEmpty()) {
            System.out.println("No locomotives registered in the system !");
            return;
        }
        System.out.println(LocInfo.getAggregatedBasicInfo(locomotives));
    }

    @Override
    public Optional<Locomotive> matchElement(String input) {
        int id = resourceContainer.parseToInt(input);
        return resourceContainer.getLocoBase().findLoc(id);
    }
}
