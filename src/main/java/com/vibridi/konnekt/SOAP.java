package com.vibridi.konnekt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.vibridi.konnekt.opts.SOAPAction;
import com.vibridi.konnekt.util.SOAPUtils;


/**
 * 
 * @author gabriele.vaccari
 *
 */
public class SOAP {

	private enum SOAPVersion {
		v1_1,
		v1_2
	}
	
	// TODO make this an actual SOAP builder

	private final Map<String,String> headers;
	private final String endpoint;
	private final SOAPVersion soapVersion;
	private SOAPAction soapAction;
	private String security;

	private SOAP(SOAPVersion soapVersion, String endpoint) {
		headers = new HashMap<>();
		headers.put("User-Agent", "xaccess_v3");
		headers.put("Accept", "text/xml");
		headers.put("Origin", "xaccess_v3");
		headers.put("Connection", "keep-alive");
		
		this.endpoint = endpoint;
		this.soapVersion = soapVersion;
	}

	public static SOAP buildSOAP11(String endpoint) {
		return new SOAP(SOAPVersion.v1_1, endpoint);
	}

	public static SOAP buildSOAP12(String endpoint) {
		return new SOAP(SOAPVersion.v1_2, endpoint);	
	}
	
	public SOAP setSecurity(String security) {
		this.security = security;
		return this;
	}
	
	
	/**
	 * In case of SOAP version 1.2, this method also sets the correct HTTP header Content-Type
	 * 
	 * @param soapAction
	 * @return
	 */
	public SOAP setAction(SOAPAction soapAction) {
		this.soapAction = soapAction;
		if(soapVersion == SOAPVersion.v1_2)
			headers.put("Content-Type", SOAPUtils.getSoap12ContentType(soapAction));
		return this;
	}
	
	public SOAP setHeader(String key, String value) {
		headers.put(key, value);
		return this;
	}

	public String executeMessage(String message) throws IOException {
		if(endpoint == null)
			throw new IllegalStateException("SOAP service endpoint is not set");

		String result = send(message);

		if (result == null || result.length() == 0)
			throw new IOException("Response is empty");

		return result;
	}

	/**
	 * Sends a synchronous HTTP POST request to the target URL
	 * @param message Payload of the POST request
	 * @return server response
	 * @throws IOException in case of any error
	 */
	public String send(String message) throws IOException {
		return HTTP.build(endpoint)
			.setHeaders(headers)
			.setHeader("Content-Length", "" + Integer.toString(message.getBytes("UTF-8").length))
			.post(message);
	}

}