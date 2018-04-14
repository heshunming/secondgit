package fileimport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Calculate {
	
	
	public static int calculate(Date data,String flavor_s)
	{
		//String url="F:\\华为精英挑战赛\\初赛文档\\用例示例\\TrainData_2015.1.1_2015.2.19";
		
		String inputfile_url="F:\\华为精英挑战赛\\初赛文档\\用例示例\\input_5flavors_cpu_7days.txt";
		String trainfile_url="F:\\华为精英挑战赛\\初赛文档\\用例示例\\TrainData_2015.1.1_2015.2.19.txt";	
		String outputfile_url="F:\\华为精英挑战赛\\初赛文档\\用例示例\\output_5flavors_cpu_7days.txt";
		ArrayList<String> input_str=FileImport.fileImport(inputfile_url);
		System.out.println(input_str.size());
		int j=0;
		while(j<input_str.size())
		{
			System.out.println(input_str.get(j));
			j++;
		}
		
		
		/*获得各种输入参量*/
		String[] size=input_str.get(0).split(" ");
		int cpu_cores=Integer.valueOf(size[0]);
		int mem_size=Integer.valueOf(size[1]);
		int pan_size=Integer.valueOf(size[2]);
		int flavor_nums=Integer.valueOf(input_str.get(2));		
		String flavor_name[] = new String[flavor_nums];
		String[] temp=new String[3];
		ArrayList<Integer> flavor_cpu=new ArrayList<Integer>();
		HashMap<String ,Integer>flavor_cpu_hm=new HashMap<String,Integer>();//虚拟机名称对应相应的CPU需求
	//	HashMap<String ,Integer>flavor_cpu_hm=new HashMap<String,Integer>();//虚拟机名称对应相应的CPU需求
		int index=3,i=0;
		while(index<3+flavor_nums)
		{
			temp=input_str.get(index).split(" |  ");
			flavor_name[i]=temp[0];
			flavor_cpu.add(Integer.valueOf(temp[1]));
			//flavor_mem[i]=Integer.valueOf(temp[2]);
			flavor_cpu_hm.put(temp[0], Integer.valueOf(temp[1]));
			index++;
			i++;
		}
		
		ArrayList<String>flavor_=new ArrayList<String>();
		for(int j1=3;j1<flavor_nums+3;j1++)
		{
			flavor_.add(input_str.get(j1));
		}
		String name=input_str.get(flavor_nums+4);
		//System.out.println(name);
		String start_strs[]=input_str.get(input_str.size()-2).split(" ");
		String start_str=start_strs[0];
		//System.out.println(start_str);
		String end_strs[]=input_str.get(input_str.size()-1).split(" ");
		String end_str=end_strs[0];
		//System.out.println(end_str);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		java.util.Date start = null,end = null;
		
		/*try {
			 start =sdf.parse(start_str);
			//start=sdf.format(start);
			//System.out.println(start);
			 end  = sdf.parse(end_str);
			System.out.println(sdf.format(start)); 
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}   
		int day_start=start.getDate();
		int day_end=end.getDate();
		System.out.println(" "+day_start+":"+day_end);		*/
		HashMap<String,Integer>num_predict=new HashMap<String,Integer>();
		for(String s:flavor_name)
		{
			 try {
				start =sdf.parse(start_str);
				 end  = sdf.parse(end_str);
			} catch (ParseException e) 
			 {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			num_predict.put(s, predict(trainfile_url,start, end, s));	   
		}
		
		
		
		File file_out=new File(outputfile_url);
		//file.cr
		FileWriter file_write = null;
		BufferedWriter buff_w = null;
		try {
			 file_write=new FileWriter(file_out);	
		
			 buff_w=new BufferedWriter(file_write);
		} catch (IOException e1) {			
			System.out.println("不能建立输出文件");
		}
		int sum=0;
		int minsize=999999999;
		Set predict_set=num_predict.entrySet();
		
		Iterator predict_it=predict_set.iterator();
		//做一遍清除工作，将数量为0的预测虚拟机给清楚掉
		predict_it=predict_set.iterator();
		while(predict_it.hasNext())
		{
			Map.Entry<String, Integer>e=(Entry<String, Integer>) predict_it.next();	
			sum+=e.getValue();
			if(e.getValue()<=0)//如果预测虚拟机数组某个虚拟机数量为0，则删除这个虚拟机
			{
				predict_it.remove();
			}
			
		}	
		
		try {
			buff_w.write(sum+""+'\n');//将预测虚拟机总数写入到输出文件当中去
			buff_w.flush();
		} catch (IOException e1) {				
			e1.printStackTrace();
		}
		predict_it=predict_set.iterator();
		while(predict_it.hasNext())
		{
			Map.Entry<String, Integer>e=(Entry<String, Integer>) predict_it.next();
			System.out.println(e.getKey()+":"+e.getValue());
			try {
				buff_w.write(e.getKey()+" "+e.getValue()+'\n');//将预测虚拟机的型号和对应的数量写入到输出文件当中去
				buff_w.flush();
			} catch (IOException e1) {				
				e1.printStackTrace();
			}
			
			if(e.getValue()<=0)//如果预测虚拟机数组某个虚拟机数量为0，则删除这个虚拟机
			{
				predict_it.remove();
			}
			
		}
		
		LinkedHashMap<String, Integer> flavor_out=new LinkedHashMap<String, Integer>();
		ArrayList<StringBuffer> f_out=new ArrayList<StringBuffer>(16);
		StringBuffer temp_str = new StringBuffer();
		Iterator out_it=flavor_out.entrySet().iterator();
		List<Map.Entry<String,Integer>> list_cpu = new ArrayList<Map.Entry<String,Integer>>(flavor_cpu_hm.entrySet());//虚拟机尺寸按大小升序排列
	    Collections.sort(list_cpu,new Comparator<Map.Entry<String,Integer>>() {
	            //升序排序
	            public int compare(Entry<String, Integer> o1,
	                    Entry<String, Integer> o2) {
	               // return o1.getValue().compareTo(o2.getValue());
	                return o2.getValue().compareTo(o1.getValue());
	            }
	            
	    });	
	    System.out.println("cpu大小排名");
	    for(int ki=0;ki<list_cpu.size();ki++)
	    {
	    	System.out.println(list_cpu.get(ki).getKey()+"  "+list_cpu.get(ki).getValue());
	    }
	    System.out.println();
	    int rv=0;//虚拟机需要的资源量
		int Rh=cpu_cores;//CPU所拥有的
		
		//System.out.println("cpu的最小尺寸："+minsize);
		int H_num=0;//所用的物理服务器
		Iterator it_flavor_cpu=flavor_cpu_hm.entrySet().iterator();
		//	ArrayList<StringBuffer> output_string
		//Iterator predict_it=flavor_cpu_hm.entrySet().iterator();
		if(!num_predict.isEmpty()){
			H_num=1;//如果预测的虚拟机数组不是空，则最少需要一台物理服务器
		}
		
		try {
			buff_w.write('\n');//将预测虚拟机总数写入到输出文件当中去
			buff_w.flush();
		} catch (IOException e1) {				
			e1.printStackTrace();
		}
		while(!num_predict.isEmpty())
		{
			
			for(int k=0;k<list_cpu.size();k++)
			{
				//先放最尺寸的虚拟机，然后放下一个尺寸稍微小一些
				if(num_predict.containsKey(list_cpu.get(k).getKey())&&num_predict.get(list_cpu.get(k).getKey())!=0&&Rh>list_cpu.get(k).getValue())
				{
					Rh-=list_cpu.get(k).getValue();
					num_predict.put(list_cpu.get(k).getKey(), num_predict.get(list_cpu.get(k).getKey())-1);//从预测的需求虚拟机的数量上减1，如flavor1需求数量减1
					
					if(flavor_out.containsKey(list_cpu.get(k).getKey()))//输出数组中对应虚拟机的数量加1
						flavor_out.put(list_cpu.get(k).getKey(), flavor_out.get(list_cpu.get(k).getKey())+1);
					else
						flavor_out.put(list_cpu.get(k).getKey(), 1);
					if(num_predict.containsKey(list_cpu.get(k).getKey())){
						if(num_predict.get(list_cpu.get(k).getKey())<=0)//如果预测虚拟机数组某个虚拟机数量为0，则删除这个虚拟机
						{
							num_predict.remove(list_cpu.get(k).getKey());
						}
					}					
					break;
				}
			}
			
			
			
			//找出当前剩下预测虚拟机中的最小尺寸
			predict_it=predict_set.iterator();
			boolean start_=true;
			while(predict_it.hasNext())
			{
				Map.Entry<String, Integer>e=(Entry<String, Integer>) predict_it.next();//求和，求出所需的虚拟机的总数	
				if(start_)
				{
					minsize=flavor_cpu_hm.get(e.getKey());
					start_=false;
				}
				if(flavor_cpu_hm.get(e.getKey())!=0&&flavor_cpu_hm.get(e.getKey())<minsize)
				{
					minsize=flavor_cpu_hm.get(e.getKey());
				}					
			}
			
			if((Rh<=minsize)||num_predict.isEmpty())//当物理服务器的CPU或者内存得到余量小于最小的虚拟机余量时则新建一个新的虚拟机
			{
				System.out.println("当前的预测cpu中的最小size:"+minsize);
				System.out.print(H_num+" ");				
				try
				{
					
					//buff_w.write(H_num+" ");
					//f_out.get(H_num).append(""+H_num);
					//temp_str.
					//temp_str.append(H_num+" ");
					temp_str.append(H_num+" ");
					f_out.add(temp_str);
					if(flavor_out.isEmpty())
						System.out.println("输出的集合为空");
					else{
						System.out.println("输出的集合的大小为："+flavor_out.size());
						out_it=flavor_out.entrySet().iterator();
					}
					while(out_it.hasNext())
					{
						Entry e=(Entry) out_it.next();
						//buff_w.write(e.getKey()+" "+e.getValue()+" ");
						//f_out.get(H_num-1).append(e.getKey()+" "+e.getValue()+" ");
						f_out.set(H_num-1, f_out.get(H_num-1).append(e.getKey()+" "+e.getValue()+" "));
						buff_w.flush();
						System.out.print(e.getKey()+" "+e.getValue()+" ");
							
					}
				} catch (IOException e1) 
				{
						e1.printStackTrace();
				}				
				flavor_out.clear();
				Rh=cpu_cores;
				temp_str=new StringBuffer();
				H_num++;
					
			}
		}
		try {
			buff_w.write((H_num-1)+"");
			buff_w.write('\n');//将预测虚拟机总数写入到输出文件当中去
			buff_w.flush();
			for(int in=1;in<=f_out.size();in++)
			{
				if(f_out.get(in-1)!=null){
				buff_w.write(f_out.get(in-1)+"");
				buff_w.write('\n');
				}
			}
		} catch (IOException e1) {				
			e1.printStackTrace();
		}finally 
		{
			try 
			{
				buff_w.flush();
				buff_w.close();
			} catch (IOException e) 
			{						
				e.printStackTrace();
			}
		}
		//Iterator f_it_out=f_out.iterator();		
		
		return 0;
		
	}

	/**
	 * @param trainfile_url
	 * @param start
	 * @param end
	 * @param flavor_type
	 * @return start-end时间段的总数
	 */
	public static int predict(String trainfile_url, Date start,Date end,String flavor_type) {
		//将flavor的String的map转换成date类型的map
		TreeMap<Date,Integer>flavor_date=getflavordate(trainfile_url, flavor_type);				
		//以一个星期为长度预测虚拟机flavor的台数	
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		double predict0=0d,predict1=0d,w=1.0d/14.0d;
		while(start.getTime()<=end.getTime())
		{
			predict0=0;
			Date d=new Date(start.getTime()-14*24*3600*1000);	
		//	System.out.print(sdf.format(d));;
			//System.out.println("d: "+d.getTime());
		//	System.out.println("start: "+start.getTime());
			while(d.getTime()<=start.getTime())
			{
				//System.out.println(d);
				Set date_set=flavor_date.entrySet();
				Iterator date_it=date_set.iterator();
				if(date_it.hasNext())
				{
					
					Map.Entry<Date, Integer>e=(Entry<Date, Integer>) date_it.next();
					//System.out.println(e.getKey()+" ...");
				}
				if(flavor_date.get(d)!=null)
				{
					//System.out.println(d+":"+flavor_date.get(d));
					predict0+=w*(double)flavor_date.get(d);
					//flavor_date.put();
				}
				d.setTime(d.getTime()+24*3600*1000);			
			
			}
			//System.out.println(sdf.format(start)+" :"+predict0);
			
			//System.out.println(flavor_date.size());
		    // 指定一个日期 
			//predict0/=7;
			flavor_date.put(start,(int)(predict0+0.65));
			start.setTime(start.getTime()+24*3600*1000);
			predict1+=(int)predict0;
			//System.out.println("预测在 "+sdf.format(start)+"这一天需要的虚拟机的数量为："+predict);
		}
		//System.out.println("七天"+flavor_type+"总的预测数："+(int)predict1);
		//return null;
		return (int) predict1;
	}

	/**************************************************************************/
	public static int predict2(String trainfile_url, Date start,Date end,String flavor_type) {
		//将flavor的String的map转换成date类型的map
		TreeMap<Date,Integer>flavor_date=getflavordate(trainfile_url, flavor_type);				
		//以一个星期为长度预测虚拟机flavor的台数	
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		double predict0=0d,predict1=0d,w=1.0d/7.0d;
		while(start.getTime()<=end.getTime())
		{
			predict0=0;
			Date d=new Date(start.getTime()-7*24*3600*1000);
			ArrayList<Integer> arr=new ArrayList<Integer>();
			double[] drr=new double[20];
			int n=(int) (end.getTime()-start.getTime())/(24*3600*1000);
			System.out.println("n:"+n);
			while(d.getTime()<=start.getTime())
			{				
				//if(flavor_date.get(d)==0||flavor_date.get(d)==null)
				//{
				//	arr.add(0);	
				//}else
				if(flavor_date.get(d)!=null)
				{
					//System.out.println(d+":"+flavor_date.get(d));
					//predict0+=w*(double)flavor_date.get(d);
					//flavor_date.put();
					arr.add(flavor_date.get(d));
				}
				else
					arr.add(0);
				//arr.add(flavor_date.get(d));
				System.out.print(" "+flavor_date.get(d));
				
				d.setTime(d.getTime()+24*3600*1000);	
			}
			System.out.println("arr数组的size："+arr.size());
			for(int i=0;i<arr.size();i++)
			{
				drr[i]=(double)arr.get(i);
			}
			//arr.clear();
			for(int kk=8;kk<=7+n;kk++)
			{
				predict0=gm(drr, kk);
				System.out.println("predict0:"+predict0);
				//flavor_date.put(start,(int)(predict0));
				//start.setTime(start.getTime()+24*3600*1000);
				//System.out.println(+predict0);
				predict1+=predict0;			
			}	
			break;
		}		
		return (int) (predict1);
	}

	/*************************************************************************/
	
	
	/*********************************灰度预测*********************************/
	 public static double gm(double[] fs, int T) {

	        // 预测模型函数
	        int size = fs.length;
	        int tsize = fs.length - 1;
	        double[] arr = fs;// 原始数组
	        double[] arr1 = new double[size];// 经过一次累加数组
	        double sum = 0;
	        for (int i = 0; i < size; i++) {
	            sum += arr[i];
	            arr1[i] = sum;
	        }
	        double[] arr2 = new double[tsize];// arr1的紧邻均值数组
	        for (int i = 0; i < tsize; i++) {
	            arr2[i] = (double) (arr1[i] + arr1[i + 1]) / 2;
	        }
	        /*
	         * 
	         * 下面建立 向量B和YN求解待估参数向量， 即求参数a,b
	         */
	        /*
	         * 下面建立向量B, B是5行2列的矩阵， 相当于一个二维数组。
	         */
	        double[][] B = new double[tsize][2];
	        for (int i = 0; i < tsize; i++) {
	            for (int j = 0; j < 2; j++) {
	                if (j == 1)
	                    B[i][j] = 1;
	                else
	                    B[i][j] = -arr2[i];
	            }

	        }
	        /*
	         * 下面建立向量YN
	         */
	        double[][] YN = new double[tsize][1];
	        for (int i = 0; i < tsize; i++) {
	            for (int j = 0; j < 1; j++) {
	                YN[i][j] = arr[i + 1];
	            }
	        }

	        /*
	         * B的转置矩阵BT,2行5列的矩阵
	         */
	        double[][] BT = new double[2][tsize];
	        for (int i = 0; i < 2; i++) {
	            for (int j = 0; j < tsize; j++) {
	                BT[i][j] = B[j][i];
	            }
	        }
	        /*
	         * 将BT和B的乘积所得到的结果记为数组B2T,则B2T是一个2*2的矩阵
	         */
	        double[][] B2T = new double[2][2];
	        for (int i = 0; i < 2; i++) {// rows of BT

	            {
	                for (int j = 0; j < 2; j++)// cloums of B
	                {
	                    for (int k = 0; k < tsize; k++)// cloums of BT=rows of B
	                    {
	                        B2T[i][j] = B2T[i][j] + BT[i][k] * B[k][j];
	                    }
	                }

	            }
	        }
	        /* 下面求B2T的逆矩阵，设为B_2T，怎么适用于一般的矩阵？ */
	        double[][] B_2T = new double[2][2];
	        B_2T[0][0] = (1 / (B2T[0][0] * B2T[1][1] - B2T[0][1] * B2T[1][0]))
	                * B2T[1][1];
	        B_2T[0][1] = (1 / (B2T[0][0] * B2T[1][1] - B2T[0][1] * B2T[1][0]))
	                * (-B2T[0][1]);
	        B_2T[1][0] = (1 / (B2T[0][0] * B2T[1][1] - B2T[0][1] * B2T[1][0]))
	                * (-B2T[1][0]);
	        B_2T[1][1] = (1 / (B2T[0][0] * B2T[1][1] - B2T[0][1] * B2T[1][0]))
	                * B2T[0][0];
	        /*
	         * 根据以上所求的各已知量下面求待估参数的未知量a和b，待估向量矩阵等于B_2T*BT*YN
	         * 下面我们分别求这些矩阵的乘积，首先求B_2T*BT，令B_2T*BT的乘积为A矩阵，则A就是一个2*5的矩阵
	         */
	        /*
	         * 
	         * 
	         * 
	         * 下面先求A矩阵
	         */
	        double[][] A = new double[2][tsize];
	        for (int i = 0; i < 2; i++) {// rows of B_2T
	            {
	                for (int j = 0; j < tsize; j++)// cloums of BT
	                {
	                    for (int k = 0; k < 2; k++)// cloums of B_2T=rows of BT
	                    {
	                        A[i][j] = A[i][j] + B_2T[i][k] * BT[k][j];
	                    }
	                }

	            }
	        }
	        /*
	         * 
	         * 
	         * 下面求A和YN矩阵的乘积，令乘积为C矩阵，则C就是一个2*1的矩阵
	         */
	        double[][] C = new double[2][1];
	        for (int i = 0; i < 2; i++) {// rows of A

	            {
	                for (int j = 0; j < 1; j++)// cloums of YN
	                {
	                    for (int k = 0; k < tsize; k++)// cloums of A=rows of YN
	                    {
	                        C[i][j] = C[i][j] + A[i][k] * YN[k][j];
	                    }
	                }

	            }
	        }
	        /* 根据以上所得则a=C[0][0],b=C[1][0]; */
	        double a = C[0][0], b = C[1][0];
	        int i = T;// 读取一个数值
	        double Y = (arr[0] - b / a) * Math.exp(-a * (i + 1)) - (arr[0] - b / a)
	                * Math.exp(-a * i);

	        return Y;
	    }
	/************************************************************************/
	
	
	
	
	
	
	/**
	 * @param flavor_url 虚拟机训练数据的地址
	 * @param flavor虚拟的种类
	 * @return flavor_date 某种虚拟机的时间和数量
	 */
	public static TreeMap<Date,Integer> getflavordate(String flavor_url,String flavor) {	
		HashMap<String,Integer>flavor_str=new HashMap<String,Integer>();
		flavor_str=GetData.getData(flavor_url, flavor);
		TreeMap<Date,Integer>flavor_date=new TreeMap<Date,Integer>();
		
		Set set_flavor=flavor_str.entrySet();
		Iterator<?> f_it=set_flavor.iterator();
		while(f_it.hasNext())
		{
			Map.Entry<String, Integer> e= (Entry<String, Integer>) f_it.next();
			String date_str=e.getKey();
			Integer number=e.getValue();
			SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd"); 
			try
			{
				flavor_date.put(sdf2.parse(date_str), number);
				//System.out.println(sdf2.parse(date_str)+":"+number);
			} catch (ParseException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return flavor_date;
	}
}
