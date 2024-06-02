import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;

public class Polynomial {
	
	// Fields 
	double[] coefficients; 
	int[] exponents; 
	
	// Constructor 1
	public Polynomial() {
					
		coefficients = null; 
		exponents = null; 
	}
	
	// Constructor 2
	public Polynomial(double [] coArr, int [] exArr) {
		
		coefficients = new double[coArr.length];
		for (int i = 0; i < coArr.length; i++) {
			coefficients[i] = coArr[i]; 
		}
		
		
		exponents = new int [exArr.length]; 
		for (int i = 0; i < exArr.length; i++) {
			exponents[i] = exArr[i]; 
		}
	}
	
	// Constructor 3
	public Polynomial(File filename) throws Exception {
		BufferedReader input = new BufferedReader(new FileReader(filename)); 
		String line = input.readLine(); 
		
		// Split lines at each + or - and keep the signs as part of the string. 
		String[] terms = line.split("(?=[+-])"); 
		
		coefficients = new double[terms.length]; 
		exponents = new int[terms.length]; 
		
		// Index to insert into for coefficients and exponents
		int index = 0; 
		
		for (int i = 0; i < terms.length; i++) {
			
			// if there is no x in the term, exponent is 0
			if (terms[i].contains("x") == false) {
				double coefficient = Double.parseDouble(terms[i]); 
				
				this.coefficients[index] = coefficient; 
				this.exponents[index] = 0; 
				index++; 
				
			}
			
			// if x is in the term, there is an exponent
			else {
				
				String[] components = terms[i].split("x"); 
				double coefficient = Double.parseDouble(components[0]);
				int exponent = Integer.parseInt(components[1]); 
				
				this.coefficients[index] = coefficient; 
				this.exponents[index] = exponent; 
				index++; 
			}
		}
		
		input.close();
		
		
		
	}
	
	public Polynomial add (Polynomial poly) {
		
		int maxSize = this.coefficients.length + poly.coefficients.length; 
		double[] resultCo = new double[maxSize]; 
		int[] resultEx = new int[maxSize]; 
		
		// boolean array to keep track of terms that have been matched in the argument polynomial 
		boolean[] matched = new boolean[poly.coefficients.length]; 
		
		// boolean flag to check whether we have a 0 polynomial 
		boolean empty = true; 
		
		// Next index in resultCo & resultEx to add values into, to ensure we don't overwrite data. 
		int index = 0; 

				
		// Iterate through the calling polynomial's terms to find matches in argument polynomial 
		for (int i = 0; i < this.exponents.length; i++) {
			boolean matchFound = false; 
			for (int k = 0; k < poly.exponents.length; k++ ) {
				
				if (this.exponents[i] == poly.exponents[k] && (this.coefficients[i] + poly.coefficients[k] == 0)) {
					matched[k] = true;
					matchFound = true;
					break; 
					
				}
				else if (this.exponents[i] == poly.exponents[k]) {
					resultCo[index] = this.coefficients[i] + poly.coefficients[k]; 
					resultEx[index] = this.exponents[i]; 
					
					matched[k] = true; 
					matchFound = true; 
					empty = false; 
					index++; 
					break; 
				}
			}
			
			if (!matchFound) {
				resultCo[index] = this.coefficients[i]; 
				resultEx[index] = this.exponents[i]; 
				index++; 
			}			
		}
		
		// Add terms from the argument polynomial that had to like-terms with the calling polynomial 
		for (int i=0; i < poly.coefficients.length; i++) {
			if (matched[i] != true) {
				empty = false;
				resultCo[index] = poly.coefficients[i]; 
				resultEx[index] = poly.exponents[i]; 
				index++; 
			}
		}
		 
		if (empty) {
			return new Polynomial(); 	
		}
		
		// Remove extra space from arrays 
		double[] resultCoefficients = new double[index]; 
		int[] resultExponents = new int[index]; 
		
		for (int i =0; i < index; i++) {
			resultCoefficients[i] = resultCo[i]; 
			resultExponents[i] = resultEx[i]; 
		}
		

		return new Polynomial(resultCoefficients, resultExponents);
		
		
	}
	
	public double evaluate(double x) {
		double result = this.coefficients[0] * Math.pow(x,this.exponents[0]); 

		for (int i =1; i < this.coefficients.length; i++) {
			result += this.coefficients[i] * Math.pow(x,this.exponents[i]); 
		}
		
		return result; 
	}
	
	public Boolean hasRoot(double x) {	
		return this.evaluate(x) == 0; 
		
	}
	
	public Polynomial multiply(Polynomial poly) {
		
		int maxSize = this.coefficients.length * poly.coefficients.length; 
		double[] resultCo = new double[maxSize]; 
		int[] resultEx = new int[maxSize]; 
		
		
		
		// Next index in resultCo & resultEx to add values into, to ensure we don't overwrite data. 
		int index = 0; 
		
		for (int i = 0; i < this.coefficients.length; i++) {
			for (int k = 0; k < poly.coefficients.length; k++) {
				
				/*
				 * Note: we don't check for the case of when the product of the coefficients is 0 here b/c 
				 *	we are assuming we are dealing with non-zero coefficients. 
				 * */
	
				resultCo[index] = this.coefficients[i] * poly.coefficients[k]; 
				resultEx[index] = this.exponents[i] + poly.exponents[k]; 
				index++; 
				
			}
			
		}
		
		/* Note: we don't need to check for a 0 polynomial here because the only way for it to occur is if we 
		 * have a 0 coefficient. Which we assume not to have. 
		 * */ 
		
		Polynomial terms = new Polynomial(resultCo, resultEx);
		
		return terms.collectLikeTerms();
		
	}
	
	
	public Polynomial collectLikeTerms() {
		
		double[] resultCo = new double[this.coefficients.length]; 
		int[] resultEx = new int[this.exponents.length]; 
		
		// boolean array to keep track of terms that have been collected in the polynomial 
		boolean[] collected = new boolean[this.coefficients.length];
		
		// Next index in resultCo & resultEx to add values into, to ensure we don't overwrite data. 
		int index = 0; 
		
		// boolean flag to check whether we have a 0 polynomial 
		boolean empty = true; 
		
		// For each term, loop over all other terms and check if exponents match and if the term has already been collected. 
		
		for (int i = 0; i < this.coefficients.length; i++) {
			
			if (collected[i]) {
				continue;
			}
			
			double coVal = this.coefficients[i]; 
			int exVal = this.exponents[i]; 
			collected[i] = true; 
			
			for (int k = i + 1; k < this.exponents.length; k++) {

				if(this.exponents[i] == this.exponents[k] && collected[k] == false) {
					
					coVal += this.coefficients[k]; 
					collected[k] = true; 
					
				}
			}
			
			if (coVal != 0) {
				resultCo[index] = coVal; 
				resultEx[index] = exVal;
				index++; 
				empty = false; 
			}
			
		}
		

		if (empty) {
			return new Polynomial(); 	
		}
		
		// Remove extra space from arrays 
		double[] resultCoefficients = new double[index]; 
		int[] resultExponents = new int[index]; 
		
		for (int i =0; i < index; i++) {
			resultCoefficients[i] = resultCo[i]; 
			resultExponents[i] = resultEx[i]; 
		}
		

		return new Polynomial(resultCoefficients, resultExponents);
		

	}
	
	public void saveToFile(String filename) throws FileNotFoundException {
		
		StringBuilder polynomial = new StringBuilder(); 
		
		for (int i =0; i < this.coefficients.length; i++) {
			
			
			
			// Don't need to add 'x' for constant terms. 
			if (this.exponents[i] == 0) {
				
				if (i == 0 && this.coefficients[i] > 0) {
					polynomial.append(Double.toString(this.coefficients[i]));
					
				}
				else if (this.coefficients[i] > 0) {
					polynomial.append("+");
					polynomial.append(Double.toString(this.coefficients[i]));
					
					
				} else {
					polynomial.append(Double.toString(this.coefficients[i]));
				
				}
				
				
			}
			// Include 'x' for terms with exponent > 0. 
			else {
				
				if (i == 0 && this.coefficients[i] > 0) {
					polynomial.append(Double.toString(this.coefficients[i]));
					polynomial.append("x");
					polynomial.append(Integer.toString(this.exponents[i])); 
					
				}
				else if (this.coefficients[i] > 0) {
					polynomial.append("+");
					polynomial.append(Double.toString(this.coefficients[i]));
					polynomial.append("x");
					polynomial.append(Integer.toString(this.exponents[i])); 
					
				} else {
					polynomial.append(Double.toString(this.coefficients[i]));
					polynomial.append("x");
					polynomial.append(Integer.toString(this.exponents[i])); 
		
				}

			}
		}
		
		
		// Write polynomial to file. 
		File testFile = new File("src/" + filename);
		PrintStream output = new PrintStream(testFile); 
		output.println(polynomial.toString());
		output.close();
		
	}

}
