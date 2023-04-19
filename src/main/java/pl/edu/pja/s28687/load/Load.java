package pl.edu.pja.s28687.load;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public abstract class Load implements IDeliverable {

    private BigDecimal weight;
    private String description;
    private int id;
    private boolean loaded;

    public Load(int id, double weight){
        this.id = id;
        this.weight = BigDecimal.valueOf(weight);
    }

    public Load(int id, double weight, String description){
        this(id, weight);
        this.description = description;
    }

    public abstract Set<LoadType> flags();

    public int getId() {
        return id;
    }

    public void setLoaded(){
        this.loaded = true;
    }

    public void setDeloaded(){
        this.loaded = false;
    }

    public String getDescription() {
        return description == null? "No description" : description;
    }
    public boolean isLoaded(){
        return this.loaded;
    }

    public BigDecimal getWeight() {
        return this.weight.setScale(2, RoundingMode.CEILING);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Load load)) return false;
        return this.id == load.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, description, id, flags());
    }
}
