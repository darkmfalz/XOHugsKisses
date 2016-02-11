
public class TicTacTree {

	//Game-related
	private int[][] board;
	private int[] combos;
	private boolean isMyMove;
	private boolean goalState;
	private int winner;
	private int x;
	private int y;
	
	//Tree-related
	private TicTacTree parent;
	private TicTacTree leftChild;
	private TicTacTree rightSibling;
	
	int depth;
	
	public TicTacTree(){
		
		board = new int[3][3];
		combos = new int[8];
		isMyMove = false;
		goalState = false;
		winner = 0;
		x = -1;
		y = -1;
		parent = null;
		leftChild = null;
		rightSibling = null;
		depth = 0;
		
	}
	
	public TicTacTree(int move){
		
		board = new int[3][3];
		combos = new int[8];
		isMyMove = false;
		goalState = false;
		winner = 0;
		x = (move - 1)%3;
		y = (move - 1)/3;
		board[x][y] = -1;
		parent = null;
		leftChild = null;
		rightSibling = null;
		depth = 0;
		
	}
	
	public TicTacTree(TicTacTree parent, int[][] oldBoard, int[] oldCombos, int move, boolean isMyMove){
		depth = parent.depth + 1;
		this.parent = parent;
		leftChild = null;
		rightSibling = null;
		
		goalState = false;
		winner = 0;
		this.isMyMove = isMyMove;
		x = (move - 1)%3;
		y = (move - 1)/3;
		board = new int[oldBoard.length][oldBoard[0].length];
		
		//Transfer the board over -- plus the move that was made
		int wiener = 1;
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board[i].length; j++){
				if(i != x || j != y)
					board[i][j] = oldBoard[i][j];
				else
					if(isMyMove)
						board[i][j] = 1;
					else
						board[i][j] = -1;
				wiener *= board[i][j];
			}
		//If the board is full, this is a terminal state
		if(wiener != 0)
			goalState = true;
		
		//Check for winning combinations
		//0, 1, 2 are the vertical combos
		//3, 4, 5 are the horizontal combos
		//6, 7 are the diagonal combos
		combos = new int[oldCombos.length];
		for(int i = 0; i < combos.length; i++)
			combos[i] = oldCombos[i];
		
		combos[x] += board[x][y];
		if(Math.abs(combos[x]) >= 3){
			
			goalState = true;
			winner = Math.abs(combos[x])/combos[x];
			
		}
		combos[y + 3] += board[x][y];
		if(Math.abs(combos[y + 3]) >= 3){
			
			goalState = true;
			winner = Math.abs(combos[y + 3])/combos[y + 3];
			
		}
		if(x == y){
			
			combos[6] += board[x][y];
			if(Math.abs(combos[6]) >= 3){
				
				goalState = true;
				winner = Math.abs(combos[6])/combos[6];
				
			}
			
		}
		if(x == 2 - y){
			
			combos[7] += board[x][y];
			if(Math.abs(combos[7]) >= 3){
				
				goalState = true;
				winner = Math.abs(combos[7])/combos[7];
				
			}
			
		}
		
	}
	
	public TicTacTree(TicTacTree parent, int[][] oldBoard, int[] oldCombos, int x, int y, boolean isMyMove){
		depth = parent.depth + 1;
		this.parent = parent;
		leftChild = null;
		rightSibling = null;
		
		goalState = false;
		winner = 0;
		this.isMyMove = isMyMove;
		this.x = x;
		this.y = y;
		board = new int[oldBoard.length][oldBoard[0].length];
		
		//Transfer the board over -- plus the move that was made
		int wiener = 1;
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board[i].length; j++){
				if(i != x || j != y)
					board[i][j] = oldBoard[i][j];
				else
					if(isMyMove)
						board[i][j] = 1;
					else
						board[i][j] = -1;
				wiener *= board[i][j];
			}
		//If the board is full, this is a terminal state
		if(wiener != 0)
			goalState = true;
		
		//Check for winning combinations
		combos = new int[oldCombos.length];
		for(int i = 0; i < combos.length; i++)
			combos[i] = oldCombos[i];
		
		combos[x] += board[x][y];
		if(Math.abs(combos[x]) >= 3){
			
			goalState = true;
			winner = Math.abs(combos[x])/combos[x];
			
		}
		combos[y + 3] += board[x][y];
		if(Math.abs(combos[y + 3]) >= 3){
			
			goalState = true;
			winner = Math.abs(combos[y + 3])/combos[y + 3];
			
		}
		if(x == y){
			
			combos[6] += board[x][y];
			if(Math.abs(combos[6]) >= 3){
				
				goalState = true;
				winner = Math.abs(combos[6])/combos[6];
				
			}
			
		}
		if(x == 2 - y){
			
			combos[7] += board[x][y];
			if(Math.abs(combos[7]) >= 3){
				
				goalState = true;
				winner = Math.abs(combos[7])/combos[7];
				
			}
			
		}
		
	}
	
	//Getters
	public int getMove(){
		
		return 1 + x + 3*y;
				
	}
	
	public boolean isGoalState(){
		
		return goalState;
		
	}
	
	public int getWinner(){
		
		return winner;
		
	}
	
	public int[][] getBoard(){
		
		return board;
		
	}
	
	public boolean isMyMove(){
		
		return isMyMove;
		
	}
	
	public boolean isBlank(int x, int y){
		
		return board[x][y] == 0;
		
	}
	
	public TicTacTree getParent(){
		
		return parent;
		
	}
	
	public TicTacTree getLeftChild(){
		
		return leftChild;
		
	}
	
	public TicTacTree getRightSibling(){
		
		return rightSibling;
		
	}
	
	//Setters
	public void setWinner(int winner){
		
		this.winner = winner;
		
	}
	
	public void setLeftChild(TicTacTree leftChild){
		
		this.leftChild = leftChild;
		
	}
	
	public void setRightSibling(TicTacTree rightSibling){
		
		this.rightSibling = rightSibling;
		
	}
	
	public TicTacTree makeChild(int x, int y){
		
		TicTacTree child = new TicTacTree(this, board, combos, x, y, !isMyMove);
		return child;
		
	}
	
}
