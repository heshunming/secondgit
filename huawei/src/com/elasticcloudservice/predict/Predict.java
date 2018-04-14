package com.elasticcloudservice.predict;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

//import fileimport.FileImport;
//import fileimport.GetData;

/**
 * @author mingming
 *
 */
/**
 * @author mingming
 *
 */
public class Predict {

	public static String[] predictVm(String[] ecsContent, String[] inputContent) {

		/** =========do your work here========== **/

		String[] results = new String[ecsContent.length];

		List<String> history = new ArrayList<String>();
		ArrayList<String>output=new ArrayList<String>();
		for (int i = 1; i < ecsContent.length; i++) {

			history.add(ecsContent[i]);
//			//System.out.println("history�������");
//			//System.out.println(ecsContent[i]);
//			if (ecsContent[i].contains(" |   ")
//					&& ecsContent[i].split(" |  | ").length >= 3) {
//				
//				
//				String[] array = ecsContent[i].split(" ");
//				String uuid = array[0];
//				String flavorName = array[1];
//				String createTime = array[2];
//
//				history.add(uuid + " " + flavorName + " " + createTime);
//				System.out.println("history�������");
//				System.out.println(uuid + " " + flavorName + " " + createTime);
	//		}
		}
		
		/****************************�����ļ������Լ������Ԥ��***********************************/		
		ArrayList<String> input_str=new ArrayList<String>();
		for(String s:inputContent)
		{
			input_str.add(s);
		}
		System.out.println(input_str.size());
		int j=0;
		while(j<input_str.size())
		{
			System.out.println(input_str.get(j));
			j++;
		}		
		/*��ø����������*/
		String[] size=input_str.get(0).split(" ");
		int cpu_cores=Integer.valueOf(size[0]);
		//System.out.println("cpu_cores"+cpu_cores);
		int mem_size=Integer.valueOf(size[1])*1024;
		int pan_size=Integer.valueOf(size[2]);
		int flavor_nums=Integer.valueOf(input_str.get(2));		
		String flavor_name[] = new String[flavor_nums];
		String[] temp=new String[3];
		ArrayList<Integer> flavor_cpu=new ArrayList<Integer>();
		HashMap<String ,Integer>flavor_cpu_hm=new HashMap<String,Integer>();//��������ƶ�Ӧ��Ӧ��CPU����
		HashMap<String ,Integer>flavor_mem_hm=new HashMap<String,Integer>();//��������ƶ�Ӧ��Ӧ��CPU����
		HashMap<String ,Integer>flavor_size_hm = new HashMap<String,Integer>();//��������ƶ�Ӧ��Ӧ��CPU����
		int index=3,i0=0;
		while(index<3+flavor_nums)
		{
			temp=input_str.get(index).split(" |  ");
			flavor_name[i0]=temp[0];		
			flavor_cpu_hm.put(temp[0],Integer.valueOf(temp[1]));//��������-cpu����
			flavor_mem_hm.put(temp[0],Integer.valueOf(temp[2]));//��������-�ڴ���
			index++;
			i0++;
		}
		String name=input_str.get(flavor_nums+4);//��ҪԤ�����������
		//System.out.println(name);
		String start_strs[]=input_str.get(input_str.size()-2).split(" ");
		String start_str=start_strs[0];
		//System.out.println(start_str);
		String end_strs[]=input_str.get(input_str.size()-1).split(" ");
		String end_str=end_strs[0];
		//System.out.println(end_str);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		java.util.Date start = null,end = null;
		
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
			num_predict.put(s, predict((ArrayList)history,start, end, s));	   
		}		
		/****************************�����ļ�����***********************************/
		
		
		
		/**************************************************************/
		int sum=0;//����Ԥ��������ĺ�
		int minsize=Integer.MAX_VALUE;//Ԥ��������е���Сsize		
		
		Iterator predict_it;
		predict_it=num_predict.entrySet().iterator();	
		while(predict_it.hasNext())
		{
			Map.Entry<String, Integer>e=(Entry<String, Integer>) predict_it.next();	
			System.out.println(e.getKey()+":"+e.getValue());
			sum+=e.getValue();	
		}	
		predict_it=num_predict.entrySet().iterator();	
		output.add(sum+"");
		while(predict_it.hasNext())
		{
			Map.Entry<String, Integer>e=(Entry<String, Integer>) predict_it.next();		
			output.add(e.getKey()+" "+e.getValue());
			if(e.getValue()<=0)//���Ԥ�����������ĳ�����������Ϊ0����ɾ����������
			{
				predict_it.remove();
			}
		}
		
		
		
		//		
		if(name.equals("CPU"))
		{
			flavor_size_hm=flavor_cpu_hm;
		}else if(name.equals("MEM"))
		{
			flavor_size_hm=flavor_mem_hm;
		}
		LinkedHashMap<String, Integer> flavor_out=new LinkedHashMap<String, Integer>();
		ArrayList<StringBuffer> f_out=new ArrayList<StringBuffer>(16);
		StringBuffer temp_str = new StringBuffer();
		Iterator out_it=flavor_out.entrySet().iterator();
		List<Map.Entry<String,Integer>> list_cpu = new ArrayList<Map.Entry<String,Integer>>(flavor_size_hm.entrySet());//������ߴ簴��С��������
	    Collections.sort(list_cpu,new Comparator<Map.Entry<String,Integer>>() {
	            //��������
	            public int compare(Entry<String, Integer> o1,
	                    Entry<String, Integer> o2) {
	               // return o1.getValue().compareTo(o2.getValue());
	                return o2.getValue().compareTo(o1.getValue());
	            }
	            
	    });	
	    System.out.println("size��С����");
	    for(int ki=0;ki<list_cpu.size();ki++)
	    {
	    	System.out.println(list_cpu.get(ki).getKey()+"  "+list_cpu.get(ki).getValue());
	    }
	    System.out.println();
	    int rv=0;//�������Ҫ����Դ��
		int Rh = 0;//CPU��ӵ�е�
		
		if(name.equals("CPU"))
		{
			 Rh=cpu_cores;
		}else if(name.equals("MEM"))
		{
			Rh=mem_size;
		}
		//System.out.println("cpu����С�ߴ磺"+minsize);
		int H_num=0;//���õ����������
		Iterator it_flavor_cpu=flavor_cpu_hm.entrySet().iterator();
		//	ArrayList<StringBuffer> output_string
		//Iterator predict_it=flavor_cpu_hm.entrySet().iterator();
		if(!num_predict.isEmpty()){
			H_num=1;//���Ԥ�����������鲻�ǿգ���������Ҫһ̨���������
		}
		
		while(!num_predict.isEmpty())
		{
			
			for(int k=0;k<list_cpu.size();k++)
			{
				//�ȷ����ߴ���������Ȼ�����һ���ߴ���΢СһЩ
				if(num_predict.containsKey(list_cpu.get(k).getKey())&&num_predict.get(list_cpu.get(k).getKey())!=0&&Rh>list_cpu.get(k).getValue())
				{
					Rh-=list_cpu.get(k).getValue();
					num_predict.put(list_cpu.get(k).getKey(), num_predict.get(list_cpu.get(k).getKey())-1);//��Ԥ�������������������ϼ�1����flavor1����������1
					
					if(flavor_out.containsKey(list_cpu.get(k).getKey()))//��������ж�Ӧ�������������1
						flavor_out.put(list_cpu.get(k).getKey(), flavor_out.get(list_cpu.get(k).getKey())+1);
					else
						flavor_out.put(list_cpu.get(k).getKey(), 1);
					if(num_predict.containsKey(list_cpu.get(k).getKey())){
						if(num_predict.get(list_cpu.get(k).getKey())<=0)//���Ԥ�����������ĳ�����������Ϊ0����ɾ����������
						{
							num_predict.remove(list_cpu.get(k).getKey());
						}
					}					
					break;
				}
			}
			
			
			//�ҳ���ǰʣ��Ԥ��������е���С�ߴ�
			predict_it=num_predict.entrySet().iterator();
			boolean start_=true;
			while(predict_it.hasNext())
			{
				Map.Entry<String, Integer>e=(Entry<String, Integer>) predict_it.next();//��ͣ��������������������	
				if(start_)
				{
					minsize=flavor_size_hm.get(e.getKey());
					start_=false;
				}
				if(flavor_size_hm.get(e.getKey())!=0&&flavor_size_hm.get(e.getKey())<minsize)
				{
					minsize=flavor_size_hm.get(e.getKey());
				}					
			}
			
			if((Rh<=minsize)||num_predict.isEmpty())//�������������CPU�����ڴ�õ�����С����С�����������ʱ���½�һ���µ������
			{
				System.out.println("��ǰ��Ԥ��cpu�е���Сsize:"+minsize);
				System.out.print(H_num+" ");				
				{
					
					//buff_w.write(H_num+" ");
					//f_out.get(H_num).append(""+H_num);
					//temp_str.
					//temp_str.append(H_num+" ");
					temp_str.append(H_num+" ");
					f_out.add(temp_str);
					if(flavor_out.isEmpty())
						System.out.println("����ļ���Ϊ��");
					else{
						System.out.println("����ļ��ϵĴ�СΪ��"+flavor_out.size());
						out_it=flavor_out.entrySet().iterator();
					}
					while(out_it.hasNext())
					{
						Entry e=(Entry) out_it.next();
						//buff_w.write(e.getKey()+" "+e.getValue()+" ");
						//f_out.get(H_num-1).append(e.getKey()+" "+e.getValue()+" ");
						f_out.set(H_num-1, f_out.get(H_num-1).append(e.getKey()+" "+e.getValue()+" "));
						//buff_w.flush();
						System.out.print(e.getKey()+" "+e.getValue()+" ");
							
					}
				}		
				flavor_out.clear();
				if(name.equals("CPU"))
				{
					 Rh=cpu_cores;
				}else if(name.equals("MEM"))
				{
					Rh=mem_size;
				}
				temp_str=new StringBuffer();
				H_num++;
					
			}
					
		}
		
		//buff_w.write((H_num-1)+"");
		 output.add("");
		 output.add((H_num-1)+"");
		//buff_w.write('\n');//��Ԥ�����������д�뵽����ļ�����ȥ
		//buff_w.flush();
		for(int in=1;in<=f_out.size();in++)
		{
			if(f_out.get(in-1)!=null){
			//buff_w.write(f_out.get(in-1)+"");
			output.add(f_out.get(in-1)+"");
			//buff_w.write('\n');
			}
		}
		
		
		
		//
		/*************************************************************/
		for (int i = 1; i < inputContent.length; i++) {

		}

		for (int i = 0; i < output.size(); i++) {
			//results[i] = history.get(i);
			results[i]=output.get(i);
			System.out.println(output.get(i));
		}
		return results;
	}
	
	
	
	/**
	 * @param history  ѵ������ʷ����
	 * @param flavor_name  �����������
	 * @return  flavor_name�������ĳ��ʱ�������
	 */
	public static TreeMap<Date,Integer> getData( ArrayList<String> history,String flavor_name)//���ʱ��-�����������map
	{		
		//�Ӷ������ı��ļ�����ȡʱ���ַ����ݺ��Ǹ�ʱ��������flavor������		
		HashMap<String,String> flavor=new HashMap<String,String>();//ѵ�������������������id+flavor�����
		HashMap<String,String> time=new HashMap<String,String>();//ѵ��������ʱ�䣺id+time
		HashMap<String,Integer>time_flavornum=new HashMap<String,Integer>();//flavor_name�������ĳ��ʱ���Ӧ�ڵ������������
		TreeMap<Date,Integer>flavor_date=new TreeMap<Date,Integer>();//ʱ��-�����������
		String str_temp[] = null;
		String s;
		for(int i=0;i<history.size();i++)
		{		
			s=history.get(i);
			
			//System.out.println("history���������"+history.get(i));
			str_temp=s.split("	| ");
			flavor.put(str_temp[0], str_temp[1]);
			time.put(str_temp[0], str_temp[2]);
			
		}
		Iterator<?> it=flavor.entrySet().iterator();
		Set time_entries=time.entrySet();
		Iterator<?> time_it=time_entries.iterator();
		while(it.hasNext())
		{
			Map.Entry<String, String> e=(Entry<String, String>) it.next();
			String id=e.getKey();
			String flavor_value=e.getValue();
			String time_value=time.get(id);		
			//System.out.println(id+" "+flavor_value);
			if(flavor_value.equals(flavor_name))				
			{					
					if(!time_flavornum.containsKey(time_value))
					{
						time_flavornum.put(time_value, 1);
					}else
					{
						time_flavornum.put(time_value, time_flavornum.get(time_value)+1);
					}
				
			}
		}
		//��mapͼ�е�String���͵�ʱ��ת����Date���ͣ����ں������
		Set set_flavor=time_flavornum.entrySet();
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
	
	/**
	 * @param history ��ʷ����
	 * @param start Ԥ�⿪ʼʱ��
	 * @param end   Ԥ����ֹʱ��
	 * @param flavor_tname  ��ҪԤ��������������
	 * @return start-endʱ��ε�����
	 */
	public static int predict(ArrayList<String> history, Date start,Date end,String flavor_name) {
		//��flavor��String��mapת����date���͵�map
		TreeMap<Date,Integer>flavor_date=getData(history, flavor_name);			
		//��һ������Ϊ����Ԥ�������flavor��̨��	
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		double predict0=0d,predict1=0d,w=1.0d/7.0d;
		while(start.getTime()<=end.getTime())
		{
			predict0=0;
			Date d=new Date(start.getTime()-7*24*3600*1000);		
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
		    // ָ��һ������ 
			//predict0/=7;
			flavor_date.put(start,(int)(predict0+0.5));
			start.setTime(start.getTime()+24*3600*1000);
			predict1+=(int)predict0;
			//System.out.println("Ԥ���� "+sdf.format(start)+"��һ����Ҫ�������������Ϊ��"+predict);
		}
		//System.out.println("����"+flavor_type+"�ܵ�Ԥ������"+(int)predict1);
		//return null;
		return (int) predict1;
	}
	/**************************************************************************/
	public static int predict2(ArrayList<String> history, Date start,Date end,String flavor_name) {
		//��flavor��String��mapת����date���͵�map
		TreeMap<Date,Integer>flavor_date=getData(history, flavor_name);					
		//��һ������Ϊ����Ԥ�������flavor��̨��	
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
			System.out.println("arr�����size��"+arr.size());
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
		return (int) (predict1+0.5);
	}

	/*************************************************************************/
	
	
	/*********************************�Ҷ�Ԥ��*********************************/
	 public static double gm(double[] fs, int T) {

	        // Ԥ��ģ�ͺ���
	        int size = fs.length;
	        int tsize = fs.length - 1;
	        double[] arr = fs;// ԭʼ����
	        double[] arr1 = new double[size];// ����һ���ۼ�����
	        double sum = 0;
	        for (int i = 0; i < size; i++) {
	            sum += arr[i];
	            arr1[i] = sum;
	        }
	        double[] arr2 = new double[tsize];// arr1�Ľ��ھ�ֵ����
	        for (int i = 0; i < tsize; i++) {
	            arr2[i] = (double) (arr1[i] + arr1[i + 1]) / 2;
	        }
	        /*
	         * 
	         * ���潨�� ����B��YN���������������� �������a,b
	         */
	        /*
	         * ���潨������B, B��5��2�еľ��� �൱��һ����ά���顣
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
	         * ���潨������YN
	         */
	        double[][] YN = new double[tsize][1];
	        for (int i = 0; i < tsize; i++) {
	            for (int j = 0; j < 1; j++) {
	                YN[i][j] = arr[i + 1];
	            }
	        }

	        /*
	         * B��ת�þ���BT,2��5�еľ���
	         */
	        double[][] BT = new double[2][tsize];
	        for (int i = 0; i < 2; i++) {
	            for (int j = 0; j < tsize; j++) {
	                BT[i][j] = B[j][i];
	            }
	        }
	        /*
	         * ��BT��B�ĳ˻����õ��Ľ����Ϊ����B2T,��B2T��һ��2*2�ľ���
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
	        /* ������B2T���������ΪB_2T����ô������һ��ľ��� */
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
	         * ������������ĸ���֪�����������������δ֪��a��b�����������������B_2T*BT*YN
	         * �������Ƿֱ�����Щ����ĳ˻���������B_2T*BT����B_2T*BT�ĳ˻�ΪA������A����һ��2*5�ľ���
	         */
	        /*
	         * 
	         * 
	         * 
	         * ��������A����
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
	         * ������A��YN����ĳ˻�����˻�ΪC������C����һ��2*1�ľ���
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
	        /* ��������������a=C[0][0],b=C[1][0]; */
	        double a = C[0][0], b = C[1][0];
	        int i = T;// ��ȡһ����ֵ
	        double Y = (arr[0] - b / a) * Math.exp(-a * (i + 1)) - (arr[0] - b / a)
	                * Math.exp(-a * i);

	        return Y;
	    }
	/************************************************************************/
	
	
}
