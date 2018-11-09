import java.util.ArrayList;
public class matrixGetTest extends CSVReadWrite{	
	public static void main(String[] args){
		double[][] snW0 = listToArray(readCsv("/Users/ethansong/Documents/Matrix Saves/snW3L/AAPLsynapticWeights0.csv"));
		for(int i=0; i<snW0.length; i++) {
			System.out.println();
			for(double b: snW0[i]) {
				System.out.print(b+"\t");
			}
		}
	}
}
