package pl.edu.pja.s28687.consoleInterface;

public class ObjectCreationMenu2 extends Menu{
    static String name = "Object Creation Menu";
    static String description = "Object Creation Menu";

    public ObjectCreationMenu2() {
        super(name, description);
    }

    @Override
    public void createSubMenus() {
        addSubMenu(new IndividualObjectCreationMenu());
        addSubMenu(new BulkObjectCreationMenu());
    }
}
