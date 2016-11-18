package com.vibridi.konnekt;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class TCPTest {

	private static final int testPort = 60800;

	public class TestServer implements Runnable {

		private ServerSocket ss;

		public TestServer() throws IOException {
			ss = new ServerSocket(0);
		}

		@Override
		public void run() {
			try {
				Socket client = ss.accept();
				InputStream in = client.getInputStream();
				OutputStream out = client.getOutputStream();
				byte[] datain = new byte[20480];
				int size = in.read(datain);
				out.write(datain, 0, size);
				client.close();
			} catch(Exception e) {
				assertTrue(false);
			} finally {
				try {
					ss.close();
				} catch (IOException e) {
					assertTrue(false);
				}
			}
		}

		public int getPort() {
			return ss.getLocalPort();
		}

	}

	@Test
	public void testIO_DefaultReader() throws IOException, InterruptedException {
		TestServer server = new TestServer();
		Thread t = new Thread(server);
		t.start();

		String test = "Test data string";

		String echo = TCPIP.create("localhost", server.getPort()).send(test);
		assertTrue(echo.equals(test));	
		t.join();
	}
	
	@Test
	public void testIO_DefaultReaderLong() throws IOException, InterruptedException {
		TestServer server = new TestServer();
		Thread t = new Thread(server);
		t.start();

		String test = getTestResource("long.xml");
		
		String echo = TCPIP.create("localhost", server.getPort()).send(test);
		assertTrue(echo.equals(test));
		t.join();
	}

	
	public String getTestResource(String name) throws IOException {
		return IOUtils.toString(this.getClass().getResourceAsStream("/"+name), Charset.forName("UTF-8"));
	}

}
