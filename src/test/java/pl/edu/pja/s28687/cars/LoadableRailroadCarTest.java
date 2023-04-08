package pl.edu.pja.s28687.cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.factories.CarBuilder;
import pl.edu.pja.s28687.factories.CarsFactory;
import pl.edu.pja.s28687.factories.LoadBuilder;
import pl.edu.pja.s28687.factories.LoadFactory;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.load.LiquidLoad;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoadableRailroadCarTest {
    private LocoBase locoBase;
    private CarsFactory carsFactory;
    private CarBuilder carBuilder;
    private LoadFactory loadFactory;
    private LoadBuilder loadBuilder;

    @BeforeEach
    void setUp() {
        locoBase = new LocoBase();
        carsFactory = new CarsFactory(locoBase);
        carBuilder = new CarBuilder(locoBase);
        loadFactory = new LoadFactory(locoBase);
        loadBuilder = new LoadBuilder(locoBase);

    }

    @Test
    void load() {
        LiquidLoadCar car = (LiquidLoadCar) carsFactory.createCarOfType(CarType.LIQUID);
        LiquidLoad load = (LiquidLoad) loadFactory.createLoad(LoadType.LIQUID);
        car.load(load);
        assertEquals(load, car.getLoads().get(0));
        IDeliverable load2 =  loadFactory.createLoad(LoadType.TOXIC);
        LiquidLoad load3 = (LiquidLoad) loadBuilder.setWeight(999999).setFlags(Set.of(LoadType.LIQUID)).build();
        car.load(load3);
        assertFalse(car.getLoads().contains(load3));
        LiquidLoad load4 = (LiquidLoad) loadBuilder.setWeight(1).setFlags(Set.of(LoadType.LIQUID)).build();
        car.load(load4);
        assertTrue(car.getLoads().contains(load4));
    }

    @Test
    void unLoad() {
        LiquidLoadCar car = (LiquidLoadCar) carsFactory.createCarOfType(CarType.LIQUID);
        LiquidLoad load = (LiquidLoad) loadFactory.createLoad(LoadType.LIQUID);
        car.load(load);
        assertTrue(car.getLoads().contains(load));
        car.unLoad(load);
        assertFalse(car.getLoads().contains(load));
    }

    @Test
    void getCurrentWeight() {
        LiquidLoadCar car = (LiquidLoadCar) carsFactory.createCarOfType(CarType.LIQUID);
        LiquidLoad load = (LiquidLoad) loadFactory.createLoad(LoadType.LIQUID);
        car.load(load);
        assertEquals(load.getWeight().intValue(), car.getCurrentWeight().subtract(car.getNetWeight()).intValue());
    }

    @Test
    void validateWeight() {
        LiquidLoadCar car = (LiquidLoadCar) carsFactory.createCarOfType(CarType.LIQUID);
        LiquidLoad load = (LiquidLoad) loadFactory.createLoad(LoadType.LIQUID);
        assertTrue(car.validateWeight(load));
        LiquidLoad load2 = (LiquidLoad) loadBuilder.setWeight(999999).setFlags(Set.of(LoadType.LIQUID)).build();
        assertFalse(car.validateWeight(load2));
    }

    @Test
    void validateFlags() {
        LiquidLoadCar car = (LiquidLoadCar) carsFactory.createCarOfType(CarType.LIQUID);
        IDeliverable load =  loadFactory.createLoad(LoadType.LIQUID);
        assertTrue(car.validateFlags(load));
        IDeliverable load2 =  loadFactory.createLoad(LoadType.TOXIC);
        assertFalse(car.validateFlags(load2));
        LoadableRailroadCar<?> car2 = (LoadableRailroadCar<?>) carsFactory.createCarOfType(CarType.LIQUID_TOXIC);
        assertTrue(car2.validateFlags(load2));
    }

    @Test
    void getAllowedLoadFlags() {
        LiquidLoadCar car = (LiquidLoadCar) carsFactory.createCarOfType(CarType.LIQUID);
        assertEquals(Set.of(LoadType.LIQUID), car.getAllowedLoadFlags());
        LoadableRailroadCar<?> car2 = (LoadableRailroadCar<?>) carsFactory.createCarOfType(CarType.LIQUID_TOXIC);
        assertEquals(Set.of(LoadType.LIQUID, LoadType.TOXIC), car2.getAllowedLoadFlags());
    }

    @Test
    void setAllowedFlags() {
        LiquidLoadCar car = (LiquidLoadCar) carsFactory.createCarOfType(CarType.LIQUID);
        car.setAllowedFlags(Set.of(LoadType.TOXIC));
        assertEquals(Set.of(LoadType.TOXIC), car.getAllowedLoadFlags());
    }


    @Test
    void getLoads() {
        LiquidLoadCar car = (LiquidLoadCar) carsFactory.createCarOfType(CarType.LIQUID);
        LiquidLoad load = (LiquidLoad) loadFactory.createLoad(LoadType.LIQUID);
        car.load(load);
        assertEquals(1, car.getLoads().size());
        assertEquals(load, car.getLoads().get(0));
    }

    @Test
    void validateLoad() {
        LiquidLoadCar car = (LiquidLoadCar) carsFactory.createCarOfType(CarType.LIQUID);
        LiquidLoad load = (LiquidLoad) loadFactory.createLoad(LoadType.LIQUID);
        assertTrue(car.validateLoad(load));
        LiquidLoad load2 = (LiquidLoad) loadBuilder.setWeight(999999).setFlags(Set.of(LoadType.LIQUID)).build();
        assertFalse(car.validateLoad(load2));
        IDeliverable load3 =  loadFactory.createLoad(LoadType.TOXIC);
        assertFalse(car.validateLoad(load3));
    }
}