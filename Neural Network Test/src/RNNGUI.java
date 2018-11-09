import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RNNGUI extends JFrame {
	
	private JButton runButton;
	private JLabel label;
	private JLabel expectedOutcome;
	private JLabel predictedOutcome;
	
	private static final int FRAME_WIDTH = 700;
	private static final int FRAME_HEIGHT = 500;
	
	private static String comp = "AAPL";
	
	private static String runName = "3months5+";
	private static String dataDirectory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/ShortTimeBigData/"+ comp + "WeeklyDataAdjst.csv";
	private static String ansDirectory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/ShortTimeBigData/"+ comp + "WeeklyScaledAns.csv";
	private static String Synap0Directory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Matrixes/rnn/Synap0/"+ comp + runName+"Synap0.csv";
	private static String Synap1Directory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Matrixes/rnn/Synap1/"+ comp + runName+"Synap0.csv";
	private static String SynaphDirectory = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/Matrixes/rnn/Synaph/"+ comp + runName+"Synap0.csv";
	private static ArrayList<double[]> data = CSVReadWrite.readCsv(dataDirectory);
	private static ArrayList<double[]> ans = CSVReadWrite.readCsv(ansDirectory);
	private static ArrayList<double[]> dataTaylored = data;
	private static int maxSize = data.size();
	private static double alpha = 0.1;
	private static int inputDim = data.get(0).length;
	private static int hiddenDim = 16;
	private static int outputDim = ans.get(0).length;

	private static double[][] synap0 = new double[inputDim][hiddenDim]
			, synap0Up = new double[inputDim][hiddenDim]
			, synap1 = new double[hiddenDim][outputDim]
			, synap1Up = new double[hiddenDim][outputDim]
			, synaph = new double[hiddenDim][hiddenDim]
			, synaphUp = new double[hiddenDim][hiddenDim];
	public void createComponents() {
		runButton = new JButton("Click Me!");
		ActionListener listener = new ClickListener();
		runButton.addActionListener(listener);
		
		label = new JLabel("Hello World!");
		expectedOutcome = new JLabel("Expected Outcome: ");
		predictedOutcome = new JLabel("Predicted Outcome: ");
		
		JPanel panel = new JPanel();
		panel.add(runButton);
		panel.add(label);
		panel.add(expectedOutcome);
		panel.add(predictedOutcome);
		add(panel);		
	}
	
	public RNNGUI() {
		createComponents();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);	
	}
	class ClickListener extends RNNGUI implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//label.setText("Nice!");
			//for 
			while(ans.size()>maxSize){
				ans.remove(ans.size()-1);
			}
			rand(synap0);
			rand(synap1);
			rand(synaph);
			for(int monthsBefore = 3; monthsBefore<7; monthsBefore++) {
				for(int j = 0; j<100000; j++ ) {
					//System.out.println(j);
					runCalc(monthsBefore,j);
					//System.out.println("Done with a calc sequence");
				}
			}
	}
	
	private void runCalc(int monthsBefore, int j) {
		int monthSelect = (int) (Math.random()*(data.size()-monthsBefore));
		//System.out.println("MontSelect is: " + monthSelect);
		ArrayList<double[]> monthData = new ArrayList<double[]>();
		ArrayList<double[]> ansData = new ArrayList<double[]>();
		for(int i = 0; i<monthsBefore; i++) {
			monthData.add(data.get(monthSelect+i));
			ansData.add(ans.get(monthSelect+i));
		}

		double[] output = null;

		ArrayList<double[]> layer_2_deltas = new ArrayList<double[]>();
		ArrayList<double[]> layer_1_values = new ArrayList<double[]>();
		layer_1_values.add(new double[hiddenDim]);

		for(int i = 0; i<monthData.size(); i++) {
			double[] input = monthData.get(monthData.size()-1-i);
			double[] expect = ansData.get(ansData.size()-1-i);

			double[] layer1 = functionsRNN.sigmoid(functionsRNN.add(functions.dotMultiply(input, synap0)
					, functions.dotMultiply(layer_1_values.get(layer_1_values.size()-1), synaph)), false);

			double[] layer2 = functionsRNN.sigmoid(functions.dotMultiply(layer1, synap1), false);
			output = layer2.clone();

			double[] layer2Error = functionsRNN.subtract(expect, layer2);
			layer_2_deltas.add(functionsRNN.multiply(layer2Error,functionsRNN.sigmoid(layer2, true)));

			layer_1_values.add(layer1.clone());

		}

		double[] layer1DeltaFuture = new double[hiddenDim];

		for(int position = 0; position<monthData.size(); position++) {
			double[] input = monthData.get(position).clone();
			double[] layer1 = layer_1_values.get(layer_1_values.size()-position -1);
			double[] layer1Pre = layer_1_values.get(layer_1_values.size()-position-2);

			double[] layer2Delta = layer_2_deltas.get(layer_2_deltas.size()-1-position);

			double[] layer1Delta = functionsRNN.multiply(functionsRNN.add(functions.rotateMultiply(layer1DeltaFuture, synaph)
					, functions.rotateMultiply(layer2Delta, synap1)), functionsRNN.sigmoid(layer1, true));

			double[][] intermed = functionsRNN.add(synap1Up, functionsRNN.rotateMultiply(layer2Delta, layer1));
			synap1Up = intermed.clone();
			intermed = functionsRNN.add(synaphUp,  functions.rotateMultiply(layer1Delta, layer1Pre));
			synaphUp = intermed.clone();
			intermed = functionsRNN.add(functions.rotateMultiply(layer1Delta, input), synap0Up);
			synap0Up = intermed.clone();
		}
		double[][] intermed  = functionsRNN.add(synap0, functionsRNN.multiply(synap0Up,alpha));
		synap0 = intermed.clone();
		intermed = functionsRNN.add(synap1, functionsRNN.multiply(synap1Up, alpha));
		synap1 = intermed.clone();
		intermed = functionsRNN.add(synaph, functionsRNN.multiply(synaphUp, alpha));

		intermed = functionsRNN.multiply(synap1Up, 0);
		synap1Up = intermed.clone();
		intermed = functionsRNN.multiply(synaphUp, 0);
		synaphUp = intermed.clone();
		intermed = functionsRNN.multiply(synap0Up, 0);
		synap0Up = intermed.clone();
		if(j%1000==0) {
			System.out.println("Trail: " + j);
			System.out.print("Expected Outcome: [");
			for(double i: ansData.get(0)) {
				DecimalFormat df = new DecimalFormat("#.#");
				System.out.print(df.format(i)+", ");
			}
			System.out.println("]");

			System.out.print("Predictd Outcome: [");
			for(double i: output) {
				DecimalFormat df = new DecimalFormat("#.#");
				System.out.print(df.format(i)+", ");
			}
			System.out.println("]");
			System.out.println("----------------------");
		}
		String fileSynp0 = Synap0Directory;
		String fileSynp1 = Synap1Directory;
		String fileSynph = SynaphDirectory;


		CSVReadWrite.writeCsv(synap0, fileSynp0);
		CSVReadWrite.writeCsv(synap1, fileSynp1);
		CSVReadWrite.writeCsv(synaph, fileSynph);
		//System.out.println("Done with " + comp + " using " + monthsBefore + " weeks");
		//System.out.println(j);
	}
	
	private void rand(double[][] rand) {
		for (int i = 0; i< rand.length; i++) {
			for(int j = 0 ;j <rand[0].length; j++) {
				rand[i][j] = Math.random()*2 -1;
			}
		}
	}
	}
}