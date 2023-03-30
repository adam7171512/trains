package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.HashSet;
import java.util.Set;

public class LoadBuilder{

    private Set<Flags> flags;
    private double weight;
    private int quantity;
    private LocoBase locoBase;


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
        this.locoBase = LocoBase.getInstance();
        this.quantity = quantity;
        this.flags = new HashSet<>();
    }

    public LoadBuilder setFlags(Set<Flags> flags){
        this.flags = flags;
        return this;
    }

    public LoadBuilder addFlag(Flags flag){
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
        this.flags = Set.of(Flags.values()[(int) (Math.random() * Flags.values().length)]);
        return this;
    }

    public LoadBuilder setQuantity(int quantity){
        this.quantity = quantity;
        return this;
    }

    public Load<? extends IDeliverable> build(){
        if (flags.isEmpty()) {
            flags = Set.of(Flags.BASIC_FREIGHT);
        }
        Load<? extends IDeliverable> load;
            if (flags.size() == 1) {
                load = switch (flags.iterator().next()) {
                case HEAVY_FREIGHT -> new HeavyFreightLoad(weight);
                case LIQUID -> new LiquidLoad(weight);
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
                if (flags.equals(Set.of(Flags.LIQUID, Flags.TOXIC))) {
                    load = new LiquidToxicLoad(weight);
            }
                else load = new Load<>(weight) {
                    @Override
                    public Set<Flags> flags() {
                        return flags;
                    }
                };
        }
        load.setId(locoBase.getIdForLoad());
        locoBase.registerLoad(load);
        return load;
    }
}