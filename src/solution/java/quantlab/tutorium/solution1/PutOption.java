package quantlab.tutorium.solution1;

import net.finmath.exception.CalculationException;
import net.finmath.montecarlo.RandomVariableFromDoubleArray;
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

	private final double maturity;
	private final double strike;
	private final Integer underlyingIndex;

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
		this.maturity = maturity;
		this.strike = strike;
		this.underlyingIndex = underlyingIndex;
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
		// Get underlying and numeraire

		// Get S(T)
		RandomVariable underlyingAtMaturity = model.getAssetValue(maturity, underlyingIndex);

		// The payoff: values = max(strike - underlying, 0) = V(T) = max(K-S(T),0)
		RandomVariable values = new RandomVariableFromDoubleArray(strike).sub(underlyingAtMaturity).floor(0);

		// Discounting...
		RandomVariable numeraireAtMaturity = model.getNumeraire(maturity);
		RandomVariable monteCarloWeights = model.getMonteCarloWeights(maturity);
		values = values.div(numeraireAtMaturity).mult(monteCarloWeights);

		// ...to evaluation time.
		RandomVariable numeraireAtEvalTime = model.getNumeraire(evaluationTime);
		RandomVariable monteCarloProbabilitiesAtEvalTime = model.getMonteCarloWeights(evaluationTime);
		values = values.mult(numeraireAtEvalTime).div(monteCarloProbabilitiesAtEvalTime);

		return values;
	}

}
