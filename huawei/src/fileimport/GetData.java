package fileimport;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class GetData {

	public static HashMap<String,Integer> getData( String url,String flavor_str)
	{		
		//从读到的文本文件中提取时间字符数据和那个时间的虚拟机flavor的数量
		ArrayList<String> arr=FileImport.fileImport(url);
		HashMap<String,String> flavor=new HashMap<String,String>();
		HashMap<String,String> time=new HashMap<String,String>();
		HashMap<String,Integer>flavor_1=new HashMap<String,Integer>();
		String str_temp[] = null;
		for(String s:arr)
		{			
			str_temp=s.split("	| ");
			flavor.put(str_temp[0], str_temp[1]);
			time.put(str_temp[0], str_temp[2]);
			
		}
		
		Set flavor_entries=flavor.entrySet();
		Iterator<?> it=flavor_entries.iterator();
		Set time_entries=time.entrySet();
		Iterator<?> time_it=time_entries.iterator();
		while(it.hasNext())
		{
			Map.Entry<String, String> e=(Entry<String, String>) it.next();
			String id=e.getKey();
			String flavor_value=e.getValue();
			String time_value=time.get(id);		
			//System.out.println(id+" "+flavor_value);
			if(flavor_value.equals(flavor_str))				
			{					
					if(!flavor_1.containsKey(time_value))
					{
						flavor_1.put(time_value, 1);
					}else
					{
						flavor_1.put(time_value, flavor_1.get(time_value)+1);
					}
					//System.out.print(" oooooooooooo");
			}
		}
		
		return flavor_1;
	}

}




/*ArrayList<String> arr=FileImport.fileImport(url);
HashMap<String,String> flavor=new HashMap<String,String>();
HashMap<String,String> time=new HashMap<String,String>();
HashMap<String,Integer> flavor1=new HashMap<String,Integer>();
HashMap<String,Integer> flavor2=new HashMap<String,Integer>();
HashMap<String,Integer> flavor3=new HashMap<String,Integer>();
HashMap<String,Integer> flavor4=new HashMap<String,Integer>();
HashMap<String,Integer> flavor5=new HashMap<String,Integer>();
HashMap<String,Integer> flavor6=new HashMap<String,Integer>();
HashMap<String,Integer> flavor7=new HashMap<String,Integer>();
HashMap<String,Integer> flavor8=new HashMap<String,Integer>();
HashMap<String,Integer> flavor9=new HashMap<String,Integer>();
HashMap<String,Integer> flavor10=new HashMap<String,Integer>();
HashMap<String,Integer> flavor11=new HashMap<String,Integer>();
HashMap<String,Integer> flavor12=new HashMap<String,Integer>();
HashMap<String,Integer> flavor13=new HashMap<String,Integer>();
HashMap<String,Integer> flavor14=new HashMap<String,Integer>();
HashMap<String,Integer> flavor15=new HashMap<String,Integer>();	
String str_temp[];

for(String s:arr)
{
	System.out.println(s+" ");
	str_temp=s.split("	| ");
	flavor.put(str_temp[0], str_temp[1]);
	time.put(str_temp[0], str_temp[2]);
}
//System.out.println(time.size());
//System.out.println(flavor.size());
Set flavor_entries=flavor.entrySet();
Iterator<?> it=flavor_entries.iterator();
Set time_entries=time.entrySet();
Iterator<?> time_it=time_entries.iterator();
while(it.hasNext())
{
	Map.Entry<String, String> e=(Entry<String, String>) it.next();
	String id=e.getKey();
	String flavor_value=e.getValue();
	String time_value=time.get(id);
	switch(flavor_value)
	{
		case "flavor1":					
			if(!flavor1.containsKey(time_value))
			{
				flavor1.put(time_value, 1);
			}else
			{
				flavor1.put(time_value, flavor1.get(time_value)+1);
			}
		break;
		case "flavor2":					
			if(!flavor2.containsKey(time_value))
			{
				flavor2.put(time_value, 1);
			}else
			{
				flavor2.put(time_value, flavor2.get(time_value)+1);
			}
		break;
		case "flavor3":					
			if(!flavor3.containsKey(time_value))
			{
				flavor3.put(time_value, 1);
			}else
			{
				flavor3.put(time_value, flavor3.get(time_value)+1);
			}
		break;
		case "flavor4":					
			if(!flavor4.containsKey(time_value))
			{
				flavor4.put(time_value, 1);
			}else
			{
				flavor4.put(time_value, flavor4.get(time_value)+1);
			}
		break;
		case "flavor5":					
			if(!flavor5.containsKey(time_value))
			{
				flavor5.put(time_value, 1);
			}else
			{
				flavor5.put(time_value, flavor5.get(time_value)+1);
			}
		break;
		case "flavor6":					
			if(!flavor6.containsKey(time_value))
			{
				flavor6.put(time_value, 1);
			}else
			{
				flavor6.put(time_value, flavor6.get(time_value)+1);
			}
		break;
		case "flavor7":					
			if(!flavor7.containsKey(time_value))
			{
				flavor7.put(time_value, 1);
			}else
			{
				flavor7.put(time_value, flavor7.get(time_value)+1);
			}
		break;
		case "flavor8":					
			if(!flavor8.containsKey(time_value))
			{
				flavor8.put(time_value, 1);
			}else
			{
				flavor8.put(time_value, flavor8.get(time_value)+1);
			}
		break;
		case "flavor9":					
			if(!flavor9.containsKey(time_value))
			{
				flavor9.put(time_value, 1);
			}else
			{
				flavor9.put(time_value, flavor9.get(time_value)+1);
			}
		break;
		case "flavor10":					
			if(!flavor1.containsKey(time_value))
			{
				flavor10.put(time_value, 1);
			}else
			{
				flavor10.put(time_value, flavor10.get(time_value)+1);
			}
		break;
		case "flavor11":					
			if(!flavor11.containsKey(time_value))
			{
				flavor11.put(time_value, 1);
			}else
			{
				flavor11.put(time_value, flavor11.get(time_value)+1);
			}
		break;
		case "flavor12":					
			if(!flavor12.containsKey(time_value))
			{
				flavor12.put(time_value, 1);
			}else
			{
				flavor12.put(time_value, flavor12.get(time_value)+1);
			}
		break;
		case "flavor13":					
			if(!flavor13.containsKey(time_value))
			{
				flavor13.put(time_value, 1);
			}else
			{
				flavor13.put(time_value, flavor13.get(time_value)+1);
			}
		break;
		case "flavor14":					
			if(!flavor14.containsKey(time_value))
			{
				flavor14.put(time_value, 1);
			}else
			{
				flavor14.put(time_value, flavor14.get(time_value)+1);
			}
		break;
		case "flavor15":					
			if(!flavor15.containsKey(time_value))
			{
				flavor15.put(time_value, 1);
			}else
			{
				flavor15.put(time_value, flavor15.get(time_value)+1);
			}
		break;
	}
	//System.out.println(id+":"+flavor_value);
}
Set f2_entries=flavor2.entrySet();
Iterator<?> f2_it=f2_entries.iterator();
System.out.println("flavor2: ");
while(f2_it.hasNext())
{
	Map.Entry<String, Integer> e=(Entry<String, Integer>) f2_it.next();
	String id=e.getKey();
	int num=e.getValue();
	System.out.println(id+":"+num);
}*/