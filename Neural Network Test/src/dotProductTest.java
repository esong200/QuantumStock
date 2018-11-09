
public class dotProductTest {
	public static void main(String[] args) {
		
	}
	public static double[][] dotMultiply(double[][] a, double[][] b){
		double[][]returnArr = new double[a.length][b[0].length];
		for (int i = 0; i < a.length; i++) { 
		    for (int j = 0; j < b[0].length; j++) { 
		        for (int k = 0; k < a[0].length; k++) { 
		            returnArr[i][j] += a[i][k] * b[k][j];
		        }
		    }
		}
		return returnArr;
	}	
}
