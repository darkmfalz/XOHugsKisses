import java.util.Scanner;

public class XO {

	double[] minimaxHeap;
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
		else
			System.err.println("You're a dumbass.");	
		
	}
	
	public void minimaxConstruct(){
		
		
		
	}
	
}
