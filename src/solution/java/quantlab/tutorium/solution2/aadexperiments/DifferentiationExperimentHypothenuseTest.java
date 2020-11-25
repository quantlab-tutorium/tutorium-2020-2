/**
 *
 */
package quantlab.tutorium.solution2.aadexperiments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import quantlab.tutorium.solution2.aadexperiments.value.MyValue;
import quantlab.tutorium.solution2.aadexperiments.value.ValueDifferentiableGeneric;
import quantlab.tutorium.solution2.aadexperiments.value.ValueDoubleDifferentiableFactory;
import quantlab.tutorium.solution2.aadexperiments.value.ValueDoubleFactory;

/**
 * @author Roland Bachl
 *
 */
class DifferentiationExperimentHypothenuseTest {

	@ParameterizedTest
	@MethodSource
	void test(ValueFactory<?> factory) {

		Value a = factory.getValue(7.0);
		Value b = factory.getValue(24.0);

		Value c = hypotenuse(a, b);

		/*
		 * Partial derivative dz/dx
		 */
		ConvertableToFloatingPoint dzdxAnalytic = (ConvertableToFloatingPoint) a.div(c);

		/*
		 * Partial derivative dz/dx by algorithmic differentiation - we need to have that the objects are implementing
		 * <code>ValueDifferentiable</code>.
		 */
		ConvertableToFloatingPoint dzdxAlgorithmicDifferentiation = (ConvertableToFloatingPoint)
				((ValueDifferentiable) c).getDerivativeWithRespectTo((ValueDifferentiable) a);

		assertEquals(dzdxAnalytic.asFloatingPoint(), dzdxAlgorithmicDifferentiation.asFloatingPoint());
	}

	private static Stream<ValueFactory<? extends Value>> test() {
		return Stream.of(
				new ValueDoubleFactory(),
				new ValueDoubleDifferentiableFactory(),
				MyValue.getFactory(),
				ValueDifferentiableGeneric.getFactory(MyValue.getFactory()),
				ValueDifferentiableGeneric.getFactory(new ValueDoubleFactory())
				);
	}

	/**
	 * Calculates the hypotenuse on objects implementing <code>Value</code>-interface.
	 *
	 * @param a The parameter a in sqrt(a^2 + b^2).
	 * @param b The parameter b in sqrt(a^2 + b^2).
	 * @return The value of c = sqrt(a^2 + b^2)
	 */
	private static Value hypotenuse(Value a, Value b) {
		Value c = (a.squared().add(b.squared())).sqrt();
		return c;
	}
}
