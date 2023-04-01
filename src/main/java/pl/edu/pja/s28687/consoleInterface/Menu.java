package pl.edu.pja.s28687.consoleInterface;

import java.util.*;

public abstract class Menu {

    protected String name;
    protected String description;
    protected Menu parentMenu;

    protected Map<Integer, Menu> subMenus = new TreeMap<>();

    protected Scanner scan = new Scanner(System.in);

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
        this.parentMenu = null;
        createSubMenus();
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public void addSubMenu(Menu menu){
        subMenus.put(subMenus.size() + 1, menu);
        menu.setParentMenu(this);
    }

    public void show(){
        System.out.println(description);
        for (Map.Entry<Integer, Menu> entry : subMenus.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getDescription());
        }
    }

    public void menuAction(){
        System.out.println(description);
        for (Map.Entry<Integer, Menu> entry : subMenus.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getDescription());
        }
        System.out.println("0. Back to previous menu");
        String input = scan.nextLine();
        handleInput(input);
    }

    public void handleInput(String input){
            int selection = Integer.parseInt(input);
            if (selection == 0){
                parentMenu.menuAction();
            }
            else {
                Menu menu = subMenus.get(selection);
                menu.menuAction();
            }
        }


    public abstract void createSubMenus();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }
}
