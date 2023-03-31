package pl.edu.pja.s28687;

import pl.edu.pja.s28687.logistics.Coordinates;
import pl.edu.pja.s28687.misc.TrainStatus;

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

        locomotive.updateSegmentDistanceCovered(distanceTravelled);

//        locomotive.setDefaultSpeed();
        locomotive.setStatus(TrainStatus.RUNNING);

        speedUpLocomotive();
        while (distanceTravelled.compareTo(distanceToTravel) < 0) {
            BigDecimal distanceLeft = distanceToTravel.subtract(distanceTravelled);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException("MachinistJob interrupted");
            }
            BigDecimal currentSpeed = locomotive.getCurrentSpeed();
            BigDecimal distanceInterval =
                    currentSpeed
                    .divide(BigDecimal.valueOf(36), RoundingMode.FLOOR);

            distanceInterval = distanceInterval.min(distanceLeft);
            distanceTravelled = distanceTravelled.add(distanceInterval);

            locomotive.updateCurrentTripDistanceCovered(distanceInterval);
            locomotive.updateSegmentDistanceCovered(distanceTravelled);
            BigDecimal currentSegmentprogress = distanceTravelled.divide(distanceToTravel, RoundingMode.FLOOR);
            locomotive.setCurrentSegmentProgress(currentSegmentprogress.multiply(BigDecimal.valueOf(100)));
            setLocCoordinates(currentSegmentprogress);

            if (currentSpeed.divide(locomotive.getDefaultSpeed(), RoundingMode.FLOOR).doubleValue() > 0.3
            && distanceLeft.compareTo(BigDecimal.valueOf(50)) < 0){
                slowDownLocomotive();
            }
            else if (currentSpeed.divide(locomotive.getDefaultSpeed(), RoundingMode.FLOOR).doubleValue() < 0.8){
                speedUpLocomotive();
            }

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

    public void speedUpLocomotive(){
                BigDecimal currentSpeed = locomotive.getCurrentSpeed();
                BigDecimal defaultSpeed = locomotive.getDefaultSpeed();
                currentSpeed = currentSpeed.add(defaultSpeed.multiply(BigDecimal.valueOf(0.01)));
                locomotive.setCurrentSpeed(currentSpeed);
    }

    public void slowDownLocomotive(){
            BigDecimal currentSpeed = locomotive.getCurrentSpeed();
            BigDecimal defaultSpeed = locomotive.getDefaultSpeed();
            currentSpeed = currentSpeed.subtract(defaultSpeed.multiply(BigDecimal.valueOf(0.04)));
            currentSpeed = currentSpeed.max(BigDecimal.valueOf(0));
            locomotive.setCurrentSpeed(currentSpeed);
        }

}
