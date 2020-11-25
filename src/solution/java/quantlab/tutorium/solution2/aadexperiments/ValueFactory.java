/**
 *
 */
package quantlab.tutorium.solution2.aadexperiments;

/**
 * Factory for {@link Value}.
 *
 * @author Roland Bachl
 *
 * @param <V> The type of value that is being created by this factory.
 */
public interface ValueFactory<V extends Value> {

	V getValue(double x);

}
