import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
public class TestCSVReadWrite {
	public static void main(String[] args) {
		CSVReadWrite.writeCsv(AlphaVantageCollectorWeeklyChunk.answerCompile("AAPL"), "/Users/ethansong/Documents/AnsTest.csv");
	}
}
