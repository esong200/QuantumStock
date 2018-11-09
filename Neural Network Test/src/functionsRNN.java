
public class functionsRNN{
	public static double[] dotMultiply(double[] a, double[][] b){
		double[]returnArr = new double[b[0].length];
		for(int i = 0; i<b[0].length; i++) {
			for(int j=0; j<a.length; j++) {
				returnArr[i] += b[j][i] * a[j];
			}
		}
		return returnArr;
	}
	public static double[] dotMultiply(int[] inputs, double[][] b){
		double[]returnArr = new double[b[0].length];
		for(int i = 0; i<b[0].length; i++) {
			for(int j=0; j<inputs.length; j++) {
				returnArr[i] += b[j][i] * inputs[j];
			}
		}
		return returnArr;
	}
	public static double[] dotMultiply(double[][] a, double[] b){
		double[]returnArr = new double[b.length];
		for(int i = 0; i<b.length; i++) {
			for(int j=0; j<a[0].length; j++) {
				returnArr[i] += a[i][j] * b[i];
			}
		}
		return returnArr;
	}
	public static double[][] dotMultiplyFastest(double[][] a, double[][] b){
		double[][]returnArr = new double[a.length][b[0].length];
		/*for (int i = 0; i < a.length; i++) {
            for (int k = 0; k < a[0].length; k++) {
                for (int j = 0; j < b[0].length; j++) {
                    returnArr[i][j] += a[i][k] * b[k][j];
                	}
            }
		}*/
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
		return returnArr;
	}
	/*public static double[][] dotMultiply(double[][] a, double[][]b){
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
	*/

	public static double sigmoid(double x, boolean deriv) {
		if(deriv==false) {
			return 1/(1+Math.pow(Math.E,(-x)));
		}else
			return x*(1-x);
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
				//returnArr[i] += b[i][j] * a[j];
			}
		}
		returnArr = dotMultiply(a,rotatedArr);
		return returnArr;
	}
	public static double[] rotateMultiply(double[]a, double[][]b){
		double[]returnArr = new double[a.length];
		double [][] rotatedArr = new double[b[0].length][b.length];
		for(int i= 0; i<b.length; i++) {
			for(int j = 0; j<b[i].length; j++) {
				rotatedArr[j][i] = b[i][j];
				//returnArr[i] += b[i][j] * a[j];
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
	public static double[] add(double[]noRotate, double[]rotate){
		double returnArr[] = new double[rotate.length];
		for(int i =0; i < noRotate.length; i++) {
			returnArr[i] = noRotate[i] + rotate[i];
		}
		return returnArr;
	}

	public static double[][] add(double[][]noRotate, double[][]rotate){
		double returnArr[][] = new double[rotate.length][rotate[0].length];
		for(int i =0; i < noRotate.length; i++) {
			for(int j = 0; j< noRotate[0].length; j++) {
				returnArr[i][j] = noRotate[i][j] + rotate[i][j];
			}
		}
		return returnArr;
	}

	public static double[] subtract(double[]noRotate, double[]rotate){
		double returnArr[] = new double[rotate.length];
		for(int i =0; i < noRotate.length; i++) {
			returnArr[i] = noRotate[i] - rotate[i];
		}
		return returnArr;
	}

	public static double[] multiply(double[]noRotate, double[]rotate){
		double returnArr[] = new double[rotate.length];
		for(int i =0; i < noRotate.length; i++) {
			returnArr[i] = noRotate[i]*rotate[i];
		}
		return returnArr;
	}

	public static double[][] multiply(double[][]noRotate, double x){
		double returnArr[][] = noRotate.clone();
		for(int i = 0; i<returnArr.length; i++) {
			for(int j = 0; j<returnArr[0].length; j++) {
				returnArr[i][j] = noRotate[i][j]*x;
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
		returnArr = dotMultiplyFastest(rotatedArr , a);
		return returnArr;
	}
	public static double averageArrayValue(double[]a) {
		double returnValue = 0;
		for(double i: a) {
			returnValue += i;
		}
		return returnValue/a.length;
	}


}

