/**
 *
 */
package quantlab.tutorium.exercise1;

import org.junit.jupiter.api.Test;

/**
 * @author Roland Bachl
 *
 */
public class MainVsTest {

	/**
	 * This static(!) method is the entry point into your program. AS opposed to
	 * e.g. C you can have multiple main methods in a single project, but only one
	 * per class.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		runProgram();
	}

	/**
	 * This is where your actual code goes.
	 */
	private static void runProgram() {
		System.out.println("It's running!");
	}

	/**
	 * When running as a JUnit test, JUnit checks for the @Test annotation and for
	 * each instantiates an object of the class to run the test on.
	 */
	@Test
	public void test() {
		runProgram();
		System.out.println("...too.");
		System.out.println(this);
	}

	//	@Test
	//	public void testDuplicate() {
	//		runProgram();
	//		System.out.println("...too.");
	//		System.out.println(this);
	//	}
}
