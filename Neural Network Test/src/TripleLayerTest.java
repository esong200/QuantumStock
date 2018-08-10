
public class TripleLayerTest extends functions{
	public static void main(String[] args) {
		double[]inputs = new double[29];
		double[] desiredOutcome = {0,0,1,0,0,0,0};
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
		for(int i=0; i<inputs.length; i++) {
			inputs[i]=(2*Math.random())-1;
		}
		for(int m=0; m<100; m++) {
			long start = System.currentTimeMillis();
			intermediateAnswer0 = sigmoid1d(dotMultiply(inputs, synapticWeights0), false);
			intermediateAnswer1 = sigmoid1d(dotMultiply(intermediateAnswer0, synapticWeights1), false);
			finalAnsArr = sigmoid1d(dotMultiply(synapticWeights1, intermediateAnswer1), false);
			System.out.println("Training Answers "+m+":");
		}

	}
}
