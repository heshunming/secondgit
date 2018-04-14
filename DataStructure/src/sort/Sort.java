package sort;

public class Sort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int []arr={1,4,55,32,19,8,17};
		for(int a:arr)
		{
			System.out.print(a+" ");
		}
		System.out.println();
	//	insertionSort(arr);
		//shellsort(arr);
		//HeapSort.heapsort(arr);
		//MergeSort.mergeSort(arr);
		Qsort.quicksort(arr);
		for(int a:arr)
		{
			System.out.print(a+" ");
		}
	}
	public static void insertionSort(int [] a)
	{
		int j;
		for(int p=1;p<a.length;p++)
		{
			int temp=a[p];
			
			for(j=p;j>0&&temp<a[j-1];j--)
			{
				if(temp<a[j-1])
				{
					a[j]=a[j-1];
				}
			}
			a[j]=temp;
		}
	}
	public static void shellsort(int[] arr)
{
		int j;
		for(int gap=arr.length/2;gap>0;gap/=2)
		{
			for(int i=gap;i<arr.length;i++)
			{
				int temp=arr[i];
				for(j=i;j>=gap&&temp<arr[j-gap];j-=gap)
				{
					arr[j]=arr[j-gap];
				}
				arr[j]=temp;
			}
		}
	}
}
