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
		String[] comps = {  "ARLP", "ATVI", "CTL", "DE", "EA", "FTI", "HAS", "IVZ", "KSS", "MO", "NVDA", "PDLI", "PEP", "STT", "T", "WYNN"};

		for(String comp:comps) {


		//String filePathD = "C:\\Users\\Tim Huang\\eclipse\\StockProofReading\\" + comp + "Data.csv";
			String filePathA = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Data\\" + comp + "Ans.csv";
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
			String newFile = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\Data\\" + comp + "DataAdjst.csv";

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
	}

	}
