package sort;

public class Qsort {

	public static void quicksort(int []a)
	{
		quicksort(a,0,a.length-1);
	}

	private static void quicksort(int[] a, int left, int right) {
		// TODO Auto-generated method stub
		if(left<right){
		int pivot=median3(a,left,right);
		int i=left,j=right-1;
		for(;;)
		{
			while(a[++i]<pivot)
			{}
			while(a[--j]>pivot)
			{}
			if(i<j)
			{
				swapReferences(a,i,j);
			}else 
				break;
		}
		swapReferences(a, i, right-1);
		quicksort(a,left,i-1);
		quicksort(a,i+1,right);
		}else;
	}

	private static void swapReferences(int[] a, int i, int j) {
		// TODO Auto-generated method stub
		int temp;
		temp=a[i];
		a[i]=a[j];
		a[j]=temp;
	}

	private static int median3(int[] a, int left, int right) {
		// TODO Auto-generated method stub
		int center=(left+right)/2;
		if(a[center]<a[left])
		{
			swapReferences(a, center, left);
		}
		if(a[right]<a[left])
		{
			swapReferences(a, left, right);
		}
		if(a[right]<a[center])
		{
			swapReferences(a, center, right);
		}
		swapReferences(a,center,right-1);
		return a[right-1];
	}
}
