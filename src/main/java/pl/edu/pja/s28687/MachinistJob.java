package pl.edu.pja.s28687;

import pl.edu.pja.s28687.Logistics.Coordinates;
import pl.edu.pja.s28687.Misc.RailroadHazard;
import pl.edu.pja.s28687.Misc.TrainStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class MachinistJob extends Thread{
    private Locomotive locomotive;
    private BigDecimal distanceToTravel;
    public MachinistJob(Locomotive locomotive, BigDecimal distanceToTravel){
        this.distanceToTravel = distanceToTravel;
        this.locomotive = locomotive;
    }

    @Override
    public void run(){
        BigDecimal distanceTravelled = BigDecimal.valueOf(0);
        BigDecimal distanceLeft = distanceToTravel.subtract(distanceTravelled);
        locomotive.updateSegmentDistanceCovered(distanceTravelled);
        locomotive.setDefaultSpeed();
        locomotive.setStatus(TrainStatus.RUNNING);
        while (distanceTravelled.compareTo(distanceToTravel) < 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException("MachinistJob interrupted");
            }
            BigDecimal distanceInterval = locomotive
                    .getCurrentSpeed()
                    .divide(BigDecimal.valueOf(36), RoundingMode.FLOOR);

            distanceInterval = distanceInterval.min(distanceLeft);
            distanceTravelled = distanceTravelled.add(distanceInterval);

            locomotive.updateCurrentTripDistanceCovered(distanceInterval);
            locomotive.updateSegmentDistanceCovered(distanceTravelled);
            BigDecimal currentSegmentprogress = distanceTravelled.divide(distanceToTravel, RoundingMode.FLOOR);
            locomotive.setCurrentSegmentProgress(currentSegmentprogress);
            setLocCoordinates(currentSegmentprogress);
            randomlyChangeLocomotiveSpeed();
        }
        locomotive.setStatus(TrainStatus.WAITING);
        locomotive.stopTheTrain();
    }

    public void randomlyChangeLocomotiveSpeed() {
        Random random = new Random();
        int randomValue = random.nextInt(2) == 0 ? -1 : 1;
        BigDecimal currentSpeed = locomotive.getCurrentSpeed();
        currentSpeed = currentSpeed
                .add(currentSpeed
                        .multiply(
                                BigDecimal.valueOf(randomValue * 0.03)));

        locomotive.setCurrentSpeed(currentSpeed);
    }

    public void setLocCoordinates(BigDecimal currentSegmentProgress){
        Coordinates source = locomotive.getLastTrainStation().getCoordinates();
        BigDecimal sourceX = BigDecimal.valueOf(source.getX());
        BigDecimal sourceY = BigDecimal.valueOf(source.getY());
        Coordinates dest = locomotive.getNextTrainStation().getCoordinates();
        BigDecimal destX = BigDecimal.valueOf(dest.getX());
        BigDecimal destY = BigDecimal.valueOf(dest.getY());

        Coordinates current = new Coordinates(
                sourceX.add(destX.subtract(sourceX).multiply(currentSegmentProgress)),
                sourceY.add(destY.subtract(sourceY).multiply(currentSegmentProgress))
        );
        locomotive.setCoordinates(current);
    }
}
