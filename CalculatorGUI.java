package edu.wit.dcsn.comp2000.stackapp;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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
 * This class wraps a StackCalculator with a GUI. The GUI provides buttons for the user
 * to enter digits, enter the 4 basic arithmetic operators, backspace, clear, calculate
 * and quit the application. Upon pressing calculate, the entered expression is passed
 * to the StackCalculator to be evaluated. The result is printed to the display.
 * @author Scott Austin
 *
 */
public class CalculatorGUI extends Application
{
	// application variables
	GridPane grid;
	TextField tfDisplay;
	Button[] btns;
	Stage stage;

	// instance variables
	StackCalculator calculator;

	/** 
	 * Launches the application.
	 * @param args  -unused-
	 */
	public static void main(String[] args) 
	{
		launch(args);
	}

	/** 
	 * Creates the application window.
	 * @param primaryStage  the stage displaying the application controls
	 */
	@Override
	public void start(Stage primaryStage) 
	{
		stage = primaryStage;
		stage.setTitle("Stack Calculator");
		
		calculator = new StackCalculator();
		
		initializeGrid();
		initializeTextField();
		initializeButtons();
		initializeScene();
		
		stage.show();
	}
	
	/**
	 * Initialize the grid. The grid defines the layout for how all the controls
	 * will fit into the application. The grid is 6x4. The first row will contain
	 * the TextField displaying the entered expression. The remaining 5x4 grid
	 * will be comprised of the calculator buttons.
	 */
	private void initializeGrid()
	{
		grid = new GridPane();
		
		// Total percent over 100 for all columns treats the value as a weight.
		//  All columns and rows have the same weight.
		ColumnConstraints column = new ColumnConstraints();
		column.setPercentWidth(100);
		grid.getColumnConstraints().addAll(column, column, column, column);
		
		RowConstraints row = new RowConstraints();
		row.setPercentHeight(100);
		grid.getRowConstraints().addAll(row, row, row, row, row, row);
	}
	
	/**
	 * Initialize the text field. The text field displays the currently entered
	 * expression. The result of the expression will be shown here when the
	 * user presses calculate.
	 */
	private void initializeTextField()
	{
		tfDisplay = new TextField();
		tfDisplay.setEditable(false);
		tfDisplay.setFocusTraversable(false);
		tfDisplay.setShape(new Rectangle());
		
		grid.add(tfDisplay, 0, 0, 4, 1);
	}

	/**
	 * Initialize the buttons. There are 20 buttons representing the inputs 
	 * of a standard calculator.
	 */
	private void initializeButtons()
	{
		btns = new Button[20];
		for (int i = 0; i < btns.length; ++i)
		{
			btns[i] = new Button();
			btns[i].setText(ButtonProperties.values[i].text);

			// Creates the proper EventHandler for each kind of button.
			EventHandler<ActionEvent> eventHandler;
			switch (ButtonProperties.values[i])
			{
			case CLEAR:
				eventHandler = new EventHandler<ActionEvent>() 
				{
					@Override
					public void handle(ActionEvent event) 
					{
						tfDisplay.clear();
					}
				};
				break;
			case BACK:
				eventHandler = new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						if (!tfDisplay.getText().isEmpty())
						{
							String newText = tfDisplay.getText(0, tfDisplay.getText().length()-1);
							tfDisplay.setText(newText);
						}
					}
				};
				break;
			case QUIT:
				eventHandler = new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						stage.close(); // exits the application
					}
				};
				break;
			case EQUALS:
				eventHandler = new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						int result = calculator.calculate(tfDisplay.getText());
						tfDisplay.appendText("=" + result);
					}
				};
				break;
			default: // case: Digit or Operator
				eventHandler = new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						Button btn = (Button) event.getSource();
						tfDisplay.appendText(btn.getText());
					}
				};
			}
			btns[i].setOnAction(eventHandler);
			
			// This forces the button to take up the entire space of its cell.
			btns[i].setMaxWidth(Double.MAX_VALUE);
			btns[i].setMaxHeight(Double.MAX_VALUE);
			grid.add(btns[i], i%4, i/4 + 1);
		}
	}
	
	/**
	 * Initialize the scene. Defines the window size and applies the calculator.css 
	 * style to the calculator.
	 */
	private void initializeScene()
	{
		Scene scene = new Scene(grid, 300, 400);
		scene.getStylesheets().add("calculator.css");
		stage.setScene(scene);
	}

	/**
	 * This enum provides a named representation of calculator buttons.
	 * It has a text property that stores what text should be displayed on
	 * the button.
	 */
	private enum ButtonProperties
	{
		CLEAR("C"),	BACK("<"),	QUIT("Q"),	DIVIDE("/"),
		SEVEN("7"),	EIGHT("8"),	NINE("9"),	MULTIPLY("*"),
		FOUR("4"),	FIVE("5"),	SIX("6"),	MINUS("-"),
		ONE("1"),	TWO("2"),	THREE("3"),	PLUS("+"),
		ZERO("0"),	OPEN("("),	CLOSE(")"),	EQUALS("=");
		
		// 0-indexed array of the enum types. Corresponds to the btns array
		//  in the CalculatorGUI class.
		private static final ButtonProperties values[] = values();

		private String text;

		/**
		 * Constructs the ButtonProperty.
		 * @param text  stored text representing the display text of this button.
		 */
		private ButtonProperties(String text)
		{
			this.text = text;
		}
	}

}
