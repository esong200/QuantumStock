import java.util.ArrayList;


public class DataWritingWeekly extends AlphaVantageCollectorWeekly /*extends ShortTimePerAlphaVantageCollector*/ {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String pdli = "PDLI";
//		ArrayList<double[]> ans = answerCompile(pdli);
//		String filePath = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\" + pdli + "Ans.csv";
//		writeCsv(ans, filePath);
//		ArrayList<double[]> ansr = answerCompile("ANDV");
//		String filePaths = "C:\\Users\\Tim Huang\\Documents\\GitHub\\highlighter\\Neural Network Test\\ANDVAns.csv";
//		writeCsv(ansr,filePaths);
		String[] comps = //{"AAPL","ANDV","ATVI", "BA", "COKE", "CTL","CVS","DE", "EA", "FTI",
				//"MSFT", "COKE", "WMT", "CVS", "WBA", "JNJ", "NKE", "ADS", "UAA","TSLA", "AMZN", "GOOG", "FB"};
				//"MSFT", "CVS", "WBA", "JNJ", "NKE", "ADS", "UAA", "SBUX"};
			//{"MSFT", "COKE", "WMT", "CVS", "WBA", "JNJ", "NKE", "ADS", "UAA"};
			{"AAPL"};
			//{"LMT", "BA", "TSLA", "AMZN", "GOOG", "FB"};
		for(String comp:comps) {
			try {
				String filePathA = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/UltraBigWeeklyChunk/" + comp + "Ans.csv";
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
				String newFile = "/Users/ethansong/Documents/GitHub/highlighter/Neural Network Test/UltraBigWeeklyChunk/" + comp + "DataAdjst.csv";

				while(ANDVAns.size()>adjst.size()) {
					ANDVAns.remove(ANDVAns.size()-1);
				}
				writeCsv(adjst, newFile);
				//writeCsv(ANDVDat, filePathD);
				writeCsv(ANDVAns, filePathA);
				System.out.println(adjst.size());
				System.out.println(adjst.get(0).length);
				System.out.println("Data and Ans write successful");
				try {
					System.out.println("sleeping");
					Thread.sleep(60000);

				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
			} catch (Exception e){
				System.out.println(comp + " stock retrieval failed. Moving on to next one.");
				e.printStackTrace();
				try {
					System.out.println("sleeping");
					Thread.sleep(60000);

				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
			}
		}
			

		//String filePathD = "C:\\Users\\Tim Huang\\eclipse\\StockProofReading\\" + comp + "Data.csv";
			
		}
	}