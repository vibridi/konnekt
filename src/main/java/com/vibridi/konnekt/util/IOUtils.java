package com.vibridi.konnekt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class IOUtils {

	/**
	 * Reads the stream into a string
	 */
	public static String toString(InputStream stream, Charset chs) throws IOException{
		if (stream == null)
			return "";

		BufferedReader rd = new BufferedReader(new InputStreamReader(stream, chs));
		int r;
		char[] buffer = new char[1024];
		StringBuilder response = new StringBuilder();
		while((r = rd.read(buffer,0,buffer.length)) != -1) {
			response.append(buffer,0,r);
		}
		rd.close();
		return response.toString();
	}

}
