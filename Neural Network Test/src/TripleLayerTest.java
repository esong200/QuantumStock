
public class TripleLayerTest extends functions{
	public static void main(String[] args) {
		ArrayList<double[]> data = readCsv("C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\ANDVDataAdjst.csv");
		ArrayList<double[]> ans = readCsv("C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\ANDVAns.csv");
		ArrayList<double[]> dataTaylored = data;
		double[][] dataTayloredMatrix = new double[dataTaylored.size()][dataTaylored.get(0).length];
		int maxSize = data.size();
		while(ans.size()>maxSize){
			ans.remove(ans.size()-1);
		}
		double[] inputs = new double[data.get(0).length];
		double[] desiredOutcome = new double[ans.get(0).length];
		for(int i=0; i<data.size(); i++) {
			for(int j=0; j<data.get(i).length; j++) {
				dataTaylored.get(i)[j] *= 10;
				dataTayloredMatrix[i][j]=dataTaylored.get(i)[j];
			}
		}

		for(int i=0; i<data.get(0).length; i++) {
			inputs[i]=(dataTaylored.get(0)[i]);
		}
		double[][]synapticWeights0 = new double[inputs.length][23];
		double[]	intermediateAnswer0 = new double[synapticWeights0[0].length];
		double[][] synapticWeights1 = new double[intermediateAnswer0.length][18];
		double[]intermediateAnswer1 = new double[synapticWeights1[0].length];
		double[][] synapticWeights2 = new double[intermediateAnswer1.length][desiredOutcome.length];
		double[]finalAnsArr = new double[desiredOutcome.length];
		double[] error2 = new double[desiredOutcome.length];
		double[] delta2 = new double[desiredOutcome.length];
		double[] error1 = new double[intermediateAnswer1.length];
		double[] delta1 = new double[intermediateAnswer1.length];
		double[] error0 = new double[intermediateAnswer0.length];
		double[] delta0 = new double[intermediateAnswer0.length];
		double totalTime = 0;
		double elapsed = 0;
		for (int i=0; i<synapticWeights0.length; i++) {
			for(int j=0; j<synapticWeights0[0].length; j++) {
				synapticWeights0[i][j] = (2*Math.random()) -1;
			}
		}
		for (int i=0; i<synapticWeights1.length; i++) {
			for(int j=0; j<synapticWeights1[0].length; j++) {
				synapticWeights1[i][j] = (2*Math.random()) -1;
			}
		}
		for (int i=0; i<synapticWeights2.length; i++) {
			for(int j=0; j<synapticWeights2[0].length; j++) {
				synapticWeights2[i][j] = (2*Math.random()) -1;
			}
		}

		for(int i=0; i<inputs.length; i++) {
			inputs[i]=(2*Math.random())-1;
		}
		for(int m = 0; m<1000; m++) {
			long start = System.currentTimeMillis();
			intermediateAnswer0 = sigmoid1d(dotMultiply(inputs/*1x29*/, synapticWeights0/*29x18*/), false);

			intermediateAnswer1/*1x18*/ = sigmoid1d(dotMultiply(intermediateAnswer0/*1x29*/, synapticWeights1/*29x18*/), false);
			finalAnsArr = sigmoid1d(dotMultiply(intermediateAnswer1, synapticWeights2), false);
			System.out.println();
			System.out.println("Training Answers "+m+" inside outer iteration :");
			for(int i=0; i<delta2.length; i++) {
				System.out.println(finalAnsArr[i]);
				error2[i]=desiredOutcome[i]-finalAnsArr[i];
				delta2[i]= error2[i]*sigmoid(finalAnsArr[i],true);
			}

			error1 = rotateMultiply(synapticWeights2,delta2);
			for(int i = 0; i<delta1.length; i++) {
				delta1[i]= error1[i] * (sigmoid1d(intermediateAnswer1,true)[i]);
			}
			error0 = rotateMultiply(synapticWeights1,delta1);
			for(int i = 0; i<delta0.length; i++) {
				delta0[i]= error0[i] * (sigmoid1d(intermediateAnswer0,true)[i]);
			}
			for(int i=0; i<synapticWeights2.length; i++) {
				for(int j=0; j<synapticWeights2[0].length; j++) {
					synapticWeights2[i][j] +=  rotateMultiply(delta2 , intermediateAnswer1)[i][j];
				}
			}

			for (int i = 0; i<synapticWeights1.length; i++) {
				for(int j = 0; j<synapticWeights1[0].length; j++) {
					double[][] debugger = rotateMultiply(delta1, intermediateAnswer0);
					synapticWeights1[i][j] += debugger[i][j];
				}
			}
			for(int i=0; i<synapticWeights0.length; i++) {
				for(int j=0; j<synapticWeights0[0].length; j++) {
					synapticWeights0[i][j] +=  rotateMultiply(delta0 , inputs)[i][j];
				}
			}
			long stop = System.currentTimeMillis();
			elapsed = (stop - start) / 1000.0;
			//System.out.println(m);
			totalTime+=elapsed;
			System.out.println("Average time per iteration: "+ (elapsed));
		}
		System.out.println("Total Time:" + totalTime);
		/*for(double i: inputs){
			System.out.println(i);
		}*/


	}
}
