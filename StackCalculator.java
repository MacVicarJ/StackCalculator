package edu.wit.dcsn.comp2000.stackapp;
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
 * A 4-operator calculator implemented with stacks.
 * @author Scott Austin, Erich Hauntsman, Jack MacVicar
 *
 */
public class StackCalculator 
{
	// instance variables
	private StackInterface<Integer> operandStack;
	private StackInterface<Character> operatorStack;

	/**
	 * Creates a StackCalculator.
	 */
	StackCalculator()
	{
		operandStack = new VectorStack<Integer>();
		operatorStack = new VectorStack<Character>();
	}

	/**
	 * Evaluates a string expression.
	 * @param expression  The expression to be evaluated. Method assumes the expression is valid
	 * with no whitespace. Invalid expressions may be evaluated incorrectly or the method
	 * may throw an EmptyStackException. 
	 * @return the result of the expression
	 */
	public int calculate(String expression)
	{
		char operator;
		int firstOperand, secondOperand;
		
		for(int i = 0; i < expression.length(); i++)
		{
			char nextCharacter = expression.charAt(i);
			if(isDigit(nextCharacter))
			{
				operandStack.push(nextCharacter - '0');
			} 
			else if (isOperator(nextCharacter))
			{
				// do not evaluate beyond the '(' until we find a matching ')' 
				boolean isReadyToPush = operatorStack.isEmpty() || operatorStack.peek() == '(';
				while(!isReadyToPush)
				{
					// expression must be evaluated left-to-right and follow precedence rules.
					//  evaluate any operations on the stack so long as the operator in nextCharacter
					//  does not have a higher precedence than what is on top of the stack.
					if(isHighPrecedence(operatorStack.peek()) || !isHighPrecedence(nextCharacter))
					{
						operator = operatorStack.pop();
						secondOperand = operandStack.pop();
						firstOperand = operandStack.pop();
						operandStack.push(evaluate(firstOperand, secondOperand, operator));
						isReadyToPush = operatorStack.isEmpty() || operatorStack.peek() == '(';
					}
					else 
					{
						isReadyToPush = true;
					}
				}
				operatorStack.push(nextCharacter);
			}
			else if (nextCharacter == '(')
			{
				operatorStack.push(nextCharacter);
			}
			else // assuming valid expression: nextCharacter must be ')'
			{
				// evaluate the stack until we hit the matching '(' 
				while((operator = operatorStack.pop()) != '(')
				{
					secondOperand = operandStack.pop();
					firstOperand = operandStack.pop();
					operandStack.push(evaluate(firstOperand, secondOperand, operator));
				}
			}
		}
		while (!operatorStack.isEmpty())
		{
			operator = operatorStack.pop();
			secondOperand = operandStack.pop();
			firstOperand = operandStack.pop();
			operandStack.push(evaluate(firstOperand, secondOperand, operator));
		}
		return operandStack.pop();
	}
	
	/**
	 * Evaluates a binary operation.
	 * @param firstOperand  The first operand.
	 * @param secondOperand  The second operand. If the operator is '/', this operator must not be 0.
	 * @param operator  The operator. Must be '+', '-', '*', '/'
	 * @return  The result of the operation.
	 */
	private int evaluate(int firstOperand, int secondOperand, char operator){
		int result = 0;
		switch(operator)
		{
		case '+':
			result = firstOperand + secondOperand;
			break;
		case '-':
			result = firstOperand - secondOperand;
			break;		
		case '*':
			result = firstOperand * secondOperand;
			break;
		case '/':
			result = firstOperand / secondOperand;
			break;
		}
		return result;
	}

	/**
	 * Check if a character is a digit.
	 * @param c  character to check.
	 * @return  true if the character represents a digit, false otherwise.
	 */
	private boolean isDigit(char c)
	{
		return ((c >= '0') && c <= '9');
	}
	
	/** Check if a character is an operator
	 * @param c  character to check.
	 * @return  true if the character is an operator, false otherwise.
	 */
	private boolean isOperator(char c)
	{
		return ((c == '+' || c == '-' || c == '*' || c == '/'));
	}

	/**
	 * Check if an operator has a high precedence. 
	 * @param operator  operator to test
	 * @return true if the operator is '*' or '/', false otherwise.
	 */
	public boolean isHighPrecedence(char operator)
	{
		return (operator == '*' || operator == '/');
	}
}
