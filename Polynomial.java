public class Polynomial {
	
	// fields 
	double[] coefficients; 
	
	// Constructor 1
	public Polynomial() {
		coefficients = new double[1]; 
		coefficients[0] = 0; 
	}
	
	// Constructor 3
	public Polynomial(double [] arr) {
		coefficients = new double[arr.length];
		coefficients = arr;  
	}
	
	public Polynomial add (Polynomial poly) {
		Polynomial result; 
		
		// Note: I check to see which given polynomial is shorter so that when I iterate to add the values, 
			// the longer polynomial's end values are included as well. 
		
		if (this.coefficients.length >= poly.coefficients.length) {
			 result = new Polynomial(this.coefficients); 
		}
		else {
			 result = new Polynomial(poly.coefficients); 
		}
		
		
		for (int i = 0; i < Math.min(this.coefficients.length, poly.coefficients.length); i++) {
			result.coefficients[i] = this.coefficients[i] + poly.coefficients[i]; 
			
		}
		
		return result; 
	}
	
	public double evaluate(double x) {
		double result = this.coefficients[0]; 
		
		for (int i =1; i < this.coefficients.length; i++) {
			result += this.coefficients[i] * Math.pow(x,i); 
		}
		
		return result; 
	}
	
	public Boolean hasRoot(double x) {	
		return this.evaluate(x) == 0; 
		
	}

}
