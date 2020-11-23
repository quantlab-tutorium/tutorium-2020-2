/**
 *
 */
package quantlab.tutorium.exercise3;

import quantlab.tutorium.exercise3.Car.CarFactory;
import quantlab.tutorium.exercise3.Car.Location;

/**
 * Buy a car and take take it for a ride!
 *
 * @author Roland Bachl
 *
 */
public class CarShop {

	private static Location home = Location.of(0, 0);
	private static Location shop = Location.of(30, 15);
	private static Location gasStation = Location.of(32, -7);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CarFactory factory = null;

		Car car = buyACar(factory);

		if (car != null) {
			goForARide(car);
		}
	}

	/**
	 * @param factory
	 * @return
	 */
	public static Car buyACar(CarFactory factory) {

		double distance = factory.getLocation().distanceTo(home);
		Car car;

		if (distance <= factory.pickUpCar().getRange()) {
			System.out.println("Alright, i can pick the car up myself!");
			car = factory.pickUpCar();
			car.moveTo(home);
			car.refuel();
		} else {
			System.out.println("Oh no, I guess, I will have to have the car shipped.");
			car = factory.deliverCar(home);
		}

		return car;
	}

	/**
	 * @param car
	 */
	public static void goForARide(Car car) {

		System.out.println("Let's go do some groceries.");
		if (!shop.equals(car.moveTo(shop))) {
			refund(car.getLocation());
		}

		System.out.println("Now let's refuel, just in case.");
		if (!gasStation.equals(car.moveTo(gasStation))) {
			refund(car.getLocation());
		}
		car.refuel();

		System.out.println("Ok, I'm going home now.");
		if (!home.equals(car.moveTo(home))) {
			refund(car.getLocation());
		}

		System.out.println("What a great car!");
	}

	/**
	 * @param location
	 */
	public static void refund(Location location) {
		System.out.println("Your car broke down at {" + location + "}. I want a refund!");
	}
}
