import java.util.Scanner;
import java.util.ArrayList;

public class ArrayListNeuralNetwork extends functions {
	//array specs: input array: 1x29, desiredOutcome: 1x8
	public static void main(String[] args) {
		ArrayList<double[]> data = DataCollection("AAPL");
		ArrayList<double[]> ans = answerCompile("AAPL");

		int maxSize = data.size();
		while(ans.size()>maxSize){
			ans.remove(ans.size()-1);
		}
		double[] inputs = new double[data.get(0).length];
		double[] desiredOutcome = new double[ans.get(0).length];
		for(int i=0; i<data.get(0).length; i++) {
			inputs[i]=data.get(0)[i];
		}
		for(int i=0; i<ans.get(0).length; i++) {
			desiredOutcome[i] = ans.get(0)[i];
		}
		//double[] inputs = new double[];
		double[][]synapticWeights0 = new double[inputs.length][18];
		double[]intermediateAnswer = new double[synapticWeights0[0].length];
		double[][] synapticWeights1 = new double[intermediateAnswer.length][desiredOutcome.length];
		double[]finalAnsArr = new double[desiredOutcome.length];
		double[] error1 = new double[desiredOutcome.length];
		double[] delta1 = new double[desiredOutcome.length];
		double[] error0 = new double[intermediateAnswer.length];
		double[] delta0 = new double[intermediateAnswer.length];
		int numOfOuterIterations = 1000;
		double[] times = new double[data.size()];
			for (int i=0; i<synapticWeights1.length; i++) {
				for(int j=0; j<synapticWeights1[0].length; j++) {
					synapticWeights1[i][j] = (2*Math.random()) -1;
				}
			}
		double[] testArr1 = new double[2];
		double[][] testArr2 = new double[2][3];
		for(int i=0; i<testArr1.length; i++) {
			testArr1[i] = (2*Math.random()) -1;
		}
		for(int i=0; i<testArr2.length; i++) {
			for(int j=0; j<testArr2[0].length; j++) {
				testArr2[i][j] = (2*Math.random()) -1;
			}
		}
		double[] ansArr = new double[3];
		ansArr = dotMultiply(testArr1, testArr2);
		long outermostStart = System.currentTimeMillis();
		for(int k=0; k<data.size(); k++) {
			long start = System.currentTimeMillis();
			for(int m = 0; m<50; m++) {
				intermediateAnswer/*1x18*/ = sigmoid1d(dotMultiply(inputs/*1x29*/, synapticWeights0/*29x18*/), false);
				finalAnsArr = sigmoid1d(dotMultiply(synapticWeights1, intermediateAnswer), false);
				System.out.println();
				System.out.println("Training Answers "+m+" inside outer iteration "+k+":");
				for(int i=0; i<delta1.length; i++) {
					System.out.println(finalAnsArr[i]);
					error1[i]=desiredOutcome[i]-finalAnsArr[i];
					delta1[i]= error1[i]*sigmoid(finalAnsArr[i],true);
				}
				error0 = rotateMultiply(synapticWeights1,delta1);
				for(int i = 0; i<delta0.length; i++) {
					delta0[i]= error0[i] * (sigmoid1d(intermediateAnswer,true)[i]);
				}
				for(int i=0; i<synapticWeights1.length; i++) {
					for(int j=0; j<synapticWeights1[0].length; j++) {
						synapticWeights1[i][j] +=  rotateMultiply(delta1 , intermediateAnswer)[i][j];
					}

				}

				for (int i = 0; i<synapticWeights0.length; i++) {
					for(int j = 0; j<synapticWeights0[0].length; j++) {
						double[][] debugger = rotateMultiply(delta0, inputs);
						synapticWeights0[i][j] += debugger[i][j];
					}
				}
			}
			System.out.println();
			for(int i=0; i<data.get(k).length; i++) {
				inputs[i]=data.get(k)[i];
			}
			for(int i=0; i<ans.get(k).length; i++) {
				desiredOutcome[i] = ans.get(k)[i];
			}
			long stop = System.currentTimeMillis();
			double elapsed = (stop - start) / 1000.0;
			times[k]=elapsed;
			//System.out.println(m);
			System.out.println("Time:" + elapsed);
			//inputs
		}
		long outermostStop = System.currentTimeMillis();
		double outermostElapsed = (outermostStop-outermostStart)/1000.0;
		System.out.println();
		double total = 0;
		for(int i=0; i<times.length; i++) {
			total += times[i];
		}
		System.out.println("Average time per outer loop iteration: "+ (total/times.length));
		System.out.println("Total time for outer and inner for loop iterations: "+ outermostElapsed);
		System.out.println("Synaptic Weights0:");
		System.out.println();
		for(int i=0; i<synapticWeights0.length; i++) {
			for(double j: synapticWeights0[0]) {
				System.out.println(j);
			}
		}
		System.out.println();
		System.out.println("Synaptic Weights1:");
		for(int i=0; i<synapticWeights1.length; i++) {
			for(double j: synapticWeights1[0]) {
				System.out.println(j);
			}
		}
		String file1 = "/Users/ethansong/Documents/Matrix Saves/synapticWeights0.csv";
		String file2 = "/Users/ethansong/Documents/Matrix Saves/synapticWeights1.csv";
		writeCsv(synapticWeights0, file1);
		writeCsv(synapticWeights1, file2);

	}
}
