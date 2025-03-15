package com.example.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileUploadUtils {

	public static byte[] convertToByteArray(MultipartFile file) {
		if (file.isEmpty()) {
			return null;
		}
		try {
			return file.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}