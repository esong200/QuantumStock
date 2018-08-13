import java.util.ArrayList;

public class StackRemoving {

	public static void main(String[] args){
		ArrayList<double[]> test1 = new ArrayList<double[]>();
		ArrayList<double[]> test2 = new ArrayList<double[]>();
		double[] dummy = {10,11,12};
		for(int i = 0; i< 130; i++) {
			test1.add(dummy);
			test2.add(dummy);
		}
		ArrayList<double[]> sample1 = new ArrayList<double[]>();
		ArrayList<double[]> sample2 = new ArrayList<double[]>();
		for(double[] x: test1) {
			sample1.add(x);
		}
		for(double[] x: test2) {
			sample2.add(x);
		}
		for(int i = 0; i<10000000; i++) {
			if(sample1.size() == 0) {
				for(double[] x: test1) {
					sample1.add(x);
				}
				for(double[] x: test2) {
					sample2.add(x);
				}
			}
			int rand = (int) (sample1.size()*Math.random());
			double[] data = sample1.remove(rand);
			double[] ans = sample2.remove(rand);
			System.out.println("sample1 size: " +sample1.size());
			System.out.println("sample2 size: " +sample2.size());
			System.out.println("test1 size: " +test1.size());
			System.out.println("test2 size: " +test2.size());
		}

	}

}
