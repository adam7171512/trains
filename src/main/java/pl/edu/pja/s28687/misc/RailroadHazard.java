package pl.edu.pja.s28687.Misc;

public class RailroadHazard extends Exception {
    public RailroadHazard() {
        super();
    }

    public RailroadHazard(String message) {
        super(message);
    }

    public RailroadHazard(String message, Throwable cause) {
        super(message, cause);
    }
}