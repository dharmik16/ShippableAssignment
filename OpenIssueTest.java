import java.text.ParseException;
import java.util.Scanner;

import javax.swing.JFrame;

/**
 * Class containing the main function
 * Creates an object to GetResult class
 */ 
public class OpenIssueTest {
	
	public static void main(String args[]) throws ParseException{
		GetResult g = new GetResult();
		g.setLocation(10, 10);
		g.setSize(300, 300);
		g.setVisible(true);
	}
}
