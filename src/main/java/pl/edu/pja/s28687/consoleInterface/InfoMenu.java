package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.info.LocInfo;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.info.TrainSetInfo;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InfoMenu {


    private static void printLocList(LocoBase locoBase){
        System.out.println("_______________________________");
        for(String s: getTrainSetsDescriptions(locoBase)) System.out.println(s);
        System.out.println("Enter locomotive ID to display more info or 0 to go back");
    }

    public static void menu(LocoBase locoBase){
        Scanner scan = new Scanner(System.in);
        int selection = 99;

        while (selection != 0){
            printLocList(locoBase);
            selection = scan.nextInt();
            trainSetInfoLoop(selection, locoBase);
            scan.nextInt();
        }
    }

    public static List<String> getTrainSetsDescriptions(LocoBase locoBase){
        List<TrainSet> trainSets = locoBase.getTrainSets();
        List<String> trainSetsDescriptions = new ArrayList<>();
        for (int i = 0; i < trainSets.size(); i++){
            TrainSet trainSet = trainSets.get(i);
            Locomotive loc = trainSet.getLocomotive();
            String trainSetDescr =
                    (i+1) + ". TrainSet ID: " + trainSet.getId() + " " +
                            loc.getLocName() + " " +
                            " cruising between " + loc.getSourceStation() +
                            " and " + loc.getDestStation() +
                            "\nCars occupied:" +
                            "\nregular " + loc.carsOccupied()+"/"+loc.getCarLimit() +
                            "\npowered: " + loc.getPoweredCarsNumber() + "/" + loc.getPoweredCarLimit() +
                            "\nFreight used  : " + loc.getCurrentPayload() + "/" + loc.getMaxPayload() + "tonnes" +
                            "\nPassengers on board:" + loc.passengersOnBoard() +
                            "\n_  _   _   _   _   _  _  _  _  _";
            trainSetsDescriptions.add(trainSetDescr);
        }
        return trainSetsDescriptions;
    }

    public static void trainSetInfoLoop(int selection, LocoBase locoBase){
        switch (selection) {
            case 0 -> System.out.println("lol");
            default -> {
                TrainSet trainSet = locoBase.findTrainSet(selection).get();
                System.out.println(TrainSetInfo.getTrainSetInfo(trainSet));
            }

        }
    }
}
