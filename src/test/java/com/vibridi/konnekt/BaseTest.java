package com.vibridi.konnekt;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

public class BaseTest {
	public String getTestResource(String name) throws IOException {
		return IOUtils.toString(this.getClass().getResourceAsStream("/"+name), Charset.forName("UTF-8"));
	}
}
