package pl.edu.pja.s28687.Logistics;

import java.math.BigDecimal;
import java.util.Objects;

public class Coordinates implements Comparable<Coordinates>{
    private double x;
    private double y;
    public Coordinates(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Coordinates(BigDecimal x, BigDecimal y) {
        this.x = x.doubleValue();
        this.y = y.doubleValue();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates that)) return false;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
    }



    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Coordinates o) {
        double thisDist = Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2));
        double otherDist = Math.sqrt(Math.pow(o.getX(), 2) + Math.pow(o.getY(), 2));
        return BigDecimal.valueOf(thisDist).compareTo(BigDecimal.valueOf(otherDist)) ;
    }

    public static double distance(Coordinates coordinates1, Coordinates coordinates){
        return Math.sqrt(Math.pow(coordinates.x - coordinates1.x, 2) + Math.pow(coordinates.y - coordinates1.y, 2));
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
