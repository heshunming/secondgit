package sort;

public  class HeapSort {

	public static int leftChild(int i)
	{
		return 2*i+1;
	}
	public static void percDown(int[] arr,int i,int n)
	{
		int child;
		int tmp;
		for(tmp=arr[i];leftChild(i)<n;i=child)//i=child��i�ĸոս������ӽڵ����ִ�����ǲ���
		{
			child=leftChild(i);
			if(child!=n-1&&arr[child]<arr[child+1])
			{
				child++;
			}
			if(tmp<arr[child])//�������ӽڵ�����ֵ�㵽�������
			{
				arr[i]=arr[child];
			}else
				break;
		}
		arr[i]=tmp; 
	}
	
	public static void heapsort(int arr[])
	{
		for(int i=arr.length/2-1;i>=0;i--)
		{
			percDown(arr,i,arr.length);//�������ѵĹ���
		}
		for(int i=arr.length-1;i>0;i--)
		{
			swapReferences(arr,0,i);//ɾ������Ԫ��Ҳ���Ǹ�����Ԫ�أ��������ȼ�1����һ������
			percDown(arr,0,i);
		}
	}
	private static void swapReferences(int[] arr, int i, int i2) {
		// TODO Auto-generated method stub
		int tmp=arr[i];
		arr[i]=arr[i2];
		arr[i2]=tmp;
	}
}
