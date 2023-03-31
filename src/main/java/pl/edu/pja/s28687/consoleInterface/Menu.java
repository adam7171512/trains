package pl.edu.pja.s28687.consoleInterface;

public abstract class Menu {
    protected String name;
    protected String description;
    protected Menu parentMenu;

    public Menu(String name, String description, Menu parentMenu) {
        this.name = name;
        this.description = description;
        this.parentMenu = parentMenu;
    }

    public abstract void show();

    public abstract void handleInput(String input);

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
