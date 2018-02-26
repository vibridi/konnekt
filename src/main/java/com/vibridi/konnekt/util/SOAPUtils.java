package com.vibridi.konnekt.util;

import java.io.IOException;
import java.util.Map;

import com.vibridi.konnekt.opts.SOAPAction;

public class SOAPUtils {

	private static String bodyNS[] = { 
		 "http://www.w3.org/2003/05/soap-envelope",
		 "http://schemas.xmlsoap.org/soap/envelope/"
	};
	
	private static final String notifyStartTag = "<wsnt:Notify";
	private static final String notifyEndTag = "</wsnt:Notify>";
	
	/**
	 * Return the body message using the template and replaces values
	 * @param templateResourceName relative resource path
	 * @param replaces dictionary of key and replaces
	 * @return the resulting message body
	 * @throws IOException if any error
	 */
	public static String composeBody(String template, Map<String,String> replaces) throws IOException{
		for(String s : replaces.keySet()){
			if (replaces.get(s)==null)
				template=template.replace(s,"");
			else
				template=template.replace(s, replaces.get(s));
		}
		return template;
	}
	
	public static String composeMessage(String envelopeTemplate, String body) {
		return envelopeTemplate.replace("@BODY@", body);
	}
	
	public static String extractNotification(String response) {
		int start = response.indexOf(notifyStartTag);
		int end = -1;
		if(start == -1)
			return null;
		end = response.indexOf(notifyEndTag, start);
		if(end == -1)
			return null;
		return response.substring(start, end + notifyEndTag.length());
	}
	
	public static String getSoap12ContentType(SOAPAction soapAction) {
		StringBuilder sb = new StringBuilder("application/soap+xml");
		if(soapAction != null) {
			sb.append("; action=\"")
				.append(soapAction.toString())
				.append("\";"); 
		}
		return sb.toString();
	}
	
	/**
	 * Extract the host from a URL
	 * @param url Full URL containing the host
	 * @return the host name
	 */
	public static String getHost(String url){
		if(url == null || url.length() == 0)
			return "";

		int doubleslash = url.indexOf("//");
		if(doubleslash == -1)
			doubleslash = 0;
		else
			doubleslash += 2;

		int end = url.indexOf('/', doubleslash);
		end = end >= 0 ? end : url.length();

		int port = url.indexOf(':', doubleslash);
		end = (port > 0 && port < end) ? port : end;

		return url.substring(doubleslash, end);
	}
	
}
