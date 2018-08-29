import java.util.ArrayList;

public class dataRunning {

	public static void main(String[] args) {
		String[] comps = {"AAPL","ADS","ANDV","ARLP","ATVI","BRKS","COKE","CTL","CVS","DE","EA","FTI","HAS","INTC","IVZ","JNJ","KSS","LMT","MO","MSFT","NVDA","NKE","PDLI","PEP","SBUX","STT","T","TTWO","WBA","WMT","WYNN"};
		int months = 3;

		ArrayList<String> list = new ArrayList<String>();

		for(String comp:comps) {
		String possible = comp + ": ";
		double[] data = AlphaVantageCollector.DataAdd(comp);
		double[] test = run(comp, months, data);
		for(int i = 0; i<test.length; i++) {
			if(Math.round(test[i]) == 1) {
				switch(i) {
				case 0:
					possible = possible.concat("<-10");
					break;
				case 1:
					possible = possible.concat("-10 to -5");
					break;
				case 2:
					possible = possible.concat("-5 to -2");
					break;
				case 3:
					possible = possible.concat("-2 to 0");
					break;
				case 4:
					possible = possible.concat("0 to 2");
					break;
				case 5:
					possible = possible.concat("2 to 5");
					break;
				case 6:
					possible = possible.concat("5 to 10");
					break;
				case 7:
					possible = possible.concat("10<");
					break;
				}
				possible = possible.concat(", ");
			}
		}
		possible = possible.substring(0, possible.length()-2);
		list.add(possible);
		String filePath = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Matrixes\\rnn\\Results\\" + months +"monthsResults.csv";
		CSVReadWrite.writeCSv(list, filePath);
		try {
			System.out.println("written" + comp);
			Thread.sleep(60000);

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		}
		String filePath = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Matrixes\\rnn\\Results\\" + months +"monthsResults.csv";
		CSVReadWrite.writeCSv(list, filePath);
	}

	private static double[] run(String comp, int monthsBefore, double[] newData){
		double[][] synap0;
		double[][] synap1;
		double[][] synaph;
		try {
			String fileSynp0 = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Matrixes\\rnn\\Synap0\\" + comp + monthsBefore +"monthsSynap0.csv";
			String fileSynp1 = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Matrixes\\rnn\\Synap1\\" + comp + monthsBefore +"monthsSynap1.csv";
			String fileSynph = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Matrixes\\rnn\\Synaph\\" + comp + monthsBefore +"monthsSynaph.csv";
			synap0 = CSVReadWrite.listToArray(CSVReadWrite.readCsv(fileSynp0));
			synap1 = CSVReadWrite.listToArray(CSVReadWrite.readCsv(fileSynp1));
			synaph = CSVReadWrite.listToArray(CSVReadWrite.readCsv(fileSynph));
		}
		catch(Exception e) {
			try{
				String fileSynp0 = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Matrixes\\rnn\\Synap0\\All" + monthsBefore +"monthsSynap0.csv";
				String fileSynp1 = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Matrixes\\rnn\\Synap1\\All" + monthsBefore +"monthsSynap1.csv";
				String fileSynph = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Matrixes\\rnn\\Synaph\\All" + monthsBefore +"monthsSynaph.csv";
				synap0 = CSVReadWrite.listToArray(CSVReadWrite.readCsv(fileSynp0));
				synap1 = CSVReadWrite.listToArray(CSVReadWrite.readCsv(fileSynp1));
				synaph = CSVReadWrite.listToArray(CSVReadWrite.readCsv(fileSynph));
			}
			catch(Exception f) {
				double[] ne = {0,0,0,0,0,0,0,0};
				return ne;
			}
		}

		ArrayList<double[]> data = CSVReadWrite.readCsv("C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\ShortTimeBigData\\" + comp + "BigDataAdjst.csv");
		data.add(0, newData);
		double[] layer2 = null;

		ArrayList<double[]> layer_1_values = new ArrayList<double[]>();
		layer_1_values.add(new double[synap0[0].length]);
		for(int i = 0; i<monthsBefore; i++) {
			double[] input = data.get(monthsBefore-1-i);

			double[] layer1 = functions.sigmoid(functions.add(functions.dotMultiply(input, synap0)
					, functions.dotMultiply(layer_1_values.get(layer_1_values.size()-1), synaph)), false);

			layer2 = functions.sigmoid(functions.dotMultiply(layer1, synap1), false);

		}
		return layer2;
	}

}
