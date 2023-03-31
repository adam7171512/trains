package pl.edu.pja.s28687.consoleInterface.objectCreationMenus;


import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.factories.LocomotiveFactory;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.util.Scanner;

public class LocomotiveCreationMenu {

    private LocomotiveFactory locomotiveFactory;

    public LocomotiveCreationMenu(LocomotiveFactory locomotiveFactory) {
        this.locomotiveFactory = locomotiveFactory;
    }

    public void createLocomotive(){
        make();
    }

    private void make(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter Locomotive name");
        String name = scan.next();
        System.out.println("Please enter maximum number of cars attachable");
        int maxCars = scan.nextInt();
        System.out.println("Please enter maximum allowable payload in tonnes");
        BigDecimal freight = BigDecimal.valueOf(scan.nextDouble());
        System.out.println("Please enter maximum number of cars with power supply");
        int maxPoweredCars = scan.nextInt();
        System.out.println("Please enter locomotive speed in km/h");
        BigDecimal speed = BigDecimal.valueOf(scan.nextDouble());
        locomotiveFactory
                .makeLocomotive(name, maxCars, maxPoweredCars, freight, speed);
    }
}
