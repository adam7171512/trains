package pl.edu.pja.s28687.consoleInterface;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public abstract class AbstractSelectMenu implements ISelectMenu {

    protected IMenu parentMenu;
    protected Map<Integer, IMenu> subMenus = new TreeMap<>();

    protected Scanner scan;

    public abstract String getTitle();
    public abstract String getDescription();

    public AbstractSelectMenu(){
        scan = new Scanner(System.in);
    }

    @Override
    public void menuFlow() {
        printMenu();
        menuSpecificAction();
    }

    public void addSubMenu(IMenu menu){
        subMenus.put(subMenus.size() + 1, menu);
        menu.setParentMenu(this);
    }

    public void printMenu(){
        System.out.println(getTitle());
        String spacer = "-".repeat(getTitle().length());
        System.out.println(spacer);
        if (getDescription() != null && !getDescription().isEmpty()) {
            System.out.println(getDescription());
        }
        printSubMenus();
        if (parentMenu != null) {
            printGoBackOption();
        }
    }

    public void menuSpecificAction(){
        int input = readinput();
        handleInput(input);
    }

    @Override
    public void printSubMenus() {
        StringBuilder menuBuilder = new StringBuilder();
        for (Map.Entry<Integer, IMenu> entry : subMenus.entrySet()) {
            menuBuilder.append(entry.getKey()).append(". ").append(entry.getValue().getTitle()).append("\n");
//            System.out.println(entry.getKey() + ". " + entry.getValue().getTitle());
        }
        System.out.println(menuBuilder.toString());
    }

    public void setParentMenu(IMenu parentMenu) {
        this.parentMenu = parentMenu;
    }

    @Override
    public void setSubMenus(List<IMenu> submenus){
        submenus.forEach(this::addSubMenu);
    }


    @Override
    public void printGoBackOption() {
        System.out.println("0. Back to previous menu");
    }

    @Override
    public int readinput() {
        return ConsoleInterface.parseToInt(scan.nextLine());
    }

    @Override
    public void handleInput(int input) {
        IMenu menu = subMenus.get(input);
        if (menu != null){
            menu.menuFlow();
         }
        else if (parentMenu != null){
            parentMenu.menuFlow();
        }
        else {
            menuFlow();
        }
    }
}
