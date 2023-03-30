package pl.edu.pja.s28687.load;

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

    public Load(double weight){
        this.weight = BigDecimal.valueOf(weight);
    }

    public Load(int quantity, double weight){
        this.quantity = BigDecimal.valueOf(quantity);
        this.weight = BigDecimal.valueOf(weight).setScale(2, RoundingMode.CEILING);
        this.loaded = false;
    }


    public abstract Set<Flags> flags();

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
        return Objects.requireNonNullElse(this.quantity, BigDecimal.ONE);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Load<?> load)) return false;
        return id == load.id
                && loaded == load.loaded
                && Objects.equals(quantity, load.quantity)
                && weight.equals(load.weight)
                && Objects.equals(name, load.name)
                && flags().equals(load.flags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, weight, name, id, flags());
    }
}
