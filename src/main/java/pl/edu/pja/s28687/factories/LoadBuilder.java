package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.HashSet;
import java.util.Set;

public class LoadBuilder{

    private Set<LoadType> flags;
    private double weight;
    private int quantity;
    private LocoBase locoBase;
    private double volume;


    public LoadBuilder(double weight, int quantity, LocoBase locoBase){
        this.flags = new HashSet<>();
        this.locoBase = locoBase;
        this.weight = weight;
        this.quantity = quantity;
    }

    public LoadBuilder(LocoBase locoBase){
        this.locoBase = locoBase;
        this.quantity = 1;
        this.flags = new HashSet<>();
    }

    public LoadBuilder(double weight, LocoBase locoBase){
        this(weight, 1, locoBase);
    }

    public LoadBuilder(int quantity, LocoBase locoBase){
        this.locoBase = locoBase;
        this.quantity = quantity;
        this.flags = new HashSet<>();
    }

    public LoadBuilder setFlags(Set<LoadType> flags){
        this.flags = flags;
        return this;
    }

    public LoadBuilder addFlag(LoadType flag){
        this.flags.add(flag);
        return this;
    }

    public LoadBuilder setWeight(double weight){
        this.weight = weight;
        return this;
    }

    public LoadBuilder setRandomProperties(){
        this.weight = Math.random() * 100 + 1;
        this.quantity = (int) (Math.random() * 100) + 1;
        this.flags = Set.of(LoadType.values()[(int) (Math.random() * LoadType.values().length)]);
        return this;
    }

    public LoadBuilder setQuantity(int quantity){
        this.quantity = quantity;
        return this;
    }

    public LoadBuilder setLiquidVolume(double volume){
        this.volume = volume;
        return this;
    }

    public IDeliverable build(){
        if (flags.isEmpty()) {
            flags = Set.of(LoadType.BASIC_FREIGHT);
        }
        IDeliverable load;
            if (flags.size() == 1) {
                load = switch (flags.iterator().next()) {
                case HEAVY_FREIGHT -> new HeavyFreightLoad(weight);
                case LIQUID -> new LiquidLoad(weight, volume);
                case LUGGAGE -> new Mail(weight);
                case GASEOUS -> new GaseousLoad(weight);
                case TOXIC -> new ToxicLoad(weight);
                case REFRIGERATED -> new RefrigeratedLoad(weight);
                case EXPLOSIVE -> new Explosives(weight);
                case PASSENGERS -> new Passengers(quantity);
                default -> new BasicFreightLoad(weight);
            };
            }
            else {
                if (flags.equals(Set.of(LoadType.LIQUID, LoadType.TOXIC))) {
                    load = new LiquidToxicLoad(weight, volume);
            }
                else load = new Load(weight) {
                    @Override
                    public Set<LoadType> flags() {
                        return flags;
                    }
                };
        }
        load.setId(locoBase.getIdForLoad());
        locoBase.registerLoad(load);
        return load;
    }
}