package pl.edu.pja.s28687.factories;

import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.logistics.Coordinates;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CircularPlacementStrategyTest {

    private static final CircularPlacementStrategy strategy = new CircularPlacementStrategy();

    @Test
    void createCoordinates_ValidQuantityN_ShouldCreateListOfNCoordinates() {
        for (int i = 1; i < 27; i++) {
            List<Coordinates> coordinates = strategy.createCoordinates(i, 100, 100);
            assertEquals(i, coordinates.size());
        }
    }

    @Test
    void createCoordinates_ValidQuantityN_ShouldCreateListOfCoordinatesWithinBounds() {
        List<Coordinates> coordinates = strategy.createCoordinates(121, 100, 100);
        for (Coordinates coordinate : coordinates) {
            assertTrue(coordinate.getX() >= 0 && coordinate.getX() <= 100);
            assertTrue(coordinate.getY() >= 0 && coordinate.getY() <= 100);
        }
    }

    @Test
    void createCoordinates_ValidQuantityNAndUnusualRatio_ShouldCreateListOfNCoordinates() {
        for (int i = 1; i < 27; i++) {
            List<Coordinates> coordinates = strategy.createCoordinates(i, 17, 319);
            assertEquals(i, coordinates.size());
        }
    }

    @Test
    void createCoordinates_NegativeQuantity_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> strategy.createCoordinates(-5, 100, 100));
    }

    @Test
    void createCoordinates_Quantity0_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> strategy.createCoordinates(0, 100, 100));
    }
}
