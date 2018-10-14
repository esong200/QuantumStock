import java.io.File;
import java.util.ArrayList;

public class TripleLayerPseudorandomRunner {
	public static void main(String[] args) {
		String comp = "AAPL";
		String runName = "UltraBig";
		String dataDirectory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/CurrentData/"+comp+"CurrentData.csv";
		String weights0Directory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/UltraBig/Matrixes/TripleLayer/Weights0/"+ comp + runName+"synapticWeights0.csv";
		String weights1Directory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/UltraBig/Matrixes/TripleLayer/Weights1/"+ comp + runName+"synapticWeights1.csv";
		String weights2Directory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/UltraBig/Matrixes/TripleLayer/Weights2/"+ comp + runName+"synapticWeights2.csv";
		ArrayList<double[]> data = CSVReadWrite.readCsv(dataDirectory);
		ArrayList<double[]> dataTaylored = data;
		ArrayList<double[]> pseudorandomData = CSVReadWrite.readCsv(dataDirectory);
		System.out.println(data.size());
		System.out.println(pseudorandomData.size());
		double[][] dataTayloredMatrix = new double[dataTaylored.size()][dataTaylored.get(0).length];
		int maxSize = data.size();
		double[] inputs = new double[data.get(0).length];
		System.out.println(inputs.length);
		for(int i=0; i<data.size(); i++) {
			for(int j=0; j<data.get(i).length; j++) {
				dataTaylored.get(i)[j] *= 10;
				dataTayloredMatrix[i][j]=dataTaylored.get(i)[j];
			}
		}
		for(int i=0; i<data.get(0).length; i++) {
			inputs[i]=(dataTaylored.get(0)[i]);
		}
		System.out.println(dataTaylored.size());
		
		
		double[][]synapticWeights0 =new double[inputs.length][42];
		double[]intermediateAnswer0 = new double[synapticWeights0[0].length];
		double[][] synapticWeights1 = new double[intermediateAnswer0.length][25];
		double[]intermediateAnswer1 = new double[synapticWeights1[0].length];
		double[][] synapticWeights2 = new double[intermediateAnswer1.length][8];
		double[]finalAnsArr = new double[8];
		double totalTime = 0;
		double elapsed = 0;
		try {
			synapticWeights0 = CSVReadWrite.listToArray(CSVReadWrite.readCsv(weights0Directory));
			synapticWeights1 = CSVReadWrite.listToArray(CSVReadWrite.readCsv(weights1Directory));
			synapticWeights2 = CSVReadWrite.listToArray(CSVReadWrite.readCsv(weights2Directory));
		} catch (Exception e) {
			System.out.println("Weights do not exist");
		} 
		System.out.println("Weight retrieval successful. Testing for proper size.");
		try {
			double[][] backwardsTest = synapticWeights0.clone();
			double[] sizeTest1 = functions.dotMultiply(inputs, synapticWeights0);
			double[] sizeTest2 = functions.dotMultiply(intermediateAnswer0, synapticWeights1);
			double[] sizeTest3 = functions.dotMultiply(intermediateAnswer1, synapticWeights2);			
		}
		catch (Exception e) {
			System.out.println("Weights are incorrect dimentions.");
		}

		for(int m = 0; m<1; m++) {
			if(pseudorandomData.size()==0) {
				for(double[] x: dataTaylored) {
					pseudorandomData.add(x);
				}
			}
			int rnd = (int) ((int) pseudorandomData.size()*Math.random());
			long start = System.currentTimeMillis();
			intermediateAnswer0 = functions.sigmoid1d(functions.dotMultiply(inputs, synapticWeights0), false);
			intermediateAnswer1 = functions.sigmoid1d(functions.dotMultiply(intermediateAnswer0, synapticWeights1), false);
			finalAnsArr = functions.sigmoid1d(functions.dotMultiply(intermediateAnswer1, synapticWeights2), false);
			for(double x : finalAnsArr) {
				System.out.println(x);
			}
			//System.out.println();
			//System.out.println("Training Answers "+m+" inside outer iteration :");
			
			long stop = System.currentTimeMillis();
			elapsed = (stop - start) / 1000.0;
			//System.out.println(m);
			totalTime+=elapsed;
			//System.out.println("Average time per iteration: "+ (elapsed));
		}
		System.out.println("Total Time:" + totalTime);
		
	}
}
