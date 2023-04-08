package pl.edu.pja.s28687.consoleInterface.objectCreationMenus.individual;

import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.factories.LocomotiveBuilder;
import pl.edu.pja.s28687.validators.locomotive.ILocomotiveCarValidator;
import pl.edu.pja.s28687.validators.locomotive.LocomotiveCarValidatorForCurrentCarWeight;
import pl.edu.pja.s28687.validators.locomotive.LocomotiveCarValidatorForMaxCarWeight;
import pl.edu.pja.s28687.validators.locomotive.PassengerTrainCarValidator;

import java.math.BigDecimal;

public class LocomotiveCreation extends AbstractLeafMenu {
    @Override
    public void menuSpecificAction() {
        System.out.println("Please enter Locomotive name");
        String name = scan.nextLine();
        System.out.println("Please enter maximum number of cars attachable");

        int maxCars;
        do {
            maxCars = resourceContainer.parseToInt(scan.nextLine());
        } while (maxCars < 0);

        System.out.println("Please enter maximum allowable payload in tonnes");

        double maxPayload;
        do {
            maxPayload = resourceContainer.parseToDouble(scan.nextLine());
        } while (maxPayload < 0);

        BigDecimal freight = BigDecimal.valueOf(maxPayload);

        System.out.println("Please enter maximum number of cars with power supply");
        int maxPoweredCars;
        do {
            maxPoweredCars = resourceContainer.parseToInt(scan.nextLine());
        } while (maxPoweredCars < 0);

        System.out.println("Please enter locomotive speed in km/h");

        double nominalSpeed;
        do {
            nominalSpeed = resourceContainer.parseToDouble(scan.nextLine());
        } while (nominalSpeed < 0);
        BigDecimal speed = BigDecimal.valueOf(nominalSpeed);

        int validator;
        do {
            System.out.println("Please select railroad car validator:");
            System.out.println("1. Validate by current weigh");
            System.out.println("2. Validate by max weight");
            System.out.println("3. Allow only passenger, restaurant, luggage and post office cars");
            validator = resourceContainer.parseToInt(scan.nextLine());
        } while (validator < 1 || validator > 3);
        ILocomotiveCarValidator locomotiveCarValidator = switch (validator) {
            case 1 -> new LocomotiveCarValidatorForCurrentCarWeight();
            case 2 -> new LocomotiveCarValidatorForMaxCarWeight();
            case 3 -> new PassengerTrainCarValidator();
            default -> throw new IllegalStateException("Unexpected value: " + validator);
        };
        new LocomotiveBuilder(resourceContainer.getLocoBase()).setName(name)
                .setRegularCars(maxCars)
                .setPoweredCars(maxPoweredCars)
                .setMaxPayload(freight)
                .setDefaultSpeed(speed)
                .setCarValidator(locomotiveCarValidator).build();
    }

    @Override
    public String getTitle() {
        return "Locomotive creation";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
