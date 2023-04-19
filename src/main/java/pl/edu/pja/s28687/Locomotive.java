package pl.edu.pja.s28687;

import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.gui.TrainSetView;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.logistics.Coordinates;
import pl.edu.pja.s28687.logistics.RouteSegment;
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
    private static final Logger LOGGER = Logger.getLogger(Locomotive.class.getName());

    static {
        LOGGER.setLevel(Level.SEVERE);
    }

    private final int id;
    private final String name;
    private final int carLimit;
    private final BigDecimal maxPayload;
    private final int poweredCarLimit;
    private final BigDecimal nominalSpeed;
    private TrainStation homeTrainStation;
    private TrainStation sourceTrainStation;
    private TrainStation destTrainStation;
    private TrainStation lastTrainStation;
    private BigDecimal currentSpeed = BigDecimal.valueOf(0);
    private List<RouteSegment> route = new ArrayList<>();
    private List<IRailroadCar> cars = new ArrayList<>();
    private List<ILoadCarrier<? extends IDeliverable>> loadCarriers = new ArrayList<>();
    private BigDecimal currentTripDistanceCovered = BigDecimal.ZERO;
    private BigDecimal currentSegmentDistanceCovered = BigDecimal.ZERO;
    private RouteSegment currentSegment;
    private TrainStatus status;
    private ILocomotiveCarValidator carValidator;
    private ILocomotiveLoadValidator loadValidator;
    private Integer trainSetId;

    public Locomotive(String name,
                      int id,
                      int carLimit,
                      BigDecimal maxPayload,
                      int poweredCarLimit,
                      BigDecimal nominalSpeed,
                      ILocomotiveCarValidator carValidator,
                      ILocomotiveLoadValidator loadValidator) {
        this.name = name;
        this.id = id;
        this.carLimit = carLimit;
        this.maxPayload = maxPayload;
        this.poweredCarLimit = min(poweredCarLimit, carLimit);
        this.nominalSpeed = nominalSpeed;
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
        return nominalSpeed.setScale(2, RoundingMode.FLOOR);
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
                throw new ValidationException("Can't attach this car ! This train does not support cars of type ! "
                        + car.getCarType());
            }
        }
        cars.add(car);
        car.setAttachedTo(this);
        if (car instanceof ILoadCarrier<?>) {
            loadCarriers.add((AbstractLoadCarrier<? extends IDeliverable>) car);
        }
        return true;
    }

    public boolean detach(IRailroadCar car) {
        if (!cars.contains(car)) {
            throw new ValidationException("Can't detach this car ! This train does not contain this car !");
        }
        cars.remove(car);
        loadCarriers.remove(car);
        car.setDetached();

        return true;
    }

    public boolean validateCar(IRailroadCar car) {
        return carValidator.validate(car, this);
    }

    public List<IRailroadCar> getCars() {
        return cars;
    }

    public BigDecimal getCurrentSegmentProgress() {
        if (currentSegment == null || currentSegment.getDistance().compareTo(BigDecimal.ZERO) == 0) {
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
        if (view != null){
            notifyView();
        }
    }

    public BigDecimal getCurrentTripDistance() {
        return route
                .stream()
                .map(RouteSegment::getDistance)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(0))
                .setScale(2, RoundingMode.FLOOR);
    }

    public BigDecimal getCurrentTripDistanceCovered() {
        return currentTripDistanceCovered.setScale(2, RoundingMode.FLOOR);
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
                        divide(currentTripDistance, RoundingMode.FLOOR)
                        .setScale(2, RoundingMode.FLOOR);
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

    private TrainSetView view;

    public void setView(TrainSetView view) {
        this.view = view;
    }

    public void notifyView(){
        view.notify(this);
    }

    public int getCurrentCarNumber() {
        return cars.size();
    }

    public int getPoweredCarsNumber() {
        return (int) cars.stream().filter(IRailroadCar::isPowered).count();
    }

    public int getCarLimit() {
        return carLimit;
    }

    public int getPoweredCarLimit() {
        return poweredCarLimit;
    }

    public BigDecimal getMaxPayload() {
        return maxPayload.setScale(2, RoundingMode.CEILING);
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
        return maxPayload.subtract(getCurrentPayload()).setScale(2, RoundingMode.CEILING);
    }

    public RouteSegment getCurrentSegment() {
        return currentSegment;
    }

    public void setCurrentSegment(RouteSegment segment) {
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
                filter(car -> car instanceof IPassengerCarrier).
                map(car -> ((PassengerCar) car).
                        getNumberOfPassengers()).
                reduce(Integer::sum).
                orElse(0);
    }

    public List<RouteSegment> getRoute() {
        return route;
    }

    public void setRoute(List<RouteSegment> route) {
        this.route = route;
    }

    public TrainStation getNextTrainStation() {
        return getCurrentSegment().destination();
    }

    public ILocomotiveLoadValidator getLoadValidator() {
        return loadValidator;
    }

    //todo: refactor this
    public Coordinates getCoordinates() {
        if (currentSegment == null) {
            return new Coordinates(0, 0);
        }
        Coordinates source = currentSegment.source().getCoordinates();
        BigDecimal sourceX = BigDecimal.valueOf(source.getX());
        BigDecimal sourceY = BigDecimal.valueOf(source.getY());
        Coordinates dest = currentSegment.destination().getCoordinates();
        BigDecimal destX = BigDecimal.valueOf(dest.getX());
        BigDecimal destY = BigDecimal.valueOf(dest.getY());

        BigDecimal currentSegmentProgress = getCurrentSegmentProgress();

        return new Coordinates(
                sourceX.add(destX.subtract(sourceX).multiply(currentSegmentProgress)),
                sourceY.add(destY.subtract(sourceY).multiply(currentSegmentProgress))
        );
    }

    public void raiseAlert(String message) {
        LOGGER.log(Level.SEVERE, message);
    }

    @Override
    public String toString() {
        return "Locomotive{" +
                "locId=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static void setLogLevel(Level level){
        LOGGER.setLevel(level);
    }

}

