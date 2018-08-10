import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class CSVReadWrite {

	public static void writeCsv(ArrayList<double[]> matrix, String file) {
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
	public static void writeCsv(double[] matrix, String file) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);


				for(double d: matrix) {
					fw.append(String.valueOf(d));
					fw.append(",");
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
	public static String combine(String file1, String file2, String finalName) {
		ArrayList<double[]> first = readCsv(file1);
		ArrayList<double[]> second = readCsv(file2);
		ArrayList<double[]> combine = new ArrayList<double[]>();;
		first.forEach(arr ->{
			combine.add(arr);
		});
		second.forEach(arr ->{
			combine.add(arr);
		});
		writeCsv(combine, finalName);
		return finalName;
	}
}
