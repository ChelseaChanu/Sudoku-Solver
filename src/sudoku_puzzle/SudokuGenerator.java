package sudoku_puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {
	
	public SudokuPuzzle generateRandomSudoku(SudokuPuzzleType puzzleType) {
		SudokuPuzzle puzzle = new SudokuPuzzle(SudokuPanel.GRID_SIZE, SudokuPanel.GRID_SIZE, SudokuPanel.GRID_SIZE, SudokuPanel.GRID_SIZE, puzzleType.getValidValues());
		SudokuPuzzle copy = new SudokuPuzzle(puzzle);
		
		Random randomGenerator = new Random();
		
		List<String> notUsedValidValues =  new ArrayList<String>(Arrays.asList(copy.getValidValues()));
		for(int r = 0;r < SudokuPanel.GRID_SIZE;r++) {
			int randomValue = randomGenerator.nextInt(notUsedValidValues.size());
			copy.makeMove(r, 0, notUsedValidValues.get(randomValue), true);
			notUsedValidValues.remove(randomValue);
		}
		
		backtrackSudokuSolver(0, 0, copy);
		
		int numberOfValuesToKeep = (int)(0.22222*(SudokuPanel.GRID_SIZE*SudokuPanel.GRID_SIZE));
		
		for(int i = 0;i < numberOfValuesToKeep;) {
			int randomRow = randomGenerator.nextInt(SudokuPanel.GRID_SIZE);
			int randomColumn = randomGenerator.nextInt(SudokuPanel.GRID_SIZE);
			
			if(puzzle.isSlotAvailable(randomRow, randomColumn)) {
				puzzle.makeMove(randomRow, randomColumn, copy.getValue(randomRow, randomColumn), false);
				i++;
			}
		}
		
		return puzzle;
	}
	
	/**
	 * Solves the sudoku puzzle
	 * Pre-cond: r = 0,c = 0
	 * Post-cond: solved puzzle
	 * @param r: the current row
	 * @param c: the current column
	 * @return valid move or not or done
	 * Responses: Erroneous data 
	 */
    private boolean backtrackSudokuSolver(int r,int c,SudokuPuzzle puzzle) {
    	//If the move is not valid return false
		if(!puzzle.inRange(r,c)) {
			return false;
		}
		
		//if the current space is empty
		if(puzzle.isSlotAvailable(r, c)) {
			
			//loop to find the correct value for the space
			for(int i = 0;i < puzzle.getValidValues().length;i++) {
				
				//if the current number works in the space
				if(!puzzle.numInRow(r, puzzle.getValidValues()[i]) && !puzzle.numInCol(c,puzzle.getValidValues()[i]) && !puzzle.numInBox(r,c,puzzle.getValidValues()[i])) {
					
					//make the move
					puzzle.makeMove(r, c, puzzle.getValidValues()[i], true);
					
					//if puzzle solved return true
					if(puzzle.boardFull()) {
						return true;
					}
					
					//go to next move
					if(r == SudokuPanel.GRID_SIZE - 1) {
						if(backtrackSudokuSolver(0,c + 1,puzzle)) return true;
					} else {
						if(backtrackSudokuSolver(r + 1,c,puzzle)) return true;
					}
				}
			}
		}
		
		//if the current space is not empty
		else {
			//got to the next move
			if(r == SudokuPanel.GRID_SIZE - 1) {
				return backtrackSudokuSolver(0,c + 1,puzzle);
			} else {
				return backtrackSudokuSolver(r + 1,c,puzzle);
			}
		}
		
		//undo move
		puzzle.makeSlotEmpty(r, c);
		
		//backtrack
		return false;
	}
}
