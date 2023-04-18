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
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.min;

public class Locomotive implements ILocomotive {
    private static final Logger logger = Logger.getLogger(Locomotive.class.getName());

    static {
        logger.setLevel(Level.SEVERE);
    }

    private final int id;
    private final String name;
    private final int maxCars;
    private final BigDecimal maxFreight;
    private final int maxPoweredCars;
    private final BigDecimal nominalSpeed;
    private TrainStation homeTrainStation;
    private TrainStation sourceTrainStation;
    private TrainStation destTrainStation;
    private TrainStation lastTrainStation;
    private BigDecimal currentSpeed = BigDecimal.valueOf(0);
    private List<RailroadLink> route = new ArrayList<>();
    private List<IRailroadCar> cars = new ArrayList<>();
    private List<ILoadCarrier<? extends IDeliverable>> loadCarriers = new ArrayList<>();
    private BigDecimal currentTripDistanceCovered = BigDecimal.ZERO;
    private BigDecimal currentSegmentDistanceCovered = BigDecimal.ZERO;
    private RailroadLink currentSegment;
    private TrainStatus status;
    private Coordinates currentLocation;
    //todo: separate this
    private TrainSetRepresentation visualRepresentation;
    private ILocomotiveCarValidator carValidator;
    private ILocomotiveLoadValidator loadValidator;
    private Integer trainSetId;

    public Locomotive(String name,
                      int id,
                      int maxCars,
                      BigDecimal maxFreight,
                      int maxPoweredCars,
                      BigDecimal nominalSpeed,
                      ILocomotiveCarValidator carValidator,
                      ILocomotiveLoadValidator loadValidator) {
        this.name = name;
        this.id = id;
        this.maxCars = maxCars;
        this.maxFreight = maxFreight;
        this.maxPoweredCars = min(maxPoweredCars, maxCars);
        this.nominalSpeed = nominalSpeed;
        this.currentLocation = new Coordinates(0, 0);
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

    public BigDecimal getNominalSpeed() {
        return nominalSpeed;
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
        if (!carValidator.validate(car, this)) {
            if (!carValidator.validateCarLimit(car, this)) {
                throw new ValidationException("Can't attach this car ! Maximum number of cars reached !");
            } else if (!carValidator.validatePoweredCarLimit(car, this)) {
                throw new ValidationException("Can't attach this car ! Maximum number of powered cars reached !");
            } else if (!carValidator.validatePayloadLimit(car, this)) {
                throw new ValidationException("Can't attach this car ! Maximum payload reached !");
            } else {
                throw new ValidationException("Can't attach this car ! This train does not support cars of type ! " + car.getCarType());
            }
        }
        cars.add(car);
        car.setAttachedTo(this);
        if (car instanceof ILoadCarrier<?>) {
            loadCarriers.add((LoadableRailroadCar<? extends IDeliverable>) car);
        }
        return true;
    }

    public boolean detach(IRailroadCar car) {
        if (!cars.contains(car)) {
            throw new ValidationException("Can't detach this car ! This train does not contain this car !");
        }
        cars.remove(car);
        car.setDetached();
        if (car instanceof ILoadCarrier<?>) {
            loadCarriers.remove((LoadableRailroadCar<? extends IDeliverable>) car);
        }
        return true;
    }

    public boolean validateCar(IRailroadCar car) {
        return carValidator.validate(car, this);
    }

    public List<IRailroadCar> getCars() {
        return cars;
    }

    public BigDecimal getCurrentSegmentProgress() {
        if (currentSegment == null) {
            return BigDecimal.ZERO;
        }
        return currentSegmentDistanceCovered
                .divide(currentSegment.getDistance(), RoundingMode.FLOOR)
                .setScale(2, RoundingMode.FLOOR);
    }

    public BigDecimal getCurrentSegmentDistanceCovered() {
        return currentSegmentDistanceCovered;
    }

    public void setCurrentSegmentDistanceCovered(BigDecimal distance) {
        this.currentSegmentDistanceCovered = distance;
        //todo: separate this
        updateVisualRepresentation();
    }

    public void updateVisualRepresentation() {
        if (visualRepresentation != null) {
            visualRepresentation.updatePosition(getCoordinates());
        }
    }

    public BigDecimal getCurrentTripDistance() {
        return route
                .stream()
                .map(RailroadLink::getDistance)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(0));
    }

    public BigDecimal getCurrentTripDistanceCovered() {
        return currentTripDistanceCovered;
    }

    public void setCurrentTripDistanceCovered(BigDecimal distance) {
        currentTripDistanceCovered = distance;
    }

    public BigDecimal getCurrentTripProgress() {
        BigDecimal currentTripDistance = getCurrentTripDistance();

        return currentTripDistance.compareTo(BigDecimal.ZERO) == 0 ?
                BigDecimal.ZERO
                :
                currentTripDistanceCovered.
                        divide(currentTripDistance, RoundingMode.FLOOR);
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
        this.lastTrainStation = trainStation;
    }

    public TrainStation getSourceStation() {
        return sourceTrainStation;
    }

    public void setSourceStation(TrainStation sourceTrainStation) {
        this.sourceTrainStation = sourceTrainStation;
        this.lastTrainStation = sourceTrainStation;
    }

    public TrainStation getDestinationStation() {
        return destTrainStation;
    }

    public void setDestinationStation(TrainStation destTrainStation) {
        this.destTrainStation = destTrainStation;
    }

    public BigDecimal getCurrentSpeed() {
        return currentSpeed.setScale(2, RoundingMode.CEILING);
    }

    public void setCurrentSpeed(BigDecimal speed) throws RailroadHazard {
        BigDecimal speedBeforeChange = this.currentSpeed;
        this.currentSpeed = speed;
        if (speedBeforeChange.doubleValue() < 200 && speed.doubleValue() > 200) {
            throw new RailroadHazard(
                    "Railroad Hazard !! Train " +
                            this.name + " , ID : " + this.id +
                            " exceeded speed 200 km/h" +
                            " on " + this.getCurrentSegment() +
                            " !!!");
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    //todo: separate this
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
    }

    public TrainStatus getStatus() {
        return status;
    }

    public void setStatus(TrainStatus status) {
        this.status = status;
    }

    public int getPassengerNumber() {
        return cars.
                stream().
                filter(car -> car instanceof PassengerCar).
                map(car -> ((PassengerCar) car).
                        getNumberOfPassengers()).
                reduce(Integer::sum).
                orElse(0);
    }

    public List<RailroadLink> getRoute() {
        return route;
    }

    public void setRoute(List<RailroadLink> route) {
        this.route = route;
    }


    public TrainStation getNextTrainStation() {
        RailroadLink rl = getCurrentSegment();
        return rl.getStation1() == getLastTrainStation() ? rl.getStation2() : rl.getStation1();
    }

    public ILocomotiveLoadValidator getLoadValidator() {
        return loadValidator;
    }

    public List<ILoadCarrier<? extends IDeliverable>> getLoadableCars() {
        return loadCarriers;
    }

    public Coordinates getCoordinates() {
        Coordinates source = getLastTrainStation().getCoordinates();
        BigDecimal sourceX = BigDecimal.valueOf(source.getX());
        BigDecimal sourceY = BigDecimal.valueOf(source.getY());
        Coordinates dest = getNextTrainStation().getCoordinates();
        BigDecimal destX = BigDecimal.valueOf(dest.getX());
        BigDecimal destY = BigDecimal.valueOf(dest.getY());

        BigDecimal currentSegmentProgress = getCurrentSegmentProgress();

        Coordinates currentCoordinates = new Coordinates(
                sourceX.add(destX.subtract(sourceX).multiply(currentSegmentProgress)),
                sourceY.add(destY.subtract(sourceY).multiply(currentSegmentProgress))
        );
        return currentCoordinates;
    }

    public Logger getLogger() {
        return logger;
    }

    public void raiseAlert(String message) {
        logger.log(Level.SEVERE, message);
    }

    @Override
    public String toString() {
        return "Locomotive{" +
                "locId=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}

