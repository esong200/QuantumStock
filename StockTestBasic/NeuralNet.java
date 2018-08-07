public class NeuralNet
{
	public static double wa = 0, wb = 0, wc = 0;
	//multipliers of the stock traits
	//Test COmment
	public static void main(String[] args)
	{
		Stock aapl = new Stock(1,2,3, 17.2);
		double tempwa = wa, tempwb = wb, tempwc = wc;
		double estrate = aapl.realrating();
		for(int x = 0; x < 20; x++)
		{
			for(int y = 0; y < 100; y++)
			{
				double a= wa + (2*Math.random() - 1);
				double b= wb + (2*Math.random() - 1);
				double c= wc + (2*Math.random() - 1);
				double testrate = Math.abs(aapl.rate(a,b,c)-aapl.realrating());
				if(testrate < estrate)
				{
					estrate = testrate;
					tempwa = a;
					tempwb = b;
					tempwc = c;
					System.out.println("The error is currently " + estrate);
				}
			}
			wa=tempwa;
			wb=tempwb;
			wc=tempwc;
		}
	}
}
