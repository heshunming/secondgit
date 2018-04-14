package sort;

import java.util.Scanner;

public class QuickSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner cin=new Scanner(System.in);
		String str=cin.nextLine();
		String[] nums=str.split(" ");
		int num[]=new int[nums.length];
		for(int i=0;i<nums.length;i++)
		{
			num[i]=Integer.valueOf(nums[i]);
			//System.out.print(num[i]+" ");
		}
		SQList L=new SQList();
		L.array=num;
		L.length=num.length-1;
		//System.out.println(""+(num.length));
		//num=QSort(num,0,nums.length-1);
		QSort(L,0,L.length);
		for(int value:num)
		{
			System.out.print(value+" ");
		}

	}
	public static void  QSort(SQList L,int low,int high)
	{
		
		int pivot;
		if(low<high)
		{
			pivot=Partition(L,low,high);
			
			QSort(L, low, pivot-1);
			QSort(L,pivot+1,high);
		}
		//return arr;
		
	}
	public static int Partition(SQList L,int low,int high)
	{
		int pivotkey;
		pivotkey=L.array[low];
		while(low<high)
		{
			while(low<high&&L.array[high]>=pivotkey)
			{
				high--;
			}
			L.swap(L, low, high);
			while(low<high&&L.array[low]<=pivotkey)
			{
				low++;
			}
			L.swap(L, low, high);
		}
		
		return low;
	}	

}
