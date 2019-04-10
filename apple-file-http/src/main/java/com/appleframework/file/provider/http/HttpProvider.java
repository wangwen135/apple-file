package com.appleframework.file.provider.http;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.file.UploadObject;
import com.appleframework.file.UploadTokenParam;
import com.appleframework.file.provider.AbstractProvider;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * http文件服务
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年1月5日
 */
public class HttpProvider extends AbstractProvider {
	
	private final static Logger logger = LoggerFactory.getLogger(HttpProvider.class);

	public static final String NAME = "http";
	
	private final OkHttpClient client = new OkHttpClient();
	
	private Gson gson = new Gson();
	
	private static final MediaType FROM_DATA = MediaType.parse("multipart/form-data");


	public HttpProvider(String urlprefix, String bucketName) {		
		Validate.notBlank(bucketName, "[bucketName] not defined");
		Validate.notBlank(urlprefix, "[urlprefix] not defined");
		this.urlprefix = urlprefix.endsWith(DIR_SPLITER) ? urlprefix : urlprefix + DIR_SPLITER;
		this.bucketName = bucketName;
	}

	@Override
	public String upload(UploadObject object) {
		String fileName = object.getFileName();
		try {
			RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), object.getFile());
			MultipartBody requestBody = new MultipartBody.Builder()
					.setType(FROM_DATA)
					.addFormDataPart(fileName, fileName, fileBody)
					.build();

			String url = this.urlprefix + "file/upload?groupName=" + bucketName;
			Request request = new Request.Builder().url(url).post(requestBody).build();

			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				if (logger.isInfoEnabled()) {
					logger.info("Unexpected code " + response);
				}
				throw new IOException("Unexpected code " + response);
			}
			else {
				String resultM = response.body().string();
				if (logger.isInfoEnabled()) {
					logger.info(resultM);
				}
				UploadResult result = gson.fromJson(resultM, UploadResult.class);
				if(result.getStatus() == UploadResult.UPLOAD_SUCCSSS) {
					return result.getUrl();
				}
				else {
					return null;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public String getDownloadUrl(String fileKey) {
		return null;
	}

	@Override
	public boolean delete(String fileKey) {
		if (fileKey.contains(DIR_SPLITER))
			fileKey = fileKey.replace(urlprefix, "");
		return true;
	}
	
	@Override
	public Map<String, Object> createUploadToken(UploadTokenParam param) {		
		return null;
	}

	@Override
	public void close() throws IOException {}

	@Override
	public String name() {
		return NAME;
	}

}
