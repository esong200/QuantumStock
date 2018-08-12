import java.util.ArrayList;

public class TripleLayerTest {
	public static void main(String[] args) {
		String company = "KSS";
		ArrayList<double[]> data = CSVReadWrite.readCsv("/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Data/"+company+"DataAdjst.csv");
		ArrayList<double[]> ans = CSVReadWrite.readCsv("/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Data/"+company+"Ans.csv");
		ArrayList<double[]> dataTaylored = data;
		ArrayList<Integer> correct = new ArrayList<Integer>();
		double[][] dataTayloredMatrix = new double[dataTaylored.size()][dataTaylored.get(0).length];
		int maxSize = data.size();
		int count = 0;
		int greatestAccuracy = 0;
		int greatestAccuracyIndex = 0;
		int best200 = 0;
		int bestComplete = 0;
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
		double[] correctPercentageArray = new double[5020];
		double totalTime = 0;
		double elapsed = 0;



		try {
			synapticWeights0 = CSVReadWrite.listToArray(CSVReadWrite.readCsv("/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Matrixes/TripleLayer/Weights0/"+company+"synapticWeights0.csv"));
			synapticWeights1 = CSVReadWrite.listToArray(CSVReadWrite.readCsv("/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Matrixes/TripleLayer/Weights1/"+company+"synapticWeights1.csv"));
			synapticWeights2 = CSVReadWrite.listToArray(CSVReadWrite.readCsv("/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Matrixes/TripleLayer/Weights2/"+company+"synapticWeights2.csv"));
		}
		catch (Exception e){
			System.out.println("Synaptic Weight not avaliable, using random ones.");
			for (int i=0; i<synapticWeights0.length; i++) {
				for(int j=0; j<synapticWeights0[0].length; j++) {
					synapticWeights0[i][j] = (Math.random()) -0.5;
				}
			}
			for (int i=0; i<synapticWeights1.length; i++) {
				for(int j=0; j<synapticWeights1[0].length; j++) {
					synapticWeights1[i][j] = (Math.random()) -0.5;
				}
			}
			for (int i=0; i<synapticWeights2.length; i++) {
				for(int j=0; j<synapticWeights2[0].length; j++) {
					synapticWeights2[i][j] = (Math.random()) -0.5;
				}
			}
		}

		for(int m = 0; m<100000; m++) {
			int rand = (int) ((int) dataTaylored.size()*Math.random());
			for(int i=0; i<data.get(0).length; i++) {
				inputs[i]=(dataTaylored.get(rand))[i];
			}
			for(int i=0; i<ans.get(0).length; i++) {
				desiredOutcome[i] = ans.get(rand)[i];
			}
			long start = System.currentTimeMillis();
			intermediateAnswer0 = functions.sigmoid1d(functions.dotMultiply(inputs/*1x29*/, synapticWeights0/*29x18*/), false);

			intermediateAnswer1/*1x18*/ = functions.sigmoid1d(functions.dotMultiply(intermediateAnswer0/*1x29*/, synapticWeights1/*29x18*/), false);
			finalAnsArr = functions.sigmoid1d(functions.dotMultiply(intermediateAnswer1, synapticWeights2), false);
			//System.out.println();
			//System.out.println("Training Answers "+m+" inside outer iteration :");
			for(int i=0; i<delta2.length; i++) {
				//System.out.println(finalAnsArr[i]);
				error2[i]=desiredOutcome[i]-finalAnsArr[i];
				delta2[i]= error2[i]*functions.sigmoid(finalAnsArr[i],true);
			}

			error1 = functions.rotateMultiply(synapticWeights2,delta2);
			for(int i = 0; i<delta1.length; i++) {
				delta1[i]= error1[i] * (functions.sigmoid1d(intermediateAnswer1,true)[i]);
			}
			error0 = functions.rotateMultiply(synapticWeights1,delta1);
			for(int i = 0; i<delta0.length; i++) {
				delta0[i]= error0[i] * (functions.sigmoid1d(intermediateAnswer0,true)[i]);
			}
			for(int i=0; i<synapticWeights2.length; i++) {
				for(int j=0; j<synapticWeights2[0].length; j++) {
					synapticWeights2[i][j] +=  functions.rotateMultiply(delta2 , intermediateAnswer1)[i][j];
				}
			}

			for (int i = 0; i<synapticWeights1.length; i++) {
				for(int j = 0; j<synapticWeights1[0].length; j++) {
					double[][] debugger = functions.rotateMultiply(delta1, intermediateAnswer0);
					synapticWeights1[i][j] += debugger[i][j];
				}
			}
			for(int i=0; i<synapticWeights0.length; i++) {
				for(int j=0; j<synapticWeights0[0].length; j++) {
					synapticWeights0[i][j] +=  functions.rotateMultiply(delta0 , inputs)[i][j];
				}
			}
			long stop = System.currentTimeMillis();
			elapsed = (stop - start) / 1000.0;
			//System.out.println(m);
			totalTime+=elapsed;
			//System.out.println("Average time per iteration: "+ (elapsed));
			double largest = 0;
			int index = 0;
			int index2 = 0;
			int index3 = 0;
			double second = 0;
			double third = 0;
			for(int i = 0; i < finalAnsArr.length; i++) {
				if(finalAnsArr[i]>largest) {
					largest = finalAnsArr[i];
					index = i;
				}
			}
			for(int i = 0; i<finalAnsArr.length; i++) {
				if(finalAnsArr[i]>second && finalAnsArr[i]<largest) {
					second = finalAnsArr[i];
					index2 = i;
				}
			}
			for(int i = 0; i<finalAnsArr.length; i++) {
				if(finalAnsArr[i]>third && finalAnsArr[i]<second) {
					third = finalAnsArr[i];
					index3 = i;
				}
			}
			if(desiredOutcome[index]==1 || desiredOutcome[index2]==1 /*|| desiredOutcome[index3]==1*/) {
				if(correct.size() == 200) {
					correct.remove(0);
				}
				correct.add(1);
			}
			else {
				if(correct.size() == 200) {
					correct.remove(0);
				}
				correct.add(0);
			}
			int right = 0;
			for(double i:correct) {
				if(i == 1) {
					right++;
				}
			}
			while(right > best200) {
				int test = 0;
				for(double[] x: dataTaylored) {
					double[] test0, test1, testfinal;
					double testBest = 0, testSecond = 0;
					int bestind = 0, secondind = 0;
					test0 = functions.sigmoid1d(functions.dotMultiply(x/*1x29*/, synapticWeights0/*29x18*/), false);
					test1/*1x18*/ = functions.sigmoid1d(functions.dotMultiply(test0/*1x29*/, synapticWeights1/*29x18*/), false);
					testfinal = functions.sigmoid1d(functions.dotMultiply(test1, synapticWeights2), false);
					double[] actualAns = ans.get(dataTaylored.indexOf(x));
					for(int i = 0; i < testfinal.length; i++) {
						if(testfinal[i]>largest) {
							testBest = testfinal[i];
							bestind = i;
						}
					}
					for(int i = 0; i<testfinal.length; i++) {
						if(testfinal[i]>testSecond && testfinal[i]<testBest) {
							testSecond = testfinal[i];
							secondind = i;
						}
					}
					if(actualAns[bestind]==1 || actualAns[secondind]==1 /*|| desiredOutcome[index3]==1*/) {
						test++;
					}

					//System.out.println("test:" + test);
				}
				if(test <= bestComplete) {
					test = 0;
					break;
				}
				best200 = right;
				bestComplete = test;
				String file1 = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Matrixes/TripleLayer/Weights0/"+company+"synapticWeights0.csv";
				String file2 = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Matrixes/TripleLayer/Weights1/"+company+"synapticWeights1.csv";
				String file3 = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Matrixes/TripleLayer/Weights2/"+company+"synapticWeights2.csv";
				CSVReadWrite.writeCsv(synapticWeights0, file1);
				CSVReadWrite.writeCsv(synapticWeights1, file2);
				CSVReadWrite.writeCsv(synapticWeights2, file3);

				System.out.println("Saved Correct:" + bestComplete);
				System.out.println("Best200:" + best200);
				}
			if(m%200 == 0) {
				System.out.println(m+"th iteration. Last 200 correct:" + right);
			}
			if(right>greatestAccuracy) {
				greatestAccuracy = right;
			}

		}
		System.out.println("Total Time:" + totalTime);
		/*for(double i: inputs){
			System.out.println(i);
		}*/
		System.out.println("The greatest correct rate: "+greatestAccuracy);
		CSVReadWrite.writeCsv(correctPercentageArray, "/Users/ethansong/Documents/Matrix Saves/snW3L/"+company+"accuracyTable.csv");
		/*String file1 = "/Users/ethansong/Documents/Matrix Saves/synapticWeights0.csv";
		String file2 = "/Users/ethansong/Documents/Matrix Saves/synapticWeights1.csv";
		String file3 = "/Users/ethansong/Documents/Matrix Saves/synapticWeights2.csv";
		writeCsv(synapticWeights0, file1);
		writeCsv(synapticWeights1, file2);
		writeCsv(synapticWeights2, file3);*/


	}
}
