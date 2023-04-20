package pl.edu.pja.s28687;

import pl.edu.pja.s28687.consoleInterface.ConsoleInterface;

public class Main {
    /**
     * Please select "Presentation" option in the menu to see the presentation
     * 1 pixel = 1 km.
     * Time frequency and distance multiplier can be changed in the dispatch menu for more dynamic presentation.
     * Skipping presentations.java file, as tests are provided.
     * Logging is set to SEVERE by default, so it won't spam the console. You can change that option in the logging menu.
     * Logging to Appstate.txt is set to OFF by default, so it won't spam the file. You can change that option in the logging menu.
     */

    public static void main(String[] args) {
        ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.show();
    }
}