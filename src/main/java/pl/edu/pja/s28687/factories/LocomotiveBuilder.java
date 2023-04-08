package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.validators.locomotive.ILocomotiveCarValidator;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.validators.locomotive.ILocomotiveLoadValidator;
import pl.edu.pja.s28687.validators.locomotive.LocomotiveCarValidatorForCurrentCarWeight;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.validators.locomotive.LocomotiveLoadValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LocomotiveBuilder {

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
    private LocoBase locoBase;
    private ILocomotiveCarValidator carValidator;
    private ILocomotiveLoadValidator loadValidator;

    public LocomotiveBuilder(LocoBase locobase){
        this.locoBase = locobase;
    }

    public LocomotiveBuilder setName(String name){
        this.name = name;
        return this;
    }

    public LocomotiveBuilder setDefaultSpeed(BigDecimal defaultSpeed){
        this.defaultSpeed = defaultSpeed;
        return this;
        }

    public LocomotiveBuilder setMaxPayload(BigDecimal maxPayload){
        this.maxPayload = maxPayload;
        return this;
        }

    public LocomotiveBuilder setRegularCars(int regularCars){
        this.regularCars = regularCars;
        return this;
        }

    public LocomotiveBuilder setPoweredCars(int poweredCars){
        this.poweredCars = poweredCars;
        return this;
        }

    public LocomotiveBuilder setCarValidator(ILocomotiveCarValidator validator){
        this.carValidator = validator;
        return this;
    }

    public LocomotiveBuilder setLoadValidator(ILocomotiveLoadValidator validator){
        this.loadValidator = validator;
        return this;
    }


   public LocomotiveBuilder setRandomProperties(){
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
        int id = locoBase.getIdForLocomotive();
        if (name == null) name = "Loc " + id;
        if (defaultSpeed == null) defaultSpeed = BigDecimal.valueOf(100);
        if (maxPayload == null) maxPayload = BigDecimal.valueOf(10000);
        if (regularCars == 0) regularCars = 20;
        if (poweredCars == 0) poweredCars = 10;
        if (carValidator == null) carValidator = new LocomotiveCarValidatorForCurrentCarWeight();
        if (loadValidator == null) loadValidator = new LocomotiveLoadValidator();

        Locomotive locomotive = new Locomotive(
                name, id, regularCars, maxPayload, poweredCars,  defaultSpeed, carValidator, loadValidator);
        locoBase.registerLocomotive(locomotive);
        return locomotive;
        }





}
