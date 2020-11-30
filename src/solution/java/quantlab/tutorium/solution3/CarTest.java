/**
 *
 */
package quantlab.tutorium.solution3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import quantlab.tutorium.exercise3.Car;
import quantlab.tutorium.exercise3.Car.CarFactory;
import quantlab.tutorium.exercise3.Car.Location;

/**
 * This tests cars from different manufacturers against each other. How fast can one get from the first location to the
 * last. Not all locations need to be visited along the way. But the cars are only allowed to refuel at these stops.
 * Which car gets to the end first? Taking into account its speed and range!
 *
 * @author Roland Bachl
 *
 */
public class CarTest implements Runnable {

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

	public static void main(String[] args) {

		// TODO please provide your factory here.
		CarFactory factory = null;

		CarTest test = new CarTest(factory);

		long time = System.currentTimeMillis();
		test.run();
		time = System.currentTimeMillis() - time;

		System.out.println("Test completed in " + time + " ms.\n");
		System.out.println("Intermediate stops taken: \t\t" + test.numberOfIntermediateStops());
		System.out.println("Total distance travelled: \t\t" + test.totalDistance());
		System.out.println("Total time on road: \t\t\t" + test.timeOnRoad());
	}

	private final CarFactory factory;
	private Car car;

	private List<Location> shortestRoute;
	private double shortestDistance;

	public CarTest(CarFactory factory) {
		super();
		this.factory = factory;
	}

	@Override
	public void run() {

		prepareCar();

		double odometerStart = car.getOdometer();

		calculateShortestRoute();
		driveRoute();
		checkCar(odometerStart);
	}

	private void prepareCar() {
		Car car = factory.deliverCar(locations.get(0));
		if (!car.getLocation().equals(locations.get(0))) {
			throw new RuntimeException("Factory failed to deliver car.");
		}
		car.refuel();
		car.resetTripOdometer();
		this.car = car;
	}

	private void calculateShortestRoute() {
		double range = car.getRange();

		double[] distances = new double[locations.size()];
		Arrays.fill(distances, Double.POSITIVE_INFINITY);
		distances[0] = 0;
		int[] precedingNodes = new int[locations.size()];

		for (int current = 0; current < locations.size() - 1; current++) {
			for (int next = current + 1; next < locations.size(); next++) {
				double segment = locations.get(current).distanceTo(locations.get(next));

				if (segment > range) {
					break;
				}

				double distance = segment + distances[current];
				if (distance < distances[next]) {
					distances[next] = distance;
					precedingNodes[next] = current;
				}
			}
		}

		if (distances[distances.length - 1] == Double.POSITIVE_INFINITY) {
			throw new RuntimeException("Failed to calculate possible route for car.");
		}

		shortestDistance = distances[distances.length - 1];
		List<Location> route = new ArrayList<Location>();
		int index = locations.size() - 1;
		while (index > 0) {
			route.add(locations.get(index));
			index = precedingNodes[index];
		}

		Collections.reverse(route);
		shortestRoute = Collections.unmodifiableList(route);
	}

	//	private void calculateShortestRouteAlternative() {
	//		double range = car.getRange();
	//
	//		Map<Location, Double> distances = new HashMap<Location, Double>(locations.size() - 1);
	//		Map<Location, Location> precedingNodes = new HashMap<Car.Location, Car.Location>(locations.size() - 1);
	//
	//		List<Location> locationsToCheck = new ArrayList<Location>(locations);
	//
	//		while (locationsToCheck.size() > 1) {
	//			Location current = locationsToCheck.remove(0);
	//
	//			for (Location next : locationsToCheck) {
	//				double segment = current.distanceTo(next);
	//				if (segment > range) {
	//					break;
	//				}
	//
	//				double distance = segment + distances.getOrDefault(current, 0.0);
	//				if (distance < distances.getOrDefault(next, Double.POSITIVE_INFINITY)) {
	//					distances.put(next, distance);
	//					precedingNodes.put(next, current);
	//				}
	//			}
	//		}
	//
	//		Double finalDistance = distances.getOrDefault(locations.get(locations.size() - 1),
	//				Double.POSITIVE_INFINITY);
	//		if (finalDistance.isInfinite()) {
	//			throw new RuntimeException("Failed to calculate possible route for car.");
	//		}
	//
	//		shortestDistance = finalDistance;
	//		List<Location> route = new ArrayList<Location>();
	//		Location stop = locations.get(locations.size() - 1);
	//
	//		while (!stop.equals(locations.get(0))) {
	//			route.add(stop);
	//			stop = precedingNodes.get(stop);
	//		}
	//
	//		Collections.reverse(route);
	//		shortestRoute = Collections.unmodifiableList(route);
	//
	//	}

	private void driveRoute() {
		for (Location stop : shortestRoute) {
			car.moveTo(stop);
			car.refuel();
		}
	}

	private void checkCar(double odometerStart) {
		if (!car.getLocation().equals(locations.get(locations.size() - 1))) {
			throw new RuntimeException("Car failed to follow calculated route.");
		}

		if ((car.getTripOdometerd() != shortestDistance) || (car.getOdometer() != odometerStart + shortestDistance)) {
			throw new RuntimeException("Car failed to record travelled distance.");
		}
	}

	public int numberOfIntermediateStops() {
		return shortestRoute.size() - 1;
	}

	public double totalDistance() {
		return shortestDistance;
	}

	public double timeOnRoad() {
		return shortestDistance / car.getSpeed();
	}
}
