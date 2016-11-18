package com.vibridi.konnekt.read;

import java.io.IOException;
import java.io.InputStream;

public interface InReader {
	public static final int END_OF_STREAM = -1;
	public byte[] readInput(InputStream in) throws IOException;
}
