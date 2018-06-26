package com.appleframework.file;

import org.apache.commons.lang3.Validate;

import com.appleframework.file.provider.aliyun.AliyunossProvider;
import com.appleframework.file.utils.ResourceUtils;

public class FileSystemClient extends AbstractFileSystemClient {

	private FileSystemClient(String id) {
		String provider = ResourceUtils.getProperty(id + ".filesystem.provider");
		Validate.notBlank(provider, "[" + id + ".filesystem.provider] not defined");
		boolean isPrivate = ResourceUtils.getBoolean(id + ".filesystem.private");
		String urlprefix = ResourceUtils.getProperty(id + ".filesystem.urlprefix");
		String endpoint = ResourceUtils.getProperty(id + ".filesystem.endpoint");
		String bucketName = ResourceUtils.getProperty(id + ".filesystem.bucketName");
		String accessKey = ResourceUtils.getProperty(id + ".filesystem.accessKey");
		String secretKey = ResourceUtils.getProperty(id + ".filesystem.secretKey");
		fsProvider = new AliyunossProvider(urlprefix, endpoint, bucketName, accessKey, secretKey, isPrivate);
	}

	public static FileSystemClient getClient(String id) {
		return createClient(id);
	}

	public static FileSystemClient getPublicClient() {
		return createClient(PUBLIC_ID);
	}

	public static FileSystemClient getPrivateClient() {
		return createClient(PRIVATE_ID);
	}

	private static FileSystemClient createClient(String id) {
		if (null == id) {
			id = "aliyun";
		}
		FileSystemClient client = (FileSystemClient) clients.get(id);
		if (client != null)
			return client;

		synchronized (clients) {
			client = (FileSystemClient) clients.get(id);
			if (client != null)
				return client;
			client = new FileSystemClient(id);
			clients.put(id, client);
		}

		return client;
	}

}
