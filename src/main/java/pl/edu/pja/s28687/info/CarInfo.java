package pl.edu.pja.s28687.Info;

import pl.edu.pja.s28687.Cars.PassengerCar;
import pl.edu.pja.s28687.Cars.RailroadCar;

public class CarInfo {
    public static String getBasicInfo(RailroadCar car){
        StringBuilder stringBuilder = new StringBuilder().
                append("Car ID : ").append(car.getId()).
                append(" | Freight type : ").append(car.getAllowable()).
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
