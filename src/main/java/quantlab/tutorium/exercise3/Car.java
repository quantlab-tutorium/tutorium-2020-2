/**
 *
 */
package quantlab.tutorium.exercise3;

/**
 * Interface representing a basic car.
 *
 * @author Roland Bachl
 *
 */
public interface Car {

	/**
	 * @return The current location of the car.
	 */
	Location getLocation();

	/**
	 * try to move the car to the given location.
	 *
	 * @param target The desired location.
	 * @return The location the car ends up at.
	 */
	Location moveTo(Location target);

	/**
	 * Refuel the car.
	 *
	 * @return The amount of fuel refilled.
	 */
	double refuel();

	/**
	 * @return The current range of the car, given its level of fuel.
	 */
	double getRange();

	/**
	 * @return The speed the car is moving at.
	 */
	double getSpeed();

	/**
	 * @return The weight of the car.
	 */
	double getWeight();

	/**
	 * @return The total distance this car has traveled since construction.
	 */
	double getOdometer();

	/**
	 * @return The distance this car has traveled since the beginning of the trip.
	 */
	double getTripOdometerd();

	/**
	 * Reset the trip odometer to 0.
	 */
	void resetTripOdometer();

	/**
	 * A factory that produces a {@link Car}.
	 *
	 * @author Roland Bachl
	 *
	 */
	public interface CarFactory {

		/**
		 * @return The location of the factory.
		 */
		Location getLocation();

		/**
		 * Construct a car and pick it up from the factory itself.
		 *
		 * @return The car.
		 */
		default Car pickUpCar() {
			return deliverCar(getLocation());
		}

		/**
		 * Construct a car at the factory, but deliver it to a chosen location.
		 *
		 * @param destination The destionation of delivery.
		 * @return The car.
		 */
		Car deliverCar(Location destination);
	}

	/**
	 * Location system to be used by cars.
	 *
	 * @author Roland Bachl
	 *
	 */
	class Location {
		private final double x;
		private final double y;

		/**
		 * Create a location from Cartesian coordinates.
		 *
		 * @param x x-coordinate
		 * @param y y-coordinate
		 * @return The location
		 */
		public static Location of(double x, double y) {
			return new Location(x, y);
		}

		/**
		 * Create a location from Cartesian coordinates.
		 *
		 * @param x x-coordinate
		 * @param y y-coordinate
		 */
		private Location(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		/**
		 * @return The x coordinate of the location.
		 */
		public double getX() {
			return x;
		}

		/**
		 * @return The y coordinate of the location.
		 */
		public double getY() {
			return y;
		}

		/**
		 * Calculate a location that is between the point and the destination, but a fraction away.
		 *
		 * @param destination
		 * @param fraction
		 * @return The point a fration from the destination.
		 */
		public Location between(Location destination, double fraction) {
			double x = this.x + fraction * (destination.x - this.x);
			double y = this.y + fraction * (destination.y - this.y);
			return new Location(x, y);
		}

		// We need to manually override this, if we want to do meaningful equals checks. (Otherwise it will function
		// like a==b, which only returns true, if they are the same object, but it will not work for two separate
		// objects with the same location.
		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if (!(o instanceof Location)) {
				return false;
			}
			Location l = (Location) o;
			return x == l.x && y == l.y;
		}

		// Don't get confused by this. It's mostly here because it is good practice to also provide hashCode, when
		// overriding equals. It's main use is the HashMap.
		@Override
		public int hashCode() {
			return Double.valueOf(x + y).hashCode();
		}

		/**
		 * Calculates the Euclidean distance between this point and the target.
		 *
		 * @param destination The destination location.
		 * @return The Eulcidean distance.
		 */
		public double distanceTo(Location destination) {
			double deltaX = x - destination.x;
			double deltaY = y - destination.y;

			return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		}

		@Override
		public String toString() {
			return "x= " + x + ", y= " + y;
		}
	}
}
