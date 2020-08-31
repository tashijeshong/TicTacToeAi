package TicTacToeAI;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class gameOperations {
	
	protected boolean placedInDifferentSpot = true;
	protected int[] previousPositions = new int[20];
	protected int previousPositionsCounter = -1;
	protected int drawCounter = 0;
	protected String winnerChecker = "";
	protected boolean cpuTurn = true;

	Scanner keyboard = new Scanner(System.in);

	static char[][] gameBoard = {{ ' ', '|', ' ', '|', ' ' },
									{ '-', '+', '-', '+', '-' }, 
									{ ' ', '|', ' ', '|', ' ' },
									{ '-', '+', '-', '+', '-' }, 
									{ ' ', '|', ' ', '|', ' ' }};

	protected int computerPositionMove = 1000;

	//Prints the state of the game board
	protected void printGameBoard() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(gameBoard[i][j]);
			}
			System.out.println("");
		}
	}

	/*
	 * Method: places 'X' or 'O' to the game board]
	 * 
	 * @param: gameboard, user (player or cpu), position that user enters
	 */
	protected void placePositions(char[][] gameBoard, boolean cpuTurn, int pos) { 
																					
		char symbol = ' ';

		if (cpuTurn == false)
			symbol = 'X';
		else
			symbol = 'O';

		if ((pos != 'X') || (pos != 'O')) {
			switch (pos) {
			case 1:
				gameBoard[0][0] = symbol;
				break;
			case 2:
				gameBoard[0][2] = symbol;
				break;
			case 3:
				gameBoard[0][4] = symbol;
				break;
			case 4:
				gameBoard[2][0] = symbol;
				break;
			case 5:
				gameBoard[2][2] = symbol;
				break;
			case 6:
				gameBoard[2][4] = symbol;
				break;
			case 7:
				gameBoard[4][0] = symbol;
				break;
			case 8:
				gameBoard[4][2] = symbol;
				break;
			case 9:
				gameBoard[4][4] = symbol;
				break;
			default:
				break;
			}
		} else
			placedInDifferentSpot = false;
	}

	/*
	 * Method: that checks the game board if there is a winner or draw
	 * 
	 * @param: status of the game board
	 * 
	 * @returns: if "player" or "cpu" wins or draws
	 */
	protected String checkForWinner() { 

		String returnValue = "";

		if (gameBoard[0][0] == 'X' && gameBoard[0][2] == 'X' && gameBoard[0][4] == 'X')
			returnValue = ("Player wins");
		else if (gameBoard[0][0] == 'O' && gameBoard[0][2] == 'O' && gameBoard[0][4] == 'O')
			returnValue = ("Cpu wins");

		else if (gameBoard[2][0] == 'X' && gameBoard[2][2] == 'X' && gameBoard[2][4] == 'X')
			returnValue = ("Player wins");
		else if (gameBoard[2][0] == 'O' && gameBoard[2][2] == 'O' && gameBoard[2][4] == 'O')
			returnValue = ("Cpu wins");

		else if (gameBoard[4][0] == 'X' && gameBoard[4][2] == 'X' && gameBoard[4][4] == 'X')
			returnValue = ("Player wins");
		else if (gameBoard[4][0] == 'O' && gameBoard[4][2] == 'O' && gameBoard[4][4] == 'O')
			returnValue = ("Cpu wins");

		// vertical
		else if (gameBoard[0][0] == 'X' && gameBoard[2][0] == 'X' && gameBoard[4][0] == 'X')
			returnValue = ("Player wins");
		else if (gameBoard[0][0] == 'O' && gameBoard[2][0] == 'O' && gameBoard[4][0] == 'O')
			returnValue = ("Cpu wins");

		else if (gameBoard[0][2] == 'X' && gameBoard[2][2] == 'X' && gameBoard[4][2] == 'X')
			returnValue = ("Player wins");
		else if (gameBoard[0][2] == 'O' && gameBoard[2][2] == 'O' && gameBoard[4][2] == 'O')
			returnValue = ("Cpu wins");

		else if (gameBoard[0][4] == 'X' && gameBoard[2][4] == 'X' && gameBoard[4][4] == 'X')
			returnValue = ("Player wins");
		else if (gameBoard[0][4] == 'O' && gameBoard[2][4] == 'O' && gameBoard[4][4] == 'O')
			returnValue = ("Cpu wins");

		// diagonal
		else if (gameBoard[0][0] == 'X' && gameBoard[2][2] == 'X' && gameBoard[4][4] == 'X')
			returnValue = ("Player wins");
		else if (gameBoard[0][0] == 'O' && gameBoard[2][2] == 'O' && gameBoard[4][4] == 'O')
			returnValue = ("Cpu wins");

		else if (gameBoard[0][4] == 'X' && gameBoard[2][2] == 'X' && gameBoard[4][0] == 'X')
			returnValue = ("Player wins");
		else if (gameBoard[0][4] == 'O' && gameBoard[2][2] == 'O' && gameBoard[4][0] == 'O')
			returnValue = ("Cpu wins");
		else if (drawCounter == 9)
			returnValue = ("draw");

		return returnValue;

	}

	/*
	 * Method: checks if player put the 'X' in the right positions
	 * 
	 * @param: previousPosition - array that has all the previous positions chosen
	 * from the player and the cpu. positionInput - the positions that user has
	 * chosen positionsGoneCounter - counter of how many positions have gone by
	 * 
	 * @returns: valid position
	 */
	protected int checkUserPlacement(int positionInput) {
		Scanner keyboard = new Scanner(System.in);

		int i = 0;
		while (i <= previousPositionsCounter) {
			while (previousPositions[i] == positionInput) {
				System.out.println("that position is filled, pick again: ");
				positionInput = keyboard.nextInt();
			}

			i++;
		}
		return positionInput;
	}

	/*
	 * checks if cpu's random number is put in the right place
	 * 
	 * @param: same as checkUserPlacement returns: a valid position
	 * 
	 * @returns: available random number
	 */
	protected int checkCpuPlacement(int previousPositions[], int previousPositionsCounter, int randomNumber) { 
		int i = 0;
		while (i < previousPositionsCounter) {
			while (previousPositions[i] == randomNumber) {
				randomNumber = ThreadLocalRandom.current().nextInt(1, 9 + 1);
				return randomNumber;
			}
			i++;
		}
		return randomNumber;
	}
	//Checks if game is over after player's turn
	protected boolean gameOver() {

		if (checkForWinner().equalsIgnoreCase("player wins"))
			return true;
		else if (checkForWinner().equalsIgnoreCase("cpu wins"))
			return true;
		else if (checkForWinner().equalsIgnoreCase("draw"))
			return true;
		else
			return false;

	}
	//Starts the game
	protected void play() {
		System.out.println("Rules:	Each grid in the display board counts from position of 1-9.\n"
				+ "	Enter a number from 1-9 then press enter to place your symbol.");
		MiniMax callPlay = new MiniMax();
		callPlay.play();
	}

}
