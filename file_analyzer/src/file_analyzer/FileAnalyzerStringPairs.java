package file_analyzer;

// Used to store a string (should be a 2 characters pair). Also holds a double value
// that is used to either save how many time that character pair appeared or
// it can be used to store it's ratio when divided by the sum of occurrences in
// its row
public class FileAnalyzerStringPairs {
	private String _pair = "";
	private double _sumOccurrences;
	
	public FileAnalyzerStringPairs(String pair, double sumOccurrences) {
		_pair = pair;
		_sumOccurrences = sumOccurrences;
	}
	
	public String getPair() {
		return _pair;
	}
	
	public double getSumOccurrences() {
		return _sumOccurrences;
	}
	
	@Override
	public String toString() {
		return "[\""+_pair+"\","+_sumOccurrences+"]";
	}
}
