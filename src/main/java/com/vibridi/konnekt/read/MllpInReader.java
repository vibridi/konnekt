package com.vibridi.konnekt.read;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.vibridi.konnekt.MLLP;
import com.vibridi.konnekt.exception.MllpException;

public class MllpInReader implements InReader {

	@Override
	public byte[] readInput(InputStream in) throws IOException {
		return readMllpInput(in);
	}

	private byte[] readMllpInput(InputStream in) throws IOException  {	
		boolean end_of_message = false;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		int characterReceived = in.read();

		if (characterReceived == MLLP.END_BLOCK) {
			return null;
		}

		if (characterReceived != MLLP.START_BLOCK) {
			throw new MllpException("Start-of-block character has not been received");
		}

		while (!end_of_message) {
			characterReceived = in.read();
			if (characterReceived == END_OF_STREAM) {
				throw new MllpException("Received no data.");
			}

			if (characterReceived == MLLP.END_BLOCK) {
				characterReceived = in.read();

				if (characterReceived != MLLP.CARRIAGE_RETURN) {
					throw new MllpException("Incorrect end-of-message characters.");
				}
				end_of_message = true;
			} else {
				bos.write(characterReceived);
			}
		}
		return bos.toByteArray();
	}

}
