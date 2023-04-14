package pl.edu.pja.s28687.factories;

import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.logistics.Coordinates;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RandomPlacementStrategyTest {

    private static final RandomPlacementStrategy strategy = new RandomPlacementStrategy();

    @Test
    void createCoordinates_ValidArgumentNStations_ShouldCreateListOfNCoordinates() {
        for (int i = 1; i < 27; i++) {
            List<Coordinates> coordinates = strategy.createCoordinates(i, 100, 100);
            assertEquals(i, coordinates.size());
        }
    }

    @Test
    void createCoordinates_InvalidBounds_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> strategy.createCoordinates(5, -100, 100));
    }

    @Test
    void createCoordinates_NegativeQuantity_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> strategy.createCoordinates(-5, 100, 100));
    }

    @Test
    void createCoordinates_QuantityZero_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> strategy.createCoordinates(-5, 100, 100));
    }
}