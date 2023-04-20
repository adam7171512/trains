package pl.edu.pja.s28687.consoleInterface;

import java.util.Scanner;

public abstract class AbstractLeafMenu implements ILeafMenu {
    protected ResourceContainer resourceContainer;
    private IMenu parentMenu;
    protected Scanner scan;

    public AbstractLeafMenu(){
        scan = new Scanner(System.in);
    }

    @Override
    public void menuFlow(){
        printMenu();
        menuSpecificAction();
        parentMenu.menuFlow();
    }
    @Override
    public abstract void menuSpecificAction();
    @Override
    public void setParentMenu(IMenu menu) {
        this.parentMenu = menu;
    }
    @Override
    public void printMenu() {
        System.out.println(getTitle());
        System.out.println(getDescription());
    }
    @Override
    public abstract String getDescription();

    @Override
    public void setResourceContainer(ResourceContainer resourceContainer) {
        this.resourceContainer = resourceContainer;
    }
}
