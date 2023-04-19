package pl.edu.pja.s28687.cars;

public interface IPowered {
    default void cutOffPower() {
        System.out.println("Cutting off power");
    }

    default void turnOnPower() {
        System.out.println("Turning on power");
    }
}
