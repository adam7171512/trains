package pl.edu.pja.s28687.cars;

import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.ValidationException;
import pl.edu.pja.s28687.factories.CarsFactory;
import pl.edu.pja.s28687.factories.LoadFactory;
import pl.edu.pja.s28687.load.BasicFreightLoad;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.LiquidToxicLoad;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.validators.CarFreightValidator;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoadableRailroadCarTest {


    private static final LocoBase LOCO_BASE = new LocoBase();
    private static final ICarLoadValidator LOAD_VALIDATOR = new CarFreightValidator();
    private static final LoadFactory LOAD_FACTORY = new LoadFactory(LOCO_BASE);
    private static final CarsFactory CARS_FACTORY = new CarsFactory(LOCO_BASE);

    @Test
    void load_BasicFreight_ToBasicFreightCar_WhenBelowWeightLimit_ShouldReturnTrue() {
        BasicFreightCar car = new BasicFreightCar(1, LOAD_VALIDATOR);
        BasicFreightLoad load = new BasicFreightLoad(1);
        assertTrue(car.getCurrentWeight().add(load.getWeight()).compareTo(car.getMaxWeight()) <= 0);
        assertTrue(car.load(load));
    }

    @Test
    void load_BasicFreight_ToBasicFreightCar_WhenAboveWeightLimit_ShouldThrowValidationException() {
        BasicFreightCar car = new BasicFreightCar(1, LOAD_VALIDATOR);
        BasicFreightLoad load = new BasicFreightLoad(20000000);
        assertTrue(car.getCurrentWeight().add(load.getWeight()).compareTo(car.getMaxWeight()) > 0);
        assertThrows(ValidationException.class, () -> car.load(load));
    }

    @Test
    void load_HeavyFreight_ToBasicFreightCar_ShouldThrowValidationException() {
        // used generic methods disallow us to directly load concrete incompatible load types to concrete car types
        ILoadCarrier<IDeliverable> car = (ILoadCarrier<IDeliverable>) CARS_FACTORY.createCarOfType(CarType.BASIC_FREIGHT);
        IDeliverable load = LOAD_FACTORY.createRandomLoadOfType(LoadType.HEAVY_FREIGHT);
        assertTrue(car.getCurrentWeight().add(load.getWeight()).compareTo(car.getMaxWeight()) < 0);
        assertThrows(ValidationException.class, () -> car.load(load));
    }

    @Test
    void load_BasicFreight_ToHeavyFreightCar_ShouldThrowValidationException() {
        // used generic methods disallow us to directly load concrete incompatible load types to concrete car types
        ILoadCarrier<IDeliverable> car = (ILoadCarrier<IDeliverable>) CARS_FACTORY.createCarOfType(CarType.HEAVY_FREIGHT);
        IDeliverable load = LOAD_FACTORY.createRandomLoadOfType(LoadType.BASIC_FREIGHT);
        assertTrue(car.getCurrentWeight().add(load.getWeight()).compareTo(car.getMaxWeight()) < 0);
        assertThrows(ValidationException.class, () -> car.load(load));
    }

    @Test
    void load_LiquidLoad_ToHeavyFreightCar_ShouldThrowValidationException() {
        // used generic methods disallow us to directly load concrete incompatible load types to concrete car types
        ILoadCarrier<IDeliverable> car = (ILoadCarrier<IDeliverable>) CARS_FACTORY.createCarOfType(CarType.HEAVY_FREIGHT);
        IDeliverable load = LOAD_FACTORY.createRandomLoadOfType(LoadType.LIQUID);
        assertTrue(car.getCurrentWeight().add(load.getWeight()).compareTo(car.getMaxWeight()) < 0);
        assertThrows(ValidationException.class, () -> car.load(load));
    }

    @Test
    void load_LiquidToxicLoad_ToLiquidLoadCar_ShouldThrowValidationException() {
        // used generic methods disallow us to directly load concrete incompatible load types to concrete car types
        ILoadCarrier<IDeliverable> car = (ILoadCarrier<IDeliverable>) CARS_FACTORY.createCarOfType(CarType.LIQUID);
        IDeliverable load = LOAD_FACTORY.createRandomLoadOfType(Set.of(LoadType.LIQUID, LoadType.TOXIC));
        assertThrows(ValidationException.class, () -> car.load(load));

    }

    @Test
    void load_LiquidToxicLoad_ToLiquidToxicLoadCar_ShouldReturnTrue() {
        LiquidToxicLoadCar car = new LiquidToxicLoadCar(2, LOAD_VALIDATOR);
        LiquidToxicLoad load = new LiquidToxicLoad(1, 2);
        assertTrue(car.getCurrentWeight().add(load.getWeight()).compareTo(car.getMaxWeight()) < 0);
        assertTrue(car.load(load));
    }

    @Test
    void load_LiquidLoad_ToLiquidToxicLoadCar_ShouldThrowValidationException() {
        // used generic methods disallow us to directly load concrete incompatible load types to concrete car types
        ILoadCarrier<IDeliverable> car = (ILoadCarrier<IDeliverable>) CARS_FACTORY.createCarOfType(CarType.LIQUID_TOXIC);
        IDeliverable load = LOAD_FACTORY.createRandomLoadOfType(Set.of(LoadType.LIQUID));
        assertThrows(ValidationException.class, () -> car.load(load));
    }

    @Test
    void unload_LoadedLoad_ShouldReturnTrue() {
        BasicFreightCar car = new BasicFreightCar(1, LOAD_VALIDATOR);
        BasicFreightLoad load = new BasicFreightLoad(1);
        assertTrue(car.getCurrentWeight().add(load.getWeight()).compareTo(car.getMaxWeight()) <= 0);
        assertTrue(car.load(load));
        assertTrue(car.unLoad(load));
    }

    @Test
    void unload_NotLoadedLoad_ShouldThrowValidationException() {
        BasicFreightCar car = new BasicFreightCar(1, LOAD_VALIDATOR);
        BasicFreightLoad load = new BasicFreightLoad(1);
        assertThrows(ValidationException.class, () -> car.unLoad(load));
    }

    @Test
    void unload_LoadedLoad_ShouldRemoveItFromLoadList() {
        BasicFreightCar car = new BasicFreightCar(1, LOAD_VALIDATOR);
        BasicFreightLoad load = new BasicFreightLoad(1);
        assertTrue(car.getCurrentWeight().add(load.getWeight()).compareTo(car.getMaxWeight()) <= 0);
        assertTrue(car.load(load));
        assertTrue(car.getLoads().contains(load));
        assertTrue(car.unLoad(load));
        assertFalse(car.getLoads().contains(load));
    }
}