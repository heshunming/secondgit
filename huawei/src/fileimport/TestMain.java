package fileimport;

import java.util.Date;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*String url="F:\\华为精英挑战赛\\初赛文档\\练习数据\\data_2015_3.txt";
		String flavor="flavor";

		String inputfile_url="F:\\华为精英挑战赛\\初赛文档\\用例示例\\input_5flavors_cpu_7days.txt";
		String trainfile_url="F:\\华为精英挑战赛\\初赛文档\\用例示例\\TrainData_2015.1.1_2015.2.19.txt";
		ArrayList<String> input_str=FileImport.fileImport(inputfile_url);
		System.out.println(input_str.size());
		int j=0;
		while(j<input_str.size())
		{
			System.out.println(input_str.get(j));
			j++;
		}
		/*HashMap<String,Integer>flavor_map=new HashMap<String,Integer>();
		for(int i=1;i<=15;i++)
		{
			flavor=flavor+i+"";
			flavor_map=GetData.getData(url, flavor);			
			Set f2_entries=flavor_map.entrySet();
			Iterator<?> f2_it=f2_entries.iterator();
			System.out.println("flavor"+i+":");
			while(f2_it.hasNext())
			{
				Map.Entry<String, Integer> e=(Entry<String, Integer>) f2_it.next();
				String id=e.getKey();
				int num=e.getValue();
				System.out.println(id+":"+num);
			}	
			flavor="flavor";
		}*/
			
		Date date=new Date(System.currentTimeMillis());
		String url=" ";
		Calculate.calculate(date, url);
		
	}

}
