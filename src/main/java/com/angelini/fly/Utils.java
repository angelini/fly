package com.angelini.fly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.google.common.io.CharStreams;

public class Utils {
	
	public static String readFile(String path) throws IOException {
		InputStream stream = Utils.class.getResourceAsStream(path);
		return CharStreams.toString(new InputStreamReader(stream, "UTF-8"));
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
