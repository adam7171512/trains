package pl.edu.pja.s28687.info;

import pl.edu.pja.s28687.cars.PassengerCar;
import pl.edu.pja.s28687.cars.RailroadCar;

public class CarInfo {
    public static String getBasicInfo(RailroadCar car){
        StringBuilder stringBuilder = new StringBuilder().
                append("Car ID : ").append(car.getId()).
                append(" | Car type : ").append(car.getCarType()).
                append(" | Payload : ").
                append(car.getCurrentWeight()).
                append(" out of ").
                append(car.getGrossWeight()).append(" tonnes");

        if (car instanceof PassengerCar) {
            stringBuilder
                    .append(" | Passengers : ")
                    .append(((PassengerCar) car)
                            .getNumberOfPassengers())
                    .append(" / ")
                    .append(((PassengerCar) car)
                            .getNumberOfSeats());
        }
        return stringBuilder.toString();
    }
}
