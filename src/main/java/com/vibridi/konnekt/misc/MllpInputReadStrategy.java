package com.vibridi.konnekt.misc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import com.vibridi.konnekt.MLLP;
import com.vibridi.konnekt.exception.MllpException;

public class MllpInputReadStrategy implements InputReadStrategy {

	@Override
	public byte[] readInput(InputStream in) throws IOException {
		return getMessage(in);
	}

	public byte[] getMessage(InputStream in) throws IOException  {	
		boolean end_of_message = false;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		int characterReceived = in.read();

		if (characterReceived == MLLP.END_BLOCK) {
			return null;
		}

		if (characterReceived != MLLP.START_BLOCK) {
			throw new MllpException("Start of block character has not been received");
		}

		while (!end_of_message) {
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
