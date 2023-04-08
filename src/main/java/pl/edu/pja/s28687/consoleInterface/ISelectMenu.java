package pl.edu.pja.s28687.consoleInterface;

import java.util.List;

public interface ISelectMenu extends IMenu {
    void printSubMenus();
    void setSubMenus(List<IMenu> subMenus);
    void printGoBackOption();
    int readinput();
    void handleInput(int input);
    void addSubMenu(IMenu menu);
}
