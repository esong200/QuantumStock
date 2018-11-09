import java.io.File;
import java.util.ArrayList;

public class TripleLayerPseudorandomWeeklyChunk {
	public static void main(String[] args) {
		String comp = "AAPL";
		String runName = "UltraBig";
		String dataDirectory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/UltraBigWeeklyChunk/"+ comp + "DataAdjst.csv";
		String ansDirectory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/UltrabigWeeklyChunk/"+ comp + "Ans.csv";
		String weights0Directory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/UltraBigWeeklyChunk/Matrixes/TripleLayer/Weights0/" + comp + runName + "synapticWeights0.csv";
		String weights1Directory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/UltraBigWeeklyChunk/Matrixes/TripleLayer/Weights1/" + comp + runName + "synapticWeights1.csv";
		String weights2Directory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/UltraBigWeeklyChunk/Matrixes/TripleLayer/Weights2/" + comp + runName + "synapticWeights2.csv";
		ArrayList<double[][]> data = CSVReadWrite.readCsvDouble(dataDirectory, 4, 59);
		ArrayList<double[][]> ans = CSVReadWrite.readCsvDouble(ansDirectory, 4, 8);
		ArrayList<double[][]> dataTaylored = data;
		ArrayList<double[][]> pseudorandomData = CSVReadWrite.readCsvDouble(dataDirectory, 4, 59);
		ArrayList<double[][]> pseudorandomAns = CSVReadWrite.readCsvDouble(ansDirectory, 4, 8);
		System.out.println(data.size());
		System.out.println(ans.size());
		System.out.println(pseudorandomData.size());
		System.out.println(pseudorandomAns.size());
		ArrayList<Integer> correct = new ArrayList<Integer>();
		double[][] dataTayloredMatrix = new double[dataTaylored.size()][dataTaylored.get(0).length];
		int maxSize = data.size();
		while(ans.size()>maxSize){
			ans.remove(ans.size()-1);
		}
		System.out.println(ans.size());
		double[][] inputs = new double[data.get(0).length][data.get(0)[0].length];
		double[][] desiredOutcome = new double[ans.get(0).length][ans.get(0)[0].length];
		System.out.println("Chunk Length: " + inputs.length);
		System.out.println("Data length: " + inputs[0].length);
		for(int i=0; i<data.size(); i++) {
			for(int j=0; j<data.get(i).length; j++) {
				for(int k=0; k<data.get(i)[0].length; k++) {
					dataTaylored.get(i)[j][k] *= 10;
					//dataTayloredMatrix[i][j][k]=dataTaylored.get(i)[j][k];
				}
				
			}
		}

		for(int i=0; i<data.get(0).length; i++) {
			for(int j=0; j<data.get(0)[0].length; j++) {
				inputs[i][j]=(dataTaylored.get(0)[i][j]);
			}
			
		}
		System.out.println(dataTaylored.size());
		int best200 = 80;
		int bestComplete = 5;
		
		double[][]synapticWeights0 =new double[inputs[0].length][42];
		double[][]intermediateAnswer0 = new double[4][synapticWeights0[0].length];
		double[][] synapticWeights1 = new double[intermediateAnswer0[0].length][25];
		double[][]intermediateAnswer1 = new double[4][synapticWeights1[0].length];
		double[][] synapticWeights2 = new double[intermediateAnswer1[0].length][desiredOutcome.length];
		double[][] finalAnsArr = new double[4][desiredOutcome.length];
				
		double[][] error2 = new double[finalAnsArr.length][finalAnsArr[0].length];
		double[][] delta2 = new double[finalAnsArr.length][finalAnsArr[0].length];
		
		double[][] error1 = new double[intermediateAnswer1.length][intermediateAnswer1[0].length];
		double[][] delta1 = new double[intermediateAnswer1.length][intermediateAnswer1[0].length];
		
		double[][] error0 = new double[intermediateAnswer0.length][intermediateAnswer0[0].length];
		double[][] delta0 = new double[intermediateAnswer0.length][intermediateAnswer0[0].length];
		
		double totalTime = 0;
		double elapsed = 0;
		try {
			synapticWeights0 = CSVReadWrite.listToArray(CSVReadWrite.readCsv(weights0Directory));
			synapticWeights1 = CSVReadWrite.listToArray(CSVReadWrite.readCsv(weights1Directory));
			synapticWeights2 = CSVReadWrite.listToArray(CSVReadWrite.readCsv(weights2Directory));
			
		} catch (Exception e) {
			System.out.println("Weights do not exist. Generating from random seed.");
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
		} 
		System.out.println("Weight retrieval successful. Testing for proper size.");
		try {
			double[][] backwardsTest = synapticWeights0.clone();
			double[][] sizeTest1 = functions.dotMultiplyFastest(inputs, synapticWeights0);
			double[][] sizeTest2 = functions.dotMultiplyFastest(intermediateAnswer0, synapticWeights1);
			double[][] sizeTest3 = functions.dotMultiplyFastest(intermediateAnswer1, synapticWeights2);
			for(int i=0; i<synapticWeights0.length; i++) {
				for(int j=0; j<synapticWeights0[0].length; j++) {
					backwardsTest[i][j] +=  functions.rotateMultiply(delta0 , inputs)[i][j];
				}
			}
			
		}
		catch (Exception e) {
			System.out.println("Weights are incorrect dimentions. Generating from random seed.");
			synapticWeights0 =new double[inputs[0].length][42];
			intermediateAnswer0 = new double[4][synapticWeights0[0].length];
			synapticWeights1 = new double[intermediateAnswer0[0].length][25];
			intermediateAnswer1 = new double[4][synapticWeights1[0].length];
			synapticWeights2 = new double[intermediateAnswer1[0].length][desiredOutcome.length];
			finalAnsArr = new double[4][desiredOutcome.length];
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
		}
		System.out.println("Retrieved weights are the correct dimensions.");
		System.out.println("ArrayList Size: "+ data.size());

		for(int m = 0; m<1000000; m++) {
			if(pseudorandomData.size()==0) {
				for(double[][] x: dataTaylored) {
					pseudorandomData.add(x);
				}
				for(double[][] x: ans) {
					pseudorandomAns.add(x);
				}
				ans = CSVReadWrite.readCsvDouble(ansDirectory, 4, 8);
			}
			int rnd = (int) ((int) pseudorandomData.size()*Math.random());
			/*for(int i=0; i<inputs.length; i++) {
				inputs[i] = 10 * pseudorandomData.remove(rnd)[i];
			}*/
			inputs =  pseudorandomData.remove(rnd);
			/*for(int i=0; i<inputs.length; i++) {
				inputs[i] *=10;
			}*/
			desiredOutcome = pseudorandomAns.remove(rnd);
			/*int rand = (int) ((int) dataTaylored.size()*Math.random());
			
			for(int i=0; i<data.get(0).length; i++) {
				inputs[i]=(dataTaylored.get(rand))[i];
			}
			for(int i=0; i<ans.get(0).length; i++) {
				desiredOutcome[i] = ans.get(rand)[i];
			}*/
			long start = System.currentTimeMillis();
			intermediateAnswer0 = functions.sigmoid(functions.dotMultiplyFastest(inputs, synapticWeights0), false);
			intermediateAnswer1 = functions.sigmoid(functions.dotMultiplyFastest(intermediateAnswer0, synapticWeights1), false);
			finalAnsArr = functions.sigmoid(functions.dotMultiplyFastest(intermediateAnswer1, synapticWeights2), false);
			//System.out.println();
			//System.out.println("Training Answers "+m+" inside outer iteration :");
			
			//Error Calculations
			for(int i=0; i<delta2.length; i++) {
				for(int j=0; j<delta2[0].length; j++) {
					error2[i][j]=desiredOutcome[i][j]-finalAnsArr[i][j];
					delta2[i][j]= error2[i][j]*functions.sigmoid(finalAnsArr[i][j],true);
				}
				//System.out.println(finalAnsArr[i]);	
			}
			System.out.println(error1.length + " x "+ error1[0].length);
			error1 = functions.rotateMultiply(synapticWeights2,delta2);
			System.out.println(error1.length + " x "+ error1[0].length);
			for(int i = 0; i<delta1.length; i++) {
				for(int j=0; j<delta1[0].length ; j++) {
					delta1[i][j]= error1[i][j] * (functions.sigmoid(intermediateAnswer1,true)[i][j]);
				}
			}
			error0 = functions.rotateMultiply(synapticWeights1,delta1);
			for(int i = 0; i<delta0.length; i++) {
				for(int j=0; j<delta0[0].length; j++) {
					delta0[i][j]= error0[i][j] * (functions.sigmoid(intermediateAnswer0,true)[i][j]);
				}
			}
			//Synaptic Weight Adjustments
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
				if(finalAnsArr[0][i]>largest) {
					largest = finalAnsArr[0][i];
					index = i;
				}
			}
			for(int i = 0; i<finalAnsArr[0].length; i++) {
				if(finalAnsArr[0][i]>second && finalAnsArr[0][i]<largest) {
					second = finalAnsArr[0][i];
					index2 = i;
				}
			}
			for(int i = 0; i<finalAnsArr[0].length; i++) {
				if(finalAnsArr[0][i]>third && finalAnsArr[0][i]<second) {
					third = finalAnsArr[0][i];
					index3 = i;
				}
			}
			if(desiredOutcome[0][index]==1 || desiredOutcome[0][index2]==1 /*|| desiredOutcome[index3]==1*/) {
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
			while(right > (bestComplete/dataTaylored.size())*230) {
				int test = 0;
				//System.out.println("testing");
				for(double[][] x: dataTaylored) {
					double[][] test0, test1, testfinal;
					double testBest = 0, testSecond = 0;
					int bestind = 0, secondind = 0;
					test0 = functions.sigmoid(functions.dotMultiplyFastest(x/*1x29*/, synapticWeights0/*29x18*/), false);
					test1/*1x18*/ = functions.sigmoid(functions.dotMultiplyFastest(test0/*1x29*/, synapticWeights1/*29x18*/), false);
					testfinal = functions.sigmoid(functions.dotMultiplyFastest(test1, synapticWeights2), false);
					double[][] actualAns = ans.get(dataTaylored.indexOf(x));
					for(int i = 0; i < testfinal[0].length; i++) {
						if(testfinal[0][i]>largest) {
							testBest = testfinal[0][i];
							bestind = i;
						}
					}
					for(int i = 0; i<testfinal[0].length; i++) {
						if(testfinal[0][i]>testSecond && testfinal[0][i]<testBest) {
							testSecond = testfinal[0][i];
							secondind = i;
						}
					}
					if(actualAns[0][bestind]==1 || actualAns[0][secondind]==1 /*|| desiredOutcome[index3]==1*/) {
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
				String file1 = weights0Directory;
				String file2 = weights1Directory;
				String file3 = weights2Directory;
				CSVReadWrite.writeCsv(synapticWeights0, file1);
				CSVReadWrite.writeCsv(synapticWeights1, file2);
				CSVReadWrite.writeCsv(synapticWeights2, file3);
				System.out.println("Matrixes written");

				System.out.println("New Saved Correct:" + bestComplete + " out of " + data.size() + ", " + bestComplete + " out of "+ data.size() + " ("+ (Math.round((((double)(bestComplete)*100)/(double)(data.size()))*100.0))/100.0  + "%)");
				System.out.println("Best200: " + best200);
				break;
			}
				if(m%200 == 0) {
				System.out.println("Last 200 correct:" + right + ", Best Saved Correct so far: " +bestComplete + " out of "+ data.size() + " ("+ (Math.round((((double)(bestComplete)*100)/(double)(data.size()))*100.0))/100.0 + "%)");
			}
			if(m%10000 == 0) {
				double  avg = 0;
				for(double[] i: error2) {
					for(double j : i) {
						avg+=j;
					}
					
				}
				avg= avg/error2.length;
				System.out.println(comp+" error " + m + " :" + avg);

			}
		}
		System.out.println("Total Time:" + totalTime);
		System.out.println(comp + " Best Save: " + bestComplete);
		/*for(double i: inputs){
			System.out.println(i);
		}*/

		//String file1 = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Matrixes\\Weights0\\" + comp + "synapticWeights0.csv";
		//String file2 = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Matrixes\\Weights0\\"+ comp +"synapticWeights1.csv";
		//String file3 = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Matrixes\\Weights0\\"+ comp +"synapticWeights2.csv";
		//writeCsv(synapticWeights0, file1);
		//writeCsv(synapticWeights1, file2);
		//writeCsv(synapticWeights2, file3);
		
	}
}
