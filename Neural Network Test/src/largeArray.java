import java.util.Scanner;

public class largeArray extends functions {

	public static void main(String[] args) {
		String rawData="";
		double[][] inputs = new double[35][55];
		String[][] inputStrings  = new String[35][55];
		double[][]synapticWeights0 = new double[55][35] ;
		double[][]intermediateAnswer = new double[35][35];
		double[] synapticWeights1 = new double[35];
		double[]finalAnsArr = new double[35];
		double[] desiredOutcome = {0,0,1,1,1,1,0,1,1,0,1,0,1,0,0,1,1,1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,1,1};
		double[] error1 = new double[desiredOutcome.length];
		double[] delta1 = new double[desiredOutcome.length];
		double[][] delta0 = new double[35][35]; 
		double[][] prevSynapticWeights0 = new double[synapticWeights0.length][synapticWeights0[0].length];
		double[] prevSynapticWeights1 = new double[synapticWeights1.length];
		int numOfIterations = 50;
		double[] times = new double[numOfIterations];


		System.out.print("Enter data: ");
		Scanner scan = new Scanner (System.in);
		/*if(scan.hasNext()) {
			for(int i=0; i<55; i++) {
				rawData=scan.nextLine();
				rawData=rawData.replaceAll(" ","");
				for(int j=0; j<35; j++) {
					inputs[j][i]=Double.parseDouble(rawData.substring(0,rawData.indexOf("%")));
					rawData = rawData.replaceFirst(rawData.substring(0,rawData.indexOf("%")+1),"");
				}
			}*/
			
			for(int i=0; i<inputs.length; i++) {
				for(int j=0; j<inputs[0].length; j++) {
					inputs[i][j] = (2*Math.random());
				}
			}
			for (int i=0; i<synapticWeights1.length; i++) {
				synapticWeights1[i] = (2*Math.random()) -1;
			}

		
		for(int m = 0; m<numOfIterations; m++) {
			long start = System.currentTimeMillis(); 
			intermediateAnswer = sigmoid(dotMultiplyFastest(inputs, synapticWeights0), false);
			finalAnsArr = sigmoid1d(dotMultiply(synapticWeights1, intermediateAnswer), false);
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
		prevSynapticWeights0=synapticWeights0;
		prevSynapticWeights1=synapticWeights1;
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
		
		//scan.close();
		
	}

}
