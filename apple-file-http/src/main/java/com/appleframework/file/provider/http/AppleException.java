package com.appleframework.file.provider.http;

import java.io.IOException;

import okhttp3.Response;

public class AppleException extends IOException {

	private static final long serialVersionUID = -41081236342194496L;
	
	public final Response response;
    private String error;


    public AppleException(Response response) {
        super(response != null ? response.message() : null);
        this.response = response;
    }
    
    public AppleException(Response response, String message) {
        super(message);
        this.response = response;
    }

    public AppleException(Exception e) {
        this(e, null);
    }

    public AppleException(Exception e, String msg) {
        super(msg, e);
        this.response = null;
        this.error = msg;
    }

    public int code() {
        return response == null ? -1 : response.code();
    }

    public String error() {
        if (error != null) {
            return error;
        }
        if (response == null || response.code() / 100 == 2) {
            return null;
        }
        return error;
    }
}
