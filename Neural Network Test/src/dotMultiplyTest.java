
public class dotMultiplyTest extends TripleLayerPseudorandomWeeklyChunk{
	public static void main(String[] args) {
		double[][] synapticWeights0 = new double[59][42];
		double[][] inputs = new double[4][59];
		for (int i=0; i<synapticWeights0.length; i++) {
			for(int j=0; j<synapticWeights0[0].length; j++) {
				synapticWeights0[i][j] = (2*Math.random()) -1;
			}
		}
		for (int i=0; i<inputs.length; i++) {
			for(int j=0; j<inputs[0].length; j++) {
				inputs[i][j] = (2*Math.random()) -1;
			}
		}
		
		
		functions.dotMultiplyFastest(inputs, synapticWeights0);
		System.out.println("Done");
	}
}
