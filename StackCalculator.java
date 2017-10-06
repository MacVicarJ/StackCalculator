import java.util.Vector;

//use two stacks one for operations and one for numbers 
public class StackCalculator {
	private StackInterface<Integer> operandStack;
	private StackInterface<Character> operatorStack;

	StackCalculator()
	{
		operandStack = new VectorStack<Integer>();
		operatorStack = new VectorStack<Character>();
	}
	
	int calculate(String expression)
	{
		// loop through expression
		// for i = 0 i < expression.length i++
		// 		if (isDigit( expression.charAt(i) )
		//			push to operandStack
		//      else
		//          while !operatorStack.isEmpty && operatorStack.peek() has higher precedence
		// 				evaluate top two operands with top operator
		//              push result operandStack
		//          push to operatorStack
		//
		// while !operatorStack.isEmpty
		//		evaluate top two operands with the operator
		//		push result operandStack
		
		// return operandstack.pop
		
		
		for(int i = 0; i < expression.length(); i++){
			char nextCharacter = expression.charAt(i);
			if(isDigit(nextCharacter)){
				operandStack.push(nextCharacter - '0');
			}else{
				
				while(!operatorStack.isEmpty() && precedence(nextCharacter)){
					operatorStack.pop();
					operandStack.pop();
					operandStack.pop(); 
				}
			}
		}
		return 0;
	}
	
	private final boolean isDigit(char c){
		return ((c >= 48) && c <= 57); //change to 0 and 9 if this doesnt work
	}
	
	//peek from operator stack if multiplication or division 
	//check character in the string 
	//if that is plus or minus 
	//evaluate what's on the stack
	
	public boolean precedence (char operator){
		char otherOperator = operatorStack.peek();
		
		if(otherOperator == '*' || otherOperator == '/'){
			if(operator == '+' || operator == '-'){
				return true;
			
			}
		}
		return false;
		
	}
}
