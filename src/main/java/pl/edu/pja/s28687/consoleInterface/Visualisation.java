package pl.edu.pja.s28687.consoleInterface;
import pl.edu.pja.s28687.gui.Canvas;

public class Visualisation extends AbstractLeafMenu{
    @Override
    public void menuSpecificAction() {
        Canvas canvas = new Canvas(resourceContainer.getLocoBase());
        canvas.start();
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
