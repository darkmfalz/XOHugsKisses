import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


public class Test {

	public static void main(String[] args){
		
		try {
			XO larry = new XO();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
