package pl.edu.pja.s28687.consoleInterface;
import pl.edu.pja.s28687.gui.Canvas2;

public class Visualisation extends AbstractLeafMenu{
    @Override
    public void menuSpecificAction() {
        Canvas2 canvas = Canvas2.getInstance(resourceContainer.getLocoBase());
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
