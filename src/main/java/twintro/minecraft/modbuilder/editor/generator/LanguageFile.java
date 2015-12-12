package twintro.minecraft.modbuilder.editor.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
		//TODO
	}
	
	public void save(){
		try {
			FileOutputStream outStream = null;
			try {
				outStream = new FileOutputStream(this);
				for (String line : list) outStream.write(line.getBytes());
			} finally {
				if (outStream != null) outStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
