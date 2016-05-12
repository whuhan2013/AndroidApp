package com.image.sdk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import android.content.Context;
import android.widget.Toast;

public class IncSDK {

	static {
		System.loadLibrary("IncSDK");
	}

	/**
	 * 图像识别
	 *
	 * @param w
	 *            图像宽
	 * @param h
	 *            图像高
	 * @param buf
	 *            图像像素数组
	 * @param filepath
	 *            样本数据文件路径
	 * @return 返回样本序号
	 */
	public static native int RecMyImage(int w, int h, int[] buf, String filepath);

	/**
	 * 从Assert文件夹中考文件
	 *
	 * @param oldPath
	 *            Assert文件夹下的文件名
	 * @param newPath
	 *            将要复制到的路径
	 * @param context
	 *            Activity Context
	 */
	public static void copyFilesFassets(String oldPath, String newPath,
										Context context) {
		try {

			String fileNames[] = context.getAssets().list(oldPath);// 获取assets目录下的所有文件及目录名
			// 如果是目录
			if (fileNames.length > 0)
			{
				File file = new File(newPath);
				file.mkdirs();// 如果文件夹不存在，则递归
				for (String fileName : fileNames) {
					copyFilesFassets(oldPath + "/" + fileName, newPath + "/"
							+ fileName, context);
				}
			}
			// 如果是文件
			else {
				InputStream is = context.getAssets().open(oldPath);
				FileOutputStream fos = new FileOutputStream(new File(newPath));
				byte[] buffer = new byte[1024];
				int byteCount = 0;
				while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
					// buffer字节
					fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
				}
				fos.flush();// 刷新缓冲区
				is.close();
				fos.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
