package pl.edu.pja.s28687.factories;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class LoadFactory {

    private LocoBase locoBase;

    public LoadFactory(LocoBase locoBase){
        this.locoBase = locoBase;
    }

    public IDeliverable createPassengerLoad(int quantity){
        IDeliverable load = new Passengers(locoBase.getIdForLoad(), quantity);
        locoBase.registerLoad(load);
        return load;
    }
    public IDeliverable createBasicFreightLoad(double weight){
        IDeliverable load = new BasicFreightLoad(locoBase.getIdForLoad(), weight);
        locoBase.registerLoad(load);
        return load;
    }
    public IDeliverable createBasicFreightLoad(double weight, int count){
        IDeliverable load = new BasicFreightLoad(locoBase.getIdForLoad(), weight, count);
        locoBase.registerLoad(load);
        return load;
    }
    public IDeliverable createBasicFreightLoad(double weight, int count, boolean fragile, boolean highValue) {
        IDeliverable load = new BasicFreightLoad(locoBase.getIdForLoad(), weight, count, fragile, highValue);
        locoBase.registerLoad(load);
        return load;
    }
    public IDeliverable createHeavyFreightLoad(double weight){
        IDeliverable load = new HeavyFreightLoad(locoBase.getIdForLoad(), weight);
        locoBase.registerLoad(load);
        return load;
    }
    public IDeliverable createHeavyFreightLoad(double weight, boolean fragile, boolean highValue){
        IDeliverable load = new HeavyFreightLoad(locoBase.getIdForLoad(), weight, fragile, highValue);
        locoBase.registerLoad(load);
        return load;
    }
    public IDeliverable createLiquidLoad(double weight, double volume){
        IDeliverable load = new LiquidLoad(locoBase.getIdForLoad(), weight, volume);
        locoBase.registerLoad(load);
        return load;
    }
    public IDeliverable createLiquidToxicLoad(double weight, double volume, double combustionTemperature){
        IDeliverable load = new LiquidToxicLoad(locoBase.getIdForLoad(), weight, volume, combustionTemperature);
        locoBase.registerLoad(load);
        return load;
    }

    public IDeliverable createToxicLoad(double weight){
        IDeliverable load = new ToxicLoad(locoBase.getIdForLoad(), weight);
        locoBase.registerLoad(load);
        return load;
    }

    public IDeliverable createToxicLoad(double weight, boolean flammable){
        IDeliverable load = new ToxicLoad(locoBase.getIdForLoad(), weight, flammable);
        locoBase.registerLoad(load);
        return load;
    }

    public IDeliverable createExplosiveLoad(double weight){
        IDeliverable load = new ExplosiveLoad(locoBase.getIdForLoad(), weight);
        locoBase.registerLoad(load);
        return load;
    }

    public IDeliverable createExplosiveLoad(double weight, boolean sensitive){
        IDeliverable load = new ExplosiveLoad(locoBase.getIdForLoad(), weight, sensitive);
        locoBase.registerLoad(load);
        return load;
    }

    public IDeliverable createGaseousLoad(double weight, double densityAtmPressure, double boilingPoint){
        IDeliverable load = new GaseousLoad(locoBase.getIdForLoad(), weight, densityAtmPressure, boilingPoint);
        locoBase.registerLoad(load);
        return load;
    }

    public IDeliverable createRefrigeratedLoad(double weight, double temperature){
        IDeliverable load = new RefrigeratedLoad(locoBase.getIdForLoad(), weight, temperature);
        locoBase.registerLoad(load);
        return load;
    }

    public IDeliverable createLuggageLoad(double weight, int quantity){
        IDeliverable load = new LuggageLoad(locoBase.getIdForLoad(), weight, quantity);
        locoBase.registerLoad(load);
        return load;
    }

    public IDeliverable createLuggageLoad(double weight, int quantity, boolean fragile, boolean highValue, boolean priority) {
        IDeliverable load = new LuggageLoad(locoBase.getIdForLoad(), weight, quantity, fragile, highValue, priority);
        locoBase.registerLoad(load);
        return load;
    }

    public IDeliverable createMailLoad(int quantity) {
        IDeliverable load = new MailLoad(locoBase.getIdForLoad(), quantity);
        locoBase.registerLoad(load);
        return load;
    }

    public IDeliverable createNonStandardLoad(double weight, Set<LoadType> flags){
        int id = locoBase.getIdForLoad();
        IDeliverable load = new Load(id, weight) {
            @Override
            public Set<LoadType> flags() {
                return flags;
            }

            @Override
            public String getBasicInfo() {
                return null;
            }

            @Override
            public String getFullInfo() {
                return null;
            }

        };
        locoBase.registerLoad(load);
        return load;
    }

    public IDeliverable createRandomLoadOfType(LoadType loadType){
        Random random = new Random();
        return switch (loadType) {
            case HEAVY_FREIGHT -> createHeavyFreightLoad(random.nextDouble() * 70 + 1);
            case LIQUID -> {
                double weight = random.nextDouble(80) + 1;
                double volume = Math.random() < 0.5 ?
                        weight - weight * random.nextDouble(0.2)
                        : weight + weight * random.nextDouble(0.2);
                // volume in cubic m
                yield createLiquidLoad(weight, volume);
            }
            case LIQUID_TOXIC -> {
                double weight = random.nextInt(80) + 1;
                double volume = Math.random() < 0.5 ?
                        weight - weight * random.nextDouble(0.2)
                        : weight + weight * random.nextDouble(0.2);
                int combustion_temp = random.nextInt(100) + 200;
                yield createLiquidToxicLoad(weight, volume, combustion_temp);
            }
            case TOXIC -> createToxicLoad(random.nextDouble() * 10 + 1);
            case EXPLOSIVE -> createExplosiveLoad(random.nextDouble() * 10 + 1);
            case GASEOUS -> {
                int weight = random.nextInt(80) + 1;
                double density = 0.6 + random.nextDouble() * 0.4; // in atm pressure kg/m3
                int boiling_point = -200 + random.nextInt(80);
                yield createGaseousLoad(weight, density, boiling_point);
            }
            case REFRIGERATED -> {
                int weight = random.nextInt(20) + 1;
                int temperature = -20 + random.nextInt(30);
                yield createRefrigeratedLoad(weight, temperature);
            }
            case PASSENGERS -> createPassengerLoad(random.nextInt(87) + 1);
            case LUGGAGE -> {
                double weight = random.nextDouble(2) + 1;
                int quantity = random.nextInt(20) + 1;
                yield createLuggageLoad(weight, quantity);
            }
            case MAIL -> createMailLoad(random.nextInt(10000) + 1);
            default -> createBasicFreightLoad(random.nextDouble() * 30 + 1);
        };
    }

    public IDeliverable createRandomLoad(){
        Random random = new Random();
        LoadType loadType = LoadType.values()[random.nextInt(LoadType.values().length)];
        return createRandomLoadOfType(loadType);
    }

    public List<IDeliverable> createRandomLoads(int quantity) {
        List<IDeliverable> loads = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            loads.add(createRandomLoad());
        }
        return loads;
    }

    public List<IDeliverable> createRandomLoadsOfType(int quantity, LoadType loadType) {
        List<IDeliverable> loads = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            loads.add(createRandomLoadOfType(loadType));
        }
        return loads;
    }
}
