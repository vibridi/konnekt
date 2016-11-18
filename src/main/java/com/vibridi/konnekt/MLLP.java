package com.vibridi.konnekt;

import java.io.IOException;

import com.vibridi.konnekt.misc.MllpInputReadStrategy;

public class MLLP {

	public static final char START_BLOCK = 0x0B;
	public static final char END_BLOCK = 0x1C;
	public static final char CARRIAGE_RETURN = 0x0D; // '\r'
	
	private String server;
	private int port;
	
	public static MLLP create(String server, int port) {
		return new MLLP(server,port);
	}	
	
	private MLLP(String server, int port) {
		this.server = server;
		this.port = port;
	}
	
	/**
	 * Sends a message over MLLP
	 *  
	 * The method attempts to replace newline characters ('\r\n' on Windows and '\n' on UNIX)
	 * with the '\r' byte, as mandated by MLLP. It is your responsibility to make sure the 
	 * newline characters are in the correct places.
	 * @param data
	 * @return the response sent by the receiver
	 * @throws IOException
	 */
	public String send(String data) throws IOException {
		return send(data
				.replace("\r\n","\r")	// windows 
				.replace("\n","\r")		// unix and unix-like
				.getBytes());
	}
	
	/**
	 * Sends bytes over MLLP.
	 * 
	 * The method does not perform any validation on the input data. If you wish to 
	 * check for OS-dependent newline characters, use {@link #send(String)} instead. 
	 * @param data
	 * @return the response sent by the receiver
	 * @throws IOException
	 */
	public String send(byte[] data) throws IOException {
		byte[] mllp = new byte[data.length + 3];
		
		mllp[0] = START_BLOCK;
		System.arraycopy(data, 0, mllp, 1, data.length);
		mllp[data.length] = END_BLOCK;
		mllp[data.length + 1] = CARRIAGE_RETURN;
		
		return TCPIP.create(server, port)
				.setInputReadStrategy(new MllpInputReadStrategy())
				.send(data);
	}
	
}
