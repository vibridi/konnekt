package com.vibridi.konnekt.util;

import com.vibridi.konnekt.MLLP;

public class MllpComposer {

	public static byte[] compose(byte[] data) {
		byte[] mllp = new byte[data.length + 3];
		mllp[0] = MLLP.START_BLOCK;
		System.arraycopy(data, 0, mllp, 1, data.length);
		mllp[data.length + 1] = MLLP.END_BLOCK;
		mllp[data.length + 2] = MLLP.CARRIAGE_RETURN;
		return mllp;
	}
	 
}
