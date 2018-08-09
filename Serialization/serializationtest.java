import java.io.Serializable;

public class serializationtest implements java.io.Serializable{
	int[][] a;
	public serializationtest(int [][] a)
	{
		this.a = a;
	}
	public int[][] getdata()
	{
		return a;
	}
	public void printdata()
	{
		for(int x = 0; x<a.length; x++)
		{
			for(int y = 0; y<a[0].length; y++)
			{
				System.out.print(a[x][y] + "  ");
			}
			System.out.println("");
		}
	}

}
