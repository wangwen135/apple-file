/**
 * 
 */
package com.appleframework.file;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 上传接口
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年1月5日
 */
public interface FSProvider extends Closeable {

	String name();
	/**
	 * 文件上传
	 * @param object
	 * @return
	 */
	public String upload(UploadObject object) throws InterruptedException, ExecutionException, IOException;
	/**
	 * 获取文件下载地址
	 * @param file 文件（全路径或者fileKey）
	 * @return
	 */
	public String getDownloadUrl(String fileKey);
	
	/**
	 * 删除图片
	 * @return
	 */
	public boolean delete(String fileKey);
	
	public String downloadAndSaveAs(String fileKey,String localSaveDir);
	
	public Map<String, Object> createUploadToken(UploadTokenParam param);
}
