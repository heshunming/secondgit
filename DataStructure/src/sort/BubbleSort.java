package sort;

import java.util.Scanner;

public class BubbleSort {

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
		BubbleSOrt(L);
		for(int value:num)
		{
			System.out.print(value+" ");
		}
	}
	public static void BubbleSOrt(SQList L)
	{
		int i,j;
		for(i=0;i<L.length;i++)
		{
			for(j=L.length-1;j>=i;j--)
			{
				if(L.array[j+1]<L.array[j])
				{
					L.swap(L, j, j+1);
				}
			}
		}
	}

}
