import java.util.Scanner;
import java.util.ArrayList;

public class ArrayListNeuralNetwork extends functions {
	public static void main(String[] args) {
		String rawData="";

		ArrayList<double[]> data = DataCollection("MSFT");
		ArrayList<double[]> ans = answerCompile("MSFT");
		int maxSize = data.size();
		while(ans.size()>maxSize){
			ans.remove(ans.size()-1);
		}//make arrays the same size
		double[][] desiredOutcome = new double[ans.size()][ans.get(0).length];
		

		/*System.out.print("Enter data: ");
		Scanner scan = new Scanner (System.in);
		if(scan.hasNext()) {
			for(int i=0; i<55; i++) {
				rawData=scan.nextLine();
				rawData=rawData.replaceAll(" ","");
				for(int j=0; j<35; j++) {
					inputs[j][i]=Double.parseDouble(rawData.substring(0,rawData.indexOf("%")));
					rawData = rawData.replaceFirst(rawData.substring(0,rawData.indexOf("%")+1),"");
				}
			}
		}
		*/

			for (int i=0; i<synapticWeights1.length; i++) {
				synapticWeights1[i] = (2*Math.random()) -1;
			}
		for(int m = 0; m<numOfIterations; m++) {
			long start = System.currentTimeMillis();
			intermediateAnswer = sigmoid(dotMultiplyFastest(inputs, synapticWeights0), false);
			finalAnsArr = sigmoid(dotMultiply(synapticWeights1, intermediateAnswer), false);
			System.out.println("Training Answer "+m+":");
			for(int i=0; i<delta1.length; i++) {
				System.out.println(finalAnsArr[i]);
				error1[i]=desiredOutcome[i]-finalAnsArr[i];
				delta1[i]= error1[i]*sigmoid(finalAnsArr[i],true);
			}
			double[][] error0 =  rotateMultiply(synapticWeights1,delta1);
						//rotateMultiply(synapticWeights1,delta1);

			for(int i = 0; i<delta0.length; i++) {
				for(int j=0; j<delta0[0].length; j++) {
					delta0[i][j]=error0[i][j] * sigmoid(intermediateAnswer,true)[i][j];
				}
			}
			for(int i=0; i<delta1.length; i++) {
				synapticWeights1[i] += rotateMultiply(intermediateAnswer, delta1)[i];
			}

			for (int i = 0; i<synapticWeights0.length; i++) {
				for(int j = 0; j<synapticWeights0[0].length; j++) {
					synapticWeights0[i][j] += rotateMultiply(delta0, inputs )[i][j];
				}
			}
			long stop = System.currentTimeMillis();
			double elapsed = (stop - start) / 1000.0;
			times[m]=elapsed;
			//System.out.println(m);
			System.out.println("Time:" + elapsed);

		}
		System.out.println();
		double total = 0;
		for(int i=0; i<times.length; i++) {
			total += times[i];
		}
		System.out.println("Average time per iteration: "+ (total/times.length));
		/*System.out.println("Synaptic Weights1:");
		for(double i: synapticWeights1) {
			System.out.println(i);
			System.out.println();
		}
		System.out.println();
		System.out.println("Synaptic Weights0:");
		for(double i[]: synapticWeights0) {
			System.out.println();
			for(double x: i) {
				System.out.print(x + "\t");
			}
			System.out.println();

		}
		*/

	}
}
