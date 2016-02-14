import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class XOold {


	private TicTacTreeold current;
	Scanner scanner = new Scanner(System.in);
	//true if I am x, false if I am o
	boolean xo;
	
	public XOold() throws FileNotFoundException, UnsupportedEncodingException{
		
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
			current = new TicTacTreeold();
		else{
			
			current = new TicTacTreeold(scanner.nextInt());
			printBoard(current);
			
		}
		populateTreeAB(current, Integer.MIN_VALUE, Integer.MAX_VALUE);
		current = new TicTacTreeold();
		//populateTree(current);
		//playGameAB();
		//populateTree(current);
		//playGame();
		
	}
	
	private void playGame(){
		
		while(!current.isGoalState()){
		
			TicTacTreeold currentChild = current.getLeftChild();
			TicTacTreeold bestChild = current.getLeftChild();
			int score = bestChild.getWinner();
			
			while(currentChild != null){
				
				if(currentChild.getWinner() > score){
					
					bestChild = currentChild;
					score = bestChild.getWinner();
					
				}
				currentChild = currentChild.getRightSibling();
				
			}
			ArrayList<TicTacTreeold> bestChildren = new ArrayList<TicTacTreeold>();
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
			current = new TicTacTreeold();
		else{
			
			current = new TicTacTreeold(scanner.nextInt());
			printBoard(current);
			
		}
		//populateTreeAB(current, Integer.MIN_VALUE, Integer.MAX_VALUE);
		//playGameAB();
		populateTree(current);
		playGame();
	
	}
	
	private void populateTree(TicTacTreeold current){

		if(!current.isGoalState()){
			
			TicTacTreeold currentChild = null;
			
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					if(current.isBlank(i, j)){
						
						TicTacTreeold child = current.makeChild(i, j);
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
						
						if(current.depth == 0)
							System.out.println(i + " " + j + " " + currentChild.getWinner());
						
					}
			
		}
		
	}
	
	private void playGameAB(){
		
		while(!current.isGoalState()){
		
			TicTacTreeold currentChild = current.getLeftChild();
			TicTacTreeold bestChild = current.getLeftChild();
			int score = bestChild.getWinner();
			
			while(currentChild != null){
				
				if(currentChild.getWinner() > score && currentChild.getWinner() != 2){
					
					bestChild = currentChild;
					score = bestChild.getWinner();
					
				}
				currentChild = currentChild.getRightSibling();
				
			}
			ArrayList<TicTacTreeold> bestChildren = new ArrayList<TicTacTreeold>();
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
								
								TicTacTreeold child = current.makeChild(i, j);
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
		
		resetGameAB();
		
	}
	
	private void resetGameAB(){
		
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
			current = new TicTacTreeold();
		else{
			
			current = new TicTacTreeold(scanner.nextInt());
			printBoard(current);
			
		}
		populateTreeAB(current, Integer.MIN_VALUE, Integer.MAX_VALUE);
		playGameAB();
		//populateTree(current);
		//playGame();
	
	}
	
	private int populateTreeAB(TicTacTreeold current, int alpha, int beta){
		
		if(current.isGoalState()){
			return current.getWinner();
		}
		
		if(current.isMyMove()){
			
			TicTacTreeold currentChild = current.getLeftChild();
			int v = Integer.MIN_VALUE;
			
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++){
					
					if(current.isBlank(i, j)){
						
						TicTacTreeold child = current.makeChild(i, j);
						
						v = Math.max(v, populateTreeAB(child, alpha, beta));
						if(current.getLeftChild() == null){
							current.setLeftChild(child);
							current.setWinner(child.getWinner());
						}
						else
							currentChild.setRightSibling(child);
						currentChild = child;
						alpha = Math.max(alpha, v);
						if(child.getWinner() != 2)
							current.setWinner(Math.min(current.getWinner(), child.getWinner()));
						
						if(beta <= alpha){
							current.setWinner(v);
							return v;
						}
						
						if(current.depth == 0)
							System.out.println(i + " " + j + " " + currentChild.getWinner());
						
					}
					
				}
			
			current.setWinner(v);
			return v;
			
		}
		else{
			
			TicTacTreeold currentChild = current.getLeftChild();
			int v = Integer.MAX_VALUE;
			
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++){
					
					if(current.isBlank(i, j)){
						
						TicTacTreeold child = current.makeChild(i, j);
						
						v = Math.min(v, populateTreeAB(child, alpha, beta));
						if(current.getLeftChild() == null){
							current.setLeftChild(child);
							current.setWinner(child.getWinner());
						}
						else
							currentChild.setRightSibling(child);
						currentChild = child;
						beta = Math.min(beta, v);
						if(child.getWinner() != 2)
							current.setWinner(Math.max(current.getWinner(), child.getWinner()));
						
						if(beta <= alpha){
							current.setWinner(v);
							return v;
						}
						
						if(current.depth == 0)
							System.out.println(i + " " + j + " " + currentChild.getWinner());
						
					}
					
				}
			
			current.setWinner(v);
			return v;
			
		}
		
	}
	
	private void printBoard(TicTacTreeold current){
		
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
		
		LinkedList<TicTacTreeold> queue = new LinkedList<TicTacTreeold>();
		queue.add(current);
		
		while(!queue.isEmpty()){
			
			TicTacTreeold thisun = queue.poll();
			
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
			
			TicTacTreeold currentChild = thisun.getLeftChild();
			
			while(currentChild != null){
				
				queue.add(currentChild);
				currentChild = currentChild.getRightSibling();
				
			}
			
		}
		
		writer.close();
		
	}
	
}
