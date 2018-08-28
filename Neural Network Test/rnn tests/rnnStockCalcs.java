import java.util.ArrayList;
import java.text.*;

public class infoTests {

	public static void main(String[] args) {

		String comp = "IVZ";
		String runName = "";
		ArrayList<double[]> data = CSVReadWrite.readCsv("C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Data\\" + comp + "DataAdjst.csv");
		ArrayList<double[]> ans = CSVReadWrite.readCsv("C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Data\\"+ comp + "Ans.csv");
		ArrayList<double[]> dataTaylored = data;
		//ArrayList<double[]> pseudorandomData = CSVReadWrite.readCsv("C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Data\\" + comp + "DataAdjst.csv");
		//ArrayList<double[]> pseudorandomAns = CSVReadWrite.readCsv("C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Data\\"+ comp + "Ans.csv");
		ArrayList<Integer> correct = new ArrayList<Integer>();
		double[][] dataTayloredMatrix = new double[dataTaylored.size()][dataTaylored.get(0).length];
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

		int monthsBefore = 1;

		for(int j = 0; j<1000000; j++) {
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

				double[] layer1 = functions.sigmoid(functions.add(functions.dotMultiply(input, synap0)
						, functions.dotMultiply(layer_1_values.get(layer_1_values.size()-1), synaph)), false);

				double[] layer2 = functions.sigmoid(functions.dotMultiply(layer1, synap1), false);
				output = layer2.clone();

				double[] layer2Error = functions.subtract(expect, layer2);
				layer_2_deltas.add(functions.multiply(layer2Error,functions.sigmoid(layer2, true)));

				layer_1_values.add(layer1.clone());

			}

			double[] layer1DeltaFuture = new double[hiddenDim];

			for(int position = 0; position<monthData.size(); position++) {
				double[] input = monthData.get(position).clone();
				double[] layer1 = layer_1_values.get(layer_1_values.size()-position -1);
				double[] layer1Pre = layer_1_values.get(layer_1_values.size()-position-2);

				double[] layer2Delta = layer_2_deltas.get(layer_2_deltas.size()-1-position);

				double[] layer1Delta = functions.multiply(functions.add(functions.rotateMultiply(layer1DeltaFuture, synaph)
						, functions.rotateMultiply(layer2Delta, synap1)), functions.sigmoid(layer1, true));

				double[][] intermed = functions.add(synap1Up, functions.rotateMultiply(layer2Delta, layer1));
				synap1Up = intermed.clone();
				intermed = functions.add(synaphUp,  functions.rotateMultiply(layer1Delta, layer1Pre));
				synaphUp = intermed.clone();
				intermed = functions.add(functions.rotateMultiply(layer1Delta, input), synap0Up);
				synap0Up = intermed.clone();
			}
			double[][] intermed  = functions.add(synap0, functions.multiply(synap0Up,alpha));
			synap0 = intermed.clone();
			intermed = functions.add(synap1, functions.multiply(synap1Up, alpha));
			synap1 = intermed.clone();
			intermed = functions.add(synaph, functions.multiply(synaphUp, alpha));

			intermed = functions.multiply(synap1Up, 0);
			synap1Up = intermed.clone();
			intermed = functions.multiply(synaphUp, 0);
			synaphUp = intermed.clone();
			intermed = functions.multiply(synap0Up, 0);
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


	}


	private static void rand(double[][] rand) {
		for (int i = 0; i< rand.length; i++) {
			for(int j = 0 ;j <rand[0].length; j++) {
				rand[i][j] = Math.random()*2 -1;
			}
		}
	}
}
