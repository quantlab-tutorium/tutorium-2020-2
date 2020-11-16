/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christian-fries.de.
 *
 * Created on 08.11.2020
 */
package quantlab.tutorium.solution2.aadexperiments.value;

import quantlab.tutorium.solution2.aadexperiments.ConvertableToFloatingPoint;
import quantlab.tutorium.solution2.aadexperiments.DiffernetiationExperimentHypothenuse;
import quantlab.tutorium.solution2.aadexperiments.Value;
import quantlab.tutorium.solution2.aadexperiments.ValueFactory;

/**
 * Implementation of {@link Value} using float.
 *
 * @author Roland Bachl
 *
 */
public class MyValue implements Value, ConvertableToFloatingPoint {

	public static void main(String[] args) {

		DiffernetiationExperimentHypothenuse.runExperiment(getFactory());

		System.out.println("Using custom epsilon:");
		/*
		 * Note: The behavior of the program will change, depending on which implementation is used for Value. Use
		 * either ValueDouble or ValueDoubleDifferentiable.
		 */
		Value a = new MyValue(7.0f);
		Value b = new MyValue(24.0f);
		Value epsilon = new MyValue(1E-2f);

		DiffernetiationExperimentHypothenuse.runExperiment(a, b, epsilon);
	}

	private float value;

	public MyValue(float value) {
		super();
		this.value = value;
	}

	public MyValue(double value) {
		this((float) value);
	}

	@Override
	public Value squared() {
		return new MyValue(value * value);
	}

	@Override
	public Value sqrt() {
		return new MyValue(Math.sqrt(value));
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
		return (double) value;
	}

	@Override
	public String toString() {
		return "" + value;
	}

	public static ValueFactory getFactory() {
		return new ValueFactory() {

			@Override
			public MyValue getValue(double x) {
				return new MyValue(x);
			}
		};
	}

	private static double valueOf(Value x) {
		return ((ConvertableToFloatingPoint) x).asFloatingPoint();
	}
}
