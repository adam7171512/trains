package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.load.IPassengers;

import java.math.BigDecimal;

public interface IPassengerCarrier extends ILoadCarrier<IPassengers>{
    int getNumberOfSeats();
    int getNumberOfPassengers();
}
