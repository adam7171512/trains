package pl.edu.pja.s28687;

import pl.edu.pja.s28687.consoleInterface.ConsoleInterface;

public class Main {
    /**
     * Please select "Presentation" option in the menu to see the presentation
     * 1 pixel = 1 km.
     *
     * For more dynamic presentation :
     * Train speed time update interval and distance multiplier can be changed in the dispatch menu:
     *
     * 4. Dispatching, route manipulations and dynamic settings -> automatic -> Change time intervals and...
     *
     * Tests are provided instead of presentation.java file.
     *
     * Logging level was set to SEVERE by default, so it won't spam the console.
     * You can change that option in the logging menu.
     *
     * Logging to Appstate.txt is set to OFF by default, so it won't spam the file.
     * You can change that option in the logging menu.
     */
    public static void main(String[] args) {
        ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.show();
    }
}