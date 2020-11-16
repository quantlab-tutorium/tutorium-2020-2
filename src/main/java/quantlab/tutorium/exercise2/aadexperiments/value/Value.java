/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christian-fries.de.
 *
 * Created on 08.11.2020
 */
package quantlab.tutorium.exercise2.aadexperiments.value;

/**
 *
 */
public interface Value {

	/**
	 * Applies x*x to this object x.
	 *
	 * @return New object representing the result.
	 */
	Value squared();

	/**
	 * Applies sqrt(x) to this object x.
	 *
	 * @return New object representing the result.
	 */
	Value sqrt();

	/**
	 * Applies a+x to this object a.
	 *
	 * @return New object representing the result.
	 */
	Value add(Value x);

	/**
	 * Applies a-x to this object a.
	 *
	 * @return New object representing the result.
	 */
	Value sub(Value x);

	/**
	 * Applies a*x to this object a.
	 *
	 * @return New object representing the result.
	 */
	Value mult(Value x);

	/**
	 * Applies a/x to this object a.
	 *
	 * @return New object representing the result.
	 */
	Value div(Value x);
}