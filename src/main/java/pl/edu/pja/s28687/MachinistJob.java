package pl.edu.pja.s28687;

import pl.edu.pja.s28687.logistics.Coordinates;
import pl.edu.pja.s28687.misc.RailroadHazard;
import pl.edu.pja.s28687.misc.TrainStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.logging.Level;

public class MachinistJob extends Thread {
    private Locomotive locomotive;
    private BigDecimal distanceToTravel;

    public MachinistJob(Locomotive locomotive, BigDecimal distanceToTravel) {
        this.distanceToTravel = distanceToTravel;
        this.locomotive = locomotive;
    }

    @Override
    public void run() {
        BigDecimal distanceTravelled = BigDecimal.valueOf(0);

        locomotive.updateSegmentDistanceCovered(distanceTravelled);
        locomotive.setStatus(TrainStatus.RUNNING);
        boolean reachedNominalSpeed = false;
        while (distanceTravelled.compareTo(distanceToTravel) < 0) {
            if (locomotive.getStatus() == TrainStatus.EMERGENCY) {
                locomotive.stopTheTrain();
                locomotive.getLogger().log(Level.SEVERE,
                        "\nLocomotive " + locomotive.getLocName() + " , ID : " + locomotive.getId()
                                + " had an emergency at : " + locomotive.getCurrentSegment() + " and had to stop."
                                + " Help is on the way. Train will continue its trip in 30 seconds");
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    throw new RuntimeException("MachinistJob interrupted");
                }
                locomotive.setStatus(TrainStatus.RUNNING);
            }
            if (!reachedNominalSpeed) {
                speedUpLocomotive();
                if (locomotive.getCurrentSpeed().compareTo(locomotive.getDefaultSpeed()) >= 0) {
                    reachedNominalSpeed = true;
                }
            }
            BigDecimal distanceLeft = distanceToTravel.subtract(distanceTravelled);
            try {
                Thread.sleep(1000);
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
            BigDecimal currentSegmentProgress = distanceTravelled.divide(distanceToTravel, RoundingMode.FLOOR);
            locomotive.setCurrentSegmentProgress(currentSegmentProgress.multiply(BigDecimal.valueOf(100)));
            setLocCoordinates(currentSegmentProgress);

            if (currentSpeed.divide(locomotive.getDefaultSpeed(), RoundingMode.FLOOR).doubleValue() > 0.3
                    && distanceLeft.compareTo(BigDecimal.valueOf(3)) < 0) {
                slowDownLocomotive();
            } else if (currentSpeed.divide(locomotive.getDefaultSpeed(), RoundingMode.FLOOR).doubleValue() < 0.3) {
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

        setLocomotiveSpeed(currentSpeed);
    }

    public void setLocCoordinates(BigDecimal currentSegmentProgress) {
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

    public void speedUpLocomotive() {
        BigDecimal currentSpeed = locomotive.getCurrentSpeed();
        BigDecimal defaultSpeed = locomotive.getDefaultSpeed();
        currentSpeed = currentSpeed.add(defaultSpeed.multiply(BigDecimal.valueOf(0.04)));
        setLocomotiveSpeed(currentSpeed);
    }

    public void slowDownLocomotive() {
        BigDecimal currentSpeed = locomotive.getCurrentSpeed();
        BigDecimal defaultSpeed = locomotive.getDefaultSpeed();
        currentSpeed = currentSpeed.subtract(defaultSpeed.multiply(BigDecimal.valueOf(0.04)));
        currentSpeed = currentSpeed.max(BigDecimal.valueOf(0));
        setLocomotiveSpeed(currentSpeed);
    }

    private void setLocomotiveSpeed(BigDecimal speed) {
        try {
            locomotive.setSpeed(speed);
        } catch (RailroadHazard e) {
            locomotive.getLogger().log(Level.SEVERE, e.getMessage());
        }
    }

}
