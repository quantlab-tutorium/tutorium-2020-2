/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christian-fries.de.
 *
 * Created on 08.11.2020
 */
package quantlab.tutorium.exercise2.aadexperiments;

import quantlab.tutorium.exercise2.aadexperiments.value.Value;
import quantlab.tutorium.exercise2.aadexperiments.value.ValueDifferentiable;
import quantlab.tutorium.exercise2.aadexperiments.value.ValueDoubleDifferentiable;

/**
 *
 */
public class DiffernetiationExperimentHypothenuse {

	public static void main(String[] args) {

		/*
		 * Note: The behavior of the program will change, depending on
		 * which implementation is used for Value. Use either ValueDouble or ValueDoubleDifferentiable.
		 */
		Value a = new ValueDoubleDifferentiable(7.0);
		Value b = new ValueDoubleDifferentiable(24.0);
		Value epsilon = new ValueDoubleDifferentiable(1E-5);

		System.out.println("Results for a = " + a + " and b = " + b);
		System.out.println();

		/*
		 * Note that the code below this point does not make any use of the specific implementation.
		 * It only references the interfaces <code>Value</code> and <code>ValueDifferentiable</code>
		 */

		/*
		 * Evaluation of function
		 */
		Value c = hypotenuse(a,b);

		System.out.println("Value is c = ............................: " + c);
		System.out.println("The type of c is ........................: " + c.getClass().getSimpleName());
		System.out.println();


		/*
		 * Partial derivative dz/dx
		 */
		Value dzdxAnalytic = a.div(c);
		System.out.println("Derivative (analytic)....................: " + dzdxAnalytic);
		System.out.println();


		/*
		 * Partial derivative dz/dx by finite difference
		 */
		Value dzdxFinitedifferce = (hypotenuse(a.add(epsilon), b).sub(hypotenuse(a, b)).div(epsilon));
		System.out.println("Derivative (finite difference)...........: " + dzdxFinitedifferce);
		System.out.println();


		/*
		 * Partial derivative dz/dx by algorithmic differentiation - we need to have that the objects are implementing <code>ValueDifferentiable</code>.
		 */
		Value dzdxAlgorithmicDifferentiation = ((ValueDifferentiable)c).getDerivativeWithRespectTo((ValueDifferentiable)a);
		System.out.println("Derivative (automatic differentiation)...: " + dzdxAlgorithmicDifferentiation);
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
