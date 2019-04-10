package com.appleframework.file.provider.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadManager {
		
	private static final MediaType FROM_DATA = MediaType.parse("multipart/form-data");

	private final OkHttpClient client = new OkHttpClient();

	public Response uploadFile(String url, File file, String fileName) throws AppleException {
		RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
		MultipartBody requestBody = new MultipartBody.Builder()
				.setType(FROM_DATA)
				.addFormDataPart(fileName, fileName, fileBody)
				.build();
		Request request = new Request.Builder().url(url).post(requestBody).build();
		try {
			return client.newCall(request).execute();
		} catch (Exception e) {
			throw new AppleException(e);			
		}
	}
	
	public Response uploadBytes(String url, byte[] bytes, String fileName) throws AppleException {
		RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), bytes);
		MultipartBody requestBody = new MultipartBody.Builder()
				.setType(FROM_DATA)
				.addFormDataPart(fileName, fileName, fileBody)
				.build();
		Request request = new Request.Builder().url(url).post(requestBody).build();
		try {
			return client.newCall(request).execute();
		} catch (Exception e) {
			throw new AppleException(e);			
		}
	}
	
	public Response uploadInputStream(String url, InputStream stream, String fileName) throws AppleException {
		try {
			byte[] bytes = this.input2byte(stream);
			RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), bytes);
			MultipartBody requestBody = new MultipartBody.Builder()
					.setType(FROM_DATA)
					.addFormDataPart(fileName, fileName, fileBody)
					.build();
			Request request = new Request.Builder().url(url).post(requestBody).build();
			return client.newCall(request).execute();
		} catch (Exception e) {
			throw new AppleException(e);			
		}
	}
	
	public byte[] input2byte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}
	
}
