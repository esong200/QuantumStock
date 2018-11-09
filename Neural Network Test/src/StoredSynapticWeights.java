
public class StoredSynapticWeights extends largeArray  {
	private double[][] storedSynapticWeights0 = new double[10][10];
	private double[] storedSynapticWeights1 = new double[10];
	
	public void newStorage(double[][] w0, double[] w1) {
		storedSynapticWeights0=w0;
		storedSynapticWeights1=w1;
	}
}
