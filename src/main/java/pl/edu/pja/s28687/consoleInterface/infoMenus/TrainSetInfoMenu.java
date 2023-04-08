package pl.edu.pja.s28687.consoleInterface.infoMenus;

import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.consoleInterface.IBrowsable;
import pl.edu.pja.s28687.info.TrainSetInfo;

import java.util.Optional;

public class TrainSetInfoMenu extends AbstractLeafMenu implements IBrowsable<TrainSet> {
    @Override
    public void menuSpecificAction() {

        String message = "Please enter train set ID, ls to list out train sets or 0 to exit";
        Optional<TrainSet> ts;
        do {
            ts = preProcessInput(scan, message);
            ts.ifPresentOrElse(
                    trainSet -> System.out.println(TrainSetInfo.getTrainSetInfo(trainSet)),
                    () -> System.out.println("No train set with this ID "));
        } while (ts.isPresent());
    }

    @Override
    public String getTitle() {
        return "Train Set info";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void listElements() {
        String s = TrainSetInfo.getAggregatedBasicTrainSetsInfo(resourceContainer.getLocoBase());
        if (s.isEmpty()) {
            System.out.println("No train sets registered in the system !");
        } else {
            System.out.println(s);
        }
    }

    @Override
    public Optional<TrainSet> matchElement(String input) {
        int id = resourceContainer.parseToInt(input);
        return resourceContainer.getLocoBase().findTrainSet(id);
    }
}
