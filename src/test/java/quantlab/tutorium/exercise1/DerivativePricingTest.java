package quantlab.tutorium.exercise1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.finmath.exception.CalculationException;
import net.finmath.functions.AnalyticFormulas;

/**
 * Test class for {@link DerivativePricing}.
 *
 * @author Roland Bachl
 *
 */
public class DerivativePricingTest {

	// Test parameters
	static final double tolerance = 0.1;

	// Parameters for the model
	static final double initialValue = 100;
	static final double riskFreeRate = 0.1;
	static final double volatility = 0.25;

	// Parameters for the process
	static final int seed = 12345;
	static final int numberOfFactors = 1;
	static final int numberOfPaths = 100000;
	static final int numberOfTimeSteps = 500;

	// parameters for the derivative
	static final double maturity = 1;
	static final double strike = 120;

	// Fields for testing
	static double productValue;
	static double referenceValue;
	static DerivativePricing test;

	@BeforeAll
	public static void setup() throws CalculationException {
		test = new DerivativePricing(initialValue, riskFreeRate, volatility, maturity, strike, seed, numberOfFactors,
				numberOfPaths, numberOfTimeSteps);

		// Do this to precalculate the process
		test.getModel().getAssetValue(0, 0);
	}

	@Test
	public void testCall() throws CalculationException {
		System.out.println("Testing call option value:");

		// Determine call value
		productValue = test.callOptionValue();

		// Check against reference value
		referenceValue = AnalyticFormulas.blackScholesOptionValue(
				initialValue,
				riskFreeRate,
				volatility,
				maturity,
				strike);
		assertEquals(referenceValue, productValue, tolerance);
	}

	@Test
	public void testPut() throws CalculationException {
		System.out.println("Testing put option value:");

		// Determine put value
		productValue = test.putOptionValue();

		// Check against reference value
		referenceValue = AnalyticFormulas.blackScholesOptionValue(
				initialValue,
				riskFreeRate,
				volatility,
				maturity,
				strike,
				false);
		assertEquals(referenceValue, productValue, tolerance);
	}

	@AfterEach
	public void printAndCleanUp() {
		System.out.println("MC:        " + productValue);
		System.out.println("Analytic:  " + referenceValue);
		System.out.println("\n\n");

		productValue = 0;
	}
}
