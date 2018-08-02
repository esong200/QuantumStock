import java.util.Scanner;
public class firstQuarterlyTests extends functions {
	public static void main(String[] args) {
		String rawData="";
		double[][] inputs = new double[35][55];
		String[][] inputStrings  = new String[35][55];
		double[][]synapticWeights0 = new double[55][35] ;
		double[][]intermediateAnswer = new double[35][35];
		double[] synapticWeights1 = new double[35] ;
		double[]finalAnsArr = new double[35];
		double[] desiredOutcome = {0,0,1,1,1,1,0,1,1,0,1,0,1,0,0,1,1,1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,1,1};
		System.out.print("Enter data: ");
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
		for(int m = 0; m<10; m++) {
			intermediateAnswer = sigmoid(dotMultiply(inputs, synapticWeights0), false);
			finalAnsArr = sigmoid(dotMultiply(synapticWeights1, intermediateAnswer), false);
			double[] error1 = new double[desiredOutcome.length];
			double[] delta1 = new double[desiredOutcome.length];
			System.out.println("Training Answer "+m+":");
			for(int i=0; i<delta1.length; i++) {
				System.out.println(finalAnsArr[i]);
				error1[i]=desiredOutcome[i]-finalAnsArr[i];
				delta1[i]= error1[i]*sigmoid(finalAnsArr[i],true);
			}
			double[][] error0 =  rotateMultiply(synapticWeights1,delta1);
			double[][] delta0 = new double[35][35]; /*rotateMultiply(synapticWeights1,delta1);*/

			for(int i = 0; i<delta0.length; i++) {
				for(int j=0; j<delta0[0].length; j++) {
					delta0[i][j]=error0[i][j] * sigmoid(intermediateAnswer,true)[i][j];
				}
			}
			for(int i=0; i<delta1.length; i++) {
				double[] x = rotateMultiply(intermediateAnswer, delta1);
				synapticWeights1[i]+= x[i];
			}

			for (int i = 0; i<synapticWeights0.length; i++) {
				for(int j = 0; j<synapticWeights0[0].length; j++) {
					synapticWeights0[i][j] += rotateMultiply(delta0, inputs )[i][j];
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Synaptic Weights1:");
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
		scan.close();
		/*System.out.println();
		System.out.println("Test Answers:");
		double[][] testArr = {{1,0,0},{0,1,0},{0,1,1},{0,1,1}};
		double[] answers = sigmoid(dotMultiply(synapticWeights1, sigmoid(dotMultiply(testArr, synapticWeights0), false)), false);
		for(double i: answers) {
			System.out.println(i);

		}
		*/
	}

}
