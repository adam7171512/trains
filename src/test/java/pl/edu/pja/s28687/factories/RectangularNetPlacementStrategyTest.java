package pl.edu.pja.s28687.factories;

import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.logistics.Coordinates;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RectangularNetPlacementStrategyTest {

    private static final RectangularNetPlacementStrategy strategy = new RectangularNetPlacementStrategy();

    @Test
    void createCoordinates_ValidQuantityN_ShouldCreateListOfNCoordinates() {
        for (int i = 1; i < 27; i++) {
            List<Coordinates> coordinates = strategy.createCoordinates(i, 100, 100);
            assertEquals(i, coordinates.size());
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