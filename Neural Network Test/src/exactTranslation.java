import java.util.ArrayList;
import java.util.Random;

public class exactTranslation {

	public static void main(String[] args) {
		Random generator = new Random(2);
		ArrayList<int[]> binary = new ArrayList<int[]>();
		int binary_dim = 8;

		int largest_num = (int) Math.pow(2, binary_dim);
		for(int i = 0; i < largest_num; i++) {
			binary.add(toBin(i));
		}

		double alpha = .1;
		int input_dim = 2;
		int hidden_dim = 16;
		int output_dim = 1;

		double[][] synapse_0 = new double[input_dim][hidden_dim]
				, synapse_0_update = new double[input_dim][hidden_dim]
				, synapse_1 = new double[hidden_dim][output_dim]
				, synapse_1_update = new double[hidden_dim][output_dim]
				, synapse_h = new double[hidden_dim][hidden_dim]
				, synapse_h_update = new double[hidden_dim][hidden_dim];
		rand(synapse_0);
		rand(synapse_1);
		rand(synapse_h);

		for(int j = 0; j<10000; j++)
		{
			int a_int = (int) (generator.nextDouble()*(largest_num/2));
			int[] a = binary.get(a_int);

			//System.out.println("a: " + a_int);

			int b_int = (int) (generator.nextDouble()*(largest_num/2));
			int[] b = binary.get(b_int);

			//System.out.println("b: " + b_int);
			//System.out.println("b from bin: " + fromBin(b));


			int c_int = a_int + b_int;
			int[] c = binary.get(c_int);

			int[] d = binary.get(0);

			double overallError = 0;

			ArrayList<double[]> layer_2_deltas = new ArrayList<double[]>();
			ArrayList<double[]> layer_1_values = new ArrayList<double[]>();
			layer_1_values.add(new double[hidden_dim]);

			if(j==8) {
				System.out.println();
			}

			for(int position = 0; position<binary_dim; position++) {

				double[] X = {a[binary_dim - position -1], b[binary_dim - position -1]};
				double[] y = {c[binary_dim - position -1]};

				double[] layer_1 = functions.sigmoid1d(functionsRNN.add(functions.dotMultiply(X, synapse_0),
						functions.dotMultiply(layer_1_values.get(layer_1_values.size()-1), synapse_h)), false);

				double[] layer_2 = functions.sigmoid1d(functions.dotMultiply(layer_1, synapse_1), false);

				double[] layer_2_error = functionsRNN.subtract(y, layer_2);
				layer_2_deltas.add(functionsRNN.multiply(layer_2_error, functions.sigmoid1d(layer_2, true)));
				overallError += Math.abs(layer_2_error[0]);

				d[binary_dim-position-1] = (int) Math.round(layer_2[0]);

				layer_1_values.add(layer_1.clone());
			}

			double[] future_layer_1_delta = new double[hidden_dim];

			for(int position = 0; position < binary_dim; position++) {

				double[] X = {a[position], b[position]};
				double[] layer_1 = layer_1_values.get(layer_1_values.size()-position-1);
				double[] prev_layer_1 = layer_1_values.get(layer_1_values.size()-position-2);

				double[] layer_2_delta = layer_2_deltas.get(layer_2_deltas.size()-position-1);

				double[] layer_1_delta = functionsRNN.multiply((functionsRNN.add(functions.rotateMultiply(future_layer_1_delta, synapse_h),
						functions.rotateMultiply(layer_2_delta, synapse_1))),functions.sigmoid1d(layer_1, false));

				double[][] intermed = functionsRNN.add(synapse_1_update, functions.rotateMultiply(layer_2_delta, layer_1));
				synapse_1_update = intermed.clone();
				intermed = functionsRNN.add(synapse_h_update, functions.rotateMultiply(layer_1_delta, prev_layer_1));
				synapse_h_update = intermed.clone();
				intermed = functionsRNN.add(synapse_0, functions.rotateMultiply(layer_1_delta, X));
				synapse_0_update = intermed.clone();

				future_layer_1_delta = layer_1_delta.clone();
			}
			double[][] intermed;
			intermed = functionsRNN.add(synapse_0, functionsRNN.multiply(synapse_0_update, alpha));
			synapse_0 = intermed.clone();
			intermed = functionsRNN.add(synapse_1, functionsRNN.multiply(synapse_1_update, alpha));
			synapse_1 = intermed.clone();
			intermed = functionsRNN.add(synapse_h, functionsRNN.multiply(synapse_h_update, alpha));
			synapse_h = intermed.clone();

			intermed = functionsRNN.multiply(synapse_0_update, 0);
			synapse_0_update = intermed.clone();
			intermed = functionsRNN.multiply(synapse_1_update, 0);
			synapse_1_update = intermed.clone();
			intermed = functionsRNN.multiply(synapse_h_update, 0);
			synapse_h_update = intermed.clone();

			//if(j%1000 == 0) {
				System.out.println("trail: " + j);
				System.out.println("Error: " + overallError);
				System.out.println("True: " + fromBin(c));
				System.out.println("Pred: " + fromBin(d));
			//}
		}
	}
//
	private static int[] toBin(int nonBin) {
		int size = 9;
/*		while(Math.pow(2, size)<= nonBin) {
			size++;
		}*/
		int[] binary = new int[size];
		for(int i = size-1; i >= 0; i--) {
			binary[i] = nonBin%2;
			nonBin = nonBin/2;
		}
		return binary;
	}

	private static int fromBin(int[] Bin) {
		int returnInt = 0;
		for(int i = 0; i<Bin.length; i++) {
			if(Bin[Bin.length-i-1] == 1) {
				returnInt+=(int) Math.pow(2, i);
			}
		}
		return returnInt;
	}

	private static void rand(double[][] rand) {
		Random generator = new Random(2);
		for (int i = 0; i< rand.length; i++) {
			for(int j = 0 ;j <rand[0].length; j++) {
				rand[i][j] = generator.nextDouble()*2 -1;
			}
		}
	}

}
