package quantlab.tutorium.exercise1;

import net.finmath.exception.CalculationException;
import net.finmath.montecarlo.assetderivativevaluation.AssetModelMonteCarloSimulationModel;
import net.finmath.montecarlo.assetderivativevaluation.products.AbstractAssetMonteCarloProduct;
import net.finmath.stochastic.RandomVariable;;

/**
 * Implements the valuation of a European put option on a single asset.
 *
 * @author Roland Bachl
 *
 */
public class PutOption extends AbstractAssetMonteCarloProduct {


	/**
	 * Construct a product representing an European option on an asset S (where S the asset with index 0 from the model
	 * - single asset case).
	 *
	 * @param maturity        The maturity T in the option payoff max(S(T)-K,0)
	 * @param strike          The strike K in the option payoff max(S(T)-K,0).
	 * @param underlyingIndex The index of the underlying to be fetched from the model.
	 */
	public PutOption(double maturity, double strike, int underlyingIndex) {
		super();

		// TODO Your constructor goes here.
	}

	/**
	 * Construct a product representing an European option on an asset S (where S the asset with index 0 from the model
	 * - single asset case).
	 *
	 * @param maturity The maturity T in the option payoff max(S(T)-K,0)
	 * @param strike   The strike K in the option payoff max(S(T)-K,0).
	 */
	public PutOption(double maturity, double strike) {
		this(maturity, strike, 0);
	}

	@Override
	public RandomVariable getValue(double evaluationTime, AssetModelMonteCarloSimulationModel model)
			throws CalculationException {
		// TODO Calculate the (stochastic) payoff, discounted to the given time.

		// Get S(T)

		// The payoff: values = max(strike - underlying, 0) = V(T) = max(K-S(T),0)

		// Discounting...

		// ...to evaluation time.

		return null;
	}

}
