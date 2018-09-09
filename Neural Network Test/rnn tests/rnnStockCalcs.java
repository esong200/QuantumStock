import java.util.ArrayList;
import java.text.*;

public class rnnStockCalcs {

	public static void main(String[] args) {

		String[] comps = {"AAPL","ADS","ANDV","ARLP","ATVI","BRKS","COKE","CTL","CVS","DE","EA","FTI","HAS","INTC","IVZ","JNJ","KSS","LMT","MO","MSFT","NVDA","NKE","PDLI","PEP","SBUX","STT","T","TTWO","WBA","WMT","WYNN"};
		String runName = "";
		for(String comp: comps) {
			String dataDirectory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/ShortTimeBigData/"+ comp + "WeeklyDataAdjst.csv";
			String ansDirectory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/ShortTimeBigData/"+ comp + "WeeklyScaledAns.csv";
			String Synap0Directory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/STBigMatrixes50/TripleLayer/Weights0/"+ comp + runName+"synapticWeights0.csv";
			String Synap1Directory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/STBigMatrixes50/TripleLayer/Weights1/"+ comp + runName+"synapticWeights1.csv";
			String SynaphDirectory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/STBigMatrixes50/TripleLayer/Weights2/"+ comp + runName+"synapticWeights2.csv";
		ArrayList<double[]> data = CSVReadWrite.readCsv(dataDirectory);
		ArrayList<double[]> ans = CSVReadWrite.readCsv(ansDirectory);
		ArrayList<double[]> dataTaylored = data;
		//ArrayList<double[]> pseudorandomData = CSVReadWrite.readCsv("C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Data\\" + comp + "DataAdjst.csv");
		//ArrayList<double[]> pseudorandomAns = CSVReadWrite.readCsv("C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Data\\"+ comp + "Ans.csv");
		int maxSize = data.size();
		while(ans.size()>maxSize){
			ans.remove(ans.size()-1);
		}
		double alpha = 0.1;
		int inputDim = data.get(0).length;
		int hiddenDim = 16;
		int outputDim = ans.get(0).length;

		double[][] synap0 = new double[inputDim][hiddenDim]
				, synap0Up = new double[inputDim][hiddenDim]
				, synap1 = new double[hiddenDim][outputDim]
				, synap1Up = new double[hiddenDim][outputDim]
				, synaph = new double[hiddenDim][hiddenDim]
				, synaphUp = new double[hiddenDim][hiddenDim];

		rand(synap0);
		rand(synap1);
		rand(synaph);

		for(int monthsBefore = 3; monthsBefore<7; monthsBefore++) {

		for(int j = 0; j<750000; j++) {
			int monthSelect = (int) (Math.random()*(data.size()-monthsBefore));
			//System.out.println("MontSelect is: " + monthSelect);
			ArrayList<double[]> monthData = new ArrayList<double[]>();
			ArrayList<double[]> ansData = new ArrayList<double[]>();
			for(int i = 0; i<monthsBefore; i++) {
				monthData.add(data.get(monthSelect+i));
				ansData.add(ans.get(monthSelect+i));
			}

			double[] output = null;

			ArrayList<double[]> layer_2_deltas = new ArrayList<double[]>();
			ArrayList<double[]> layer_1_values = new ArrayList<double[]>();
			layer_1_values.add(new double[hiddenDim]);

			for(int i = 0; i<monthData.size(); i++) {
				double[] input = monthData.get(monthData.size()-1-i);
				double[] expect = ansData.get(ansData.size()-1-i);

				double[] layer1 = functionsRNN.sigmoid(functionsRNN.add(functions.dotMultiply(input, synap0)
						, functions.dotMultiply(layer_1_values.get(layer_1_values.size()-1), synaph)), false);

				double[] layer2 = functionsRNN.sigmoid(functions.dotMultiply(layer1, synap1), false);
				output = layer2.clone();

				double[] layer2Error = functionsRNN.subtract(expect, layer2);
				layer_2_deltas.add(functionsRNN.multiply(layer2Error,functionsRNN.sigmoid(layer2, true)));

				layer_1_values.add(layer1.clone());

			}

			double[] layer1DeltaFuture = new double[hiddenDim];

			for(int position = 0; position<monthData.size(); position++) {
				double[] input = monthData.get(position).clone();
				double[] layer1 = layer_1_values.get(layer_1_values.size()-position -1);
				double[] layer1Pre = layer_1_values.get(layer_1_values.size()-position-2);

				double[] layer2Delta = layer_2_deltas.get(layer_2_deltas.size()-1-position);

				double[] layer1Delta = functionsRNN.multiply(functionsRNN.add(functions.rotateMultiply(layer1DeltaFuture, synaph)
						, functions.rotateMultiply(layer2Delta, synap1)), functionsRNN.sigmoid(layer1, true));

				double[][] intermed = functionsRNN.add(synap1Up, functionsRNN.rotateMultiply(layer2Delta, layer1));
				synap1Up = intermed.clone();
				intermed = functionsRNN.add(synaphUp,  functions.rotateMultiply(layer1Delta, layer1Pre));
				synaphUp = intermed.clone();
				intermed = functionsRNN.add(functions.rotateMultiply(layer1Delta, input), synap0Up);
				synap0Up = intermed.clone();
			}
			double[][] intermed  = functionsRNN.add(synap0, functionsRNN.multiply(synap0Up,alpha));
			synap0 = intermed.clone();
			intermed = functionsRNN.add(synap1, functionsRNN.multiply(synap1Up, alpha));
			synap1 = intermed.clone();
			intermed = functionsRNN.add(synaph, functionsRNN.multiply(synaphUp, alpha));

			intermed = functionsRNN.multiply(synap1Up, 0);
			synap1Up = intermed.clone();
			intermed = functionsRNN.multiply(synaphUp, 0);
			synaphUp = intermed.clone();
			intermed = functionsRNN.multiply(synap0Up, 0);
			synap0Up = intermed.clone();
			if(j%1000==0) {
				System.out.println("Trail: " + j);
				System.out.print("Expected Outcome: [");
				for(double i: ansData.get(0)) {
					DecimalFormat df = new DecimalFormat("#.#");
					System.out.print(df.format(i)+", ");
				}
				System.out.println("]");

				System.out.print("Predictd Outcome: [");
				for(double i: output) {
					DecimalFormat df = new DecimalFormat("#.#");
					System.out.print(df.format(i)+", ");
				}
				System.out.println("]");
				System.out.println("----------------------");
			}

		}
		String fileSynp0 = Synap0Directory;
		String fileSynp1 = Synap1Directory;
		String fileSynph = SynaphDirectory;


		CSVReadWrite.writeCsv(synap0, fileSynp0);
		CSVReadWrite.writeCsv(synap1, fileSynp1);
		CSVReadWrite.writeCsv(synaph, fileSynph);
		System.out.println("Done with " + comp + " using " + monthsBefore + " weeks");
	}
		}

	}


	private static void rand(double[][] rand) {
		for (int i = 0; i< rand.length; i++) {
			for(int j = 0 ;j <rand[0].length; j++) {
				rand[i][j] = Math.random()*2 -1;
			}
		}
	}
}
