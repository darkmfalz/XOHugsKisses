import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class XO {


	private TicTacTree current;
	Scanner scanner = new Scanner(System.in);
	//true if I am x, false if I am o
	boolean xo;
	
	public XO() throws FileNotFoundException, UnsupportedEncodingException{
		
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
		populateTreeAB(current, Integer.MIN_VALUE, Integer.MAX_VALUE);
		playGameAB();
		//populateTree(current);
		//playGame();
		
	}
	
	private void playGame(){
		
		while(!current.isGoalState()){
		
			TicTacTree currentChild = current.getLeftChild();
			TicTacTree bestChild = current.getLeftChild();
			int score = bestChild.getWinner();
			
			while(currentChild != null){
				
				if(currentChild.getWinner() > score){
					
					bestChild = currentChild;
					score = bestChild.getWinner();
					
				}
				currentChild = currentChild.getRightSibling();
				
			}
			ArrayList<TicTacTree> bestChildren = new ArrayList<TicTacTree>();
			currentChild = current.getLeftChild();
			while(currentChild != null){
				
				if(currentChild.getWinner() == score)
					bestChildren.add(currentChild);
				currentChild = currentChild.getRightSibling();
				
			}
			
			Random random = new Random();
			current = bestChildren.get(random.nextInt(bestChildren.size()));
			printBoard(current);
			System.out.println(current.getMove());
			
			if(current.isGoalState())
				break;
			
			boolean goodMove = false;
			
			while(!goodMove){
				
				int move = scanner.nextInt();
				currentChild = current.getLeftChild();
				
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
		
		resetGame();
		
	}
	
	private void resetGame(){
		
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
		//populateTreeAB(current, Integer.MIN_VALUE, Integer.MAX_VALUE);
		//playGameAB();
		populateTree(current);
		playGame();
	
	}
	
	private void populateTree(TicTacTree current){

		if(!current.isGoalState()){
			
			TicTacTree currentChild = null;
			
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					if(current.isBlank(i, j)){
						
						TicTacTree child = current.makeChild(i, j);
						if(current.getLeftChild() == null){
							
							current.setLeftChild(child);
							populateTree(child);
							current.setWinner(child.getWinner());
							currentChild = child;
							
						}
						else{
							
							currentChild.setRightSibling(child);
							populateTree(child);
							currentChild = child;
							
						}
						
						if(!current.isMyMove())
							current.setWinner(Math.max(current.getWinner(), child.getWinner()));
						else
							current.setWinner(Math.min(current.getWinner(), child.getWinner()));
						
					}
			
		}
		
	}
	
	private void playGameAB(){
		
		while(!current.isGoalState()){
		
			TicTacTree currentChild = current.getLeftChild();
			TicTacTree bestChild = current.getLeftChild();
			int score = bestChild.getWinner();
			
			while(currentChild != null){
				
				if(currentChild.getWinner() > score){
					
					bestChild = currentChild;
					score = bestChild.getWinner();
					
				}
				currentChild = currentChild.getRightSibling();
				
			}
			ArrayList<TicTacTree> bestChildren = new ArrayList<TicTacTree>();
			currentChild = current.getLeftChild();
			while(currentChild != null){
				
				if(currentChild.getWinner() == score)
					bestChildren.add(currentChild);
				currentChild = currentChild.getRightSibling();
				
			}
			
			Random random = new Random();
			current = bestChildren.get(random.nextInt(bestChildren.size()));
			printBoard(current);
			System.out.println(current.getMove());
			System.err.println(current.getWinner());
			
			if(current.isGoalState())
				break;
			
			boolean goodMove = false;
			
			while(!goodMove){
				
				int move = scanner.nextInt();
				System.err.println(current.getWinner());
				currentChild = current.getLeftChild();
				
				while(currentChild != null){
					
					if(currentChild.getMove() == move){
						
						current = currentChild;
						goodMove = true;
						break;
						
					}
					currentChild = currentChild.getRightSibling();
				}
				
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
									populateTreeAB(current, Integer.MIN_VALUE, Integer.MAX_VALUE);
									break top;
									
								}
								
							}
					
				}
				
				if(!goodMove)
					System.err.println("Invalid move");
				
			}
			
			printBoard(current);
			
		}
		
	}
	
	private int populateTreeAB(TicTacTree current, int alpha, int beta){
		
		if(current.isGoalState())
			return current.getWinner();
		
		if(current.isMyMove()){
			
			TicTacTree currentChild = current.getLeftChild();
			int v = Integer.MIN_VALUE;
			
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++){
					
					if(current.isBlank(i, j)){
						
						TicTacTree child = current.makeChild(i, j);
						
						v = Math.max(v, populateTreeAB(child, alpha, beta));
						if(current.getLeftChild() == null){
							current.setLeftChild(child);
							current.setWinner(child.getWinner());
						}
						else
							currentChild.setRightSibling(child);
						currentChild = child;
						alpha = Math.max(alpha, v);
						current.setWinner(Math.min(current.getWinner(), currentChild.getWinner()));
						if(beta <= alpha)
							return v;
						
					}
					
				}
			
			return v;
			
		}
		else{
			
			TicTacTree currentChild = current.getLeftChild();
			int v = Integer.MAX_VALUE;
			
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++){
					
					if(current.isBlank(i, j)){
						
						TicTacTree child = current.makeChild(i, j);
						
						v = Math.min(v, populateTreeAB(child, alpha, beta));
						if(current.getLeftChild() == null){
							current.setLeftChild(child);
							current.setWinner(child.getWinner());
						}
						else
							currentChild.setRightSibling(child);
						currentChild = child;
						beta = Math.min(beta, v);
						current.setWinner(Math.max(current.getWinner(), child.getWinner()));
						if(beta <= alpha)
							return v;
						
					}
					
				}
			
			return v;
			
		}
		
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
	
	private void printTree() throws FileNotFoundException, UnsupportedEncodingException{
		
		PrintWriter writer = new PrintWriter("testdata.txt", "UTF-8");
		
		LinkedList<TicTacTree> queue = new LinkedList<TicTacTree>();
		queue.add(current);
		
		while(!queue.isEmpty()){
			
			TicTacTree thisun = queue.poll();
			
			writer.println(thisun.getWinner() + " " + thisun.depth + " " + thisun.isMyMove() + " " + thisun.isGoalState());
			for(int i = 0; i < 3; i++){
				writer.print("[ ");
				for(int j = 0; j < 3; j++)
					if(thisun.getBoard()[j][i] == 1)
						if(xo)
							writer.print("X ");
						else
							writer.print("O ");
					else if(thisun.getBoard()[j][i] == -1)
						if(xo)
							writer.print("O ");
						else
							writer.print("X ");
					else
						writer.print("  ");
				writer.println("]");
			}
			writer.println();
			
			TicTacTree currentChild = thisun.getLeftChild();
			
			while(currentChild != null){
				
				queue.add(currentChild);
				currentChild = currentChild.getRightSibling();
				
			}
			
		}
		
		writer.close();
		
	}
	
}
