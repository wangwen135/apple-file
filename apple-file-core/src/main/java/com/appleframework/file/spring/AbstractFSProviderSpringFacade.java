/**
 * 
 */
package com.appleframework.file.spring;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import com.appleframework.file.FSProvider;
import com.appleframework.file.UploadObject;
import com.appleframework.file.UploadTokenParam;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年1月7日
 */
public abstract class AbstractFSProviderSpringFacade {

	protected FSProvider fsProvider;
	
	protected String endpoint;
	protected String groupName;
	protected String accessKey;
	protected String secretKey;
	protected String urlprefix;
	protected String servers;
	
	protected boolean privated;

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public void setUrlprefix(String urlprefix) {
		this.urlprefix = urlprefix;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}
	
	public void setPrivated(boolean privated) {
		this.privated = privated;
	}

	public void destroy() throws Exception {
		fsProvider.close();
	}

	public String upload(String fileName, File file) {
		return fsProvider.upload(new UploadObject(fileName, file));
	}

	public String upload(String fileName, InputStream in, String mimeType) {
		return fsProvider.upload(new UploadObject(fileName, in, mimeType));
	}

	public boolean delete(String fileName) {
		return fsProvider.delete(fileName);
	}

	public Map<String, Object> createUploadToken(UploadTokenParam param) {
		return fsProvider.createUploadToken(param);
	}
	
}
