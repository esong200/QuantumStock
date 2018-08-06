import java.util.Scanner;

public class MultipleOutputs extends functions, DataReader {
//Outputs in format {<-10%, -10% - -5%, -5% - -2%, -2% - 0%, 0% - 2%, 2% - 5%, 5% - 10%, >10%}
 public static void main(String[] args) {
 	String rawData="";
 	double[][] inputs = retreaveData(36, 13);
 	double[][] synapticWeights0 = new double[13][35] ;
 	double[][] intermediateAnswer = new double[35][35];
 	double[][] synapticWeights1 = new double[35][8];
 	double[][] finalAnsArr = new double[35][8];
 	double[][] desiredOutcome = retreaveAnswers(36, 8, 13);   //need more data to put the desired Outcome
  double[][] error1 = new double[desiredOutcome.length][desiredOutcome[0].length];
  double[][] delta1 = new double[desiredOutcome.length][desiredOutcome[0].length];


  //to read inputs can be replaced w/ excel reading
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
  for (int i=0; i<synapticWeights1.length; i++) {
      for(int j=0; j<synapticWeights1[0].length; j++){
        synapticWeights1[i][j] = (2*Math.random()) -1;
      }
		} //synaptic training
 	for(int m = 0; m<1000; m++) {
 		intermediateAnswer = sigmoid(dotMultiply(inputs, synapticWeights0), false);
 		finalAnsArr = sigmoid(dotMultiply(synapticWeights1, intermediateAnswer), false);
 		System.out.println("Training Answer "+m+":");
 		for(int i=0; i<delta1.length; i++) {
			for(int j = 0; j<delta1[0].length; j++){
 			//System.out.println(finalAnsArr[i]);
 				error1[i][j]=desiredOutcome[i][j]-finalAnsArr[i][j];
 				delta1[i][j]= error1[i][j]*sigmoid(finalAnsArr[i][j],true);
 		}
	}
 		double[][] error0 =  rotateMultiply(synapticWeights1,delta1);
 		double[][] delta0 = new double[error0.length][error0[0].length]; /*rotateMultiply(synapticWeights1,delta1);*/

 		for(int i = 0; i<delta0.length; i++) {
 			for(int j=0; j<delta0[0].length; j++) {
 				delta0[i][j]=error0[i][j] * sigmoid(intermediateAnswer,true)[i][j];
 			}
 		}
  		for(int i=0; i<synapticWeights1.length; i++) {
  			for(int j=0; j<synapticWeights1[0].length;j++) {
  				synapticWeights1[i][j] += rotateMultiply(intermediateAnswer, delta1)[i][j];
  			}
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
 	for(int i=0; i<synapticWeights1.length; i++) {
 		for(int j=0; j<synapticWeights1.length; j++) {
 	 		System.out.println(i);
 	 		System.out.println();
 		}
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
 }

 }
