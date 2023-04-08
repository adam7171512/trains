package pl.edu.pja.s28687.consoleInterface;

public interface IMenu {
    void menuFlow();
    void setParentMenu(IMenu menu);
    String getTitle();
    String getDescription();

    void printMenu();

    void menuSpecificAction();
}
