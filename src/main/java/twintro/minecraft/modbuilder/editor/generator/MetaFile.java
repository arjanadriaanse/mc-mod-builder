package twintro.minecraft.modbuilder.editor.generator;

import java.io.File;
import java.io.FileReader;
import java.util.HashSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import twintro.minecraft.modbuilder.data.resources.meta.MetadataResource;
import twintro.minecraft.modbuilder.data.resources.meta.ModbuilderResource;
import twintro.minecraft.modbuilder.data.resources.meta.PackResource;

public class MetaFile extends File {
	public MetadataResource resource;
	
	private MetaFile(String dir){
		super(dir);
	}
	
	public static MetaFile create(String dir){
		return new MetaFile(dir).create();
	}
	
	public static MetaFile open(String dir){
		try {
			return new MetaFile(dir).open();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public MetaFile create(){
		resource = new MetadataResource();
		resource.pack = new PackResource();
		resource.pack.pack_format = 1;
		resource.pack.description = "A ModBuilder Minecraft Mod";
		resource.modbuilder = new ModbuilderResource();
		resource.modbuilder.blocks = new HashSet<String>();
		resource.modbuilder.items = new HashSet<String>();
		resource.modbuilder.recipes = new HashSet<String>();
		resource.modbuilder.structures = new HashSet<String>();
		save();
		return this;
	}
	
	public MetaFile open() throws Exception{
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		resource = gson.fromJson(new FileReader(this), MetadataResource.class);
		return this;
	}
	
	public void save(){
		ResourcePackIO.createFile(resource, this);
	}
}
