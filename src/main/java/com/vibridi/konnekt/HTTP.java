package com.vibridi.konnekt;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import com.vibridi.konnekt.exception.HttpException;
import com.vibridi.konnekt.opts.MIMEType;
import com.vibridi.konnekt.util.IOUtils;

public class HTTP {
	
	public enum Method {
		GET,
		POST,
		PUT,
		DELETE,
		HEAD,
		OPTIONS,
		CONNECT
	}

	private StringBuffer sb;
	private Map<String,String> params;
	private Map<String,String> headers;
	
	public static HTTP build(String url) {
		return new HTTP(url);
	}
	
	private HTTP(String url) {
		this.sb = new StringBuffer(url);
		this.params = new HashMap<String,String>();
		this.headers = new HashMap<String,String>();
	}	
	
	// ******* PUBLIC METHODS ********
	public String getFullUrl() {
		return resolveParams();
	}
	
	public HTTP appendPath(String path) {
		sb.append(path);
		return this;
	}
	
	// ******* QUERY PARAMS ********
	public HTTP setParam(String key, String value) {
		params.put(key, value);
		return this;
	}
	
	public HTTP setParams(Map<String,String> params) {
		this.params = params;
		return this;
	}
	
	// ******* HTTP HEADERS ********
	public HTTP setAccept(MIMEType mimeType) {
		headers.put("Accept", mimeType.stringValue());
		return this;
	}
	
	/**
	 * Set the <code>Connection</code> header.
	 * @param value
	 * @return
	 */
	public HTTP setConnection(String value) {
		headers.put("Connection", value);
		return this;
	}
	
	public HTTP setContentType(String mimeType) {
		headers.put("Content-Type", mimeType);
		return this;
	}
	
	public HTTP setHost(String value) {
		headers.put("Host", value);
		return this;
	}
	
	public HTTP setOrigin(String value) {
		headers.put("Origin", value);
		return this;
	}
	
	public HTTP setPragma(String value) {
		headers.put("Pragma", value);
		return this;
	}
	
	public HTTP setUserAgent(String value) {
		headers.put("User-Agent", value);
		return this;
	}
	
	// ******* GENERIC HEADERS ********
	public HTTP setHeader(String key, String value) {
		headers.put(key, value);
		return this;
	}
	
	public HTTP setHeaders(Map<String,String> headers) {
		this.headers = headers;
		return this;
	}

	
	// ******* HTTP METHODS ********
	public String get() throws IOException {
		HttpURLConnection conn = getConnection();
		conn.setRequestMethod(Method.GET.name());
		return execute(conn);
	}
	
	public String post(String payload) throws IOException {
		HttpURLConnection conn = getConnection();
		conn.setRequestMethod(Method.POST.name());
		conn.setDoInput(true);
		conn.setDoOutput(true);
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(payload.getBytes());
		out.flush();
		out.close();
		return execute(conn);		
	}
	
	public String put(String payload) throws IOException {
		HttpURLConnection conn = getConnection();
		conn.setRequestMethod(Method.PUT.name());
		conn.setDoInput(true);
		conn.setDoOutput(true);
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.writeBytes(payload);
		out.flush();
		out.close();
		return execute(conn);
	}
	
	public String delete() throws IOException {
		HttpURLConnection conn = getConnection();
		conn.setRequestMethod(Method.DELETE.name());
		return execute(conn);
	}
	
	
	// ******* PRIVATE METHODS ********
	private HttpURLConnection getConnection() throws MalformedURLException, IOException {
		URL url = new URL(resolveParams());
		HttpURLConnection conn = null;
		
		if (url.toString().toLowerCase().startsWith("https")){
			conn = (HttpsURLConnection) url.openConnection();
			((HttpsURLConnection) conn).setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
		} else {
			conn = (HttpURLConnection) url.openConnection();
		}
		
		resolveHeaders(conn);
		return conn;
	}
	
	/**
	 * 
	 * @param conn
	 * @return
	 * @throws IOException in case of connection errors (timeouts, connection refused, etc...)
	 * @throws HttpException in case of HTTP errors. The exception object contains the response code and 
	 * the content of the error stream
	 */
	private String execute(HttpURLConnection conn) throws IOException, HttpException {
		int responseCode = conn.getResponseCode(); // throws if it can't get the resp code
		System.out.println("HTTP response code: " + responseCode);
			
		try {
			String response = IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8);
			System.out.println(response);
			return response; // all good, here usually resp code is 200
			
		} catch(Exception e) { // couldn't read the input stream, try reading error stream
			String error = "";
			try {
				error = IOUtils.toString(conn.getErrorStream(), StandardCharsets.UTF_8);
			} catch(IOException f) { // couldn't read the error stream, though resp code is known
				throw new HttpException(e.getMessage(), f, responseCode);
			}
			throw new HttpException(error, e, responseCode); // could read the error stream and resp code is known
		}
	}
	
	private String resolveParams() {
		if(params.size() < 1)
			return sb.toString();
		
		StringBuffer tmp = new StringBuffer(sb.toString());
		tmp.append("?");
		Iterator<String> itr = params.keySet().iterator();
		do {
			String k = itr.next();
			tmp.append(k).append("=").append(params.get(k));	
		} while(itr.hasNext() && tmp.append("&").length() > 0);
		
		return tmp.toString();
	}
	
	private void resolveHeaders(HttpURLConnection conn) {
		for(Map.Entry<String, String> entry : headers.entrySet())
			conn.setRequestProperty(entry.getKey(), entry.getValue());
	}
	
}
