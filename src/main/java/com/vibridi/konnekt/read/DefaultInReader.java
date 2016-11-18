package com.vibridi.konnekt.read;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DefaultInReader implements InReader {

	@Override
	public byte[] readInput(InputStream in) throws IOException {	
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] chunk = new byte[1024];
		int size = -1;
		while((size = in.read(chunk)) != END_OF_STREAM)
			bos.write(chunk,0,size);
		bos.flush();
		return bos.toByteArray();
	}

}
