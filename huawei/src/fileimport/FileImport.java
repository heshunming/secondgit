package fileimport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class FileImport {

	public static ArrayList<String> fileImport(String url) 
	{
		//从文件中读取数据保存到ArrayList并返回
		File file=new File(url);
		ArrayList<String> arr=new ArrayList<String>();		
		BufferedReader in = null;
		String str;
		try {
			 in=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			 try {
				while((str=in.readLine())!=null)
				 {
					 arr.add(str);
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return  arr;
		
	}
}
