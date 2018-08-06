
public class functions extends DataReader {
	public static double[] dotMultiply(double[] a, double[][] b){
		double[]returnArr = new double[b.length];
		for(int i = 0; i< returnArr.length; i++) {
			returnArr[i]= 0;
		}
		for(int i = 0; i<b.length; i++) {
			for(int j=0; j<a.length-1; j++) {
				returnArr[i] += b[i][j] * a[j];
			}
		}
		return returnArr;
	}
	public static double[][] dotMultiply(double[][] a, double[][] b){
		double[][]returnArr = new double[a.length][b[0].length];
		for (int i = 0; i < a.length; i++) {
            for (int k = 0; k < a[0].length; k++) {
                for (int j = 0; j < b[0].length; j++) {
                    returnArr[i][j] += a[i][k] * b[k][j];
                	}
            }
		}
		return returnArr;
	}
	public static double sigmoid(double x, boolean deriv) {
		if(deriv==false) {
			return 1/(1+Math.pow(Math.E,(-x)));
		}else
			return 1/(1+Math.pow(Math.E,(-x))) * (1-(1/(1+Math.pow(Math.E,(-x)))));
	}

	public static double[] sigmoid(double[]x, boolean deriv) {
		double[] Return = new double[x.length];
		for(int i = 0; i< x.length; i++) {
			Return[i] = sigmoid(x[i], deriv);
		}
		return Return;
	}

	public static double[][] sigmoid(double[][]x, boolean deriv){
		double[][] Return = new double[x.length][x[0].length];
		for(int i = 0; i<x.length; i++) {
			Return[i]= sigmoid(x[i], deriv);
		}
		return Return;
	}

	public static double[] rotateMultiply(double[][]b, double[]a){
		double[]returnArr = new double[a.length];
		double [][] rotatedArr = new double[b[0].length][b.length];
		for(int i= 0; i<b.length; i++) {
			for(int j = 0; j<b[i].length; j++) {
				rotatedArr[j][i] = b[i][j];
			}
		}
		returnArr = dotMultiply(a,rotatedArr);

		return returnArr;
	}
	public static double[][] rotateMultiply(double[]noRotate, double[]rotate){
		double returnArr[][] = new double[rotate.length][noRotate.length];
		for(int i=0; i<noRotate.length; i++) {
			for(int j=0; j<rotate.length; j++) {
				returnArr[j][i]=noRotate[i]*rotate[j];
			}
		}
		return returnArr;
	}
	public static double[][] rotateMultiply(double[][]a, double[][]b){
		double[][] returnArr = new double[a.length][b.length];
		double [][] rotatedArr = new double[b[0].length][b.length];
		for(int i= 0; i<b.length; i++) {
			for(int j = 0; j<b[i].length; j++) {
				rotatedArr[j][i] = b[i][j];
			}
		}
		returnArr = dotMultiply(rotatedArr , a);
		return returnArr;
	}

}
