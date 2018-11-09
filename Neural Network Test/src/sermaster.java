import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class sermaster
{
	public static void main(String[] args)
	{
		int[][] arr = {{0,2,3},{1,0,2}};
		ser(arr, "file.txt");
		int[][] a = deser("file.txt");
		for(int x = 0; x<a.length; x++)
		{
			for(int y = 0; y<a[0].length; y++)
			{
				System.out.print(a[x][y] + "  ");
			}
			System.out.println("");
		}
	}

	public static void ser(int[][] a, String filename)
	{
		serializationtest object = new serializationtest(a);
		try
        {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);
            out.close();
            file.close();
            System.out.println("Successfully serialized " + filename);
        }
        catch(IOException ex)
        {
            System.out.println("IOException");
        }
	}

	public static int[][] deser(String filename)
	{
		serializationtest object1 = null;
        try
        {   
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            object1 = (serializationtest)in.readObject();

            in.close();
            file.close();
            return object1.getdata();
        }

        catch(IOException ex)
        {
            return null;
        }

        catch(ClassNotFoundException ex)
        {
            return null;
        }
	}
}