package com.vibridi.konnekt;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

import com.vibridi.konnekt.read.MllpInReader;
import com.vibridi.konnekt.util.MllpComposer;

public class MLLPTest extends BaseTest {

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
				
				MllpInReader mirs = new MllpInReader();
				byte[] datain = mirs.readInput(in);
				byte[] dataout = MllpComposer.compose(datain);
				
				out.write(dataout, 0, dataout.length);
				client.close();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public int getPort() {
			return ss.getLocalPort();
		}

	}
	
	
	@Test
	public void testMllp() throws IOException, InterruptedException {
		TestServer server = new TestServer();
		Thread t = new Thread(server);
		t.start();
	
		String test = this.getTestResource("hl7.txt");
		String echo = MLLP.create("localhost", server.getPort()).send(test);
		assertTrue(echo.equals(test.replace("\r\n", "\r")));
		t.join();
	}
	
	@Test
	public void testMllpComposition() throws IOException {
		String control = "Test String";
		byte[] dat = MllpComposer.compose(control.getBytes());
		
		assertTrue(dat[0] == MLLP.START_BLOCK);
		assertTrue(dat[control.length() + 1] == MLLP.END_BLOCK);
		assertTrue(dat[control.length() + 2] == MLLP.CARRIAGE_RETURN);
		
		byte[] body = new byte[control.length()];
		System.arraycopy(dat, 1, body, 0, control.length());
		
		assertTrue(control.equals(new String(body)));
	}
	
	@Test
	public void testMllpRead() throws IOException {
		String control = "Test String";
		
		InputStream in = new FileInputStream(new File("./src/test/resources/read-test"));
		
		MllpInReader mir = new MllpInReader();
		byte[] dat = mir.readInput(in);
		
		assertTrue(dat.length == control.length());
		
		byte[] body = new byte[control.length()];
		System.arraycopy(dat, 0, body, 0, control.length());
		
		assertTrue(control.equals(new String(body)));
	}
	
}
