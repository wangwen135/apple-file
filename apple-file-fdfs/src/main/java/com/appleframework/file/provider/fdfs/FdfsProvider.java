package com.appleframework.file.provider.fdfs;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.file.UploadObject;
import com.appleframework.file.UploadTokenParam;
import com.appleframework.file.provider.AbstractProvider;
import com.appleframework.file.provider.FSOperErrorException;
import com.appleframework.file.sdk.fdfs.FastdfsClient;
import com.appleframework.file.sdk.fdfs.FastdfsClient.Builder;
import com.appleframework.file.sdk.fdfs.FileId;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年1月7日
 */
public class FdfsProvider extends AbstractProvider {

	private static final Logger logger = LoggerFactory.getLogger(FdfsProvider.class);

	public static final String NAME = "fastDFS";
	private FastdfsClient client;

	public FdfsProvider(String urlprefix, String bucketName, String[] servers, long connectTimeout, int maxThreads) {
		this.urlprefix = urlprefix.endsWith(DIR_SPLITER) ? urlprefix : urlprefix + DIR_SPLITER;
		this.bucketName = bucketName;
		Builder builder = FastdfsClient.newBuilder()
				.connectTimeout(connectTimeout)
				.readTimeout(connectTimeout)
				.maxThreads(maxThreads);
		String[] tmpArray;
		for (String s : servers) {
			tmpArray = s.split(":");
			builder.tracker(tmpArray[0], Integer.parseInt(tmpArray[1]));
		}
		client = builder.build();
	}

	@Override
	public String upload(UploadObject object) throws InterruptedException, ExecutionException, IOException {
		CompletableFuture<FileId> upload = null;
			if (object.getFile() != null) {
				upload = client.upload(bucketName, object.getFile());
			} else if (object.getBytes() != null) {
				upload = client.upload(bucketName, object.getFileName(), object.getBytes());
			} else if (object.getInputStream() != null) {
				byte[] bs = IOUtils.toByteArray(object.getInputStream());
				upload = client.upload(bucketName, object.getFileName(), bs);
			} else if (StringUtils.isNotBlank(object.getUrl())) {

			}
			return getFullPath(upload.get().toString());
	

	}

	@Override
	public Map<String, Object> createUploadToken(UploadTokenParam param) {
		return null;
	}

	@Override
	public boolean delete(String fileKey) {
		try {
			if (fileKey.contains(DIR_SPLITER))
				fileKey = fileKey.replace(urlprefix, "");
			FileId path = FileId.fromString(fileKey);
			client.delete(path).get();
			return true;
		} catch (Exception e) {
			processException(e);
		}
		return false;
	}

	@Override
	public String getDownloadUrl(String fileKey) {
		return getFullPath(fileKey);
	}

	@Override
	public String name() {
		return NAME;
	}

	private void processException(Exception e) {
		throw new FSOperErrorException(name(), e);
	}

	@Override
	public void close() throws IOException {
		client.close();
	}

}
