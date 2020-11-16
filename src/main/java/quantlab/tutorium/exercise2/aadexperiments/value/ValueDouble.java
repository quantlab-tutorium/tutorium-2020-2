/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christian-fries.de.
 *
 * Created on 08.11.2020
 */
package quantlab.tutorium.exercise2.aadexperiments.value;

/**
 *
 */
public class ValueDouble implements Value, ConvertableToFloatingPoint {

	private Double value;

	public ValueDouble(Double value) {
		super();
		this.value = value;
	}

	@Override
	public Value squared() {
		return new ValueDouble(value * value);
	}

	@Override
	public Value sqrt() {
		return new ValueDouble(Math.sqrt(value));
	}

	@Override
	public Value add(Value x) {
		return new ValueDouble(this.value + valueOf(x));
	}

	@Override
	public Value sub(Value x) {
		return new ValueDouble(value - valueOf(x));
	}

	@Override
	public Value mult(Value x) {
		return new ValueDouble(value * valueOf(x));
	}

	@Override
	public Value div(Value x) {
		return new ValueDouble(value / valueOf(x));
	}

	@Override
	public Double asFloatingPoint() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	private static double valueOf(Value x) {
		return ((ConvertableToFloatingPoint)x).asFloatingPoint();
	}
}
