package twintro.minecraft.modbuilder.editor.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

public class LanguageFile extends File {
	public Set<String> list;
	
	private LanguageFile(String dir){
		super(dir);
	}
	
	public static LanguageFile create(String dir){
		return new LanguageFile(dir).create();
	}
	
	public static LanguageFile open(String dir){
		try {
			return new LanguageFile(dir).open();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public LanguageFile create(){
		list = new HashSet<String>();
		if (!getParentFile().exists()) getParentFile().mkdirs();
		save();
		return this;
	}
	
	public LanguageFile open() throws IOException{
		list = new HashSet<String>();
		InputStream inStream = new FileInputStream(this);
	    InputStreamReader inReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));
	    BufferedReader reader = null;
	    try{
		    reader = new BufferedReader(inReader);
		    String line;
		    while ((line = reader.readLine()) != null){
		    	list.add(line);
		    }
	    } finally {
	    	if (reader != null) reader.close();
	    }
	    return this;
	}
	
	public static String toLine(String name, boolean isItem){
		String prefix;
		if (isItem) prefix = "item";
		else prefix = "tile";
		return prefix + ".modbuilder_" + name + ".name=" + name.replaceAll("_", " ");
	}
	
	public void save(){
		try {
			FileOutputStream outStream = null;
			try {
				outStream = new FileOutputStream(this);
				for (String line : list) outStream.write((line + "\n\r").getBytes());
			} finally {
				if (outStream != null) outStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
