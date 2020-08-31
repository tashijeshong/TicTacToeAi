package TicTacToeAI;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class MiniMax extends gameOperations {

	public MiniMax() {

	}

	/*
	 * @param: int x and int y from gameBoard array
	 * 
	 * @returns: returns the position
	 */
	protected int getPositionFromArray(int x, int y) {

		if (x == 0 && y == 0)
			return 1;
		else if (x == 0 && y == 2)
			return 2;
		else if (x == 0 && y == 4)
			return 3;
		else if (x == 2 && y == 0)
			return 4;
		else if (x == 2 && y == 2)
			return 5;
		else if (x == 2 && y == 4)
			return 6;
		else if (x == 4 && y == 0)
			return 7;
		else if (x == 4 && y == 2)
			return 8;
		else if (x == 4 && y == 4)
			return 9;
		else
			return -1;

	}

	// Helper method for binary search & getArrayPosition
	protected int[] getMidArray(int mid) {

		int[] arrayPositions = new int[2];

		if (mid == 1) {
			arrayPositions[0] = 0;
			arrayPositions[1] = 0;
		} else if (mid == 2) {
			arrayPositions[0] = 0;
			arrayPositions[1] = 2;
		} else if (mid == 3) {
			arrayPositions[0] = 0;
			arrayPositions[1] = 4;
		} else if (mid == 4) {
			arrayPositions[0] = 2;
			arrayPositions[1] = 0;
		} else if (mid == 5) {
			arrayPositions[0] = 2;
			arrayPositions[1] = 2;
		} else if (mid == 6) {
			arrayPositions[0] = 2;
			arrayPositions[1] = 4;
		} else if (mid == 7) {
			arrayPositions[0] = 4;
			arrayPositions[1] = 0;
		} else if (mid == 8) {
			arrayPositions[0] = 4;
			arrayPositions[1] = 2;
		} else if (mid == 9) {
			arrayPositions[0] = 4;
			arrayPositions[1] = 2;
		}

		return arrayPositions;
	}

	// binary search algo. implemented
	protected int[] getArrayFromPosition(int leftB, int rightB, int pos) {
		int[] arrayPositions = new int[2];

		int mid = (leftB + rightB) / 2;

		if (pos == mid) {
			arrayPositions = getMidArray(mid);
			return arrayPositions;
		} else if (pos < mid) {
			getArrayFromPosition(leftB, mid, pos);
		} else if (pos > mid) {
			getArrayFromPosition(mid, rightB, pos);
		}

		return arrayPositions;

	}

	/*
	 * 
	 * @param: current state of the gameBoard array
	 * 
	 * @returns: returns an int as a score for the game
	 */
	protected int evaluateScore(char[][] gameBoard) {

		if (checkForWinner().equalsIgnoreCase("player wins"))
			return 1;
		else if (checkForWinner().equalsIgnoreCase("player wins"))
			return -1;
		else
			return 0;
	}

	// returns the actual points (0,2) rather than position = 2;
	public static List<Point> getAvailableCells(char[][] gameBoard) {
		List<Point> availableCells = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (gameBoard[i][j] == ' ')
					availableCells.add(new Point(i, j));
			}
		}
		return availableCells;

	}

	protected int minimax(char[][] gameBoard, int depth, String player, boolean cpuTurn) {

		if (checkForWinner().equalsIgnoreCase("cpu wins")) {
			return 1;
		} else if (checkForWinner().equalsIgnoreCase("player wins")) {
			return -1;
		} else if (getAvailableCells(gameBoard).size() == 0) // if draw
			return 0;

		int maxEval = Integer.MIN_VALUE;
		int minEval = Integer.MAX_VALUE;

		// Method maximizes the score for the CPU
		for (int i = 0; i < getAvailableCells(gameBoard).size(); i++) {
			Point point = getAvailableCells(gameBoard).get(i);

			int pointPosition = getPositionFromArray(point.x, point.y);

			checkCpuPlacement(previousPositions, previousPositionsCounter, pointPosition);

			if (cpuTurn == true) {
				placePositions(gameBoard, cpuTurn, pointPosition);
				int currentScore = minimax(gameBoard, depth + 1, "cpu", false);
				maxEval = Math.max(currentScore, maxEval);

				if (depth == 0)
					// System.out.println("Computer score for the position " + pointPosition + " = " + currentScore); 
					if (currentScore >= 0)
						if (depth == 0)
							computerPositionMove = pointPosition; 
				if (currentScore == 1) {
					gameBoard[point.x][point.y] = ' ';
					break;
				}
				if (i == getAvailableCells(gameBoard).size() - 1 && maxEval < 0)
					if (depth == 0)
						computerPositionMove = pointPosition;

			} else if (cpuTurn == false) {
				placePositions(gameBoard, cpuTurn, pointPosition);
				int currentScore = minimax(gameBoard, depth + 1, "player", true);
				minEval = Math.min(currentScore, minEval);

				if (minEval == -1) {
					gameBoard[point.x][point.y] = ' ';
					break;
				}
			}
			gameBoard[point.x][point.y] = ' ';
		}

		if (cpuTurn == true)
			return maxEval;
		else
			return minEval;
	}

	protected void play() {
		Scanner keyboard = new Scanner(System.in);
		printGameBoard();

		System.out.println("Enter (1) for cpu to start or (2) for user to start");
		int choice = keyboard.nextInt();

		if (choice == 1) {
			int randomNumber = ThreadLocalRandom.current().nextInt(1, 9 + 1);

			randomNumber = checkCpuPlacement(previousPositions, previousPositionsCounter, randomNumber);

			cpuTurn = true;
			placePositions(gameBoard, cpuTurn, randomNumber);// 4
			drawCounter++;
			printGameBoard();

			previousPositionsCounter++;
			previousPositions[previousPositionsCounter] = randomNumber;
		}

		// while-loop checks if game is over after cpu's turn
		while (gameOver() == false) { 
			System.out.println("choose a position from the board: ");

			cpuTurn = false;
			int positionInput = keyboard.nextInt();

			positionInput = checkUserPlacement(positionInput);
			placePositions(gameBoard, cpuTurn, positionInput);
			drawCounter++;
			printGameBoard();
			System.out.println("You chose position " + positionInput);

			previousPositionsCounter++;
			previousPositions[previousPositionsCounter] = positionInput; 
			
			if (gameOver() == true) { 
				break;
			}

			minimax(gameBoard, 0, "cpu", true);
			System.out.println("Computer chose position : " + computerPositionMove);

			cpuTurn = true;
			placePositions(gameBoard, cpuTurn, computerPositionMove);
			printGameBoard();

			drawCounter++;
			previousPositionsCounter++;
			previousPositions[previousPositionsCounter] = computerPositionMove;

		}

		if (checkForWinner().equalsIgnoreCase("player wins"))
			System.out.println("You win!");
		else if (checkForWinner().equalsIgnoreCase("cpu wins"))
			System.out.println("You lose!");
		else
			System.out.println("Draw!");
	}

}
