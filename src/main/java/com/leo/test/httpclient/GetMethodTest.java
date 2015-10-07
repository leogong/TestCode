package com.leo.test.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class GetMethodTest {

	public static void main(String[] args) throws HttpException, IOException {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod("http://war3.uuu9.com");
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			String responseBody = new String(getMethod
					.getResponseBodyAsString().getBytes("ISO-8859-1"), "GB2312");
			System.out.println(responseBody);
		} finally {
			getMethod.releaseConnection();
		}
	}

}
