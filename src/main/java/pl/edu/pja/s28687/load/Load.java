package pl.edu.pja.s28687.Load;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Load<T extends IDeliverable>{

    BigDecimal quantity;
    private BigDecimal weight;
    private String name;
    private int id;
    private boolean loaded;
    protected Set<Flags> flags;

    public Load(double weight){
        this.weight = BigDecimal.valueOf(weight);
        flags = new HashSet<>();
        setFlags();

    }

    public Load(int quantity, double weight){
        this.quantity = BigDecimal.valueOf(quantity);
        this.weight = BigDecimal.valueOf(weight).setScale(2, RoundingMode.CEILING);
        this.loaded = false;
        setFlags();
    }


    public Set<Flags> flags(){
        return flags;
    }


    public int getId() {
        return id;
    }

    public void setLoaded(){
        this.loaded = true;
    }

    public void setDeloaded(){
        this.loaded = false;
    }

    public String getName() {
        return this.name;
    }
    public BigDecimal getQuantity(){
        return this.quantity;
    }

    public boolean isLoaded(){
        return this.loaded;
    }
    public void setId(int id){
        this.id = id;
    }

    public BigDecimal getWeight() {
        return this.weight;
    }

    public abstract void setFlags();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Load<?> load)) return false;
        return id == load.id && loaded == load.loaded && Objects.equals(quantity, load.quantity) && weight.equals(load.weight) && Objects.equals(name, load.name) && flags.equals(load.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, weight, name, id, flags);
    }
}
