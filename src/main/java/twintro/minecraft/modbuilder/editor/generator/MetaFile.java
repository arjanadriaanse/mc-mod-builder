package twintro.minecraft.modbuilder.editor.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import twintro.minecraft.modbuilder.data.resources.meta.MetadataResource;
import twintro.minecraft.modbuilder.data.resources.meta.ModbuilderResource;
import twintro.minecraft.modbuilder.data.resources.meta.PackResource;

public class MetaFile extends File {
	public MetadataResource resource;
	
	private MetaFile(String dir, boolean newMod){
		super(dir);
		if (newMod) create();
		else open();
	}
	
	public static MetaFile create(String dir){
		return new MetaFile(dir, true);
	}
	
	public static MetaFile open(String dir){
		return new MetaFile(dir, false);
	}
	
	private void create(){
		resource = new MetadataResource();
		resource.pack = new PackResource();
		resource.pack.pack_format = 1;
		resource.pack.description = "A ModBuilder Minecraft Mod";
		resource.modbuilder = new ModbuilderResource();
		//TODO resource.modbuilder.id = ?;
		resource.modbuilder.blocks = new HashSet<String>();
		resource.modbuilder.items = new HashSet<String>();
		resource.modbuilder.recipes = new HashSet<String>();
		resource.modbuilder.structures = new HashSet<String>();
		save();
	}
	
	private void open(){
		//TODO
	}
	
	public void save(){
		try {
			ResourcePackGenerator.createFile(resource, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
