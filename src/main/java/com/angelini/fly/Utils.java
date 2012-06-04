package com.angelini.fly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
	
	public static List<File> readDir(File dir, List<File> files) {
		for (File file : dir.listFiles()) {
	        if (file.isDirectory()) {
	            files.addAll(readDir(file, files));
	        } else {
	            files.add(file);
	        }
	    }
		
		return files;
	}
	
	public static String readFile(String path) throws IOException {
		return readFile(new File(Utils.class.getResource(path).getPath()));
	}
	
	public static String readFile(File file) throws IOException {
		FileInputStream stream = new FileInputStream(file);
		try {
			FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    return Charset.defaultCharset().decode(bb).toString();
		} finally {
			stream.close();
		}
	}
	
	public static Map<String, String> readBody(BufferedReader buff) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		String line;
		while ((line = buff.readLine()) != null) {
			sb.append(line);
		}
		
		buff.close();
		
		Map<String, String> params = new HashMap<String, String>();
		String[] split = sb.toString().split("&");
		
		for (String param : split) {
			String[] parts = param.split("=");
			if (parts.length != 2) { continue; }
			params.put(parts[0], parts[1]);
		}
		
		return params;
	}

}
