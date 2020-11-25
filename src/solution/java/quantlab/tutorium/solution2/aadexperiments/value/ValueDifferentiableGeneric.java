/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christian-fries.de.
 *
 * Created on 08.11.2020
 */
package quantlab.tutorium.solution2.aadexperiments.value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

import quantlab.tutorium.solution2.aadexperiments.ConvertableToFloatingPoint;
import quantlab.tutorium.solution2.aadexperiments.DiffernetiationExperimentHypothenuse;
import quantlab.tutorium.solution2.aadexperiments.Value;
import quantlab.tutorium.solution2.aadexperiments.ValueDifferentiable;
import quantlab.tutorium.solution2.aadexperiments.ValueFactory;

/**
 * Turns a arbitrary {@link Value} into a {@link ValueDifferentiable}.
 *
 * @author Roland Bachl
 *
 * @param <V> The type of the underlying {@link Value}.
 */
public class ValueDifferentiableGeneric<V extends Value> implements ValueDifferentiable, ConvertableToFloatingPoint {

	public static void main(String[] args) {

		DiffernetiationExperimentHypothenuse.runExperiment(getFactory(MyValue.getFactory()));
	}

	private enum Operator {
		SQUARED, SQRT, ADD, SUB, MULT, DIV
	}

	private static AtomicLong nextId = new AtomicLong();

	private V value;
	private Operator operator;
	private List<ValueDifferentiableGeneric<V>> arguments;
	private Long id;

	/**
	 * Creates a node from an operation.
	 *
	 * @param value Value of this node.
	 * @param operator Operator that lead to this value.
	 * @param arguments Arguments that were used in this operation.
	 */
	private ValueDifferentiableGeneric(V value, Operator operator, List<ValueDifferentiableGeneric<V>> arguments) {
		super();
		this.value = value;
		this.operator = operator;
		this.arguments = arguments;
		this.id = nextId.getAndIncrement();
	}

	/**
	 * Creates a node from a constant - a leaf node.
	 *
	 * @param value Value of this node.
	 */
	public ValueDifferentiableGeneric(V value) {
		this(value, null, null);
	}

	public static <V extends Value> ValueFactory<ValueDifferentiableGeneric<V>> getFactory(
			ValueFactory<V> baseFactory) {
		return new ValueFactory<ValueDifferentiableGeneric<V>>() {

			@Override
			public ValueDifferentiableGeneric<V> getValue(double x) {
				return new ValueDifferentiableGeneric<V>(baseFactory.getValue(x));
			}
		};
	}

	/*
	 * The operations, implementing the interface.
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Value squared() {
		return new ValueDifferentiableGeneric<V>((V) value.squared(), Operator.SQUARED, List.of(this));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Value sqrt() {
		return new ValueDifferentiableGeneric<V>((V) value.sqrt(), Operator.SQRT, List.of(this));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Value add(Value x) {
		return new ValueDifferentiableGeneric<V>((V) value.add(x), Operator.ADD, List.of(this, (ValueDifferentiableGeneric<V>)x));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Value sub(Value x) {
		return new ValueDifferentiableGeneric<V>((V) value.sub(x), Operator.SUB, List.of(this, (ValueDifferentiableGeneric<V>)x));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Value mult(Value x) {
		return new ValueDifferentiableGeneric<V>((V) value.mult(x), Operator.MULT, List.of(this, (ValueDifferentiableGeneric<V>)x));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Value div(Value x) {
		return new ValueDifferentiableGeneric<V>((V) value.div(x), Operator.DIV, List.of(this, (ValueDifferentiableGeneric<V>)x));
	}

	@Override
	public Double asFloatingPoint() {
		if (value instanceof ConvertableToFloatingPoint) {
			return ((ConvertableToFloatingPoint) value).asFloatingPoint();
		} else {
			throw new UnsupportedOperationException(
					"Class " + value.getClass() + " does not support floating point conversion.");
		}
	}

	@Override
	public String toString() {
		return value.toString();
	}

	/**
	 * Get the derivatives of a node with respect to all input nodes via a backward algorithmic differentiation (adjoint differentiation).
	 *
	 * @return A map x -> D which gives D = dy/dx, where y is this node and x is any input node.
	 */
	public Map<ValueDifferentiable, Double> getDerivativeWithRespectTo() {
		// The map that will contain the derivatives x -> dy/dx
		Map<ValueDifferentiable, Double> derivativesWithRespectTo = new HashMap<>();
		// Init with dy / dy = 1
		derivativesWithRespectTo.put(this, 1.0);

		// This creates a set (queue) of objects, sorted ascending by their getID() value (last = highest ID)
		TreeSet<ValueDifferentiableGeneric<V>> nodesToProcess = new TreeSet<>(
				(o1, o2) -> o1.getID().compareTo(o2.getID()));

		// Add the root note
		nodesToProcess.add(this);

		// Walk down the tree, always removing the node with the highest id and adding their arguments
		while(!nodesToProcess.isEmpty()) {

			// Get and remove the top most node.
			ValueDifferentiableGeneric<V> currentNode = nodesToProcess.pollLast();

			List<ValueDifferentiableGeneric<V>> currentNodeArguments = currentNode.getArguments();
			if(currentNodeArguments != null) {
				// Update the derivative as Di = Di + Dm * dxm / dxi (where Dm = dy/xm).
				propagateDerivativeToArguments(derivativesWithRespectTo, currentNode, currentNodeArguments);

				// Add all arguments to our queue of nodes we have to work on
				nodesToProcess.addAll(currentNode.getArguments());
			}
		}
		return derivativesWithRespectTo;
	}

	/**
	 * Apply the update rule Di = Di + Dm * dxm / dxi (where Dm = dy/xm).
	 *
	 * @param derivatives The map that contains the derivatives x -> dy/dx and will be updated, that is Di = dy/dx_{i}.
	 * @param node The current node (xm).
	 * @param arguments The (list of) arguments of the current node (the i's).
	 */
	private void propagateDerivativeToArguments(Map<ValueDifferentiable, Double> derivatives,
			ValueDifferentiableGeneric<V> node, List<ValueDifferentiableGeneric<V>> arguments) {

		switch(node.getOperator()) {
		case ADD:
			derivatives.put(arguments.get(0), derivatives.getOrDefault(arguments.get(0),0.0) + derivatives.get(node) * 1.0);
			derivatives.put(arguments.get(1), derivatives.getOrDefault(arguments.get(1),0.0) + derivatives.get(node) * 1.0);
			break;
		case SUB:
			derivatives.put(arguments.get(0), derivatives.getOrDefault(arguments.get(0),0.0) + derivatives.get(node) * 1.0);
			derivatives.put(arguments.get(1), derivatives.getOrDefault(arguments.get(1),0.0) - derivatives.get(node) * 1.0);
			break;
		case MULT:
			derivatives.put(arguments.get(0), derivatives.getOrDefault(arguments.get(0),0.0) + derivatives.get(node) * arguments.get(1).asFloatingPoint());
			derivatives.put(arguments.get(1), derivatives.getOrDefault(arguments.get(1),0.0) + derivatives.get(node) * arguments.get(0).asFloatingPoint());
			break;
		case DIV:
			break;
		case SQUARED:
			derivatives.put(arguments.get(0), derivatives.getOrDefault(arguments.get(0),0.0) + derivatives.get(node) * 2 * arguments.get(0).asFloatingPoint());
			break;
		case SQRT:
			derivatives.put(arguments.get(0), derivatives.getOrDefault(arguments.get(0),0.0) + derivatives.get(node) / 2 / Math.sqrt(arguments.get(0).asFloatingPoint()));
			break;
		default:
			throw new IllegalArgumentException(node.getOperator().toString());
		}
	}

	@Override
	public Value getDerivativeWithRespectTo(ValueDifferentiable x) {
		return new ValueDouble(getDerivativeWithRespectTo().getOrDefault(x, 0.0));
	}


	public Operator getOperator() {
		return operator;
	}

	public List<ValueDifferentiableGeneric<V>> getArguments() {
		return arguments;
	}

	Long getID() {
		return id.longValue();
	}
}
