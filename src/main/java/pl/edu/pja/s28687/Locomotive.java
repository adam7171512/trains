package pl.edu.pja.s28687;

import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.cars.LoadableRailroadCar;
import pl.edu.pja.s28687.cars.PassengerCar;
import pl.edu.pja.s28687.gui.TrainSetRepresentation;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.logistics.Coordinates;
import pl.edu.pja.s28687.logistics.RailroadLink;
import pl.edu.pja.s28687.misc.RailroadHazard;
import pl.edu.pja.s28687.misc.TrainStatus;
import pl.edu.pja.s28687.validators.locomotive.ILocomotiveCarValidator;
import pl.edu.pja.s28687.validators.locomotive.ILocomotiveLoadValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.min;

public class Locomotive implements ILocomotive {
    private final int id;
    private final String name;
    private final int maxCars;
    private final BigDecimal maxFreight;
    private final int maxPoweredCars;
    private final BigDecimal defaultSpeed;
    public int x = 0;
    public int y = 0;
    private TrainStation homeTrainStation;
    private TrainStation sourceTrainStation;
    private TrainStation destTrainStation;
    private TrainStation lastTrainStation;
    private BigDecimal currentSpeed = BigDecimal.valueOf(0);
    private List<RailroadLink> road = new ArrayList<>();
    private List<IRailroadCar> cars = new ArrayList<>();
    private List<ILoadCarrier<? extends IDeliverable>> loadCarriers = new ArrayList<>();
    private BigDecimal currentTripDistanceCovered = BigDecimal.valueOf(0);
    private BigDecimal currentSegmentDistance = BigDecimal.valueOf(0);
    private BigDecimal currentSegmentDistanceCovered = BigDecimal.valueOf(0);
    private RailroadLink currentSegment;
    private TrainStatus status;
    private Coordinates coordinates;
    private TrainStation currentSegmentDestination;
    private BigDecimal currentSegmentProgress;
    private TrainSetRepresentation visualRepresentation;
    private ILocomotiveCarValidator carValidator;
    private ILocomotiveLoadValidator loadValidator;

    private Integer trainSetId;

    public Locomotive(String name,
                      int id,
                      int maxCars,
                      BigDecimal maxFreight,
                      int maxPoweredCars,
                      BigDecimal defaultSpeed,
                      ILocomotiveCarValidator carValidator,
                      ILocomotiveLoadValidator loadValidator) {
        this.name = name;
        this.id = id;
        this.maxCars = maxCars;
        this.maxFreight = maxFreight;
        this.maxPoweredCars = min(maxPoweredCars, maxCars);
        this.defaultSpeed = defaultSpeed;
        this.coordinates = new Coordinates(0, 0);
        this.status = TrainStatus.WAITING;
        this.carValidator = carValidator;
        this.loadValidator = loadValidator;
    }

    public Integer getTrainSetId() {
        return trainSetId;
    }

    public void setTrainSetId(int trainSetId) {
        this.trainSetId = trainSetId;
    }

    public BigDecimal getDefaultSpeed() {
        return defaultSpeed;
    }

    public void setHomeTrainStation(TrainStation homeTrainStation) {
        this.homeTrainStation = homeTrainStation;
    }

    public void setSourceTrainStation(TrainStation sourceTrainStation) {
        this.sourceTrainStation = sourceTrainStation;
    }

    public void setDestTrainStation(TrainStation destTrainStation) {
        this.destTrainStation = destTrainStation;
    }

    public List<ILoadCarrier<? extends IDeliverable>> getLoadCarriers() {
        return loadCarriers;
    }

    public boolean attach(IRailroadCar car) {
        if (!carValidator.validateCarLimit(car, this)) {
            System.err.println("Can't attach this car ! Maximum number of cars reached !");
            return false;
        }
        if (!carValidator.validatePoweredCarLimit(car, this)) {
            System.err.println("Can't attach this car ! Maximum number of powered cars reached !");
            return false;
        }
        if (!carValidator.validatePayloadLimit(car, this)) {
            System.err.println("Can't attach this car ! Maximum payload limit reached !");
            return false;
        }
        if (!carValidator.validate(car, this)) {
            System.err.println("Can't attach this car ! This train does not support cars of type ! " + car.getCarType());
            return false;
        }

        cars.add(car);
        car.setAttachedTo(this);
        if (car instanceof ILoadCarrier<?>) {
            loadCarriers.add((LoadableRailroadCar<? extends IDeliverable>) car);

        }
        return true;
    }

    public void detach(IRailroadCar car) {
        cars.remove(car);
        car.setDetached();
        if (car instanceof ILoadCarrier<?>) {
            loadCarriers.remove((LoadableRailroadCar<? extends IDeliverable>) car);
        }
    }

    public boolean validateCar(IRailroadCar car) {
        return carValidator.validate(car, this);
    }

    public List<IRailroadCar> getCars() {
        return cars;
    }

    public void setCurrentTripDistanceCovered(BigDecimal distance) {
        currentTripDistanceCovered = distance;
    }

    public void updateCurrentTripDistanceCovered(BigDecimal distance) {
        currentTripDistanceCovered = currentTripDistanceCovered.add(distance);
    }

    //TODO: refactor
    public int getCurrentSegmentProgress() {
        if (currentSegmentProgress == null) return 0;
        return currentSegmentProgress.intValue();
    }

    public void setCurrentSegmentProgress(BigDecimal progress) {
        currentSegmentProgress = progress;
    }

    public BigDecimal getCurrentTripDistance() {
        return road
                .stream()
                .map(RailroadLink::getDistance)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(0));
    }

    public int getCurrentTripProgress() {
        BigDecimal currentTripDIstance = getCurrentTripDistance();

        return currentTripDIstance.intValue() == 0 ?
                0
                :
                currentTripDistanceCovered.
                        divide(currentTripDIstance, RoundingMode.FLOOR).
                        multiply(BigDecimal.valueOf(100)).
                        intValue();
    }

    public void randomlyChangeSpeed() {
        Random random = new Random();
        int randomValue = random.nextInt(2) == 0 ? -1 : 1;
        try {
            currentSpeed = currentSpeed
                    .add(currentSpeed
                            .multiply(
                                    BigDecimal.valueOf(
                                            randomValue * 0.03)));
            if (currentSpeed.doubleValue() > 200) {
                throw new RailroadHazard(
                        "Train " +
                                this.name +
                                " exceeded speed 200 km/h !!!");
            }
        } catch (RailroadHazard e) {
//            System.out.println(e.getMessage())
            ;
        }
    }

    public TrainStation getHomeStation() {
        return homeTrainStation;
    }

    public void setHomeStation(TrainStation homeTrainStation) {
        this.homeTrainStation = homeTrainStation;
    }

    public TrainStation getLastTrainStation() {
        return this.lastTrainStation;
    }

    public void setLastTrainStation(TrainStation trainStation) {
//        x = (int) trainStation.getCoordinates().getX();
//        y = (int) trainStation.getCoordinates().getY();
        this.lastTrainStation = trainStation;
    }

    public TrainStation getSourceStation() {
        return sourceTrainStation;
    }

    public void setSourceStation(TrainStation sourceTrainStation) {
        this.sourceTrainStation = sourceTrainStation;
        this.lastTrainStation = sourceTrainStation;
    }

    public TrainStation getDestStation() {
        return destTrainStation;
    }

    public void setDestStation(TrainStation destTrainStation) {
        this.destTrainStation = destTrainStation;
    }

    public BigDecimal getCurrentSpeed() {
        return currentSpeed.setScale(2, RoundingMode.CEILING);
    }

    public void setSpeed(BigDecimal speed) throws RailroadHazard {
        BigDecimal speedBeforeChange = this.currentSpeed;
        this.currentSpeed = speed;
        if (speedBeforeChange.doubleValue() < 200 && speed.doubleValue() > 200) {
            throw new RailroadHazard(
                    "Train " +
                            this.name +
                            " exceeded speed 200 km/h !!!");
        }
    }

    public int getId() {
        return id;
    }

    public String getLocName() {
        return this.name;
    }

    public void setDefaultSpeed() {
        currentSpeed = defaultSpeed;
    }

    public void stopTheTrain() {
        currentSpeed = BigDecimal.valueOf(0);
    }

    public void setCurrentSegmentDistance(BigDecimal distance) {
        this.currentSegmentDistance = distance;
    }

    public void updateSegmentDistanceCovered(BigDecimal distanceTravelled) {

        this.currentSegmentDistanceCovered = distanceTravelled;
    }

    public void setVisualRepresentation(TrainSetRepresentation visualRepresentation) {
        this.visualRepresentation = visualRepresentation;
    }

    public int getCurrentCarNumber() {
        return cars.size();
    }

    public int getPoweredCarsNumber() {
        return (int) cars.stream().filter(IRailroadCar::isPowered).count();
    }

    public int getCarLimit() {
        return maxCars;
    }

    public int getPoweredCarLimit() {
        return maxPoweredCars;
    }

    public BigDecimal getMaxPayload() {
        return maxFreight.setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal getCurrentPayload() {
        return cars.
                stream().
                map(IRailroadCar::getCurrentWeight).
                reduce(BigDecimal::add).
                orElse(BigDecimal.valueOf(0)).setScale(2, RoundingMode.CEILING);
    }

    @Override
    public BigDecimal getAvailablePayload() {
        return maxFreight.subtract(getCurrentPayload()).setScale(2, RoundingMode.CEILING);
    }

    public RailroadLink getCurrentSegment() {
        return currentSegment;
    }

    public void setCurrentSegment(RailroadLink segment) {
        this.currentSegment = segment;
        x = (int) currentSegment.getStation1().getCoordinates().getX();
        y = (int) currentSegment.getStation1().getCoordinates().getY();

    }

    public TrainStatus getStatus() {
        return status;
    }

    public void setStatus(TrainStatus status) {
        this.status = status;
    }

    public int passengersOnBoard() {
        return cars.
                stream().
                filter(car -> car instanceof PassengerCar).
                map(car -> ((PassengerCar) car).
                        getNumberOfPassengers()).
                reduce(Integer::sum).
                orElse(0);
    }

    public List<RailroadLink> getRoad() {
        return road;
    }

    public void setRoad(List<RailroadLink> road) {
        this.road = road;
    }

    @Override
    public String toString() {
        return "Locomotive{" +
                "locId=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void setCurrentSegmentDestination(TrainStation destination) {
        this.currentSegmentDestination = destination;
    }

    public TrainStation getNextTrainStation() {
        RailroadLink rl = getCurrentSegment();
        if (rl.getStation1() == getLastTrainStation()) {
            return rl.getStation2();
        } else {
            return rl.getStation1();
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        if (visualRepresentation != null) {

            visualRepresentation.updatePosition(coordinates);
        }
    }

    public ILocomotiveLoadValidator getLoadValidator() {
        return loadValidator;
    }

    public List<ILoadCarrier<? extends IDeliverable>> getLoadableCars() {
        return loadCarriers;
    }
}

