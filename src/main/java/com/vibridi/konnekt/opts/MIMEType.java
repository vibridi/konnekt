package com.vibridi.konnekt.opts;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MIMEType {

	APPLICATION_JSON("application/json"),
	APPLICATION_OCTET_STREAM("application/octet-stream"),
	APPLICATION_PDF("application/pdf"),
	APPLICATION_RTF("application/rtf"),
	APPLICATION_VND("application/vnd"),
	APPLICATION_X_JAVASCRIPT("application/x-javascript"),
	APPLICATION_X_PERL("application/x-perl"),
	APPLICATION_X_WWW_URL_ENCODED("application/x-www-form-urlencoded"),
	APPLICATION_XML("application/xml"),
	APPLICATION_XML_SOAP("application/xml+soap"),
	APPLICATION_ZIP("application/zip"),
	
	AUDIO_BASIC("audio/basic"),
	AUDIO_MPEG("audio/mpeg"),
	AUDIO_WAV("audio/wav"),
	AUDIO_X_MIDI("audio/x-midi"),
	
	IMAGE_BMP("image/bmp"),
	IMAGE_GIF("image/gif"),
	IMAGE_JPEG("image/jpeg"),
	IMAGE_PNG("image/png"),
	IMAGE_TIFF("image/tiff"),
	
	TEXT_CSS("text/css"),
	TEXT_HTML("text/html"),
	TEXT_PLAIN("text/plain"),
	TEXT_TAB_SEPARATED_VALUES("text/tab-separated-values"),
	
	VIDEO_AVI("video/avi"),
	VIDEO_MPEG("video/mpeg");
	
	
	
	private static final Map<String, MIMEType> map = new HashMap<>();
	
	static {
		for(MIMEType value : EnumSet.allOf(MIMEType.class)) {
            map.put(value.stringValue(), value);
        }
	}
	
	public static MIMEType forName(String mimeType) {
		return map.get(mimeType);
	}
	
	private String value;
	
	private MIMEType(String value) {
		this.value = value;
	}
	
	public String stringValue() {
		return value;
	}
	
}
