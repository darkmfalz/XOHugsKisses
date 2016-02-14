import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class XO {

	private TicTacTree current;
	Scanner scanner = new Scanner(System.in);
	//true if I am x, false if I am o
	boolean xo;
	
	public XO(){
		
		System.err.println("Will you play X or O?");
		String input = scanner.next();
		input = input.toLowerCase();
		
		if(input.equals("o"))
			xo = true;
		else if(input.equals("x"))
			xo = false;
		else{
			
			System.err.println("You're a dumbass.");
			return;
		
		}
			
		if(xo)
			current = new TicTacTree();
		else{
			
			current = new TicTacTree(scanner.nextInt());
			printBoard(current);
			
		}
		
		//minimax(current);
		//playGameMinimax();
		minimaxAB(current, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		playGameMinimaxAB();
		
	}
	
	private double minimax(TicTacTree current){
		
		if(current.isGoalState())
			return current.getScore();
		
		if(current.isMyMove()){
			
			double v = Double.NEGATIVE_INFINITY;
			TicTacTree currentChild = null;
			
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					if(current.isBlank(i, j)){
						
						TicTacTree child = current.makeChild(i, j);
						if(current.getLeftChild() == null)
							current.setLeftChild(child);
						else
							currentChild.setRightSibling(child);
						currentChild = child;
						
						v = Math.max(v, minimax(currentChild));
							
					}
			
			current.setScore(v);
			return v;
			
		}
		else{
			
			double v = Double.POSITIVE_INFINITY;
			TicTacTree currentChild = null;
			
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					if(current.isBlank(i, j)){
						
						TicTacTree child = current.makeChild(i, j);
						if(current.getLeftChild() == null)
							current.setLeftChild(child);
						else
							currentChild.setRightSibling(child);
						currentChild = child;
						
						v = Math.min(v, minimax(currentChild));
						
					}
			
			current.setScore(v);
			return v;
			
		}
		
	}

	private void playGameMinimax(){
		
		//While the goal state hasn't been reached
		while(!current.isGoalState()){
		
			//Find the best moves from the current board
			TicTacTree currentChild = current.getLeftChild();
			TicTacTree bestChild = current.getLeftChild();
			double score = bestChild.getScore();
			
			//First, find the best score among the possible moves
			while(currentChild != null){
				
				if(currentChild.getScore() > score){
					
					bestChild = currentChild;
					score = bestChild.getScore();
					
				}
				currentChild = currentChild.getRightSibling();
				
			}
			//Now create a list of the moves that have the same score as the best score
			ArrayList<TicTacTree> bestChildren = new ArrayList<TicTacTree>();
			currentChild = current.getLeftChild();
			while(currentChild != null){
				
				if(currentChild.getScore() == score)
					bestChildren.add(currentChild);
				currentChild = currentChild.getRightSibling();
				
			}
			
			//Select a move with the same score as the best move randomly
			Random random = new Random();
			current = bestChildren.get(random.nextInt(bestChildren.size()));
			printBoard(current);
			System.out.println(current.getMove());
			
			if(current.isGoalState())
				break;
			
			//Now, receive the other player's moves
			boolean goodMove = false;
			
			//While the other player doesn't input a valid move
			while(!goodMove){
				
				int move = scanner.nextInt();
				currentChild = current.getLeftChild();
				
				//Check to see if there is a valid move that is the same as the one inputted by the user
				while(currentChild != null){
					
					if(currentChild.getMove() == move){
						
						current = currentChild;
						goodMove = true;
						break;
						
					}
					currentChild = currentChild.getRightSibling();
				}
				
				if(!goodMove)
					System.err.println("Invalid move");
				
			}
			
			printBoard(current);
			
		}
		
		//Automatically reset the game once it's over
		resetGameMinimax();
		
	}
	
	private void resetGameMinimax(){
		
		System.err.println("Will you play X or O?");
		String input = scanner.next();
		input = input.toLowerCase();
		
		if(input.equals("o"))
			xo = true;
		else if(input.equals("x"))
			xo = false;
		else{
			
			System.err.println("You're a dumbass.");
			return;
		
		}
			
		if(xo)
			current = new TicTacTree();
		else{
			
			current = new TicTacTree(scanner.nextInt());
			printBoard(current);
			
		}
		
		minimax(current);
		playGameMinimax();
		
	}
	
	private double minimaxAB(TicTacTree current, double alpha, double beta){
		
		if(current.isGoalState())
			return current.getScore();
		
		if(current.isMyMove()){
			
			double v = Double.NEGATIVE_INFINITY;
			TicTacTree currentChild = null;
			
			top:
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					if(current.isBlank(i, j)){
						
						TicTacTree child = current.makeChild(i, j);
						if(current.getLeftChild() == null)
							current.setLeftChild(child);
						else
							currentChild.setRightSibling(child);
						currentChild = child;
						
						v = Math.max(v, minimaxAB(currentChild, alpha, beta));
						alpha = Math.max(alpha, v);
						
						if(beta <= alpha)
							break top;
						
					}
			
			current.setScore(v);
			return v;
			
		}
		else{
			
			double v = Double.POSITIVE_INFINITY;
			TicTacTree currentChild = null;
			
			top:
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					if(current.isBlank(i, j)){
						
						TicTacTree child = current.makeChild(i, j);
						if(current.getLeftChild() == null)
							current.setLeftChild(child);
						else
							currentChild.setRightSibling(child);
						currentChild = child;
						
						v = Math.min(v, minimaxAB(currentChild, alpha, beta));
						beta = Math.min(beta, v);
						
						if(beta <= alpha)
							break top;
						
					}
			
			current.setScore(v);
			return v;
			
		}
		
	}
	
	private void playGameMinimaxAB(){
		
		//While the goal state hasn't been reached
		while(!current.isGoalState()){
		
			//Find the best moves from the current board
			TicTacTree currentChild = current.getLeftChild();
			TicTacTree bestChild = current.getLeftChild();
			double score = bestChild.getScore();
			
			//First, find the best score among the possible moves
			while(currentChild != null){
				
				if(currentChild.getScore() > score){
					
					bestChild = currentChild;
					score = bestChild.getScore();
					
				}
				currentChild = currentChild.getRightSibling();
				
			}
			//Now create a list of the moves that have the same score as the best score
			ArrayList<TicTacTree> bestChildren = new ArrayList<TicTacTree>();
			currentChild = current.getLeftChild();
			while(currentChild != null){
				
				if(currentChild.getScore() == score)
					bestChildren.add(currentChild);
				currentChild = currentChild.getRightSibling();
				
			}
			
			//Select a move with the same score as the best move randomly
			Random random = new Random();
			current = bestChildren.get(random.nextInt(bestChildren.size()));
			printBoard(current);
			System.out.println(current.getMove());
			
			if(current.isGoalState())
				break;
			
			//Now, receive the other player's moves
			boolean goodMove = false;
			
			//While the other player doesn't input a valid move
			while(!goodMove){
				
				int move = scanner.nextInt();
				currentChild = current.getLeftChild();
				
				//Check to see if there is a valid move that is the same as the one inputted by the user
				while(currentChild != null){
					
					if(currentChild.getMove() == move){
						
						current = currentChild;
						goodMove = true;
						break;
						
					}
					currentChild = currentChild.getRightSibling();
				}
				
				//If the move isn't immediately found -- it's been pruned
				if(!goodMove){
					
					current.setLeftChild(null);
					
					top:
					for(int i = 0; i < 3; i++)
						for(int j = 0; j < 3; j++)
							if(current.isBlank(i, j)){
								
								TicTacTree child = current.makeChild(i, j);
								if(child.getMove() == move){
									
									current = child;
									goodMove = true;
									minimaxAB(current, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
									break top;
									
								}
								
							}
					
				}
				
				if(!goodMove)
					System.err.println("Invalid move");
				
			}
			
			printBoard(current);
			
		}
		
		//Automatically reset the game once it's over
		resetGameMinimaxAB();
		
	}
	
	private void resetGameMinimaxAB(){
		
		System.err.println("Will you play X or O?");
		String input = scanner.next();
		input = input.toLowerCase();
		
		if(input.equals("o"))
			xo = true;
		else if(input.equals("x"))
			xo = false;
		else{
			
			System.err.println("You're a dumbass.");
			return;
		
		}
			
		if(xo)
			current = new TicTacTree();
		else{
			
			current = new TicTacTree(scanner.nextInt());
			printBoard(current);
			
		}
		
		minimaxAB(current, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		playGameMinimaxAB();
		
	}
	
	private void printBoard(TicTacTree current){
		
		for(int i = 0; i < 3; i++){
			System.err.print("[ ");
			for(int j = 0; j < 3; j++)
				if(current.getBoard()[j][i] == 1)
					if(xo)
						System.err.print("X ");
					else
						System.err.print("O ");
				else if(current.getBoard()[j][i] == -1)
					if(xo)
						System.err.print("O ");
					else
						System.err.print("X ");
				else
					System.err.print("  ");
			System.err.println("]");
		}
		System.err.println();
		
	}
	
}
