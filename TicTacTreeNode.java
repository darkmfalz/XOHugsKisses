
public class TicTacTreeNode {

	private int[][] board;
	private int[] combos;
	private boolean isMyMove;
	private boolean goalState;
	private int winner;
	private int x;
	private int y;
	
	public TicTacTreeNode(int[][] oldBoard, int[] oldCombos, int move, boolean isMyMove){
		
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
	
}
