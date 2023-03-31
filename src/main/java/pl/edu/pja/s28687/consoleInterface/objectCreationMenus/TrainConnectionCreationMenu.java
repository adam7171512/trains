package pl.edu.pja.s28687.consoleInterface.objectCreationMenus;


import pl.edu.pja.s28687.factories.RailroadsFactory;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.logistics.RailroadLink;
import pl.edu.pja.s28687.TrainStation;

import java.util.Scanner;

public class TrainConnectionCreationMenu {

    private RailroadsFactory railroadsFactory;

    public TrainConnectionCreationMenu(RailroadsFactory railroadsFactory) {
        this.railroadsFactory = railroadsFactory;
    }


    //TODO: ispresent
    public void createConnection(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter first Train Station Name");
        String name1 = scan.next();
        System.out.println("Please enter second Train Station Name");
        String name2 = scan.next();
        try {
        railroadsFactory.createRailroadLink(name1, name2);
        } catch (IllegalArgumentException e)
        {
            System.err.println(e.getMessage());}
    }

}
