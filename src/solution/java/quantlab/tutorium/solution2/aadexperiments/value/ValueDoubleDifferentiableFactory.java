/**
 *
 */
package quantlab.tutorium.solution2.aadexperiments.value;

import quantlab.tutorium.solution2.aadexperiments.ValueFactory;

/**
 * A factory for {@link ValueDoubleDifferentiable}.
 *
 * @author Roland Bachl
 *
 */
public class ValueDoubleDifferentiableFactory implements ValueFactory<ValueDoubleDifferentiable> {

	@Override
	public ValueDoubleDifferentiable getValue(double x) {
		return new ValueDoubleDifferentiable(x);
	}

}
