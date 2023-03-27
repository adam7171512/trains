package pl.edu.pja.s28687.ConsoleInterface.ObjectCreationMenus;


import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.Logistics.Coordinates;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.math.BigDecimal;
import java.util.Scanner;

public class LocomotiveCreationMenu {

    public static void createLocomotive(LocoBase locoBase){
        make(locoBase);
    }

    private static void make(LocoBase locoBase){
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
        Locomotive locomotive = new Locomotive(name, maxCars, freight, maxPoweredCars, speed, LocoBase.getInstance());
    }
}
