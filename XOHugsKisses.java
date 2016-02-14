import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class XOHugsKisses {

	private NineTicTacTree current;
	Scanner scanner = new Scanner(System.in);
	//true if I am x, false if I am o
	boolean xo;
	
	public XOHugsKisses(){
		
		resetGameMinimaxAB();
		
	}
	
	private double minimaxAB(NineTicTacTree current, double alpha, double beta){
		
		if(current.isGoalState())
			return current.getScore();
		
		if(current.isMyMove()){
			
			double v = Double.NEGATIVE_INFINITY;
			NineTicTacTree currentChild = null;
			
			if(current.noMove()){
				
				top:
				for(int i = 0; i < 9; i++)
					for(int j = 0; j < 9; j++)
						if(current.isBlank(i, j)){
							
							NineTicTacTree child = current.makeChild(i, j);
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
				
			}
			else{
				
				int move1 = current.getMove2();
				
				if(current.isNotFull(move1)){
					
					int x1 = (move1 - 1)%3 * 3;
					int y1 = (move1 - 1)/3 * 3;
					
					top:
					for(int i = x1; i < 3 + x1; i++)
						for(int j = y1; j < 3 + y1; j++)
							if(current.isBlank(i, j)){
								
								NineTicTacTree child = current.makeChild(i, j);
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
					
				}
				else{
					
					top:
					for(int i = 0; i < 9; i++)
						for(int j = 0; j < 9; j++)
							if(current.isBlank(i, j)){
								
								NineTicTacTree child = current.makeChild(i, j);
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
					
				}
				
			}
			
			current.setScore(v);
			return v;
			
		}
		else{
			
			double v = Double.POSITIVE_INFINITY;
			NineTicTacTree currentChild = null;
			
			if(current.noMove()){
				
				top:
				for(int i = 0; i < 9; i++)
					for(int j = 0; j < 9; j++)
						if(current.isBlank(i, j)){
							
							NineTicTacTree child = current.makeChild(i, j);
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
				
			}
			else{
				
				int move1 = current.getMove2();
				
				if(current.isNotFull(move1)){
					
					int x1 = (move1 - 1)%3 * 3;
					int y1 = (move1 - 1)/3 * 3;
					
					top:
					for(int i = x1; i < 3 + x1; i++)
						for(int j = y1; j < 3 + y1; j++)
							if(current.isBlank(i, j)){
								
								NineTicTacTree child = current.makeChild(i, j);
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
					
				}
				else{
					
					top:
					for(int i = 0; i < 9; i++)
						for(int j = 0; j < 9; j++)
							if(current.isBlank(i, j)){
								
								NineTicTacTree child = current.makeChild(i, j);
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
					
				}
				
			}
			
			current.setScore(v);
			return v;
			
		}
		
	}
	
	private void playGameMinimaxAB(){
		
		//While the goal state hasn't been reached
		while(!current.isGoalState()){
		
			//Find the best moves from the current board
			NineTicTacTree currentChild = current.getLeftChild();
			NineTicTacTree bestChild = current.getLeftChild();
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
			ArrayList<NineTicTacTree> bestChildren = new ArrayList<NineTicTacTree>();
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
			System.out.println(current.getMove1() + " " + current.getMove2());
			
			if(current.isGoalState())
				break;
			
			//Now, receive the other player's moves
			boolean goodMove = false;
			
			//While the other player doesn't input a valid move
			while(!goodMove){
				
				String string = scanner.next();
				String[] split = string.split("\\s+");
				int move1 = Integer.parseInt(split[0]);
				int move2 = Integer.parseInt(split[1]);
				
				currentChild = current.getLeftChild();
				
				//Check to see if there is a valid move that is the same as the one inputted by the user
				while(currentChild != null){
					
					if(currentChild.getMove1() == move1 && currentChild.getMove2() == move2){
						
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
								
								NineTicTacTree child = current.makeChild(i, j);
								if(child.getMove1() == move1 && child.getMove2() == move2){
									
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
		String input = scanner.nextLine();
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
			current = new NineTicTacTree();
		else{
			
			System.err.println("Input first move");
			String string = scanner.nextLine();
			String[] split = string.split(" ");
			current = new NineTicTacTree(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
			printBoard(current);
			
		}
		
		minimaxAB(current, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		//playGameMinimaxAB();
		
	}
	
	private void printBoard(NineTicTacTree current2){
		
		for(int i = 0; i < 9; i++){
			System.err.print("[ ");
			for(int j = 0; j < 9; j++){
				
				if(current2.getBoard()[j][i] == 1)
					if(xo)
						System.err.print("X ");
					else
						System.err.print("O ");
				else if(current2.getBoard()[j][i] == -1)
					if(xo)
						System.err.print("O ");
					else
						System.err.print("X ");
				else
					System.err.print("  ");
				
				if((j + 1) % 3 == 0 && j != 8)
					System.err.print("| ");
			}
			System.err.println("]");
			if((i + 1) % 3 == 0 && i != 8){
				System.err.print(" -");
				for(int j = 0; j < 10; j++)
					System.err.print("--");
				System.err.println("--");
			}
		}
		System.err.println();
		
	}
	
}
