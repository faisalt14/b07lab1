import java.io.File;
import java.util.Arrays;

public class Driver {
	public static void main(String [] args) throws Exception{
		
		System.out.println("Testing Method add, evaluate, and hasroot "); 
		System.out.println("");
		
		double [] c1 = {1,-6,11,-6}; 
		int [] e1 = {3,2,1,0};
		Polynomial p1 = new Polynomial(c1, e1);
		
		double [] c2 = {6,5}; 
		int [] e2 = {3,1};
		Polynomial p2 = new Polynomial(c2, e2);
		
		Polynomial result = p1.add(p2);
		
		System.out.println("The coefficients are: " + Arrays.toString(result.coefficients)); 
		System.out.println("The exponents are: " + Arrays.toString(result.exponents)); 
		
		int x =2; 
		System.out.println("s(2) for p1 = " + p1.evaluate(x));
		System.out.println("Is x=2 a root for p1?: " + p1.hasRoot(x));
		
		
		System.out.println("");
		System.out.println("Testing Method Multiply "); 
		System.out.println("");
		
		double [] c3 = {1,2,1}; 
		int [] e3 = {2,1,0};
		Polynomial p3 = new Polynomial(c3, e3);
		
		double [] c4 = {1,3}; 
		int [] e4 = {1,0};
		Polynomial p4 = new Polynomial(c4, e4);
		
		Polynomial product = p3.multiply(p4);
		
		System.out.println("The coefficients are: " + Arrays.toString(product.coefficients)); 
		System.out.println("The exponents are: " + Arrays.toString(product.exponents)); 
		
		
		
		
	
		System.out.println("");
		System.out.println("Testing file constructor");
		System.out.println("");
		
		File file = new File("src/file.txt");
		Polynomial p5 = new Polynomial(file);
		
		System.out.println("The coefficients are: " + Arrays.toString(p5.coefficients)); 
		System.out.println("The exponents are: " + Arrays.toString(p5.exponents)); 
		
		
		
		System.out.println("");
		System.out.println("Testing saveToFile");
		System.out.println("");
		
		double [] c5 = {-1,-6,11,-6}; 
		int [] e5 = {3,2,1,0};
		Polynomial p6 = new Polynomial(c5, e5);
		
		p6.saveToFile("testFile.txt");
		

	}

}
 