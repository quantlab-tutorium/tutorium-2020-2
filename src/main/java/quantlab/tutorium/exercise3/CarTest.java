/**
 *
 */
package quantlab.tutorium.exercise3;

import java.util.List;

import quantlab.tutorium.exercise3.Car.Location;

/**
 * This tests cars from different manufacturers against each other. How fast can one get from the first location to the
 * last. Not all locations need to be visited along the way. But the cars are only allowed to refuel at these stops.
 * Which car gets to the end first? Taking into account its speed and range!
 *
 * @author Roland Bachl
 *
 */
public class CarTest {

	private static List<Location> locations = List.of(
			Location.of(0, 0),
			Location.of(0, 100),
			Location.of(30, 250),
			Location.of(70, 310),
			Location.of(-50, 470),
			Location.of(-23.7, 620),
			Location.of(60, 840),
			Location.of(210, 980),
			Location.of(320, 1160),
			Location.of(110, 1310),
			Location.of(-12, 1370),
			Location.of(-47, 1670),
			Location.of(-118, 1833),
			Location.of(-127, 2143),
			Location.of(-237, 2570),
			Location.of(-323, 2876),
			Location.of(-290, 3000),
			Location.of(30, 3020),
			Location.of(233, 3117),
			Location.of(180, 3320),
			Location.of(243, 3721),
			Location.of(238, 4107),
			Location.of(168, 4404),
			Location.of(84, 4803),
			Location.of(0, 5000));


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
