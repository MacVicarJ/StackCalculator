package edu.wit.dcsn.comp2000.stackapp;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Group #37
 * Scott Austin, Erich Hauntsman, Jack MacVicar
 * COMP 2000-03
 * Application 2: Stack Application: Calculator
 * Due Monday 10/09/2017
 * 
 * This project is a basic implementation of a calculator using stacks as the underlying construct
 * to perform the calculator operations. The calculator uses one stack to hold the operands and one
 * stack to hold the operators. A single operation is performed by popping one operator from the top of 
 * its stack and popping two operands from the top of its stack and performs the appropriate calculation
 * and pushes the result on the top of the operand stack. The calculation is complete when the input string
 * is empty, the operator stack is empty, and there is only one operand left on the operand stack.
 * The last operand is the result of the operation. This demonstrates a use case of the stack construct.
 */

/**
 * Driver for the calculator class. Loads a test file and iterates through
 * the expressions line-by-line. Prints the expression to the screen and the result
 * as calculated by the StackCalculator class.
 * @author Scott Austin
 */
public class CalculatorDriver 
{
	// utility constants
	private static final String TEST_FILE_VALID_SINGLE_DIGIT = "Infix Calculator Expressions - valid -- 2016-10-13 01.txt";
	private static final String TEST_FILE_VALID_MULTI_DIGIT = "Infix Calculator Expressions - valid multi-digit -- 2016-10-04 01.txt";
	private static final String TEST_FILE_INVALID = "Infix Calculator Expressions - multi-digit with invalid expressions -- 2016-10-04 01.txt";
	
	/**
	 * Simple driver method for the StackCalculator class. Loads a file containing 
	 * the expressions, separated by newlines, and passes the expression as a string 
	 * to the calculator. Prints each expression to the screen, followed by the result
	 * evaluated by the StackCalculator class.
	 * @param args  -unused-
	 * @throws FileNotFoundException  thrown if the test file cannot be found
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
		StackCalculator calculator = new StackCalculator();
		Scanner sc = new Scanner(new File(TEST_FILE_VALID_SINGLE_DIGIT));
		while (sc.hasNextLine())
		{
			String expression = sc.nextLine();
			int result = calculator.calculate(expression);
			System.out.println(expression + " = " + result);
		}
		sc.close();
	}
}
