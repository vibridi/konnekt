package com.vibridi.konnekt.misc;

import java.io.IOException;
import java.io.InputStream;

public interface InputReadStrategy {
	public static final int END_OF_STREAM = -1;
	public byte[] readInput(InputStream in) throws IOException;
}
