package quantlab.tutorium.solution1;

import net.finmath.exception.CalculationException;
import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.BrownianMotionFromMersenneRandomNumbers;
import net.finmath.montecarlo.assetderivativevaluation.AssetModelMonteCarloSimulationModel;
import net.finmath.montecarlo.assetderivativevaluation.MonteCarloAssetModel;
import net.finmath.montecarlo.assetderivativevaluation.models.BlackScholesModel;
import net.finmath.montecarlo.assetderivativevaluation.products.AbstractAssetMonteCarloProduct;
import net.finmath.montecarlo.assetderivativevaluation.products.EuropeanOption;
import net.finmath.montecarlo.model.AbstractProcessModel;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationFromArray;

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

	/**
	 * Build the model to evaluate products.
	 *
	 * @return THe model.
	 */
	public AssetModelMonteCarloSimulationModel getModel() {

		// Lazy initialization plus caching.
		if (model != null) {
			return model;
		}

		// Setup mathematical model
		AbstractProcessModel baseModel = new BlackScholesModel(initialValue, riskFreeRate, volatility);

		// Setup process
		// Time discretization
		TimeDiscretization timeDiscretization = new TimeDiscretizationFromArray(0, numberOfTimeSteps,
				maturity / numberOfTimeSteps);

		// Brownian motion
		BrownianMotion stochasticDriver = new BrownianMotionFromMersenneRandomNumbers(timeDiscretization,
				numberOfFactors, numberOfPaths, seed);

		// Combine in simulation model
		model = new MonteCarloAssetModel(baseModel, stochasticDriver);

		return model;
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
		return new PutOption(maturity, strike).getValue(getModel());
	}
}
