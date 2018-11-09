import java.util.ArrayList;

public class RNNTest extends CSVReadWrite {

	public static void main(String[] args) {
//	int binary_dim = 8;
//	int[] int2binary = new int[binary_dim];
//	int largest_number = (int) Math.pow(2, binary_dim);
	double alpha = 0.1;
	String comp = "EA";
	ArrayList<double[]> data = readCsv("/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Data/"+ comp + "DataAdjst.csv");
	ArrayList<double[]> ans = readCsv("/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Data/"+ comp + "Ans.csv");
	ArrayList<double[]> dataTaylored = data;
//	ArrayList<Integer> correct = new ArrayList<Integer>();
	double[][] dataTayloredMatrix = new double[dataTaylored.size()][dataTaylored.get(0).length];
	int maxSize = data.size();
	while(ans.size()>maxSize){
		ans.remove(ans.size()-1);
	}
	double[] inputs1 = new double[data.get(0).length];
	double[] inputs2 = new double[data.get(0).length];
	double[] desiredOutcome = new double[ans.get(0).length];
	for(int i=0; i<data.size(); i++) {
		for(int j=0; j<data.get(i).length; j++) {
			dataTaylored.get(i)[j] *= 10;
			dataTayloredMatrix[i][j]=dataTaylored.get(i)[j];
		}
	}

	double[][] synap0 = new double[inputs1.length][12];
	double[][] synap1 = new double[synap0[0].length][8];
	double[][] synapH = new double[synap0[0].length][synap0[0].length];



	double[] layer1 = new double[synap0[0].length];
	double[] layer2 = new double[synap1[0].length];

	ArrayList<double[]> layer1Values = new ArrayList<double[]>();
	ArrayList<double[]> layer2Values = new ArrayList<double[]>();
	layer1Values.add(new double[layer1.length]);
	layer2Values.add(new double[layer2.length]);

	double[] error2 = new double[layer2.length];
	ArrayList<double[]> deltas2 = new ArrayList<double[]>();

	double[] delta1 = new double[synap0[0].length];
	double[] delta2 = new double[synap1[0].length];

	for(int m = 0; m<10000; m++) {
		int rand = (int) ((int) (dataTaylored.size()-1)*Math.random());
		System.out.println(rand);
		inputs1 = dataTaylored.get(rand).clone();
		inputs2 = dataTaylored.get(rand + 1);

		for(int i = dataTaylored.size()-1; i>-1; i--) {
			inputs1 = dataTaylored.get(i);
			desiredOutcome = ans.get(i);
			for(int j =0; j<layer1.length; j++) {
				layer1[j] = functions.sigmoid1d(functions.dotMultiply(inputs1,synap0), false)[j]
						+functions.dotMultiply(layer1Values.get(layer1Values.size()-1), synapH)[j];
			}
			layer2 = functions.sigmoid1d(functions.dotMultiply(layer1, synap1), false);
			for(int j = 0; j<error2.length; j++) {
				error2[j] = desiredOutcome[j] - layer2[j];
				delta2[j] = error2[j] * functions.sigmoid1d(layer2, true)[j];
			}
			deltas2.add(delta2.clone());
			layer1Values.add(layer1.clone());
		}
		double[] delta1Future = new double[delta1.length];
		double[][] synap0Up = new double[inputs1.length][12];
		double[][] synap1Up = new double[synap0[0].length][8];
		double[][] synapHUp = new double[synap0[0].length][synap0[0].length];
		for(int i = 0; i<dataTaylored.size(); i++) {
			inputs2 = dataTaylored.get(i);
			layer1 = layer1Values.get(layer1Values.size()-i-1);
			double[] layer1Pre = layer1Values.get(layer1Values.size()-2-i);

			delta2 = deltas2.get(deltas2.size()-i-1);
			for(int j = 0; j<delta1.length; j++) {
				delta1[j] = (functions.dotMultiply(delta1Future, synapH)[j]
						+ functions.rotateMultiply(delta2, synap1)[j])
						*functions.sigmoid1d(layer1, true)[j];
			}
			for(int j = 0; j <synap1Up.length; j++) {
				for(int k = 0; k<synap1Up[0].length; k++) {
					synap1Up[j][k] += functions.rotateMultiply(delta2, layer1)[j][k];
				}
			}
			for(int j = 0; j <synapHUp.length; j++) {
				for(int k = 0; k<synapHUp[0].length; k++) {
					synapHUp[j][k] += functions.rotateMultiply(delta1, layer1Pre)[j][k];
				}
			}
			for(int j = 0; j <synap0Up.length; j++) {
				for(int k = 0; k<synap0Up[0].length; k++) {
					synap0Up[j][k] += functions.rotateMultiply(delta1, inputs2)[j][k];
				}
			}
			delta1Future = delta1.clone();
		}
		for(int i= 0; i< synap0.length; i++) {
			for(int j=0; j<synap0[0].length; j++) {
				synap0[i][j] += synap0Up[i][j]*alpha;
			}
		}
		for(int i= 0; i< synap1.length; i++) {
			for(int j=0; j<synap1[0].length; j++) {
				synap1[i][j] += synap1Up[i][j]*alpha;
			}
		}
		for(int i= 0; i< synapH.length; i++) {
			for(int j=0; j<synapH[0].length; j++) {
				synapH[i][j] += synapHUp[i][j]*alpha;
			}
		}

		}
	}


}
