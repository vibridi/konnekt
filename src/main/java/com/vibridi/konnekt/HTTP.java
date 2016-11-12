package com.vibridi.konnekt;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.vibridi.konnekt.opts.MIMEType;
import com.vibridi.konnekt.opts.Method;

public class HTTP {

	private StringBuffer sb;
	private Map<String,String> params;
	private Map<String,String> headers;
	
	public static HTTP create(String url) {
		return new HTTP(url);
	}
	
	private HTTP(String url) {
		this.sb = new StringBuffer(url);
		this.params = new HashMap<String,String>();
		this.headers = new HashMap<String,String>();
	}	
	
	// ******* PUBLIC METHODS ********
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
	
	public HTTP setContentType(MIMEType mimeType) {
		headers.put("Content-Type", mimeType.stringValue());
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
		
		
		return null;
	}
	
	public String post(String payload) throws IOException {
		HttpURLConnection conn = getConnection();
		conn.setRequestMethod(Method.POST.name());
		conn.setDoInput(true);
		conn.setDoOutput(true);
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.writeBytes(payload);
		out.flush();
		out.close();
				
		try {
			return readStream(conn.getInputStream());
		} catch(Exception e) {
			return readStream(conn.getErrorStream());
		}		
	}
	
	public String put(String payload) {
		
		return null;
	}
	
	public String delete() {
		
		return null;
	}
	
	
	// ******* PRIVATE METHODS ********
	private HttpURLConnection getConnection() throws MalformedURLException, IOException {
		resolveParams();
		URL url = new URL(sb.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		resolveHeaders(conn);
		return conn;
	}
	
	private String readStream(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
	
	protected void resolveParams() {
		if(params.size() < 1)
			return;
		
		sb.append("?");
		Iterator<String> itr = params.keySet().iterator();
		do {
			String k = itr.next();
			sb.append(k).append("=").append(params.get(k));	
		} while(itr.hasNext() && sb.append("&").length() > 0);
	}
	
	protected void resolveHeaders(HttpURLConnection conn) {
		for(Map.Entry<String, String> entry : headers.entrySet())
			conn.setRequestProperty(entry.getKey(), entry.getValue());
	}
	
}
