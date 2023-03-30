package pl.edu.pja.s28687.info;
import pl.edu.pja.s28687.cars.PassengerCar;
import pl.edu.pja.s28687.cars.RailroadCar;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.misc.TrainStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class AggregateInfo {
    public static String getInfo(LocoBase locoBase) {

        List<Locomotive> locList = locoBase.getLocomotiveList();
        List<RailroadCar> carList = locoBase.getRailroadCarsList();

        BigDecimal locsTotal = BigDecimal.valueOf(
                locList.size());
        BigDecimal locsRunning = BigDecimal.valueOf(
                locList
                        .stream()
                        .filter(l -> l.getStatus()
                                .equals(TrainStatus.RUNNING))
                        .count());
        BigDecimal carsTotal = BigDecimal.valueOf(carList.size());
        BigDecimal carsAttached = BigDecimal.valueOf(
                carList
                        .stream()
                        .filter(RailroadCar::isAttached)
                        .count());
        BigDecimal totalPayload = locList
                .stream()
                .map(Locomotive::getCurrentFreight)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        BigDecimal maxSpeed = locList
                .stream()
                .map(Locomotive::getCurrentSpeed)
                .reduce(BigDecimal::max)
                .orElse(BigDecimal.ZERO);
        BigDecimal averageTripDistance = locList
                .stream()
                .map(Locomotive::getCurrentTripDistance)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .divide(BigDecimal.valueOf(locList.stream()
                        .filter(loc -> loc.getCurrentTripDistance()
                                .compareTo(BigDecimal.ZERO) > 0)
                        .count()).max(BigDecimal.ONE), RoundingMode.FLOOR);
        BigDecimal passengerCarsCarried = BigDecimal.valueOf(
                carList
                        .stream()
                        .filter(car -> car instanceof PassengerCar)
                        .filter(RailroadCar::isAttached)
                        .count());
        BigDecimal passengersOnBoard = BigDecimal.valueOf(
                carList
                        .stream()
                        .filter(car -> car instanceof PassengerCar)
                        .filter(RailroadCar::isAttached)
                        .map(car -> ((PassengerCar) car)
                                .getNumberOfPassengers())
                        .reduce(Integer::sum)
                        .orElse(0));


        StringBuilder aggregateInfo = new StringBuilder(500)
                .append("\nLocomotives total : ").append(locsTotal)
                .append("\nLocomotives running : ").append(locsRunning)
                .append("\nRailroad cars total : ").append(carsTotal)
                .append("\nRailroad cars attached : ").append(carsAttached)
                .append("\nTotal payload : ").append(totalPayload).append(" tonnes")
                .append("\nMax train speed : ").append(maxSpeed).append(" km/h")
                .append("\nAverage route distance : ").append(averageTripDistance).append(" km")
                .append("\nPassenger cars carried : ").append(passengerCarsCarried)
                .append("\nPassengers onboarded : ").append(passengersOnBoard);
        return aggregateInfo.toString();
    }
}