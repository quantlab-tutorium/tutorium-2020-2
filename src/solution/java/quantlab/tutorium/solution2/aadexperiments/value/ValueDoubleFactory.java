/**
 *
 */
package quantlab.tutorium.solution2.aadexperiments.value;

import quantlab.tutorium.solution2.aadexperiments.ValueFactory;

/**
 * A factory for {@link ValueDouble}.
 *
 * @author Roland Bachl
 *
 */
public class ValueDoubleFactory implements ValueFactory {

	@Override
	public ValueDouble getValue(double x) {
		return new ValueDouble(x);
	}

}
