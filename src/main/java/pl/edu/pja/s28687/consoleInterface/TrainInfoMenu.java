package pl.edu.pja.s28687.consoleInterface;

import java.util.Map;
import java.util.TreeMap;

public class TrainInfoMenu extends Menu{

    protected String name;
    protected String description;
    protected Menu parentMenu;

    protected Map<Integer, Menu> subMenus = new TreeMap<>();

    public TrainInfoMenu(String name, String description) {
        super(name, description);
        this.name = name;
        this.description = description;
        this.parentMenu = null;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public void addSubMenu(Menu menu){
        subMenus.put(subMenus.size() + 1, menu);
        menu.setParentMenu(this);
    }

    public void createSubMenus(){
        subMenus.put(1, new TrainInfoMenu("1", "Informacje o pociągu"));
        subMenus.put(2, new TrainInfoMenu("2", "Informacje o wagonach"));
        subMenus.put(3, new TrainInfoMenu("3", "Informacje o lokomotywie"));
        subMenus.put(4, new TrainInfoMenu("4", "Informacje o kierowcy"));
        subMenus.put(5, new TrainInfoMenu("5", "Informacje o pasażerach"));
    }

    public void menuAction(){
        System.out.println(description);
        createSubMenus();
        for (Map.Entry<Integer, Menu> entry : subMenus.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getDescription());
        }
        System.out.println("0. Powrót do menu głównego");
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
