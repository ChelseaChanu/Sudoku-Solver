package sudoku_puzzle;

public class SudokuPuzzle {
	protected String [][] board;
	// Table to determine if a slot is mutable
	protected boolean [][] mutable;
	private final String [] VALIDVALUES;
	
	public SudokuPuzzle(int rows,int columns,int boxWidth,int boxHeight,String [] validValues) {
		this.VALIDVALUES = validValues;
		this.board = new String[SudokuPanel.GRID_SIZE][SudokuPanel.GRID_SIZE];
		this.mutable = new boolean[SudokuPanel.GRID_SIZE][SudokuPanel.GRID_SIZE];
		initializeBoard();
		initializeMutableSlots();
	}
	
	public SudokuPuzzle(SudokuPuzzle puzzle) {
		this.VALIDVALUES = puzzle.VALIDVALUES;
		this.board = new String[SudokuPanel.GRID_SIZE][SudokuPanel.GRID_SIZE];
		for(int r = 0;r < SudokuPanel.GRID_SIZE;r++) {
			for(int c = 0;c < SudokuPanel.GRID_SIZE;c++) {
				board[r][c] = puzzle.board[r][c];
			}
		}
		this.mutable = new boolean[SudokuPanel.GRID_SIZE][SudokuPanel.GRID_SIZE];
		for(int r = 0;r < SudokuPanel.GRID_SIZE;r++) {
			for(int c = 0;c < SudokuPanel.GRID_SIZE;c++) {
				this.mutable[r][c] = puzzle.mutable[r][c];
			}
		}
	}
	
	public String [] getValidValues() {
		return this.VALIDVALUES;
	}
	
	public void makeMove(int row,int col,String value,boolean isMutable) {
		if(this.isValidValue(value) &&  this.isSlotMutable(row, col)) {
			this.board[row][col] = value;
			this.mutable[row][col] = isMutable;
		}
	}
	
	public boolean numInCol(int col,String value) {
			for(int row=0;row < SudokuPanel.GRID_SIZE;row++) {
				if(this.board[row][col].equals(value)) {
					return true;
				}
			}
		return false;
	}
	
	public boolean numInRow(int row,String value) {
			for(int col=0;col < SudokuPanel.GRID_SIZE;col++) {
				if(this.board[row][col].equals(value)) {
					return true;
				}
			}
		return false;
	}
	
	public boolean numInBox(int row,int col,String value) {
		if(this.inRange(row, col)) {
			int boxRow = row - row%3;;
			int boxCol = col -col%3;
			
			for(int r = boxRow;r < boxRow+3;r++) {
				for(int c = boxCol;c < boxCol+3;c++) {
					if(this.board[r][c].equals(value)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isSlotAvailable(int row,int col) {
		 return (this.inRange(row,col) && this.board[row][col].equals("") && this.isSlotMutable(row, col));
	}
	
	public boolean isSlotMutable(int row,int col) {
		return this.mutable[row][col];
	}
	
	public String getValue(int row,int col) {
		if(this.inRange(row,col)) {
			return this.board[row][col];
		}
		return "";
	}
	
	public String [][] getBoard() {
		return this.board;
	}
	
	public boolean inRange(int row,int col) {
		return row <= SudokuPanel.GRID_SIZE && col <= SudokuPanel.GRID_SIZE && row >= 0 && col >= 0;
	}
	
	private boolean isValidValue(String value) {
		for(String str : this.VALIDVALUES) {
			if(str.equals(value)) return true;
		}
		return false;
	}
	
	
	public boolean boardFull() {
		for(int r = 0;r < SudokuPanel.GRID_SIZE;r++) {
			for(int c = 0;c < SudokuPanel.GRID_SIZE;c++) {
				if(this.board[r][c].equals("")) return false;
			}
		}
		return true;
	}
	
	public void makeSlotEmpty(int row,int col) {
		this.board[row][col] = "";
	}
	
	@Override
	public String toString() {
		String str = "Game Board:\n";
		for(int row=0;row < SudokuPanel.GRID_SIZE;row++) {
			for(int col=0;col < SudokuPanel.GRID_SIZE;col++) {
				str += this.board[row][col] + " ";
			}
			str += "\n";
		}
		return str+"\n";
	}
	
	private void initializeBoard() {
		for(int row = 0;row < SudokuPanel.GRID_SIZE;row++) {
			for(int col = 0;col < SudokuPanel.GRID_SIZE;col++) {
				this.board[row][col] = "";
			}
		}
	}
	
	private void initializeMutableSlots() {
		for(int row = 0;row < SudokuPanel.GRID_SIZE;row++) {
			for(int col = 0;col < SudokuPanel.GRID_SIZE;col++) {
				this.mutable[row][col] = true;
			}
		}
	}
}

