package pl.edu.pja.s28687;

import pl.edu.pja.s28687.misc.RailroadHazard;
import pl.edu.pja.s28687.misc.TrainStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Level;

public class MachinistJob extends Thread {
    private final Locomotive locomotive;
    private final BigDecimal distanceToTravel;
    private final int timeUpdateInterval;
    final double timeWarp;
    private final BigDecimal slowDownProcedureDistance = BigDecimal.valueOf(1000);
    private final BigDecimal stationApproachingSpeed;

    public MachinistJob(Locomotive locomotive, BigDecimal distanceToTravel) {
        this(locomotive, distanceToTravel, 1, 1);
    }

    public MachinistJob(Locomotive locomotive, BigDecimal distanceToTravel, int timeUpdateInterval, double timeWarp) {
        this.distanceToTravel = distanceToTravel;
        this.locomotive = locomotive;
        this.timeUpdateInterval = timeUpdateInterval;
        this.timeWarp = timeWarp;
        stationApproachingSpeed = locomotive.getNominalSpeed().multiply(BigDecimal.valueOf(0.3));
    }

    public void directSegment(BigDecimal distanceToTravel){
        locomotive.setCurrentSegmentDistanceCovered(BigDecimal.ZERO);
        locomotive.setStatus(TrainStatus.RUNNING);

        BigDecimal distanceLeft = distanceToTravel;
        boolean reachedNominalSpeed = false;

        BigDecimal currentSpeed;
        BigDecimal nominalSpeed = locomotive.getNominalSpeed();
        while (distanceLeft.compareTo(BigDecimal.ZERO) > 0) {
            currentSpeed = locomotive.getCurrentSpeed();
            if (locomotive.getStatus() == TrainStatus.EMERGENCY) {
                handleEmergency();
                currentSpeed = locomotive.getCurrentSpeed();
            }
            if (!reachedNominalSpeed) {
                currentSpeed = speedUp(currentSpeed, nominalSpeed);
                if (currentSpeed.compareTo(nominalSpeed) >= 0) {
                    reachedNominalSpeed = true;
                }
            }
            try {
                Thread.sleep(timeUpdateInterval);
            } catch (InterruptedException e) {
                throw new RuntimeException("MachinistJob interrupted");
            }
            BigDecimal distanceInterval = calculateDistanceInterval(distanceLeft, currentSpeed);
            distanceLeft = distanceLeft.subtract(distanceInterval);

            currentSpeed = manipulateSpeed(distanceLeft, currentSpeed, nominalSpeed);
            updateLocomotiveValues(currentSpeed, distanceInterval);
        }
        setLocomotiveSpeed(BigDecimal.ZERO);
        locomotive.setStatus(TrainStatus.WAITING);
    }

    private void updateLocomotiveValues(BigDecimal currentSpeed, BigDecimal distanceInterval) {
        locomotive.setCurrentTripDistanceCovered(
                locomotive.getCurrentTripDistanceCovered().add(distanceInterval)
        );
        locomotive.setCurrentSegmentDistanceCovered(
                locomotive.getCurrentSegmentDistanceCovered().add(distanceInterval)
        );
        setLocomotiveSpeed(currentSpeed);
    }

    private BigDecimal manipulateSpeed(BigDecimal distanceLeft, BigDecimal currentSpeed, BigDecimal nominalSpeed) {
        // If the train is approaching station, decrease the speed
        if (
                distanceLeft.compareTo(slowDownProcedureDistance) < 0
                        && currentSpeed.compareTo(stationApproachingSpeed) > 0
        ) {
            currentSpeed = slowDown(currentSpeed, nominalSpeed);

            // Speed changes every second by 3% , so given enough time
            // it will drop to very low values, so this mechanism is used to prevent it:
        } else if (currentSpeed.compareTo(stationApproachingSpeed) < 0) {
            currentSpeed = speedUp(currentSpeed, nominalSpeed);
        }
        //randomly change speed by 3% every 1 second regardless of the distance left and speed
        currentSpeed = randomlyChangeSpeed(currentSpeed);
        return currentSpeed;
    }

    private BigDecimal calculateDistanceInterval(BigDecimal distanceLeft, BigDecimal currentSpeed) {
        BigDecimal distanceInterval =
                currentSpeed
                        .multiply(BigDecimal.valueOf(timeWarp)
                                .multiply(BigDecimal.valueOf(timeUpdateInterval)))
                        .divide(BigDecimal.valueOf(3_600), RoundingMode.FLOOR);

        distanceInterval = distanceInterval.min(distanceLeft);
        return distanceInterval;
    }

    @Override
    public void run() {
        directSegment(distanceToTravel);
    }

    private void handleEmergency() {
        emergencyStop();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException("MachinistJob interrupted");
        }
        restartAfterEmergencyStop();
    }

    public void emergencyStop() {
        setLocomotiveSpeed(BigDecimal.ZERO);
        locomotive.getLogger().log(Level.SEVERE,
                "\nLocomotive " + locomotive.getName() + " , ID : " + locomotive.getId()
                        + " had an emergency at : " + locomotive.getCurrentSegment() + " and had to stop."
                        + " Help is on the way!");
    }

    public void restartAfterEmergencyStop(){
        locomotive.setStatus(TrainStatus.RUNNING);
        locomotive.getLogger().log(Level.SEVERE,
                "\nLocomotive " + locomotive.getName() + " , ID : " + locomotive.getId()
                        + " is back on track!");
    }

    public BigDecimal randomlyChangeSpeed(BigDecimal newSpeed) {
        int direction = Math.random() < 0.5 ? -1 : 1;
        newSpeed = newSpeed
                .add(newSpeed
                        .multiply(
                                BigDecimal.valueOf(direction * 0.03)));
        return newSpeed;
    }

    public BigDecimal speedUp(BigDecimal currentSpeed, BigDecimal nominalSpeed) {
        return currentSpeed.add(nominalSpeed.multiply(BigDecimal.valueOf(0.04)));
    }

    public BigDecimal slowDown(BigDecimal currentSpeed, BigDecimal nominalSpeed) {
        return currentSpeed
                .subtract(nominalSpeed.multiply
                        (BigDecimal.valueOf(0.04)))
                .max(BigDecimal.ZERO);
    }

    private void setLocomotiveSpeed(BigDecimal speed) {
        try {
            locomotive.setCurrentSpeed(speed);
        } catch (RailroadHazard e) {
            locomotive.getLogger().log(Level.SEVERE, e.getMessage());
        }
    }
}
