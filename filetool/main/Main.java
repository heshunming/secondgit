package com.filetool.main;

import com.elasticcloudservice.predict.Predict;
import com.filetool.util.FileUtil;
import com.filetool.util.LogUtil;

/**
 * 
 * �������
 * 
 * @version [�汾��, 2017-12-8]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public class Main {
	public static void main(String[] args) {

		if (args.length != 3) {
			System.err
					.println("please input args: ecsDataPath, inputFilePath, resultFilePath");
			//return;
		}

		String inputfile_url="F:\\��Ϊ��Ӣ��ս��\\�����ĵ�\\����ʾ��\\input_5flavors_cpu_7days.txt";
		String trainfile_url="F:\\��Ϊ��Ӣ��ս��\\�����ĵ�\\����ʾ��\\TrainData_2015.1.1_2015.2.19.txt";	
		String outputfile_url="F:\\��Ϊ��Ӣ��ս��\\�����ĵ�\\����ʾ��\\output_5flavors_cpu_7days.txt";
		String ecsDataPath =trainfile_url;
		String inputFilePath =inputfile_url;
		String resultFilePath =outputfile_url;

		LogUtil.printLog("Begin");

		// ��ȡ�����ļ�
		String[] ecsContent = FileUtil.read(ecsDataPath, null);
		String[] inputContent = FileUtil.read(inputFilePath, null);

		// ����ʵ�����
		String[] resultContents = Predict.predictVm(ecsContent, inputContent);

		// д������ļ�
		if (hasResults(resultContents)) {
			FileUtil.write(resultFilePath, resultContents, false);
		} else {
			FileUtil.write(resultFilePath, new String[] { "NA" }, false);
		}
		LogUtil.printLog("End");
	}

	private static boolean hasResults(String[] resultContents) {
		if (resultContents == null) {
			return false;
		}
		for (String contents : resultContents) {
			if (contents != null && !contents.trim().isEmpty()) {
				return true;
			}
		}
		return false;
	}

}
