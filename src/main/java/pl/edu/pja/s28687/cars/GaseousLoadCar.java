package pl.edu.pja.s28687.cars;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.IGaseous;
import pl.edu.pja.s28687.load.ILiquid;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.misc.TrainStatus;
import pl.edu.pja.s28687.validators.ICarLoadValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.logging.Level;


public class GaseousLoadCar extends AbstractBasicFreightCar<IGaseous> implements IGasCarrier{
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

    private static final BigDecimal VOLUME = BigDecimal.valueOf(112);

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
        logger.log(Level.SEVERE, "Starting emergency unloading procedure");
        openValve();
        for (IGaseous gaseous : getLoads()) {
            unLoad(gaseous);
        }
        closeValve();
    }

    @Override
    public String getCargoStats() {
        StringBuilder stats = new StringBuilder()
                .append("\nGas load total weight: ")
                .append(getCargoWeight())
                .append(" tonnes")
                .append("\nunits loaded: ")
                .append(getLoads().size())
                .append("\n");
        getLoads()
                .stream()
                .map(IDeliverable::getBasicInfo)
                .forEach( s -> stats.append(s).append("\n"));
        return stats.toString();
    }

    @Override
    public CarType getCarType() {
        return CarType.GASEOUS;
    }

    @Override
    public void emergencyProcedure() {
        closeValve();
        logger.log(Level.SEVERE, "Starting emergency procedure in car " + getId());
        if (getLocomotive().isPresent()){
            Locomotive loco = getLocomotive().get();
            loco.raiseAlert("Emergency in car " + getId() +
                    "!! Please send staff to check the situation immediately!");
            loco.setStatus(TrainStatus.EMERGENCY);
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
    public String getBasicInfo() {
        return this.toString() + "\nPayload :  " + getCargoWeight()
                + "  / " + getMaxPayload() + " tonnes, " + getPressure() + " bar,  temp: " + getTemperature() + " C in"
                + " / " + getVolume() + " m3 chamber," + " compatible load types : " + getAllowedLoadFlags();
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
        BigDecimal fillRatio =
                getCurrentWeight().divide(getGrossWeight(), RoundingMode.FLOOR);
        return BigDecimal.ONE.add(fillRatio)
                .multiply(BigDecimal.valueOf(20));
    }

    @Override
    public BigDecimal getTemperature() {
        return BigDecimal.valueOf(Math.random() * 20)
                .add(BigDecimal.valueOf(40)).setScale(2, RoundingMode.FLOOR);
    }

    @Override
    public BigDecimal getVolume() {
        return VOLUME;
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
