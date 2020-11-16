/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christian-fries.de.
 *
 * Created on 08.11.2020
 */
package quantlab.tutorium.solution2.aadexperiments.value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

import quantlab.tutorium.solution2.aadexperiments.ConvertableToFloatingPoint;
import quantlab.tutorium.solution2.aadexperiments.Value;
import quantlab.tutorium.solution2.aadexperiments.ValueDifferentiable;

/**
 *
 */
public class ValueDoubleDifferentiable implements ValueDifferentiable, ConvertableToFloatingPoint {

	private enum Operator {
		SQUARED, SQRT, ADD, SUB, MULT, DIV
	}

	private static AtomicLong nextId = new AtomicLong();

	private Double value;
	private Operator operator;
	private List<ValueDoubleDifferentiable> arguments;
	private Long id;

	/**
	 * Creates a node from an operation.
	 *
	 * @param value Value of this node.
	 * @param operator Operator that lead to this value.
	 * @param arguments Arguments that were used in this operation.
	 */
	private ValueDoubleDifferentiable(Double value, Operator operator, List<ValueDoubleDifferentiable> arguments) {
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
	public ValueDoubleDifferentiable(Double value) {
		this(value, null, null);
	}

	/*
	 * The operations, implementing the interface.
	 */

	@Override
	public Value squared() {
		return new ValueDoubleDifferentiable(value * value, Operator.SQUARED, List.of(this));
	}

	@Override
	public Value sqrt() {
		return new ValueDoubleDifferentiable(Math.sqrt(value), Operator.SQRT, List.of(this));
	}

	@Override
	public Value add(Value x) {
		return new ValueDoubleDifferentiable(value + valueOf(x), Operator.ADD, List.of(this, (ValueDoubleDifferentiable)x));
	}

	@Override
	public Value sub(Value x) {
		return new ValueDoubleDifferentiable(value - valueOf(x), Operator.SUB, List.of(this, (ValueDoubleDifferentiable)x));
	}

	@Override
	public Value mult(Value x) {
		return new ValueDoubleDifferentiable(value * valueOf(x), Operator.MULT, List.of(this, (ValueDoubleDifferentiable)x));
	}

	@Override
	public Value div(Value x) {
		return new ValueDoubleDifferentiable(value / valueOf(x), Operator.DIV, List.of(this, (ValueDoubleDifferentiable)x));
	}

	@Override
	public Double asFloatingPoint() {
		return value;
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
		// The map that will contain the derivatives x -> dy/dx				// The map contains in iteration m the values d F
		Map<ValueDifferentiable, Double> derivativesWithRespectTo = new HashMap<>();
		// Init with dy / dy = 1
		derivativesWithRespectTo.put(this, 1.0);

		// This creates a set (queue) of objects, sorted ascending by their getID() value (last = highest ID)
		NavigableSet<ValueDoubleDifferentiable> nodesToProcess = new TreeSet<>((o1,o2) -> o1.getID().compareTo(o2.getID()));

		// Add the root note
		nodesToProcess.add(this);

		// Walk down the tree, always removing the node with the highest id and adding their arguments
		while(!nodesToProcess.isEmpty()) {

			// Get and remove the top most node.
			ValueDoubleDifferentiable currentNode = nodesToProcess.pollLast();

			List<ValueDoubleDifferentiable> currentNodeArguments = currentNode.getArguments();
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
	private void propagateDerivativeToArguments(Map<ValueDifferentiable, Double> derivatives, ValueDoubleDifferentiable node, List<ValueDoubleDifferentiable> arguments) {

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
		case DIV:	// z = x/y
			double x = arguments.get(0).asFloatingPoint();
			double y = arguments.get(1).asFloatingPoint();
			derivatives.put(arguments.get(0), derivatives.getOrDefault(arguments.get(0), 0.0) + derivatives.get(node) / y);
			derivatives.put(arguments.get(1), derivatives.getOrDefault(arguments.get(1), 0.0) - derivatives.get(node) * x / (y*y));
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

	public List<ValueDoubleDifferentiable> getArguments() {
		return arguments;
	}

	Long getID() {
		return id.longValue();
	}

	private static double valueOf(Value x) {
		return ((ConvertableToFloatingPoint)x).asFloatingPoint();
	}
}
