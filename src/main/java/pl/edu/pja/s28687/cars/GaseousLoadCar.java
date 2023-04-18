package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.load.IGaseous;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.math.BigDecimal;
import java.util.Set;


public class GaseousLoadCar extends BasicFreightCarABC<IGaseous> implements IGasCarrier{
    private static final String SHIPPER = "TOYOTA";
    private static final String SECURITY_INFO =
            """
                    Warning: This is a gaseous load car.
                    Please keep a safe distance and do not approach.
                    Do not attempt to open any valves or containers
                    without proper authorization and training.
                    In case of an emergency, follow the evacuation procedures
                    and contact trained personnel immediately.
                    """;

    public GaseousLoadCar(int id, ICarLoadValidator validator) {
        super(id, SHIPPER, SECURITY_INFO, validator);
    }

    @Override
    public Set<LoadType> allowedLoadFlags() {
        return Set.of(LoadType.GASEOUS);
    }

    @Override
    public void safetyCheck() {
        checkValves();
        checkPressure();
        checkTemperature();
    }

    @Override
    public void emergencyUnloading() {
        System.out.println("Starting emergency unloading procedure");
        openValve();
        for (IGaseous gaseous : getLoads()) {
            unLoad(gaseous);
        }
        closeValve();
    }

    @Override
    public String getCargoStats() {
        return null;
    }

    @Override
    public CarType getCarType() {
        return CarType.GASEOUS;
    }

    @Override
    public void emergencyProcedure() {
        closeValve();
        System.out.println("Starting emergency procedure in car " + getId());
        if (getLocomotive().isPresent()){
            Locomotive loco = getLocomotive().get();
            loco.raiseAlert("Emergency in car " + getId() +
                    "!! Please send staff to check the situation immediately!");
        }

    }

    @Override
    public void routineProcedure() {
        checkPressure();
    }

    @Override
    public boolean isPowered() {
        return false;
    }

    @Override
    public void openValve() {
        safetyUnlock();
    }

    @Override
    public void closeValve() {
        safetyLock();
    }

    @Override
    public BigDecimal getPressure() {
        return null;
    }

    @Override
    public BigDecimal getTemperature() {
        return null;
    }

    private void checkValves() {
        System.out.println("Checking valves");
    }

    private void checkPressure() {
        System.out.println("Checking pressure");
        System.out.println("Pressure is " + getPressure());
    }

    private void checkTemperature() {
        System.out.println("Checking temperature");
        System.out.println("Temperature is " + getTemperature());
    }
}
