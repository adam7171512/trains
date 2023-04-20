package pl.edu.pja.s28687.consoleInterface;
import pl.edu.pja.s28687.gui.LocoMap;

public class Visualisation extends AbstractLeafMenu{
    @Override
    public void menuSpecificAction() {
        LocoMap canvas = LocoMap.getInstance(resourceContainer.getLocoBase());
        canvas.show();
    }
    @Override
    public String getTitle() {
        return "Map";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
