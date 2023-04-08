package pl.edu.pja.s28687;

import pl.edu.pja.s28687.consoleInterface.MainMenu;
import pl.edu.pja.s28687.consoleInterface.MenuFactory;
import pl.edu.pja.s28687.logistics.LocoBase;

public class Main {
    public static void main(String[] args) {
        MenuFactory mf = new MenuFactory();
        mf.create();
        mf.show();






    }
}