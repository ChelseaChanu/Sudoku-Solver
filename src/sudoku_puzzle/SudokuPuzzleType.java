package sudoku_puzzle;

public enum SudokuPuzzleType {
	NINEBYNINE(new String[] {"1","2","3","4","5","6","7","8","9"},"9 By 9 Game");
	
	private final String [] validValues;
	private final String desc;
	
	private SudokuPuzzleType(String [] validValues,String desc) {
		this.validValues = validValues;
		this.desc = desc;
	}

	
	public String [] getValidValues() {
		return validValues;
	}
	
	public String toString() {
		return desc;
	}
}

