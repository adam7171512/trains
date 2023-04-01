package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.validators.CarFreightValidator;
import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.validators.CarPassengerValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CarBuilder{

    private CarType flag;
    private Set<Flags> loadTypes;
    private final LocoBase locoBase;
    private ICarLoadValidator loadValidator;

    private boolean isPowered;
    private String nonStdCarShipper;
    private BigDecimal nonStdCarNetWeight;
    private BigDecimal nonStdCarGrossWeight;
    private int nonStdCarNumberOfSeats;
    private String nonStdCarSecurityInfo;


    public CarBuilder(LocoBase locoBase){
        this.locoBase = locoBase;
    }


    public CarBuilder setFlag(CarType flag){
        this.flag = flag;
        return this;
    }

    public CarBuilder setRandomProperties(){
        List<CarType> randomTypes = new ArrayList<>(List.of(CarType.values()));
        randomTypes.remove(CarType.NON_STANDARD);
        this.flag = randomTypes.get((int) (Math.random() * randomTypes.size()));
        return this;
    }


    public CarBuilder setLoadTypes(Set<Flags> loadTypes){
        this.loadTypes = loadTypes;
        return this;
    }

    public CarBuilder setLoadValidator(ICarLoadValidator loadValidator){
        this.loadValidator = loadValidator;
        return this;
    }

    public CarBuilder setPoweredForNonStandardCar(boolean isPowered){
        this.isPowered = isPowered;
        return this;
    }

    public CarBuilder setShipperForNonStandardCar(String shipper){
        this.nonStdCarShipper = shipper;
        return this;
    }

    public CarBuilder setSecurityInfoForNonStandardCar(String securityInfo){
        this.nonStdCarSecurityInfo = securityInfo;
        return this;
    }

    public CarBuilder setNetWeightForNonStandardCar(double netWeight){
        this.nonStdCarNetWeight = BigDecimal.valueOf(netWeight);
        return this;
        }

    public CarBuilder setGrossWeightForNonStandardCar(double grossWeight){
        this.nonStdCarGrossWeight = BigDecimal.valueOf(grossWeight);
        return this;
    }

    public CarBuilder setNumberOfSeatsForNonStandardCar(int numberOfSeats){
        this.nonStdCarNumberOfSeats = numberOfSeats;
        return this;
    }

    public <T extends IDeliverable> RailroadCar build(){
        if (flag == null){
            flag = CarType.BASIC_FREIGHT;
        }

        if (loadValidator == null){
            if (flag == CarType.PASSENGERS){
                loadValidator = new CarPassengerValidator();
            }
            else {
                loadValidator = new CarFreightValidator();
        }
        }
        RailroadCar car;
        int id = locoBase.getIdForCar();

        car = switch (flag) {
                case HEAVY_FREIGHT -> new HeavyFreightCar(id, loadValidator);
                case LIQUID -> new LiquidLoadCar(id, loadValidator);
                case LUGGAGE -> new MailAndLuggageCar(id, loadValidator);
                case GASEOUS -> new GaseousLoadCar(id, loadValidator);
                case TOXIC -> new ToxicLoadCar(id, loadValidator);
                case REFRIGERATED -> new RefrigeratedLoadCar(id, loadValidator);
                case EXPLOSIVE -> new ExplosiveLoadCar(id, loadValidator);
                case PASSENGERS -> new PassengerCar(id, loadValidator);
                case POST_OFFICE -> new PostOfficeCar(id);
                case RESTAURANT -> new RestaurantCar(id);
                case LIQUID_TOXIC -> new LiquidToxicLoadCar(id, loadValidator);
                case NON_STANDARD -> {
                    if (nonStdCarShipper == null
                            || nonStdCarSecurityInfo == null
                            || nonStdCarNetWeight == null
                            || nonStdCarGrossWeight == null
                            || nonStdCarNumberOfSeats == 0)
                    {
                        throw new IllegalArgumentException("Non standard car must have all properties set");
                    }
                     else {
                    yield new LoadableRailroadCar<T>(
                            nonStdCarShipper,
                            nonStdCarSecurityInfo,
                            nonStdCarNetWeight,
                            nonStdCarGrossWeight,
                            nonStdCarNumberOfSeats,
                            id,
                            loadValidator) {
                        @Override
                        public CarType getCarType() {
                            return CarType.NON_STANDARD;
                        }

                        @Override
                        public boolean isPowered() {
                            return isPowered;
                        }

                        @Override
                        public Set<Flags> allowedLoadFlags() {
                            return loadTypes;
                        }
                    };
                }
                }
            default -> new BasicFreightCar(id, loadValidator);
            };
        if (car instanceof LoadableRailroadCar<?> && loadTypes != null) {
            ((LoadableRailroadCar<?>) car).setAllowedFlags(loadTypes);
        }
        locoBase.registerCar(car);
        return car;
    }
}