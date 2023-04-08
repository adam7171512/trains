package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.ResourceContainer;

import java.util.Optional;
import java.util.Scanner;

public interface IBrowsable<T> {
    void listElements();
    Optional<T> matchElement(String input);
    default Optional<T> preProcessInput(Scanner scan, String message){
        boolean validated = false;
        Optional<T> result = Optional.empty();
        do {
            System.out.println(message);
            String input = scan.nextLine();
            if (input.equals("ls")) {
                listElements();
            } else if (input.equals("0")) {
                validated = true;
            } else {
                result = matchElement(input);
                if (result.isEmpty()) {
                    System.out.println("No such element");
                }
            }
        } while (!validated && result.isEmpty());
        return result;
    }
}
