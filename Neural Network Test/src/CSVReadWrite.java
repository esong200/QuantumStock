import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class CSVReadWrite {

	public static void writeCsv(double[][] matrix, String file) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			
			for(double[] doub: matrix) {
				for(double d: doub) {
					fw.append(String.valueOf(d));
					fw.append(",");
				}
				fw.append("\n");
			}

		}
		catch (Exception ex) {
			   ex.printStackTrace();
			  }
		finally {
			try {
				fw.flush();
				fw.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
