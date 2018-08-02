
public class DoubleLayer extends functions {


	public static void main(String[] args) {
		double[][] inputs = {{0,0,1},{0,1,1},{1,0,1},{1,1,0}};
		double[][]synapticWeights0 = new double[3][4];
		double[][]intermediateAnswer = new double[4][4];
		double[] synapticWeights1 = new double[4] ;
		double[]finalAnsArr = {0,0,0,0};
		double[] desiredOutcome = {0,1,1,0};
		for (int i=0; i<synapticWeights1.length; i++) {
			synapticWeights1[i] = (2*Math.random()) -1;
		}
		
		for(int m = 0; m<10000; m++) {
			intermediateAnswer = sigmoid(dotMultiply(inputs, synapticWeights0), false);
			finalAnsArr = sigmoid(dotMultiply(synapticWeights1, intermediateAnswer), false);
			double[] error1 = {0,0,0,0};
			double[] delta1 = {0,0,0,0};
			System.out.println("Training Answers: ");
			for(int i=0; i<4; i++) {
				System.out.println(finalAnsArr[i]);
				error1[i]=desiredOutcome[i]-finalAnsArr[i];
			}	
			
			for(int i = 0; i<4; i++) {
				delta1[i]= error1[i]*sigmoid(finalAnsArr[i],true);
			}
			
			double[][] error0 = rotateMultiply(synapticWeights1,delta1);
			double[][] delta0 = new double[3][4];
			
			for(int i = 0; i<3; i++) {
				for(int j=0; j<4; j++) {
					delta0[i][j]=error0[i][j] * sigmoid(intermediateAnswer,true)[i][j];
				}
			}
			
			for(int i=0; i<4; i++) {
				//error when i=3
				double[] x = rotateMultiply(intermediateAnswer, delta1);
				synapticWeights1[i]+= x[i];
			}
			
			for (int i = 0; i<3; i++) {
				for(int j = 0; j<4; j++) {
					synapticWeights0[i][j] += dotMultiply(inputs, delta0 )[i][j];
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Synaptic Weights1:");
		for(double i: synapticWeights1) {
			System.out.println( i);
		}
		System.out.println();
		System.out.println("Synaptic Weights0:");
		for(double i[]: synapticWeights0) {
			System.out.println();
			for(double x: i) {
				System.out.print(x);
			}
		}
		System.out.println();
		System.out.println("Test Answers:");
		double[][] testArr = {{1,0,0},{0,1,0},{0,1,1},{0,1,1}};
		double[] answers = sigmoid(dotMultiply(synapticWeights1, sigmoid(dotMultiply(testArr, synapticWeights0), false)), false);
		for(double i: answers) {
			System.out.println(i);

		}
	}
	
}
