package twintro.minecraft.modbuilder.editor.generator;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

public class LanguageFile extends File {
	public Set<String> list;
	
	private LanguageFile(String dir, boolean newMod){
		super(dir);
		if (newMod) create();
		else open();
	}
	
	public static LanguageFile create(String dir){
		return new LanguageFile(dir, true);
	}
	
	public static LanguageFile open(String dir){
		return new LanguageFile(dir, false);
	}
	
	private void create(){
		list = new HashSet<String>();
		if (!getParentFile().exists()) getParentFile().mkdirs();
		save();
	}
	
	private void open(){
		list = new HashSet<String>();
		try{
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
		}
		catch (Exception e){
			e.printStackTrace();
		}
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
	/*
	public void rename(String originalName, String newName){
		
	}*/
}
