
public class functionsTest {
	public static void main(String[] args) {
		double[][] arr1 = new double[1000][900];
		double[][] arr2 = new double[900][1000];
		double[][] arr3 = new double[1000][1000];
		double[][] arr4 = new double[1000][1000];
		double[][] newArr =new double[arr1.length][arr2[0].length];
		double[][] newArr1 = new double[arr3.length][arr3[0].length];
		for(int i=0; i<arr1.length; i++) {
			for(int j=0; j<arr1[0].length; j++) {
				arr1[i][j] = (2*Math.random()) -1;
			}
		}
		for(int i=0; i<arr2.length; i++) {
			for(int j=0; j<arr2[0].length; j++) {
				arr2[i][j] = (2*Math.random()) -1;
			}
		}
		newArr=dotMultiplyFastest(arr1, arr2);
		newArr1 = regularMultiply(arr3, arr4);
		
		/*for(int i=0; i<newArr.length; i++) {
			for(int j=0; j<newArr[0].length; j++) {
				System.out.println(newArr[i][j]);
			}
		}
		*/
	}
	public static double[][] dotMultiplyFastest(double[][] a, double[][] b){
		double[][]returnArr = new double[a.length][b[0].length];
		long start = System.currentTimeMillis(); 
		for (int i = 0; i < a.length; i++) {
            double[] arowi = a[i];
            double[] crowi = returnArr[i];
            for (int k = 0; k < a[0].length; k++) {
                double[] browk = b[k];
                double aik = arowi[k];
                for (int j = 0; j < b[0].length; j++) {
                    crowi[j] += aik * browk[j];
                }
            }
        }
		long stop = System.currentTimeMillis();
		double elapsed = (stop - start) / 1000.0;
		System.out.println(start);
		System.out.println(stop);
		System.out.println("Time: " + elapsed + " seconds");
		return returnArr;
	}
	public static double[][] dotMultiplyFaster(double[][] a, double[][]b){
		long start = System.currentTimeMillis(); 
		double[][]returnArr = new double[a.length][b[0].length];
		for (int i = 0; i < a.length; i++) {
            for (int k = 0; k < a[0].length; k++) {
                for (int j = 0; j < b[0].length; j++) {
                    returnArr[i][j] += a[i][k] * b[k][j];
                	}
            }
		}
		long stop = System.currentTimeMillis();
		double elapsed = (stop - start) / 1000.0;
		System.out.println(start);
		System.out.println(stop);
		System.out.println("Time: " + elapsed + " seconds");
		return returnArr;
	}
	public static double[][] regularMultiply(double[][] a, double[][] b){
		long start = System.currentTimeMillis(); 
		double[][]returnArr = new double[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[0].length; j++) {
                    returnArr[i][j] = a[i][j] * b[i][j];
                	}
		}
		long stop = System.currentTimeMillis();
		double elapsed = (stop - start) / 1000.0;
		System.out.println(start);
		System.out.println(stop);
		System.out.println("Time: " + elapsed + " seconds");
		return returnArr;
	}
}
