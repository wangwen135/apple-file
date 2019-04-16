package com.appleframework.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.appleframework.file.utils.ResourceUtils;

public abstract class AbstractFileSystemClient {

	protected static Map<String, AbstractFileSystemClient> clients = new HashMap<String, AbstractFileSystemClient>();
	protected static final String PUBLIC_ID = ResourceUtils.getProperty("public.filesystem.id", "public");
	protected static final String PRIVATE_ID = ResourceUtils.getProperty("private.filesystem.id", "private");

	protected FSProvider fsProvider;

	public String upload(File file)  throws InterruptedException, ExecutionException, IOException{
		return fsProvider.upload(new UploadObject(file));
	}

	public String upload(String fileKey, File file)  throws InterruptedException, ExecutionException, IOException{
		return fsProvider.upload(new UploadObject(fileKey, file));
	}

	public String upload(String fileKey, File file, String catalog)  throws InterruptedException, ExecutionException, IOException{
		return fsProvider.upload(new UploadObject(fileKey, file).toCatalog(catalog));
	}

	public String upload(String fileKey, byte[] contents)  throws InterruptedException, ExecutionException, IOException{
		return fsProvider.upload(new UploadObject(fileKey, contents));
	}

	public String upload(String fileKey, byte[] contents, String catalog)  throws InterruptedException, ExecutionException, IOException{
		return fsProvider.upload(new UploadObject(fileKey, contents).toCatalog(catalog));
	}

	public String upload(String fileKey, InputStream in, String mimeType)  throws InterruptedException, ExecutionException, IOException{
		return fsProvider.upload(new UploadObject(fileKey, in, mimeType));
	}

	public String upload(String fileKey, InputStream in, String mimeType, String catalog) throws InterruptedException, ExecutionException, IOException {
		return fsProvider.upload(new UploadObject(fileKey, in, mimeType).toCatalog(catalog));
	}

	public boolean delete(String fileKey) {
		return fsProvider.delete(fileKey);
	}

	public String getDownloadUrl(String fileKey) {
		return fsProvider.getDownloadUrl(fileKey);
	}

	public Map<String, Object> createUploadToken(UploadTokenParam param) {
		return fsProvider.createUploadToken(param);
	}

	public FSProvider getProvider() {
		return fsProvider;
	}

}
