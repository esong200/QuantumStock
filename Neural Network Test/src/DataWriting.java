import java.util.ArrayList;


public class DataWriting extends AlphaVantageCollector {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String pdli = "PDLI";
//		ArrayList<double[]> ans = answerCompile(pdli);
//		String filePath = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\" + pdli + "Ans.csv";
//		writeCsv(ans, filePath);
//		ArrayList<double[]> ansr = answerCompile("ANDV");
//		String filePaths = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\ANDVAns.csv";
//		writeCsv(ansr,filePaths);
		//monthlyData("AAPL");
		String[] comps = { "AAPL","ADS","ANDV", "ARLP", "ATVI", "CTL", "DE", "EA", "FTI", "HAS", "IVZ", "KSS", "MO", "NVDA", "PDLI", "PEP", "STT", "T", "WYNN"};

		for(String comp:comps) {
			//for new comps
			//writeData(comp);
			//For first of month's to gather last month data
			//addData(comp);
			//For data gathering to decrease time wasted
			sampleData(comp);
		}

	}

	private static void writeData(String comp) {
		String filePathA = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\ShortTimeBigData\\" + comp + "Ans.csv";
		ArrayList<double[]> ANDVDat = DataCollection(comp);
		try {
			System.out.println("sleeping");
			Thread.sleep(60000);

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		ArrayList<double[]> ANDVAns = answerCompile(comp);
		ArrayList<double[]> adjst = new ArrayList<double[]>();
		for(int j = 0; j<ANDVDat.size()-1; j++) {
			double[] current = ANDVDat.get(j);
			double[] previous = ANDVDat.get(j+1);
			double[] perc = new double[current.length];
			for(int k = 0; k<current.length; k++) {
				perc[k] = (current[k]-previous[k])/previous[k];
				if(previous[k] == 0) {
					perc[k] = 0;
				}
			}
			adjst.add(perc);
		}
		String newFile = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\ShortTimeBigData\\" + comp + "BigDataAdjst.csv";

		while(ANDVAns.size()>adjst.size()) {
			ANDVAns.remove(ANDVAns.size()-1);
		}
		writeCsv(adjst, newFile);
		//writeCsv(ANDVDat, filePathD);
		writeCsv(ANDVAns, filePathA);
		try {
			System.out.println("sleeping");
			Thread.sleep(60000);

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	private static void addData(String comp) {
		String dataPath = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\ShortTimeBigData\\" + comp + "BigDataAdjst.csv";
		String ansPath = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\ShortTimeBigData\\" + comp + "Ans.csv";

		ArrayList<double[]> data = readCsv(dataPath);
		ArrayList<double[]> ans = readCsv(ansPath);

		double[] addData = DataAdd(comp);
		double[] addAns = addAns(comp);

		data.add(0, addData);
		ans.add(0, addAns);

		writeCsv(data, dataPath);
		writeCsv(ans, ansPath);
	}

	private static void sampleData(String comp) {
		String dataPath = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\testingData\\" + comp + "newData.csv";
		double[] data = DataAdd(comp);
		writeCsv(data, dataPath);

	}
}
