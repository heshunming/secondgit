package com.filetool.main;

import com.elasticcloudservice.predict.Predict;
import com.filetool.util.FileUtil;
import com.filetool.util.LogUtil;

/**
 * 
 * 工具入口
 * 
 * @version [版本号, 2017-12-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Main {
	public static void main(String[] args) {

		if (args.length != 3) {
			System.err
					.println("please input args: ecsDataPath, inputFilePath, resultFilePath");
			//return;
		}

		String inputfile_url="F:\\华为精英挑战赛\\初赛文档\\用例示例\\input_5flavors_cpu_7days.txt";
		String trainfile_url="F:\\华为精英挑战赛\\初赛文档\\用例示例\\TrainData_2015.1.1_2015.2.19.txt";	
		String outputfile_url="F:\\华为精英挑战赛\\初赛文档\\用例示例\\output_5flavors_cpu_7days.txt";
		String ecsDataPath =trainfile_url;
		String inputFilePath =inputfile_url;
		String resultFilePath =outputfile_url;

		LogUtil.printLog("Begin");

		// 读取输入文件
		String[] ecsContent = FileUtil.read(ecsDataPath, null);
		String[] inputContent = FileUtil.read(inputFilePath, null);

		// 功能实现入口
		String[] resultContents = Predict.predictVm(ecsContent, inputContent);

		// 写入输出文件
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
