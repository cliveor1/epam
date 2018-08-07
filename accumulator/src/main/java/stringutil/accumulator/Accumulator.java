/**
 * @(#)Accumulator.java
 *
 * FileName:           Accumulator.java
 * Author:             Clive Or
 * Date:               2018-08-03
 *
 * Desciption: 		   Accumulator:
 *
 * Change History
 * Change Release#       Version Date     Reason/Change Detail     Author
 * 0.1                   2018-08-03       Init                     Clive Or
 */

/*--+----|----+----|----+----|----+----|----+----|----+----|----+----|---*/


package stringutil.accumulator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Accumulator 
{
	Pattern patternNewlineAtEnd = Pattern.compile("\n$");
	Matcher matcherNewlineAtEnd = null;
	
 	Pattern patternDelimiter = Pattern.compile("^///*\n*");
 	Pattern patternDelimiterStart = Pattern.compile("^///*");
 	Pattern patternDelimiterEnd = Pattern.compile("\n");
    Matcher matcherDelimiter = null;
    Matcher matcherDelimiterStart = null;
    Matcher matcherDelimiterEnd = null;

	public String delimiterDefault = ",";
	public String delimiterNewline = "\n";
	public String delimiterInputDelimiter = "\\|";	
	public String [] delimiterInput ;
	
	public String negativeList = "";
	
	public Accumulator() {
	}
	
    public int add(String numbers) throws Exception {
    	int result = 0;
    	String numberContent = null;
    	String numberDelimiter = null;
    	
        //  check - null and ""
    	if ((numbers == null) 
    			|| (numbers.trim().length() == 0)) { 
    		return result;
    	}

    	//  check  - newline at the end of line 
        matcherNewlineAtEnd = patternNewlineAtEnd.matcher(numbers.trim());
        if (matcherNewlineAtEnd.find()) {
        	throw new Exception("newline at the end of line not allowed");
        }
        
    	//  check  - delimiter 
        matcherDelimiter = patternDelimiter.matcher(numbers);
        if (matcherDelimiter.find()) {
            matcherDelimiterStart = patternDelimiterStart.matcher(numbers);
            boolean checkDelimiterStart= matcherDelimiterStart.find();
            matcherDelimiterEnd = patternDelimiterEnd.matcher(numbers);
            boolean checkDelimiterEnd= matcherDelimiterEnd.find();
            
            numberDelimiter = numbers.substring(matcherDelimiterStart.end(), matcherDelimiterEnd.start());
            if (numberDelimiter.length() == 0) {
            }
            else {
            	this.delimiterInput = numberDelimiter.split(delimiterInputDelimiter);
            }
            
            numberContent = numbers.substring(matcherDelimiterEnd.end());
            if (numberContent.trim().length() == 0) {
            	return result;
            }
            	        	
        }
        else {
        	numberContent = numbers;
        }

    	result = addN(numberContent);
    	
    	if (negativeList.length() > 0) {
        	throw new Exception("negatives not allowed!" + negativeList);
    	}
    	
    	return result;
    }
    
    public int addN(String numbers) throws Exception {
    	int accumSum = 0;
    	int in = 0;
    	

        String newNumbers = numbers.replaceAll(delimiterNewline, delimiterDefault);
        
        if (delimiterInput != null) {
            for (int j = 0; j < this.delimiterInput.length; j++ ) {
                newNumbers = newNumbers.replaceAll(delimiterInput[j], delimiterDefault);
            }
        }
        
    	String [] inStrs = newNumbers.split(delimiterDefault);
    	int inStrsLen = inStrs.length;

    	for (int i = 0; i < inStrsLen; i++) {
            in = Integer.parseInt(inStrs[i]);
            // Negative numbers 
            if (in < 0) {
            	negativeList = negativeList + inStrs[i] + delimiterDefault ;
            }
            // Numbers bigger than 1000 should be ignored
            else if (in> 1000) {
            	
            }
            else {
        		accumSum = accumSum + in;
            }
            	
            in = 0;
    	}
    	
    	return accumSum;
    }

    public static void main( String[] args )
    {
        System.out.println( "Accumulator - Start" );
        
        String inStr1 = "";
        String inStr2 = "1";
        String inStr3 = "1,2";
        String inStr4 = "1\n2,3";
        String inStr5 = "//;|%\n1;2%3";
        String inStr6 = "//;\n1\n2;3\n-3;-5;1001";
        
        Accumulator accum = new Accumulator();

        	
        try {
			System.out.println( "Accumulator 1 - Input[" + inStr1 + "]\n");
			System.out.println( "Accumulator 1 - Output: " + accum.add(inStr1) + "\n");
			System.out.println( "Accumulator 2 - Input[" + inStr2 + "]\n");
	        System.out.println( "Accumulator 2 - Output: " + accum.add(inStr2) + "\n" );
			System.out.println( "Accumulator 3 - Input[" + inStr3 + "]\n");
	        System.out.println( "Accumulator 3 - Output: " + accum.add(inStr3) + "\n" );
			System.out.println( "Accumulator 4 - Input[" + inStr4 + "]\n");
	        System.out.println( "Accumulator 4 - Output: " + accum.add(inStr4) + "\n" );
			System.out.println( "Accumulator 5 - Input[" + inStr5 + "]\n");
	        System.out.println( "Accumulator 5 - Output: " + accum.add(inStr5) + "\n" );
			System.out.println( "Accumulator 6 - Input[" + inStr6 + "]\n");
	        System.out.println( "Accumulator 6 - Output: " + accum.add(inStr6) + "\n" );

		} catch (Exception e) {
			e.printStackTrace();
		}

        System.out.println( "Accumulator - End" );      
    }
    
}
