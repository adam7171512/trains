package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.Conductor;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.cars.AbstractRailroadCar;
import pl.edu.pja.s28687.info.AggregateLogger;
import pl.edu.pja.s28687.info.TrainSetInfo;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.logging.Level;

public class LoggingMenu extends AbstractLeafMenu{

    private final AggregateLogger logger;
    public LoggingMenu(AggregateLogger logger) {
        super();
        this.logger = logger;
    }

    @Override
    public void menuSpecificAction() {
        StringBuilder menu = new StringBuilder();
        if (logger.isLogging()) {
            menu.append("1. Stop logging train set reports to Appstate.txt");
        }
        else {
            menu.append("1. Start logging train set reports to Appstate.txt");
        }
        menu.append("\n2. Print current train set report");
        menu.append("\n3. Turn on logging everything on INFO level");
        menu.append("\n0. Back");
        System.out.println(menu);

        int selection;
        do {
            selection = resourceContainer.parseToInt(scan.nextLine());
        } while (selection < 0 || selection > 3);

        switch (selection) {
            case 1 -> {
                if (logger.isLogging()) {
                    logger.stopLogging();
                }
                else {
                    logger.startLogging();
                }
            }
            case 2 -> {
                System.out.println(TrainSetInfo.getAggregatedTrainSetsInfo(resourceContainer.getLocoBase()));
            }
            case 3 -> {
                AbstractRailroadCar.setLogLevel(Level.INFO);
                Locomotive.setLogLevel(Level.INFO);
                Conductor.setLogLevel(Level.INFO);
                LocoBase.setLogLevel(Level.INFO);
            }
            case 0 -> {}
        }
    }

    @Override
    public String getTitle() {
        return "Logging";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
