import java.util.Scanner;
import java.util.ArrayList;

public class FiveLayerTest extends functions {
	//array specs: input array: 1x29, desiredOutcome: 1x8
	public static void main(String[] args) {
		String company = "AAPL";
		ArrayList<double[]> data = readCsv("/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Data/"+company+"DataAdjst.csv");
		ArrayList<double[]> ans = readCsv("/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Data/"+company+"Ans.csv");
		ArrayList<double[]> dataTaylored = data;
		int count = 0;
		int greatestAccuracy = 0;
		int greatestAccuracyIndex = 0;
		ArrayList<Integer> correct = new ArrayList<Integer>();
		double[][] dataTayloredMatrix = new double[dataTaylored.size()][dataTaylored.get(0).length];
		int maxSize = data.size();
		while(ans.size()>maxSize){
			ans.remove(ans.size()-1);
		}
		double[] inputs = new double[data.get(0).length];
		double[] desiredOutcome = new double[ans.get(0).length];
		for(int i=0; i<data.size(); i++) {
			for(int j=0; j<data.get(i).length; j++) {
				dataTaylored.get(i)[j] *= 10;
				//dataTayloredMatrix[i][j]=dataTaylored.get(i)[j];
			}
		}
		
		for(int i=0; i<data.get(0).length; i++) {
			inputs[i]=(dataTaylored.get(0)[i]);	
		}
		//writeCsv(inputs, "/Users/ethansong/Documents/Stock Data/10xANDVDataAdjst.csv");
		for(int i=0; i<ans.get(0).length; i++) {
			desiredOutcome[i] = ans.get(0)[i];
		}
		//double[] inputs = new double[];
		double[][]synapticWeights0 = new double[inputs.length][25];
		double[]intermediateAnswer0 = new double[synapticWeights0[0].length];
		double[][] synapticWeights1 = new double[synapticWeights0[0].length][20];
		double[] intermediateAnswer1 = new double[synapticWeights1[0].length];
		double[][] synapticWeights2 = new double[intermediateAnswer1.length][16];
		double[] intermediateAnswer2 = new double[synapticWeights2[0].length];
		double[][] synapticWeights3 = new double[intermediateAnswer2.length][12];
		double[] intermediateAnswer3 = new double[synapticWeights3[0].length];
		double[][] synapticWeights4 = new double[intermediateAnswer3.length][desiredOutcome.length];
		double[]finalAnsArr = new double[desiredOutcome.length];
		double[] error4 = new double[desiredOutcome.length];
		double[] delta4 = new double[desiredOutcome.length];
		double[] error3 = new double[intermediateAnswer3.length];
		double[] delta3 = new double[intermediateAnswer3.length];
		double[] error2 = new double[intermediateAnswer2.length];
		double[] delta2 = new double[intermediateAnswer2.length];
		double[] error1 = new double[intermediateAnswer1.length];
		double[] delta1 = new double[intermediateAnswer1.length];
		double[] error0 = new double[intermediateAnswer0.length];
		double[] delta0 = new double[intermediateAnswer0.length];
		double[] correctPercentageArray = new double[520];
		
		double totalTime = 0;
		double innermostTime = 0;
				
			for (int i=0; i<synapticWeights1.length; i++) {
				for(int j=0; j<synapticWeights1[0].length; j++) {
					synapticWeights1[i][j] = Math.random() - 0.5;
				}
			}
		long outermostStart = System.currentTimeMillis();
			long start = System.currentTimeMillis();
			for(int m = 0; m<100000; m++) {
				long innermostStart = System.currentTimeMillis();
				intermediateAnswer0/*1x18*/ = sigmoid1d(dotMultiply(inputs/*1x29*/, synapticWeights0/*29x18*/), false);
				intermediateAnswer1/*1x18*/ = sigmoid1d(dotMultiply(intermediateAnswer0/*1x29*/, synapticWeights1/*29x18*/), false);
				intermediateAnswer2 = sigmoid1d(dotMultiply(intermediateAnswer1,synapticWeights2),false);
				intermediateAnswer3 = sigmoid1d(dotMultiply(intermediateAnswer2,synapticWeights3),false);
				finalAnsArr = sigmoid1d(dotMultiply(intermediateAnswer3, synapticWeights4), false);
				//System.out.println("Training Answers "+m+" inside outer iteration "+k+":");
				for(int i=0; i<delta4.length; i++) {
					//System.out.println(finalAnsArr[i]);
					error4[i]=desiredOutcome[i]-finalAnsArr[i];
					delta4[i]= error4[i]*sigmoid(finalAnsArr[i],true);
				}
				error3 = rotateMultiply(synapticWeights4,delta4);
				for(int i = 0; i<delta3.length; i++) {
					delta3[i]= error3[i] * (sigmoid1d(intermediateAnswer3,true)[i]);
				}
				error2 = rotateMultiply(synapticWeights3,delta3);
				for(int i = 0; i<delta2.length; i++) {
					delta2[i]= error2[i] * (sigmoid1d(intermediateAnswer2,true)[i]);
				}
				error1 = rotateMultiply(synapticWeights2,delta2);
				for(int i = 0; i<delta1.length; i++) {
					delta1[i]= error1[i] * (sigmoid1d(intermediateAnswer1,true)[i]);
				}
				error0 = rotateMultiply(synapticWeights1,delta1);
				for(int i = 0; i<delta0.length; i++) {
					delta0[i]= error0[i] * (sigmoid1d(intermediateAnswer0,true)[i]);
				}
				for(int i=0; i<synapticWeights4.length; i++) {
					for(int j=0; j<synapticWeights4[0].length; j++) {
						synapticWeights4[i][j] +=  rotateMultiply(delta4 , intermediateAnswer3)[i][j];
					}
				}
				for(int i=0; i<synapticWeights3.length; i++) {
					for(int j=0; j<synapticWeights3[0].length; j++) {
						synapticWeights3[i][j] +=  rotateMultiply(delta3 , intermediateAnswer2)[i][j];
					}
				}
				for(int i=0; i<synapticWeights2.length; i++) {
					for(int j=0; j<synapticWeights2[0].length; j++) {
						synapticWeights2[i][j] +=  rotateMultiply(delta2 , intermediateAnswer1)[i][j];
					}
				}
				for(int i=0; i<synapticWeights1.length; i++) {
					for(int j=0; j<synapticWeights1[0].length; j++) {
						synapticWeights1[i][j] +=  rotateMultiply(delta1 , intermediateAnswer0)[i][j];
					}
				}
				for (int i = 0; i<synapticWeights0.length; i++) {
					for(int j = 0; j<synapticWeights0[0].length; j++) {
						double[][] debugger = rotateMultiply(delta0, inputs);
						synapticWeights0[i][j] += debugger[i][j];
					}
				}
				long innermostStop = System.currentTimeMillis();
				innermostTime = (innermostStop-innermostStart) / 1000.0;
				//System.out.println();
				//System.out.println("Innermost Time: " +innermostTime);
			//System.out.println();
				double largest = 0;
				int index = 0;
				int index2 = 0;
				double second = 0;
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
				if(desiredOutcome[index]==1 || desiredOutcome[index2]==1) {
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
			if(m%200 == 0) {
				int right = 0;
				for(double i:correct) {
					if(i == 1) {
						right++;
					}
				}
				System.out.println("Last 200 correct:" + right);
				correctPercentageArray[count]=right;
				if(correctPercentageArray[count]>greatestAccuracy) {
					greatestAccuracy = (int)correctPercentageArray[count];
					greatestAccuracyIndex = m;
					System.out.println("The biggest so far: "+ greatestAccuracy);
					String file1 = "/Users/ethansong/Documents/Matrix Saves/snW5L/"+company+"synapticWeights0.csv";
					String file2 = "/Users/ethansong/Documents/Matrix Saves/snW5L/"+company+"synapticWeights1.csv";
					String file3 = "/Users/ethansong/Documents/Matrix Saves/snW5L/"+company+"synapticWeights2.csv";
					String file4 = "/Users/ethansong/Documents/Matrix Saves/snW5L/"+company+"synapticWeights3.csv";
					String file5 = "/Users/ethansong/Documents/Matrix Saves/snW5L/"+company+"synapticWeights4.csv";
					
					
					writeCsv(synapticWeights0, file1);
					writeCsv(synapticWeights1, file2);
					writeCsv(synapticWeights2, file3);
					writeCsv(synapticWeights3, file4);
					writeCsv(synapticWeights4, file5);
				}
				correctPercentageArray[count]=right;
				count++;
			}
			if(m%1000 == 0) {
				double  avg = 0;
				for(double i: error2) {
					avg+=i;
				}
				avg= avg/error2.length;
				System.out.println("error " + m + " :" + avg);

			}
			int rand = (int) ((int) dataTaylored.size()*Math.random());
			//System.out.println(rand);
			for(int i=0; i<data.get(0).length; i++) {
				inputs[i]=(dataTaylored.get(rand))[i];
			}
			for(int i=0; i<ans.get(0).length; i++) {
				desiredOutcome[i] = ans.get(rand)[i];
			}
			long stop = System.currentTimeMillis();
			double time = (stop - start) / 1000.0;
			//System.out.println(m);
			//System.out.println("Time:" + time);
			//inputs
		}
		long outermostStop = System.currentTimeMillis();
		totalTime = (outermostStop-outermostStart)/1000.0;
		writeCsv(correctPercentageArray, "/Users/ethansong/Documents/Matrix Saves/snW5L"+company+"correctAnswerTable.csv");
		System.out.println();
		System.out.println("Synaptic Weights0:");
		System.out.println();
		for(int i=0; i<synapticWeights0.length; i++) {
			for(double j: synapticWeights0[0]) {
				System.out.println(j);
			}
		}
		System.out.println();
		System.out.println("Synaptic Weights1:");
		for(int i=0; i<synapticWeights1.length; i++) {
			for(double j: synapticWeights1[0]) {
				System.out.println(j);
			}
		}
		System.out.println("Average time per outer loop iteration: "+ totalTime/data.size());
		System.out.println("Total time for outer and inner for loop iterations: "+ totalTime);
		/*String file1 = "/Users/ethansong/Documents/Matrix Saves/snW5L/"+company+"synapticWeights0.csv";
		String file2 = "/Users/ethansong/Documents/Matrix Saves/snW5L/"+company+"synapticWeights1.csv";
		String file3 = "/Users/ethansong/Documents/Matrix Saves/snW5L/"+company+"synapticWeights2.csv";
		String file4 = "/Users/ethansong/Documents/Matrix Saves/snW5L/"+company+"synapticWeights3.csv";
		String file5 = "/Users/ethansong/Documents/Matrix Saves/snW5L/"+company+"synapticWeights4.csv";
		
		
		writeCsv(synapticWeights0, file1);
		writeCsv(synapticWeights1, file2);
		writeCsv(synapticWeights2, file3);
		writeCsv(synapticWeights3, file4);
		writeCsv(synapticWeights4, file5);*/
	}
}
