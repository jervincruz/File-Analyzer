package file_analyzer;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class FileAnalyzerController {

	private File _file;
	private FileAnalyzerView _view;
	private String[] _asciiArray;
	private String _outputString = "";
	private String _uncommonCharacter = "";
	private String _uncommonTitle = "";

	public FileAnalyzerController(FileAnalyzerView view) {
		_view = view;
		_view.setController(this);
	}

	public File getFile() {
		return _file;
	}

	public void openFile(JFileChooser fileChooser) throws IOException {
		int returnVal = fileChooser.showOpenDialog(_view);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			_file = fileChooser.getSelectedFile();

			_view.getFileLabel().setText(_file.getName());

			String tempString = convertToBinary(_file);
			_asciiArray = binaryToAscii(tempString);
			_asciiArray = removeWhiteSpace(_asciiArray);
			tempString = asciiArrayToString(_asciiArray);
			tempString = "File to Ascii:" + lineSplit() + "\n" + tempString;
			tempString = tempString + lineSplit();

			String tempString2 = cipherString(_asciiArray, _view.matrixSize());
			_asciiArray = stringToArray(tempString2);
			tempString = tempString + "\nReplaced all uncommon ascii characters with one character:" + "\n" + _uncommonTitle + "\nReplaced with: " + _uncommonCharacter + lineSplit() + "\n" + tempString2 + lineSplit();

			HashMap<String, String> randomAsciiHashMap = randomAsciiHashMap();
			tempString = tempString + "\nOur Randomized Ascii Hashmap:" + lineSplit() + "\n" + hashMapToStringArray(randomAsciiHashMap) + lineSplit();

			_asciiArray = arrToRandom(randomAsciiHashMap, _asciiArray);
			tempString = tempString + "\nOur New Ascii String with replaced random values:" + lineSplit() + "\n" + asciiArrayToString(_asciiArray) + lineSplit();
			_outputString = tempString;

			_view.getTextArea().setText(_outputString);
		}

	}

	private String lineSplit() {
		return "\n=============================================================";
	}

	private String[] removeWhiteSpace(String[] stringArr) {
		ArrayList<String> tempStringArrayList = new ArrayList<>();
		String valD;
		for (int i = 0; i < stringArr.length; i ++) {
			char tempString = stringArr[i].charAt(0);

			if (Character.isWhitespace(tempString))
				switch (tempString) {
				case '\t':
					valD = "\t";
					break;
				case ' ':
					valD = " ";
					break;
				case '\n':
					valD = "\n";
					break;
				case '\r':
					valD = "\r";
					break;
				case '\f':
					valD = "\f";
					break;
				default:
					valD = " ";
					break;
				} else if (Character.isISOControl(tempString)) {
					valD = "";
				} else {
					valD = Character.toString(tempString);
					tempStringArrayList.add(valD);
				}
			//tempStringArrayList.add(valD);
		}
		String returnArray[] = new String[tempStringArrayList.size()];
		returnArray = tempStringArrayList.toArray(returnArray);
		return returnArray;
	}

	private String convertToBinary(File file) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		String tempString = new String(encoded, Charset.defaultCharset());

		byte[] bytes = tempString.getBytes();
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes) {
			int val = b;
			for (int i = 0; i < 8; i++) {
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
		}
		return binary.toString();
	}

	private String[] binaryToAscii(String s) {
		int arrayLength = (int) Math.ceil(((s.length() / (double)8)));
		String[] result = new String[arrayLength];

		int j = 0;
		int lastIndex = result.length - 1;
		for (int i = 0; i < lastIndex; i++) {
			result[i] = s.substring(j, j + 8);
			j += 8;
		}
		String lastString = s.substring(j);
		StringBuilder sb = new StringBuilder();
		if (lastString.length() < 9) {
			int rest = 9 - lastString.length();
			for(int i = 1; i < rest; i++) {
				sb.append("0");
			}
			sb.append(lastString);
		}
		result[lastIndex] = sb.toString();
		StringBuilder sb2 = new StringBuilder();
		for (int i = 0; i < result.length; i++) {
			int charCode = Integer.parseInt(result[i], 2);
			sb2.append(new Character((char)charCode).toString());
		}
		String stringWithNonAscii = sb2.toString();
		//String stringWithOnlyAscii = stringWithNonAscii.replaceAll("\\P{InBasic_Latin}", "");
		String stringWithOnlyAscii = stringWithNonAscii.replaceAll("[^\\x00-\\x7F]", "");
		return stringToArray(stringWithOnlyAscii);
	}

	private String asciiArrayToString(String[] asciiArray) {
		StringBuilder sb = new StringBuilder();
		for (String e : asciiArray) {
			sb.append(e);
		}
		return sb.toString();
	}

	private String hashMapToStringArray(HashMap<String, String> randomAsciiHashMap) {
		StringBuilder sb = new StringBuilder();
		Set<String> keySet = randomAsciiHashMap.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
			String key = keySetIterator.next();
			key = key + " = " + randomAsciiHashMap.get(key);
			if (keySetIterator.hasNext()) {
				key = key + "\n";
			}
			sb.append(key);
		}
		return sb.toString();
	}

	// Jervin
	private String cipherString(String [] symbolsArr, int uniqueCutoff){
		HashMap<String, Integer> symbolsFrequency = new HashMap<>();
		String cipher = "";
		int counter = 0;

		// Keep track of number of symbols
		for (int i = 0; i < symbolsArr.length; i++){
			if(symbolsFrequency.containsKey(symbolsArr[i])){
				counter = symbolsFrequency.get(symbolsArr[i]);
				symbolsFrequency.put(symbolsArr[i], ++counter);
			}
			else{
				symbolsFrequency.put(symbolsArr[i],1);
			}
		}

		// Print whole string
		for(int i = 0; i < symbolsArr.length; i++){
			cipher += symbolsArr[i];
		}
		System.out.println(cipher);

		for(String s : symbolsFrequency.keySet()){
			System.out.println("Symbol: " + s.toString() + " Frequency:" + symbolsFrequency.get(s));
		}

		// Frequency
		Set<Integer> ss = new TreeSet<>();

		int k = 0;
		// Store Symbol Frequency
		for(String s : symbolsFrequency.keySet()){
			ss.add(symbolsFrequency.get(s));
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

		// Replace uncommon values with a randomized uncommon symbol starting from the cutoff point
		cipher = "";
		int cutoff = 0;
		Stack<String> uniqueSymbols = new Stack();

		for(String key : symbolsFrequency.keySet()){
			if(cutoff == uniqueCutoff)
				break;
			else{
				uniqueSymbols.push(key);
				cutoff++;
			}
		}
		System.out.print("\nUnique Symbols: ");
		System.out.print(uniqueSymbols);

		// Get a random symbol from the cutoff [set of uncommon symbols]
		Random rand = new Random();
		int uncommonCounter = 0;
		int randomUncommon = 0;
		String [] uncommons;
		if (symbolsFrequency.size() < uniqueCutoff) {
			uncommons = new String[symbolsFrequency.size()];
		}
		else {
			uncommons = new String[symbolsFrequency.size() - uniqueCutoff];
		}
		int j = 0;
		String chosenUncommon = "";
		_uncommonTitle = "Uncommons: ";
		System.out.print("\nUncommons: ");
		for(String s : symbolsFrequency.keySet()){
			uncommonCounter++;
			if(uncommonCounter > uniqueCutoff){
				uncommons[j] = s;
				System.out.print(uncommons[j]);
				_uncommonTitle = _uncommonTitle + uncommons[j];
				j++;
			}
		}
		randomUncommon = rand.nextInt(uncommons.length);
		chosenUncommon = uncommons[randomUncommon];
		_uncommonCharacter = uncommons[randomUncommon];

		System.out.println("Chosen Uncommon: " + chosenUncommon);
		// Iterate through original string array; if most occurring symbol not in array, replace
		for(int i = 0; i < symbolsArr.length; i++){
			if(!uniqueSymbols.contains(symbolsArr[i])){
				symbolsArr[i] = chosenUncommon;
				cipher+=symbolsArr[i];
			}
			else{
				cipher+= symbolsArr[i];
			}
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

	private String[] arrToRandom(HashMap<String, String> randomAsciiHashMap, String[] outputArr) {
		StringBuilder sb = new StringBuilder();
		String key;
		for (int i = 0; i < outputArr.length; i++) {
			if (!outputArr[i].matches("\\s")) {
				key = randomAsciiHashMap.get(outputArr[i]);
				if (key != null) {
					sb.append(key);
				}
			}
		}
		String tempString = sb.toString();
		return stringToArray(tempString);
	}

	private String[] stringToArray(String tempString) {
		return tempString.split("(?!^)");
	}

	private HashMap<String, String> randomAsciiHashMap() {
		HashMap<String, String> hMap = new HashMap<String, String>();
		ArrayList<String> randomAscii = new ArrayList<>();
		ArrayList<String> nonRandom = new ArrayList<>();
		for (int i = 32; i < 127; i++) {
			char x = (char) i;
			if (!Character.isWhitespace(x)) {
				nonRandom.add( Character.toString(x));
			}
		}
		for (int i = 32; i < 127; i++) {
			char x = (char) i;
			if (!Character.isWhitespace(x)) {
				randomAscii.add( Character.toString(x));
			}
		}
		Collections.shuffle(randomAscii);
		for (int i = 0; i < nonRandom.size(); i ++) {
			hMap.put(nonRandom.get(i), randomAscii.get(i));
		}
		return hMap;
	}

	public void saveFile(JFileChooser fileChooser) throws FileNotFoundException {
		int returnVal = fileChooser.showSaveDialog(_view);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			_file = fileChooser.getSelectedFile();
			String filePath = _file.getAbsolutePath();
			if (!filePath.endsWith(".txt")) {
				_file = new File(filePath + ".txt");
			}
			try (PrintStream out = new PrintStream(new FileOutputStream(_file))) {
				out.print(_outputString);
			}
		}
	}

	public void copyClipboard() {
		StringSelection strSelec = new StringSelection(_outputString);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(strSelec, null);
	}
}
