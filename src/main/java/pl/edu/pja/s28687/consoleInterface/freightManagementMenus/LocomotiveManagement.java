package pl.edu.pja.s28687.consoleInterface.freightManagementMenus;

import pl.edu.pja.s28687.train.Locomotive;
import pl.edu.pja.s28687.validators.ValidationException;
import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.consoleInterface.IBrowsable;
import pl.edu.pja.s28687.factories.CarAssignmentCenter;
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
            locomotive = preProcessInput(scan, """
                    Please enter : \s
                    locomotive ID\s
                    ls to list out locomotives\s
                    0 to exit""");
            locomotive.ifPresentOrElse(
                    loc -> {
                        String s ="""
                    Please select option to browse : \s
                    1. Cars attached to this locomotive\s
                    2. Cars validated for attaching to this locomotive\s
                    3. All Free cars from LocoBase\s
                    0 to exit""";
                        System.out.println(s);
                        int selection = resourceContainer.parseToInt(scan.nextLine());
                        switch (selection) {
                            case 1 -> loadedCarsMenu(loc);
                            case 2 -> availableCarsMenu(loc);
                            case 3 -> allCarsMenu(loc);
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
            System.out.println(n + ". " + loaded.getBasicInfo());
            n++;
        }
    }

    private void availableCarsMenu(Locomotive loc){
        int id;
        do {
            Map<Integer, IRailroadCar> availableCars = getAvailableCars(loc, resourceContainer.getLocoBase());
            printCars(availableCars);
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
            id = resourceContainer.parseToInt(input);
            IRailroadCar car = availableCars.get(id);
            if (car != null) {
                boolean attached = false;
                try {
                    attached = loc.attach(car);
                } catch (ValidationException e) {
                    System.err.println(e.getMessage());
                }
                if (attached) {
                    System.out.println("Car attached");
                } else {
                    System.out.println("Car not attached");
                }
                System.out.println("Press Enter to continue browsing");
                scan.nextLine();
            } else if(id != 0){
                System.out.println("Incorrect ID " + id);
            }
        } while (id != 0);
    }

    private void allCarsMenu(Locomotive loc){
        int id;
        do {
            Map<Integer, IRailroadCar> allCars = getAllCars(loc, resourceContainer.getLocoBase());
            printCars(allCars);
            System.out.println("""
                    Enter either :
                    Car ID to try to attach it
                    0 to go back""");
            String input = scan.nextLine();
            id = resourceContainer.parseToInt(input);
            IRailroadCar car = allCars.get(id);
            if (car != null) {
                boolean attached= false;
                try {
                    attached = loc.attach(car);
                } catch (ValidationException e) {
                    System.err.println(e.getMessage());
                }
                if (attached) {
                    System.out.println("Car attached");
                } else {
                    System.out.println("Car not attached");
                }
                System.out.println("Press Enter to continue browsing");
                scan.nextLine();
            } else if(id != 0){
                System.out.println("Incorrect ID " + id);
            }
        } while (id != 0);
    }

    private void printCars(Map<Integer, IRailroadCar> cars){
        if (cars.isEmpty()){
            System.out.println("No available cars");
            return;
        }
        int n = 1;
        for (IRailroadCar ava : cars.values()){
            System.out.println(n + ". " + ava.getBasicInfo());
            n++;
        }
    }

    public Map<Integer, IRailroadCar> getAvailableCars(Locomotive loc, LocoBase locoBase){
        return locoBase
                        .findSuitableCars(loc)
                        .stream()
                        .collect(Collectors.toMap(IRailroadCar::getId, Function.identity()));
    }

    public Map<Integer, IRailroadCar> getAllCars(Locomotive loc, LocoBase locoBase){
        return locoBase
                .getRailroadCarsList()
                .stream()
                .filter(car -> !car.isAttached())
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
