package com.vibridi.konnekt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import com.vibridi.konnekt.read.DefaultInReader;
import com.vibridi.konnekt.read.InReader;

public class TCPIP {

	private String server;
	private int port;
	private InReader irs;
	
	public static TCPIP create(String server, int port) {
		return new TCPIP(server, port);
	}
	
	protected TCPIP(String server, int port) {
		this.server = server;
		this.port = port;
	}
	
	public TCPIP setInputReadStrategy(InReader strategy) {
		this.irs = strategy;
		return this;
	}
	
	public String send(String data) throws IOException {
		return send(data.getBytes());
	}
	
	public String send(byte[] data) throws IOException {
		Socket tcpSocket = new Socket(server, port);
		OutputStream out = tcpSocket.getOutputStream();
		InputStream in = tcpSocket.getInputStream();
		
		out.write(data);
		
		if(irs == null) {
			irs = new DefaultInReader();
		}
		
		byte[] content = irs.readInput(in);
		tcpSocket.close();
		return new String(content, StandardCharsets.UTF_8);
	}
	
}
