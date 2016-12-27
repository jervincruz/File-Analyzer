import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;



public class fileAnalyzer {
	
	public static void main(String [] args){
	
		String [] chars = new String[17];
		chars[0] = "A";
		chars[1] = "B";
		chars[2] = "A";
		chars[3] = "A";
		chars[4] = "/";
		chars[5] = "!";
		chars[6] = "!";
		chars[7] = "!";
		chars[8] = "C";
		chars[9] = "D";
		chars[10] = "B";
		chars[11] = "A";
		chars[12] = "F";
		chars[13] = "G";
		chars[14] = "B";
		chars[15] = "B";
		chars[16] = "A";
		
		cipherString(chars);

	}
	
	
	static String cipherString(String [] symbolsArr){
		HashMap<String, Integer> symbolsRatio = new HashMap<>();
		String cipher = "";
		int counter = 0;

		
		for (int i = 0; i < symbolsArr.length; i++){
			if(symbolsRatio.containsKey(symbolsArr[i])){
				counter = symbolsRatio.get(symbolsArr[i]);
				symbolsRatio.put(symbolsArr[i], ++counter);
			}
			else{
				symbolsRatio.put(symbolsArr[i],1);
			}
		}
		
		// Print whole string
		for(int i = 0; i < symbolsArr.length; i++){
			cipher += symbolsArr[i];
		}
		System.out.println(cipher);
		
		for(String s : symbolsRatio.keySet()){
			System.out.println(s + symbolsRatio.get(s));
		}
		

		
		Set<Integer> ss = new TreeSet<>();
		
		int k = 0;
		// Store number per symbol in each array
		for(String s : symbolsRatio.keySet()){
			ss.add(symbolsRatio.get(s));
		}	
		
		int [] symbolValues = new int[ss.size()];
		for(Integer s : ss){
			symbolValues[k++] = s;
		}
		
		// Sort
		quickSort(symbolValues, 0, symbolValues.length - 1);
		
		// Get key of value in sorted array, print out values
		for(int i = 0; i < symbolValues.length; i++){
			System.out.print(symbolValues[i] + " ");
		}
		// Replace the bottom half values with one letter, get key of the middle + 1 symbol
		// Iterate through whole hashmap to see which symbols match with their values
		
		cipher = "";
		// Get first half of majority values, then replace second half with one value
		for(int i = 0; i < symbolValues.length/2; i++){
			for(String s : symbolsRatio.keySet()){
				int count = symbolValues[i];
				if(symbolsRatio.get(s).equals(symbolValues[i])){
					while(count != 0){
					cipher += s;
					count--;
					}
				}
			}
		}
		Random rand;
		String chosenSymbol = "";
		System.out.println(cipher);
		for(int i = symbolValues.length/2; i < symbolsRatio.size(); i++){
			for(String s : symbolsRatio.keySet()){
				if(symbolsRatio.get(s).equals(symbolValues[symbolValues.length/2 + 1])){
					chosenSymbol = s;
				}
			}
			cipher += chosenSymbol;
		}
		
		System.out.println(cipher);
		return cipher;
	}
	
	static void quickSort(int [] arr, int low, int high){
		if(arr == null || arr.length == 0)
			return;
		if(low >= high)
			return;
		
		int middle = low + (high - low) / 2;
		int pivot = arr[middle];
		
		int i = low;
		int j = high;
		
		while (i <= j){
			while(arr[i] > pivot){
				i++;
			}
			while(arr[j] < pivot){
				j--;
			}
			
			if(i <= j){
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}
		// Sort from greatest to least
		if(low < j)
			quickSort(arr, low, j);
		if(high > i)
			quickSort(arr, i, high);
	}
}
