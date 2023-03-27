package pl.edu.pja.s28687;

import pl.edu.pja.s28687.Cars.IPowered;
import pl.edu.pja.s28687.Cars.PassengerCar;
import pl.edu.pja.s28687.Cars.RailroadCar;
import pl.edu.pja.s28687.Gui.TrainSetRepresentation;
import pl.edu.pja.s28687.Logistics.Coordinates;
import pl.edu.pja.s28687.Logistics.LocoBase;
import pl.edu.pja.s28687.Logistics.RailroadLink;
import pl.edu.pja.s28687.Misc.RailroadHazard;
import pl.edu.pja.s28687.Misc.TrainStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Locomotive {
    private Conductor conductor;
    private final int id;
    private final String name;
    private final int maxCars;
    private final BigDecimal maxFreight;
    private final int maxPoweredCars;
    private TrainStation homeTrainStation;
    private TrainStation sourceTrainStation;
    private TrainStation destTrainStation;
    private TrainStation lastTrainStation;
    private final BigDecimal defaultSpeed;
    private BigDecimal currentSpeed = BigDecimal.valueOf(0);
    private List<RailroadLink> road = new ArrayList<>();
    private List<RailroadCar> cars = new ArrayList<>();
    private BigDecimal currentTripDistanceCovered = BigDecimal.valueOf(0);
    private BigDecimal currentSegmentDistance = BigDecimal.valueOf(0);
    private BigDecimal currentSegmentDistanceCovered = BigDecimal.valueOf(0);
    private RailroadLink currentSegment;
    private TrainStatus status;
    private Coordinates coordinates;
    public int x = 0;
    public int y = 0;
    private TrainStation currentSegmentDestination;
    private BigDecimal currentSegmentProgress;
    private TrainSetRepresentation visualRepresentation;

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public Locomotive(String name, int maxCars, BigDecimal maxFreight, int maxPoweredCars, BigDecimal defaultSpeed, LocoBase locoBase) {
        this.name = name;
        this.maxCars = maxCars;
        this.maxFreight = maxFreight;
        this.maxPoweredCars = maxPoweredCars;
        this.defaultSpeed = defaultSpeed;
        this.id = locoBase.assignLocId(this);
        this.coordinates = new Coordinates(0, 0);
        this.status = TrainStatus.WAITING;
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



    public void attach(RailroadCar car){
        if(! validateCar(car)) System.out.println("Cant attach this car");
        else {
            cars.add(car);
            car.attach();
        }
    }

    public boolean validateCar(RailroadCar car){
//        if (! validateMaxCars()) throw new RuntimeException("Too many cars !");
//        if (! validateElectric()) throw new RuntimeException("Too many electric cars!");
//        if (! validateFreightLimit(car)) throw new RuntimeException("Too heavy!");
        return (validateCarLimit() && validatePoweredCarLimit() && validateFreightLimit(car));
    }

    private boolean validatePoweredCarLimit(){
        int currentElectricCars = (int) cars.stream().filter(rCar -> rCar instanceof IPowered).count();
        return currentElectricCars < maxPoweredCars;
    }

    private boolean validateCarLimit(){
        int currentCars = cars.size();
        return currentCars < maxCars;
    }

    private boolean validateFreightLimit(RailroadCar car){
        BigDecimal totalFreightWeight = cars.
                stream().
                map(RailroadCar::getCurrentWeight).
                reduce(BigDecimal::add).
                orElse(BigDecimal.valueOf(0));
        BigDecimal newWeight = totalFreightWeight.add(car.getCurrentWeight());
        return newWeight.compareTo(maxFreight) <= 0;
    }

    public void setRoad(List<RailroadLink> road){
        this.road = road;
    }

    public List<RailroadCar> getCars(){
        return cars;
    }


    public void setCurrentTripDistanceCovered(BigDecimal distance){
        currentTripDistanceCovered = distance;
    }

    public void updateCurrentTripDistanceCovered(BigDecimal distance){
        currentTripDistanceCovered = currentTripDistanceCovered.add(distance);
    }

    //TODO: refactor
    public int getCurrentSegmentProgress(){
//        int x1, x2, y1, y2;
//        int progress = 0;
//        if (currentSegment != null) {
//
//            x1 = (int) lastTrainStation.getCoordinates().getX();
//            x2 = (int) currentSegmentDestination.getCoordinates().getX();
//            y1 = (int) lastTrainStation.getCoordinates().getY();
//            y2 = (int) currentSegmentDestination.getCoordinates().getY();
//
//
//
//            progress =  currentSegmentDistance.intValue() == 0 ?
//                    0
//                    :
//                    currentSegmentDistanceCovered.
//                            divide(currentSegmentDistance, RoundingMode.FLOOR)
//                            .multiply(BigDecimal.valueOf(100))
//                            .intValue();
////            if (currentTrainStation == destination) progress = 100;
//            x = (int) (x1 + (x2 - x1) * 0.01 * progress);
//            y = (int) (y1 + (y2 - y1) * 0.01 * progress);
//        }
        if (currentSegmentProgress == null) return 0;
        return currentSegmentProgress.intValue();
    }

    public void setCurrentSegmentProgress(BigDecimal progress){
        currentSegmentProgress = progress;
    }



    public BigDecimal getCurrentTripDistance(){
        return road
                .stream()
                .map(RailroadLink::getDistance)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(0));
    }

    public int getCurrentTripProgress(){
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
            }}
        catch (RailroadHazard e) {
//            System.out.println(e.getMessage())
            ;}
    }

    public void setSpeed(BigDecimal speed){
        this.currentSpeed = speed;
    }




    public TrainStation getHomeStation() {
        return homeTrainStation;
    }

    public void setLastTrainStation(TrainStation trainStation){
//        x = (int) trainStation.getCoordinates().getX();
//        y = (int) trainStation.getCoordinates().getY();
        this.lastTrainStation = trainStation;
    }

    public TrainStation getLastTrainStation(){
        return this.lastTrainStation;
    }

    public void setHomeStation(TrainStation homeTrainStation) {
        this.homeTrainStation = homeTrainStation;
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

    public void setCurrentSpeed(BigDecimal currentSpeed){
        this.currentSpeed = currentSpeed;
        if (currentSpeed.doubleValue() > 200) {
            try {
                throw new RailroadHazard(
                        "Train " +
                                this.name +
                                " exceeded speed 200 km/h !!!");
            } catch (RailroadHazard e)
            {
                System.err.println(e.getMessage());}
        }
    }


    public int getId() {
        return id;
    }

    public String getLocName(){
        return this.name;
    }

    public void setDefaultSpeed(){
        currentSpeed = defaultSpeed;
    }

    public void stopTheTrain(){
        currentSpeed = BigDecimal.valueOf(0);
    }

    public void setCurrentSegmentDistance(BigDecimal distance){
        this.currentSegmentDistance = distance;
    }

    public void updateSegmentDistanceCovered(BigDecimal distanceTravelled){

        this.currentSegmentDistanceCovered = distanceTravelled;
    }

    public void setVisualRepresentation(TrainSetRepresentation visualRepresentation){
        this.visualRepresentation = visualRepresentation;
    }

    public int carsOccupied(){
        return cars.size();
    }

    public int poweredCarsOccupied(){
        return (int) cars.stream().filter(car -> car instanceof IPowered).count();
    }

    public int getMaxCars() {
        return maxCars;
    }

    public int getMaxPoweredCars() {
        return maxPoweredCars;
    }

    public BigDecimal getMaxFreight(){
        return maxFreight;
    }

    public BigDecimal getCurrentFreight(){
        return cars.
                stream().
                map(RailroadCar::getCurrentWeight).
                reduce(BigDecimal::add).
                orElse(BigDecimal.valueOf(0));
    }

    public RailroadLink getCurrentSegment(){
        return currentSegment;
    }

    public TrainStatus getStatus(){
        return status;
    }

    public void setCurrentSegment(RailroadLink segment){
        this.currentSegment = segment;
        x = (int) currentSegment.getStation1().getCoordinates().getX();
        y = (int) currentSegment.getStation1().getCoordinates().getY();

    }

    public void updateCords(){

    }

    public int passengersOnBoard(){
        return cars.
                stream().
                filter(car -> car instanceof PassengerCar).
                map(car -> ((PassengerCar) car).
                        getNumberOfPassengers()).
                reduce(Integer::sum).
                orElse(0);
    }

    public void setStatus(TrainStatus status){
        this.status = status;
    }

    public List<RailroadLink> getRoad(){
        return road;
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

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        if (visualRepresentation != null) {

            visualRepresentation.updatePosition(coordinates);
        }
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
}

