package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.validators.CarFreightValidator;
import pl.edu.pja.s28687.validators.CarLiquidFreightValidator;
import pl.edu.pja.s28687.validators.CarPassengerValidator;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CarBuilder {

    private final LocoBase locoBase;
    private CarType flag;
    private Set<LoadType> loadTypes;
    private ICarLoadValidator loadValidator;

    private boolean isPowered;
    private String nonStdCarShipper;
    private BigDecimal nonStdCarNetWeight;
    private BigDecimal nonStdCarGrossWeight;
    private int nonStdCarNumberOfSeats;
    private String nonStdCarSecurityInfo;


    public CarBuilder(LocoBase locoBase) {
        this.locoBase = locoBase;
    }


    public CarBuilder setFlag(CarType flag) {
        this.flag = flag;
        return this;
    }

    public CarBuilder setRandomProperties() {
        List<CarType> randomTypes = new ArrayList<>(List.of(CarType.values()));
        randomTypes.remove(CarType.NON_STANDARD);
        this.flag = randomTypes.get((int) (Math.random() * randomTypes.size()));
        return this;
    }


    public CarBuilder setLoadTypes(Set<LoadType> loadTypes) {
        this.loadTypes = loadTypes;
        return this;
    }

    public CarBuilder setLoadValidator(ICarLoadValidator loadValidator) {
        this.loadValidator = loadValidator;
        return this;
    }

    public CarBuilder setPoweredForNonStandardCar(boolean isPowered) {
        this.isPowered = isPowered;
        return this;
    }

    public CarBuilder setShipperForNonStandardCar(String shipper) {
        this.nonStdCarShipper = shipper;
        return this;
    }

    public CarBuilder setSecurityInfoForNonStandardCar(String securityInfo) {
        this.nonStdCarSecurityInfo = securityInfo;
        return this;
    }

    public CarBuilder setNetWeightForNonStandardCar(double netWeight) {
        this.nonStdCarNetWeight = BigDecimal.valueOf(netWeight);
        return this;
    }

    public CarBuilder setMaxWeightForNonStandardCar(double grossWeight) {
        this.nonStdCarGrossWeight = BigDecimal.valueOf(grossWeight);
        return this;
    }

    public CarBuilder setNumberOfSeatsForNonStandardCar(int numberOfSeats) {
        this.nonStdCarNumberOfSeats = numberOfSeats;
        return this;
    }

    public <T extends IDeliverable> AbstractRailroadCar build() {
        if (flag == null) {
            flag = CarType.BASIC_FREIGHT;
        }

        if (loadValidator == null) {
            if (flag == CarType.PASSENGERS) {
                loadValidator = new CarPassengerValidator();
            } else if (flag == CarType.LIQUID || flag == CarType.LIQUID_TOXIC) {
                loadValidator = new CarLiquidFreightValidator();
            }
            else {
                loadValidator = new CarFreightValidator();
            }
        }
        AbstractRailroadCar car;
        int id = locoBase.getIdForCar();

        car = switch (flag) {
            //todo : reshape this builder and think about validators
            case HEAVY_FREIGHT -> new HeavyFreightCar(id, loadValidator);
            case LIQUID -> new LiquidLoadCar(id, (CarLiquidFreightValidator) loadValidator);
            case LUGGAGE -> new MailAndLuggageCar(id, loadValidator);
            case GASEOUS -> new GaseousLoadCar(id, loadValidator);
            case TOXIC -> new ToxicLoadCar(id, loadValidator);
            case REFRIGERATED -> new RefrigeratedLoadCar(id, loadValidator);
            case EXPLOSIVE -> new ExplosiveLoadCar(id, loadValidator);
            case PASSENGERS -> new PassengerCar(id, (CarPassengerValidator) loadValidator);
            case POST_OFFICE -> new PostOfficeCar(id, loadValidator);
            case RESTAURANT -> new RestaurantCar(id);
            case LIQUID_TOXIC -> new LiquidToxicLoadCar(id, (CarLiquidFreightValidator) loadValidator);
            case NON_STANDARD -> {
                if (nonStdCarShipper == null
                        || nonStdCarSecurityInfo == null
                        || nonStdCarNetWeight == null
                        || nonStdCarGrossWeight == null
                        || nonStdCarNumberOfSeats == 0) {
                    throw new IllegalArgumentException("Non standard car must have all properties set");
                } else {
                    yield new AbstractLoadCarrier<T>(
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
                        public void emergencyProcedure() {

                        }

                        @Override
                        public void routineProcedure() {

                        }

                        @Override
                        public boolean isPowered() {
                            return isPowered;
                        }

                        @Override
                        public String getFullInfo() {
                            return getBasicInfo();
                        }

                        @Override
                        public Set<LoadType> allowedLoadFlags() {
                            return loadTypes;
                        }

                        @Override
                        public void safetyCheck() {

                        }

                        @Override
                        public void emergencyUnloading() {

                        }

                        @Override
                        public BigDecimal getMaxPayload() {
                            return null;
                        }

                        @Override
                        public String getCargoStats() {
                            return null;
                        }
                    };
                }
            }
            default -> new BasicFreightCar(id, loadValidator);
        };
        if (car instanceof AbstractLoadCarrier<?> && loadTypes != null) {
            ((AbstractLoadCarrier<?>) car).setAllowedFlags(loadTypes);
        }
        locoBase.registerCar(car);
        return car;
    }
}