package sort;

public class SQList {
	  int length=10;
	 int[] array=new int[10+1];
	 public void swap(SQList L,int i,int j)
	 {
		 int temp=L.array[i];
		 L.array[i]=L.array[j];
		 L.array[j]=temp;
	 }
	
}
