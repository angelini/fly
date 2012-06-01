package com.angelini.fly;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;

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

}
