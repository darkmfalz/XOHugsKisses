
public class TicTacTree {

	private int[][] board;
	private int[] combos;
	private boolean isMyMove, goalState;
	private double score;
	private int x, y, depth;
	private TicTacTree parent, leftChild, rightSibling;
	
	//Constructor for when XO goes first
	public TicTacTree(){
			
		board = new int[3][3];
		x = -1;
		y = -1;
		combos = new int[8];
		isMyMove = true;
		goalState = false;
		score = 1;
		depth = 0;
		parent = null;
		leftChild = null;
		rightSibling = null;
			
	}
	
	//Constructor for when opponent goes first
	public TicTacTree(int move){
		
		//This is always after the opponent's first move
		board = new int[3][3];
		x = (move - 1)%3;
		y = (move - 1)/3;
		board[x][y] = -1;
		combos = new int[8];
		//Update combination history
		combos[x] += board[x][y];
		combos[y + 3] += board[x][y];
		if(x == y)
			combos[6] += board[x][y];
		if(x == 2 - y)
			combos[7] += board[x][y];
		
		isMyMove = true;
		goalState = false;
		score = 1;
		depth = 0;
		parent = null;
		leftChild = null;
		rightSibling = null;
		
	}
	
	//General constructor
	public TicTacTree(TicTacTree parent, int x, int y){
		
		//Tree-specific somethings
		depth = parent.depth + 1;
		this.parent = parent;
		leftChild = null;
		rightSibling = null;
		
		//Game-specific somethings
		goalState = false;
		//Initializing the scores as such will prevent unexpanded nodes from bein selected
		if(isMyMove)
			score = 1;
		else
			score = -1;
		isMyMove = !parent.isMyMove();
		this.x = x;
		this.y = y;
		
		int[][] oldBoard = parent.getBoard();
		board = new int[oldBoard.length][oldBoard[0].length];
		
		//Transfer the board over -- plus the move that was made
		int wiener = 1;
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board[i].length; j++){
				if(i != x || j != y)
					board[i][j] = oldBoard[i][j];
				else
					if(isMyMove)
						board[i][j] = -1;
					else
						board[i][j] = 1;
				wiener *= board[i][j];
			}
		//If the board is full, this is a terminal state
		if(wiener != 0)
			goalState = true;
		
		//Check for winning combinations
		//0, 1, 2 are the vertical combos
		//3, 4, 5 are the horizontal combos
		//6, 7 are the diagonal combos
		boolean hasCombo = false;
		int[] oldCombos = parent.getCombos();
		combos = new int[oldCombos.length];
		for(int i = 0; i < combos.length; i++)
			combos[i] = oldCombos[i];
		
		combos[x] += board[x][y];
		if(Math.abs(combos[x]) >= 3){
			
			hasCombo = true;
			goalState = true;
			score = Math.abs(combos[x])/combos[x];
			
		}
		combos[y + 3] += board[x][y];
		if(Math.abs(combos[y + 3]) >= 3){
			
			hasCombo = true;
			goalState = true;
			score = Math.abs(combos[y + 3])/combos[y + 3];
			
		}
		if(x == y){
			
			combos[6] += board[x][y];
			if(Math.abs(combos[6]) >= 3){
				
				hasCombo = true;
				goalState = true;
				score = Math.abs(combos[6])/combos[6];
				
			}
			
		}
		if(x == 2 - y){
			
			combos[7] += board[x][y];
			if(Math.abs(combos[7]) >= 3){
				
				hasCombo = true;
				goalState = true;
				score = Math.abs(combos[7])/combos[7];
				
			}
			
		}
		
		if(!hasCombo && wiener != 0)
			score = 0;
		
	}

	//Getters
	public int[] getCombos() {

		return combos;
		
	}

	public int[][] getBoard() {
		
		return board;
		
	}

	public boolean isMyMove() {
		
		return isMyMove;
		
	}
	
	public boolean isGoalState(){
		
		return goalState;
		
	}

	public boolean isBlank(int x, int y){
		
		return board[x][y] == 0;
		
	}
	
	public double getScore(){
		
		return score;
		
	}
	
	public int getMove(){
		
		return 1 + x + 3*y;
		
	}
	
	public int getDepth(){
		
		return depth;
		
	}
	
	public TicTacTree getLeftChild(){
		
		return leftChild;
		
	}
	
	public TicTacTree getRightSibling(){
		
		return rightSibling;
		
	}
	
	//Setters
	public void setScore(double score){
		
		this.score = score;
		
	}
	
	public void setLeftChild(TicTacTree child){
		
		leftChild = child;
		
	}

	public void setRightSibling(TicTacTree child){
		
		rightSibling = child;
		
	}
	
	//Miscellaneous
	public TicTacTree makeChild(int x, int y){
		
		TicTacTree child = new TicTacTree(this, x, y);
		return child;
		
	}
	
}
