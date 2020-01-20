//THIS CLASS COMPUTES THE MINIMUM CHANGE NUMBER IN ORDER TO CHANGE ANY GIVEN STRING
//TO OTHER WITH LEVENSHTEIN DISTANCE APPROACH.
// @author Arikhan Arik arikarikhan@gmail.com

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class LevenshteinDistance {

	//This function is to determine which action cost is optimal. (edit/delete/insert)
	//Other ways such as Math.min(a , Math.min(b, c)) can also be used. 
	public static int minValue(int a, int b, int c){
		//Select two values out of three and compare.
		if(a <= b){
			//if a is the minimum, return.
			if(a <= c)
				return a;
			//if c is the minimum, return.
			else
				return c;
		}

		else{
			//if b is the minimum, return.
			if(b <= c)
				return b;
			//if c is the minimum, return.
			else
				return c;
		}
		
	}
	
	//Determine the Levenshtein Distance of given two strings.
	//The data structures that have been used is 1d array and 
	//one variable to solve the problem in the method's life cycle.
	public static int levenDist(byte[] a, byte[] b) throws UnsupportedEncodingException{
		
		//Cases for null strings, just return corresponding lengths for the other string or return zero for the case of a and b are both null values.
		if(a == null || a == "".getBytes("UTF-8"))
			return b.length;
		
		if(b == null || b == "".getBytes("UTF-8"))
			return a.length;
		
		if(b == null && a == null)
			return 0;
		
		if(b == "".getBytes("UTF-8") && a == "".getBytes("UTF-8"))
			return 0;
		
		int [] levenColumn = new int[b.length+1];
		int rowValue = 0;
		for(int i = 0; i < levenColumn.length; i ++){
			levenColumn[i] = i;
		}

		for(int column = 1; column <= a.length; column++){
			//assign the column index since it fits for the first 
			//element of the column array for each iteration.
			rowValue = column;
			//specialFlag is the value that checks special characters for the first word, in case there is a special character,
			//it is needed to skip following column value, since that value also belongs to the special character.
			int specialFlag = 0;
			for(int row = 0; row <= b.length ; row++){
				//row with index 0 is omitted, since it is the comparison row of
				// null and string b.
				if(row > 0){
					//dummy variable is to keep the data during the swap phases.
					int dummy = -1;
					//if two characters are equal and there is no special character.
					if((a[column-1] == b[row-1]) && (a[column-1] > 0 && b[row -1] > 0)){
						dummy = rowValue;
						rowValue = levenColumn[row-1];
						levenColumn[row-1] = dummy;
					}
					//if two characters are both special and the same. 
					else if ((a[column-1] < 0 && b[row -1] < 0) && a[column-1] == b[row-1] && a[column] == b[row]){
						dummy = rowValue;
						rowValue = levenColumn[row-1];
						levenColumn[row-1] = dummy;		
						//special character is the same for both b and a, therefore it is needed to pass other pair to move on.
						row++;
						specialFlag = 1;
					}
					//in this case, we have special character at b but not at a.
					else if ((a[column-1] > 0 && b[row -1] < 0) ){
						//move row character index by one here to avoid second number of the same special character.
						dummy = minValue(levenColumn[row], levenColumn[row-1], rowValue) + 1;
						levenColumn[row-1] = rowValue;
						rowValue = dummy;
						row++;
					}
					//in this case, we have special character at a but not at b.
					else if ((a[column-1] < 0 && b[row -1] > 0) ){
						//move column character index by one 'at the end' to avoid second number of the same special character.
						dummy = minValue(levenColumn[row], levenColumn[row-1], rowValue) + 1;
						levenColumn[row-1] = rowValue;
						rowValue = dummy;
						specialFlag = 1;
					}
					//if characters are both regular and not equal to each other.
					else{
						dummy = minValue(levenColumn[row], levenColumn[row-1], rowValue) + 1;
						levenColumn[row-1] = rowValue;
						rowValue = dummy;
					}
				
				}
				
			}
			
			if(specialFlag == 1)
				column++;
			//Update the last element of the column array at the end
			//for the next iteration.
			levenColumn[levenColumn.length-1] = rowValue;
		}
		
		return levenColumn[levenColumn.length-1];
	}
	
	//Overloaded version of the levenDist function with maximum distance value.
	public static int levenDist(byte[] a, byte[] b, int maxDist) throws UnsupportedEncodingException{
		
		//Cases for null strings, just return corresponding lengths for the other string or return zero for the case of a and b are both null values.
		if(a == null || a == "".getBytes("UTF-8"))
			return b.length;
		
		if(b == null || b == "".getBytes("UTF-8"))
			return a.length;
		
		if(b == null && a == null)
			return 0;
		
		if(b == "".getBytes("UTF-8") && a == "".getBytes("UTF-8"))
			return 0;

		int [] levenColumn = new int[b.length+1];
		int rowValue = 0;
		for(int i = 0; i < levenColumn.length; i ++){
			levenColumn[i] = i;
		}

		for(int column = 1; column <= a.length; column++){
			
			//assign the column index since it fits for the first 
			//element of the column array for each iteration.
			rowValue = column;
			
			//specialFlag is the value that checks special characters for the first word, in case there is a special character,
			//it is needed to skip following column value, since that value also belongs to the special character.
			int specialFlag = 0;

			for(int row = 0; row <= b.length ; row++){
				//row with index 0 is omitted, since it is the comparison row of
				// null and string b.
				if(row > 0){
					//dummy variable is to keep the data during the swap phases.
					int dummy = -1;
					//if two characters are equal and there is no special character.
					if((a[column-1] == b[row-1]) && (a[column-1] > 0 && b[row -1] > 0)){
						dummy = rowValue;
						rowValue = levenColumn[row-1];
						levenColumn[row-1] = dummy;
					}
					//if two characters are both special and the same. 
					else if ((a[column-1] < 0 && b[row -1] < 0) && a[column-1] == b[row-1] && a[column] == b[row]){
						dummy = rowValue;
						rowValue = levenColumn[row-1];
						levenColumn[row-1] = dummy;		
						//special character is the same for both b and a, therefore it is needed to pass other pair to move on.
						row++;
						specialFlag = 1;
					}
					//in this case, we have special character at b but not at a.
					else if ((a[column-1] > 0 && b[row -1] < 0) ){
						//move row character index by one here to avoid second number of the same special character.
						dummy = minValue(levenColumn[row], levenColumn[row-1], rowValue) + 1;
						levenColumn[row-1] = rowValue;
						rowValue = dummy;
						row++;
					}
					//in this case, we have special character at a but not at b.
					else if ((a[column-1] < 0 && b[row -1] > 0) ){
						//move column character index by one 'at the end' to avoid second number of the same special character.
						dummy = minValue(levenColumn[row], levenColumn[row-1], rowValue) + 1;
						levenColumn[row-1] = rowValue;
						rowValue = dummy;
						specialFlag = 1;
					}
					//if characters are both regular and not equal to each other.
					else{
						dummy = minValue(levenColumn[row], levenColumn[row-1], rowValue) + 1;
						levenColumn[row-1] = rowValue;
						rowValue = dummy;
					}
				
				}
				
				
				
			}
			
			//Update the last element of the column array at the end
			//for the next iteration.
			levenColumn[levenColumn.length-1] = rowValue;
			
			//Early stop criteria applies here, if every value in column array
			//exceeds maximum length, apply early exit.
			for(int i = 0; i < levenColumn.length; i++){
				if (levenColumn[i] <= maxDist)
					break;
				else if(i + 1 == levenColumn.length)
					return maxDist + 1;
			}
		}
		
		return levenColumn[levenColumn.length-1];
	}
	
	//test cases
	public static boolean levenTest1() throws UnsupportedEncodingException{
		
		String [] parameter1 = {"Haus", "Haus", "Haus", "Kartoffelsalat"};
		String [] parameter2 = {"Maus", "Mausi", "Häuser", "Runkelrüben"};
		int [] labels = {1,2,3,12};
		int [] results = new int[labels.length];
		for(int i = 0; i < parameter1.length; i++)
			try{
				results[i] = levenDist(parameter1[i].getBytes("UTF-8"), parameter2[i].getBytes("UTF-8"));
				}catch(Exception e){
					System.out.println("Invalid datatype error.");
				}
	
		return Arrays.equals(labels, results);
		
	}

	public static boolean levenTest2() throws UnsupportedEncodingException{
		
		String [] parameter1 = {"Haus", "Haus", "Haus", "Kartoffelsalat"};
		String [] parameter2 = {"Maus", "Mausi", "Häuser", "Runkelrüben"};
		int [] parameter3 = {2,2,2,2};
		int [] labels = {1,2,3,3};
		int [] results = new int[labels.length];

		for(int i = 0; i < parameter1.length; i++)
			try{
				results[i] = levenDist(parameter1[i].getBytes("UTF-8"), parameter2[i].getBytes("UTF-8"), parameter3[i]);
				}catch(Exception e){
					System.out.println("Invalid datatype error.");
				}
		
		return Arrays.equals(labels, results);
		
	}
	
	//Performance measurement has been applied to the test cases iteratively, I have used cases such as [1, 10, 100, 1000, 10000, 100000, 10000000] and
	//check the time differences.
	//For better benchmark tests one can use Java Microbenchmark Harness (JMH) framework example to obtain more reliable results, since nanoseconds
	//have also some downfalls in case one is checking the frequency of a value update as an example.
	//For the sake of simplicity and evaluation, I coded my own test.
	public static void performanceTest() throws UnsupportedEncodingException{
		
		System.out.println("THE COMPLEXITY OF THE ALGORITHM IS N(O) = n^2");
		//Test cases for iterations.
		int[] iterationNumbers = {1, 10, 100, 1000, 10000, 100000, 10000000};
		
		//Data structures to keep results.
		long[] totalTimeTest1 = new long[iterationNumbers.length];
		long[] totalTimeTest2 = new long[iterationNumbers.length];
		int[] accuracyTest1 = new int[2];
		int[] accuracyTest2 = new int[2];
		
		//Time variables to use during the measurements.
		int totalTimeIndex = 0;
		long start;
		long finish;
		long timeElapsed;
		
		//Accuracy measurement result will be based on test results, percentage of true over true plus false.
		boolean result;
		
		//Starting the iterations.
		for (int iteration: iterationNumbers){
			System.out.println("ITERATION NUMBER: " + iteration);
			System.out.println("------------------------------------------------");
			
			//Take the time in nanoseconds.
			start = System.nanoTime();
			accuracyTest1[0] = 0;
			accuracyTest1[1] = 0;
			accuracyTest2[0] = 0;
			accuracyTest2[1] = 0;

			for (int i = 0; i < iteration; i++){
				
				result = levenTest1();

				if(result)
					accuracyTest1[1]++;
				else
					accuracyTest1[0]++;
				
			}
			finish = System.nanoTime();
			timeElapsed = finish - start;
			totalTimeTest1[totalTimeIndex] = timeElapsed;
			//Take the time in nanoseconds since it is computed too fast to display in seconds unit.
			start = System.nanoTime();
			for (int i = 0; i < iteration; i++){
				result = levenTest2();

				if(result)
					accuracyTest2[1]++;
				else
					accuracyTest2[0]++;
				
			}
			finish = System.nanoTime();
			timeElapsed = finish - start;

			totalTimeTest2[totalTimeIndex] = timeElapsed;			
			
			System.out.println("PERFORMANCE MEASUREMENT RESULTS FOR TEST CASE 1:");
			System.out.println("Total time taken:");
			System.out.println("For iteration number " + iterationNumbers[totalTimeIndex] + " = " + totalTimeTest1[totalTimeIndex] + " nanoseconds");
			System.out.println("Accuracy result = " + (accuracyTest1[1]/ (accuracyTest1[0] + accuracyTest1[1]) )*100 + "%");
			System.out.println("------------------------------------------------");

			System.out.println();
			
			System.out.println("PERFORMANCE MEASUREMENT RESULTS FOR TEST CASE 2:");
			System.out.println("Total time taken:");
			System.out.println("For iteration number " + iterationNumbers[totalTimeIndex] + " = " + totalTimeTest2[totalTimeIndex] + " nanoseconds");
			System.out.println("Accuracy result = " + (accuracyTest1[1]/ (accuracyTest1[0] + accuracyTest1[1]) )*100 + "%");
			System.out.println("------------------------------------------------");
			
			System.out.println();
			System.out.println();
			totalTimeIndex++;

		}
		

		
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		
		//Uncomment this section if you wish to see the results with manual input strings.
		//String a = "Kartoffelsalat";
		//String b = "Runkelrüben";
		//int maxDist = 2;
		//try{
		//System.out.println(levenDist(a.getBytes("UTF-8"), b.getBytes("UTF-8")));
		//System.out.println(levenDist(a.getBytes("UTF-8"), b.getBytes("UTF-8"), maxDist));
		//}catch(Exception e){
		//	System.out.println("Invalid datatype error.");
		//}
		
		//Uncomment this section if you wish to make the test only once.
		//System.out.println("Is Test 1 passed?: " + levenTest1());
		//System.out.println("Is Test 2 passed?: " + levenTest2());
		
		//This section gives directly the results of performance tests with iteration values {1, 10, 100, 1000}.
		performanceTest();
		
	}
	
	

}