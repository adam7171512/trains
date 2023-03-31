package pl.edu.pja.s28687;

public interface IValidator <T, E>{
    boolean validate(T component, E receiver);
}
