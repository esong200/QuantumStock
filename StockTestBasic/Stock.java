public class Stock {
	private double a, b, c, rating;
	public Stock(double a, double b, double c, double rating)
	{
		this.a=a;
		this.b=b;
		this.c=c;
		this.rating=rating;
	}
	public double realrating()
	{
		return rating;
	}
	public double rate(double wa, double wb, double wc)
	{
		return a*wa + b*wb + c*wc;
	}
}
