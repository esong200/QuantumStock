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
	public static ArrayList<double[]> readCsv(String file){
		BufferedReader br = null;
		ArrayList<double[]> returnArr = new ArrayList<double[]>();
		try {

			String ln = "";
			br = new BufferedReader(new FileReader(file));
			while((ln = br.readLine())!= null) {
				String[] fields = ln.split(",");
				if(fields.length>0) {
					int length = fields.length;
					double[] data = new double[length];
					for(int i = 0; i<fields.length; i++) {
						data[i] = Double.parseDouble(fields[i]);
					}
					returnArr.add(data);
				}
			}
		}catch (Exception ex) {
			   ex.printStackTrace();
		  } finally {
		   try {
		    br.close();
		   } catch (Exception e) {
		    e.printStackTrace();
		   }
		  }
		return returnArr;
	}

}
