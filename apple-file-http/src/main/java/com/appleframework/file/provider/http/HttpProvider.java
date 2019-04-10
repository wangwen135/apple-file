package com.appleframework.file.provider.http;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.file.UploadObject;
import com.appleframework.file.UploadTokenParam;
import com.appleframework.file.provider.AbstractProvider;
import com.appleframework.file.provider.FSOperErrorException;
import com.appleframework.file.utils.FilePathHelper;
import com.google.gson.Gson;

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
	
	private Gson gson = new Gson();
	
	private UploadManager uploadManager= new UploadManager();


	public HttpProvider(String urlprefix, String bucketName) {		
		Validate.notBlank(bucketName, "[bucketName] not defined");
		Validate.notBlank(urlprefix, "[urlprefix] not defined");
		this.urlprefix = urlprefix.endsWith(DIR_SPLITER) ? urlprefix : urlprefix + DIR_SPLITER;
		this.bucketName = bucketName;
	}
	
	@Override
	public String upload(UploadObject object) {
		String fileName = object.getFileName();
		if (StringUtils.isNotBlank(object.getCatalog())) {
			fileName = object.getCatalog().concat(FilePathHelper.DIR_SPLITER).concat(fileName);
		}
		try {
			Response res = null;
			if (object.getFile() != null) {
				String url = this.urlprefix + "file/upload?groupName=" + bucketName;
				res = uploadManager.uploadFile(url, object.getFile(), fileName);
			} else if (object.getBytes() != null) {
				String url = this.urlprefix + "file/upload?groupName=" + bucketName;
				res = uploadManager.uploadBytes(url, object.getBytes(), fileName);
			} else if (object.getInputStream() != null) {
				String url = this.urlprefix + "file/upload?groupName=" + bucketName;
				res = uploadManager.uploadInputStream(url, object.getInputStream(), fileName);
			} else {
				logger.error("upload object is NULL");
				throw new IllegalArgumentException("upload object is NULL");
			}			 
			return processUploadResponse(res);
		} catch (AppleException e) {
			processUploadException(fileName, e);
		}
		return null;
	}
	
	/**
	 * 处理上传结果，返回文件url
	 * 
	 * @return
	 * @throws QiniuException
	 */
	private String processUploadResponse(Response res) throws AppleException {	
		if (!res.isSuccessful()) {
			throw new AppleException(res);
		}
		else {
			try {
				UploadResult ret = gson.fromJson(res.body().string(), UploadResult.class);
				if (ret.isSuccessful()) {
					return ret.getUrl();
				}
				else {
					throw new AppleException(res, ret.getMessage());
				}
			} catch (AppleException e) {
				throw e;
			} catch (Exception e) {
				throw new AppleException(res);
			}
		}
	}
	
	private void processUploadException(String fileKey, AppleException e) {
		Response r = e.response;
		String message;
		try {
			message = e.getMessage();
		} catch (Exception e2) {
			message = r.toString();
		}
		throw new FSOperErrorException(name(), e.code(), message);
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
