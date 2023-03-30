package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LocoBuilder {

    public static final List<String> prefixes = new ArrayList<>(List.of(
            "Small",
            "Big",
            "Fast",
            "Slow",
            "Happy",
            "Sad",
            "Angry",
            "Fastest",
            "Heavy",
            "Lightweight",
            "Smart",
            "Silly",
            "Angry",
            "Drunk",
            "Careless"
    ));
    public static final List<String> suffixes = new ArrayList<>(List.of(
            "Joe",
            "Musk",
            "Steve",
            "Adam",
            "The King",
            "Tadeusz",
            "Jola",
            "Batman",
            "Superman",
            "Chojrak",
            "Dog",
            "Cat",
            "Pants",
            "Monkey",
            "Gonzales",
            "Goofy"
    ));

    private String name;
    private BigDecimal defaultSpeed;
    private BigDecimal maxPayload;
    private int regularCars;
    private int poweredCars;
    private int id;
    private LocoBase locoBase;

    public LocoBuilder(LocoBase locobase){
        this.locoBase = locobase;
    }

    public LocoBuilder setName(String name){
        this.name = name;
        return this;
    }

    public LocoBuilder setDefaultSpeed(BigDecimal defaultSpeed){
        this.defaultSpeed = defaultSpeed;
        return this;
        }

    public LocoBuilder setMaxPayload(BigDecimal maxPayload){
        this.maxPayload = maxPayload;
        return this;
        }

    public LocoBuilder setRegularCars(int regularCars){
            return this;
        }

    public LocoBuilder setPoweredCars(int poweredCars){
            return this;
        }

        public LocoBuilder setId(int id){
            return this;
        }

   public LocoBuilder setRandomProperties(){
        this.name = prefixes.get((int)(
                Math.random() * prefixes.size()))
                + " "
                + suffixes.get((int)(Math.random()
                * suffixes.size()));
        this.defaultSpeed = BigDecimal.valueOf(Math.random() * 100 + 50);
        this.maxPayload = BigDecimal.valueOf(Math.random() * 50000 + 2000);
        this.regularCars = (int)(Math.random() * 20 + 10);
        this.poweredCars = (int)(Math.random() * 10 + 5);
        return this;
        }


    public Locomotive build(){
        int id = LocoBase.getInstance().getIdForLocomotive();
        this.id = id;
        if (name == null) name = "Loc " + id;
        if (defaultSpeed == null) defaultSpeed = BigDecimal.valueOf(100);
        if (maxPayload == null) maxPayload = BigDecimal.valueOf(10000);
        if (regularCars == 0) regularCars = 20;
        if (poweredCars == 0) poweredCars = 10;

        Locomotive locomotive = new Locomotive(name, id, regularCars, maxPayload, poweredCars,  defaultSpeed);
        locoBase.registerLocomotive(locomotive);
        return locomotive;
        }





}
