package quantlab.tutorium.exercise1;

import net.finmath.exception.CalculationException;
import net.finmath.functions.AnalyticFormulas;
import net.finmath.montecarlo.assetderivativevaluation.AssetModelMonteCarloSimulationModel;
import net.finmath.montecarlo.assetderivativevaluation.products.AbstractAssetMonteCarloProduct;
import net.finmath.montecarlo.assetderivativevaluation.products.EuropeanOption;

/**
 * Price european call and put options via Monte-Carlo simulation of a Black-Scholes model.
 *
 * @author Roland Bachl
 *
 */
public class DerivativePricing {

	// Parameters for the model
	private final double initialValue;
	private final double riskFreeRate;
	private final double volatility;

	// parameters for the derivative
	private final double maturity;
	private final double strike;

	// Parameters for the process
	private final int seed;
	private final int numberOfFactors;
	private final int numberOfPaths;
	private final int numberOfTimeSteps;

	// Cache
	private transient AssetModelMonteCarloSimulationModel model;


	public DerivativePricing(double initialValue, double riskFreeRate, double volatility, double maturity,
			double strike, int seed, int numberOfFactors, int numberOfPaths, int numberOfTimeSteps) {
		super();
		this.initialValue = initialValue;
		this.riskFreeRate = riskFreeRate;
		this.volatility = volatility;
		this.maturity = maturity;
		this.strike = strike;
		this.seed = seed;
		this.numberOfFactors = numberOfFactors;
		this.numberOfPaths = numberOfPaths;
		this.numberOfTimeSteps = numberOfTimeSteps;
	}

	public static void main(String[] args) {

		// Test parameters
		final double tolerance = 0.1;

		// Initialize DerivativePricing
		DerivativePricing test = new DerivativePricing(
				100 /* initialValue */,
				0.1 /* riskFreeRate */,
				0.25 /* volatility */,
				1 /* maturity */,
				120 /* strike */,
				12345 /* seed */,
				1 /* numberOfFactors */,
				100000 /* numberOfPaths */,
				500 /* numberOfTimeSteps */
				);

		// Initialize this before try-catch
		double productValue = 0;

		// Evaluate call
		try {
			productValue = test.callOptionValue();
		} catch (CalculationException e) {
			e.printStackTrace();
		}

		// Calculate reference value
		double referenceValue = AnalyticFormulas.blackScholesOptionValue(test.initialValue, test.riskFreeRate,
				test.volatility, test.maturity, test.strike);

		// Check error
		double error = Math.abs(productValue - referenceValue);

		System.out.println("Call value:");
		System.out.println("MC:        " + productValue);
		System.out.println("Analytic:  " + referenceValue);
		System.out.println("Error:     " + error);
		if (error > tolerance) {
			System.out.println("Test failed!");
		}
		System.out.println("\n\n");

		// Evaluate put
		try {
			productValue = test.putOptionValue();
		} catch (CalculationException e) {
			e.printStackTrace();
		}

		// Calculate reference value
		referenceValue = AnalyticFormulas.blackScholesOptionValue(test.initialValue, test.riskFreeRate, test.volatility,
				test.maturity, test.strike, false);

		// Check error
		error = Math.abs(productValue - referenceValue);

		System.out.println("Put value:");
		System.out.println("MC:        " + productValue);
		System.out.println("Analytic:  " + referenceValue);
		System.out.println("Error:     " + error);
		if (error > tolerance) {
			System.out.println("Test failed!");
		}
	}

	/**
	 * Build the model to evaluate products.
	 *
	 * @return The model.
	 */
	public AssetModelMonteCarloSimulationModel getModel() {

		// Lazy initialization plus caching.
		if (model != null) {
			return model;
		}

		// TODO Create the model
		// Setup mathematical model


		// Setup process
		// Time discretization

		// Brownian motion


		// Combine in simulation model

		return null;
	}

	/**
	 * Calculate the value of a European call option.
	 *
	 * @return The option value
	 * @throws CalculationException
	 */
	public double callOptionValue() throws CalculationException {
		// Setup product
		AbstractAssetMonteCarloProduct product = new EuropeanOption(maturity, strike);

		// Evaluate call
		return product.getValue(getModel());
	}

	/**
	 * Calculate the value of a European put option.
	 *
	 * @return The option value
	 * @throws CalculationException
	 */
	public double putOptionValue() throws CalculationException {
		// TODO Calculate the value

		return 0;
	}
}
