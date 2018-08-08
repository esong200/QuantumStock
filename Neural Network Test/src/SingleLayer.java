

public class SingleLayer extends functions {
	// single layer machine learning test one
	public static void main(String[] args) {
		double[][] inputs = {{0,0,1},{0,1,1},{1,0,1},{1,1,1}};
		double[] desiredOutcome = {0,0,1,1};
		double[]finalAnsArr = {0,0,0,0};
		double[]dotPOfInitialAndSynapse = {0,0,0,0};
		double[] synapticWeights = new double[3] ;
		for (int i=0; i<3; i++) {
			synapticWeights[i] = (2*Math.random()) -1;
		}

		for(int m = 0; m<100000; m++) {
			dotPOfInitialAndSynapse = dotMultiply(synapticWeights,inputs);

			for(int i=0; i<4; i++) {
				finalAnsArr[i] = sigmoid(dotPOfInitialAndSynapse[i], false);
			}

			double[] howMuchCompMissedBy = {0,0,0,0};

			for(int i=0; i<4; i++) {
				System.out.println(finalAnsArr[i]);
				howMuchCompMissedBy[i]=desiredOutcome[i]-finalAnsArr[i];
			}

			double[] howMuchItNeedstoChangeBy = {0,0,0,0};

			for(int i = 0; i<4; i++) {
				howMuchItNeedstoChangeBy[i]= howMuchCompMissedBy[i]*sigmoid(finalAnsArr[i],true);
			}

			for(int i=0; i<3; i++) {
				synapticWeights[i]+= rotateMultiply(inputs, howMuchItNeedstoChangeBy)[i];
			}
		}
		System.out.println();
		for(double i: synapticWeights) {
			System.out.println(i);
		}
		System.out.println();
		double[][] testArr = {{1,0,0},{0,1,0}};
		double[] testAns = {0,0};
		dotPOfInitialAndSynapse = dotMultiply(synapticWeights,testArr);
		for(int i=0; i<2; i++) {
			System.out.println(sigmoid(dotPOfInitialAndSynapse[i], false));
		}
	}
}
