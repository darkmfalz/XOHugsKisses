
public class NineTicTacTree {

	private int[][] board;
	private int[][] combos;
	private boolean isMyMove, goalState;
	private boolean[] fullBoard;
	private double score, heuristic;
	private int x, y, depth;
	private NineTicTacTree parent, leftChild, rightSibling;
	
	//Constructor for when XO goes first
	public NineTicTacTree(){
			
		board = new int[9][9];
		x = -1;
		y = -1;
		combos = new int[9][8];
		fullBoard = new boolean[9];
		for(int i = 0; i < fullBoard.length; i++)
			fullBoard[i] = false;
		isMyMove = true;
		goalState = false;
		score = 1;
		depth = 0;
		parent = null;
		leftChild = null;
		rightSibling = null;
			
	}
	
	//Constructor for when opponent goes first
	public NineTicTacTree(int move1, int move2){
		
		//This is always after the opponent's first move
		board = new int[9][9];
		int x1 = (move1 - 1)%3;
		int y1 = (move1 - 1)/3;
		x = x1*3 + (move2 - 1)%3;
		y = y1*3 + (move2 - 1)/3;
		board[x][y] = -1;
		combos = new int[9][8];
		//Update combination history
		x1 *= 3;
		y1 *= 3;
		combos[move1 - 1][x - x1] += board[x][y];
		combos[move1 - 1][y - y1 + 3] += board[x][y];
		if(x == y)
			combos[move1 - 1][6] += board[x][y];
		if(x == 2 - y)
			combos[move1 - 1][7] += board[x][y];
		
		fullBoard = new boolean[9];
		for(int i = 0; i < fullBoard.length; i++)
			fullBoard[i] = false;
		
		isMyMove = true;
		goalState = false;
		score = 1;
		depth = 0;
		parent = null;
		leftChild = null;
		rightSibling = null;
		
	}
	
	//General constructor
	public NineTicTacTree(NineTicTacTree parent, int x, int y){
		
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
		this.fullBoard = new boolean[9];
		int fullBoard = 1;
		for(int i = 0; i < 9; i++){
			int singleFullBoard = 1;
			for(int j = 0; j < 9; j++){
				
				int x1 = (i)%3;
				int y1 = (i)/3;
				int x2 = x1*3 + (j)%3;
				int y2 = y1*3 + (j)/3;
				
				if(x2 != x || y2 != y)
					board[x2][y2] = oldBoard[x2][y2];
				else
					if(isMyMove)
						board[x2][y2] = -1;
					else
						board[x2][y2] = 1;
				fullBoard *= board[x2][y2];
				singleFullBoard *= board[x2][y2];
			}
			if(singleFullBoard != 0)
				this.fullBoard[i] = true;
			else
				this.fullBoard[i] = false;
		}
		//If the board is full, this is a terminal state
		if(fullBoard != 0)
			goalState = true;
		
		//Check for winning combinations
		//0, 1, 2 are the vertical combos
		//3, 4, 5 are the horizontal combos
		//6, 7 are the diagonal combos
		boolean hasCombo = false;
		int[][] oldCombos = parent.getCombos();
		combos = new int[oldCombos.length][oldCombos[0].length];
		for(int i = 0; i < combos.length; i++)
			for(int j = 0; j < combos[i].length; j++)
				combos[i][j] = oldCombos[i][j];
		
		int x1 = (getMove1() - 1)%3 * 3;
		int y1 = (getMove1() - 1)/3 * 3;
		
		combos[getMove1() - 1][x - x1] += board[x][y];
		if(Math.abs(combos[getMove1() - 1][x - x1]) >= 3){
			
			hasCombo = true;
			goalState = true;
			score = Math.abs(combos[getMove1() - 1][x - x1])/combos[getMove1() - 1][x - x1];
			
		}
		combos[getMove1() - 1][y - y1 + 3] += board[x][y];
		if(Math.abs(combos[getMove1() - 1][y - y1 + 3]) >= 3){
			
			hasCombo = true;
			goalState = true;
			score = Math.abs(combos[getMove1() - 1][y - y1 + 3])/combos[getMove1() - 1][y - y1 + 3];
			
		}
		if(x - x1 == y - y1){
			
			combos[getMove1() - 1][6] += board[x][y];
			if(Math.abs(combos[getMove1() - 1][6]) >= 3){
				
				hasCombo = true;
				goalState = true;
				score = Math.abs(combos[getMove1() - 1][6])/combos[getMove1() - 1][6];
				
			}
			
		}
		if(x - x1 == 2 - (y - y1)){
			
			combos[getMove1() - 1][7] += board[x][y];
			if(Math.abs(combos[getMove1() - 1][7]) >= 3){
				
				hasCombo = true;
				goalState = true;
				score = Math.abs(combos[getMove1() - 1][7])/combos[getMove1() - 1][7];
				
			}
			
		}
		
		if(!hasCombo && fullBoard != 0)
			score = 0;
		
		heuristic = score;
		
	}

	//Getters
	public int[][] getCombos() {

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
	
	public boolean isNotFull(int board){
		
		return !fullBoard[board - 1];
		
	}
	
	public boolean noMove(){
		
		return (x == -1) && (y == -1);
		
	}
	
	public double getScore(){
		
		return score;
		
	}
	
	public double getHeuristic(){
		
		return heuristic;
		
	}
	
	public boolean compareMove(int move1, int move2){
		
		int x1 = (move1 - 1)%3;
		int y1 = (move1 - 1)/3;
		int x2 = x1*3 + (move2 - 1)%3;
		int y2 = y1*3 + (move2 - 1)/3;
		
		return (x2 == x && y2 == y);
		
	}
	
	public int getMove1(){
		
		int a = x/3;
		int b = y/3;
		
		return 1 + a + 3*b;
		
	}
	
	public int getMove2(){
		
		int a = x - (x/3)*3;
		int b = y - (y/3)*3;
		
		return 1 + a + 3*b;
		
	}
	
	public int getDepth(){
		
		return depth;
		
	}
	
	public NineTicTacTree getLeftChild(){
		
		return leftChild;
		
	}
	
	public NineTicTacTree getRightSibling(){
		
		return rightSibling;
		
	}
	
	//Setters
	public void setScore(double score){
		
		this.score = score;
		
	}
	
	public void setHeuristic(double heuristic){
		
		this.heuristic = heuristic;
		
	}
	
	public double heuristicFunction(){
		
		heuristic = 0;
		
		for(int i = 0; i < combos.length; i++)
			for(int j = 0; j < combos[i].length; j++){
				
				if(Math.abs(combos[i][j]) == 1)
					heuristic += combos[i][j];
				else if(Math.abs(combos[i][j]) == 2)
					heuristic += combos[i][j]*10;
				else if(Math.abs(combos[i][j]) == 3)
					heuristic += combos[i][j]*100;
					
			}
		
		return heuristic;
		
	}
	
	public void setLeftChild(NineTicTacTree child){
		
		leftChild = child;
		
	}

	public void setRightSibling(NineTicTacTree child){
		
		rightSibling = child;
		
	}
	
	//Miscellaneous
	public NineTicTacTree makeChild(int x, int y){
		
		NineTicTacTree child = new NineTicTacTree(this, x, y);
		return child;
		
	}
	
}
