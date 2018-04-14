package sort;

public class MergeSort {
	private static void merge(int[]a,int[]tmp,int leftPos,int rightPos,int rightEnd)
	{
		int leftEnd=rightPos-1;
		int tmpPos=leftPos;
		int numElements=rightEnd-leftPos+1;
		while(leftPos<=leftEnd&&rightPos<=rightEnd)
		{
			if(a[leftPos]<=a[rightPos])
			{
				tmp[tmpPos++]=a[leftPos++];
			}
			else
			{
				tmp[tmpPos++]=a[rightPos++];
			}
		}
		while(leftPos<=leftEnd)//将左半部分剩余的数组拷贝到临时数组里面来
		{
			tmp[tmpPos++]=a[leftPos++];
		}
		while(rightPos<=rightEnd)//将右半部分的剩余数组拷贝到临时数组里面
		{
			tmp[tmpPos++]=a[rightPos++];
		}
		for(int i=0;i<numElements;i++,rightEnd--)		
		{//将tmp数组拷贝到a数组中去
			a[rightEnd]=tmp[rightEnd];
		}
	}
	private static void mergeSort(int []a,int[] tmpArray,int left,int right)
	{
		if(left<right)
		{
			int center=(left+right)/2;
			mergeSort(a, tmpArray, left, center);
			mergeSort(a,tmpArray,center+1,right);
			merge(a,tmpArray,left,center+1,right);
		}
	}
	public static void mergeSort(int[] arr)
	{
		int[] tmpArray=new int[arr.length];
		mergeSort(arr,tmpArray,0,arr.length-1);
	}
}
