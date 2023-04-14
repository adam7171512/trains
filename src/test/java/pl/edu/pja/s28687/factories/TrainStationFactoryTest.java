package pl.edu.pja.s28687.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.logistics.Coordinates;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TrainStationFactoryTest {


    private TrainStationFactory factory;
    private LocoBase locoBase;

    @BeforeEach
    void setUp() {
        locoBase = new LocoBase();
        factory = new TrainStationFactory(locoBase);
    }

    @Test
    void createTrainStation_ShouldCreateAndRegisterTrainStation() {
        TrainStation station = factory
                .createTrainStation("testStation", new Coordinates(0, 0));
        assertNotNull(station);
        assertEquals(1, locoBase.getTrainStations().size());
        assertTrue(locoBase.getTrainStations().contains(station));
    }

    @Test
    void createTrainStationsPolishCoords_ShouldCreateAndRegister2271TrainStations() {
        Set<TrainStation> stations = new HashSet<>(factory.createTrainStationsPolishCoords());
        assertEquals(2271, stations.size());
        assertEquals(stations, locoBase.getTrainStations());
    }

    @Test
    void createRandomTrainStations_Argument5ShouldCreateAndRegister5Stations() {
        Set<TrainStation> stations = new HashSet<>(factory.createRandomTrainStations(5));
        assertEquals(5, stations.size());
        assertEquals(stations, locoBase.getTrainStations());
    }

    @Test
    void createRandomTrainStations_NegativeArgumentShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> factory.createRandomTrainStations(-1));
    }

    @Test
    void createRandomTrainStations_CalledWithStrategy_ShouldCreateAndRegisterTrainStations() {
        Set<TrainStation> stations =
                new HashSet<>(factory.createRandomTrainStations(
                        5, new RandomPlacementStrategy(), 100, 100));
        assertEquals(5, stations.size());
        assertEquals(stations, locoBase.getTrainStations());
    }

    @Test
    void createRandomTrainStations_CalledWithBounds_ShouldCreateTrainStationsWithinBounds() {
        List<TrainStation> stations = factory.createRandomTrainStations(
                10, new RandomPlacementStrategy(), 10, 10);
        assertTrue(stations.stream().allMatch(s -> s.getCoordinates().getX() < 10 &&
                s.getCoordinates().getY() < 10));
    }
}