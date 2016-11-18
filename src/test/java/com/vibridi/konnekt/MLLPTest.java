package com.vibridi.konnekt;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

public class MLLPTest {

	@Test
	public void a() {
		int[] a = new int[9999999];
		ByteArrayOutputStream b1 = new ByteArrayOutputStream(1024);
		ByteArrayOutputStream b2 = new ByteArrayOutputStream();
		
		for(int i = 0; i < a.length; i++)
			a[i] = 0x00000001;
		
		long start = System.currentTimeMillis();
		for(int i = 0; i < a.length; i++)
			b1.write(a[i]);
		long end = System.currentTimeMillis() - start;
		
		long start2 = System.currentTimeMillis();
		for(int i = 0; i < a.length; i++)
			b2.write(a[i]);
		long end2 = System.currentTimeMillis() - start2;
	
		System.out.println("Alloc:\t" + end);
		System.out.println("Empty:\t" + end2);
	}
	
	
}
