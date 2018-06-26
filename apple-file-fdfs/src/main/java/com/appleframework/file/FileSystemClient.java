package com.appleframework.file;

import org.apache.commons.lang3.Validate;

import com.appleframework.file.provider.fdfs.FdfsProvider;
import com.appleframework.file.utils.ResourceUtils;

public class FileSystemClient extends AbstractFileSystemClient {

	private FileSystemClient(String id) {
		String provider = ResourceUtils.getProperty(id + ".filesystem.provider");
		Validate.notBlank(provider, "[" + id + ".filesystem.provider] not defined");
		String urlprefix = ResourceUtils.getProperty(id + ".filesystem.urlprefix");
		String groupName = ResourceUtils.getProperty(id + ".filesystem.groupName");
		String servers = ResourceUtils.getProperty(id + ".filesystem.servers");
		Validate.isTrue(servers != null && servers.matches("^.+[:]\\d{1,5}\\s*$"), "[servers] is not valid");
		long connectTimeout = Long.parseLong(ResourceUtils.getProperty(id + ".filesystem.connectTimeout", "3000"));
		int maxThreads = Integer.parseInt(ResourceUtils.getProperty(id + ".filesystem.maxThreads", "50"));
		fsProvider = new FdfsProvider(urlprefix, groupName, servers.split(",|;"), connectTimeout, maxThreads);
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
			id = "fdfs";
		}
		FileSystemClient client = (FileSystemClient) clients.get(id);
		if (client != null) {
			return client;
		}
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
