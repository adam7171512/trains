package pl.edu.pja.s28687;

import pl.edu.pja.s28687.ConsoleInterface.MainMenu;
import pl.edu.pja.s28687.Logistics.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        MainMenu.menu(LocoBase.getInstance());

    }
}