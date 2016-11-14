package com.vibridi.konnekt;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vibridi.konnekt.opts.MIMEType;

public class HTTPTest {
	
	@Test
	public void testString() {
		HTTP http = HTTP.create("http://www.httpbin.org");
		assertTrue(http.getFullUrl().equals("http://www.httpbin.org"));
		http.appendPath("path");
		assertTrue(http.getFullUrl().equals("http://www.httpbin.org/path"));
		http.setParam("param1", "abc");
		http.setParam("param2", "123");
		assertTrue(http.getFullUrl().equals("http://www.httpbin.org/path?param1=abc&param2=123"));
	}

	@Test
	public void testChain() {
		assertTrue(
			HTTP.create("http://www.httpbin.org")
				.appendPath("path")
				.setParam("param1", "abc")
				.setParam("param2", "123")
				.getFullUrl()
				.equals("http://www.httpbin.org/path?param1=abc&param2=123")
		);
	}
	
	@Test
	public void testPost() throws IOException {
		JsonNodeFactory fac = JsonNodeFactory.instance;
		ObjectNode obj = fac.objectNode();
		obj.put("test-field", "konnekt-test");
		
		String res = HTTP.create("http://www.httpbin.org")
				.appendPath("post")
				.setContentType(MIMEType.APPLICATION_JSON)
				.post(obj.toString());	
		
		ObjectMapper om = new ObjectMapper();
		JsonNode jn = om.readTree(res);
		assertTrue(jn.get("json").get("test-field").asText().equals("konnekt-test"));
	}
	
	@Test
	public void testHeaders() throws IOException {
		String res = HTTP.create("http://www.httpbin.org")
				.appendPath("headers")
				.setAccept(MIMEType.APPLICATION_JSON)
				.setContentType(MIMEType.TEXT_PLAIN)
				.setHost("www.httpbin.org")
				.setUserAgent("konnekt-test")
				.get();
		
		ObjectMapper om = new ObjectMapper();
		JsonNode jn = om.readTree(res).get("headers");
		assertTrue(jn.get("Accept").asText().equals("application/json"));
		assertTrue(jn.get("Content-Type").asText().equals("text/plain"));
		assertTrue(jn.get("Host").asText().equals("www.httpbin.org"));
		assertTrue(jn.get("User-Agent").asText().equals("konnekt-test"));
	}
	
	@Test
	public void testGet() throws IOException {
		String res = HTTP.create("http://www.httpbin.org")
				.appendPath("user-agent")
				.setUserAgent("konnekt-test")
				.get();	
		
		ObjectMapper om = new ObjectMapper();
		JsonNode jn = om.readTree(res);
		assertTrue(jn.get("user-agent").asText().equals("konnekt-test"));
	}
	
	@Test
	public void testPut() throws IOException {
		JsonNodeFactory fac = JsonNodeFactory.instance;
		ObjectNode obj = fac.objectNode();
		obj.put("test-field", "konnekt-test");
		
		String res = HTTP.create("http://www.httpbin.org")
				.appendPath("put")
				.setContentType(MIMEType.APPLICATION_JSON)
				.put(obj.toString());	
		
		ObjectMapper om = new ObjectMapper();
		JsonNode jn = om.readTree(res);
		assertTrue(jn.get("json").get("test-field").asText().equals("konnekt-test"));
	}
	
	@Test
	public void testDelete() throws IOException {
		String res = HTTP.create("http://www.httpbin.org")
				.appendPath("delete")
				.setParam("param1", "abc")
				.setParam("param2", "def")
				.delete();
		
		ObjectMapper om = new ObjectMapper();
		JsonNode jn = om.readTree(res);
		assertTrue(jn.get("args").get("param1").asText().equals("abc"));
		assertTrue(jn.get("args").get("param2").asText().equals("def"));
	}
	
}
