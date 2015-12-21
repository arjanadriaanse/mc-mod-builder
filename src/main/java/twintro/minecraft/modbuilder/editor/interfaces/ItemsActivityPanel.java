package twintro.minecraft.modbuilder.editor.interfaces;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import twintro.minecraft.modbuilder.data.resources.ResourceDeserializer;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.data.resources.items.BaseItemResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockModelResource;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource;
import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;

public class ItemsActivityPanel extends ActivityPanel {

	private List<String> models;
	
	public ItemsActivityPanel(String header, String button) {
		super(header, button);
		this.models = new ArrayList<String>();	
	}
	
	public ItemsActivityPanel(String header, String button, ArrayList<String> models) {
		super(header, button);
		this.models = models;	
	}
	
	@Override
	protected void add() {
		String name = JOptionPane.showInputDialog("Item name:");
		if (name != null)
			if (name.replaceAll(" ", "").length() > 0)
				new ItemEditor(this ,models, name);
	}
	
	public void addItem(ItemElement item){
		createFile(item.itemModel, "assets/modbuilder/models/item/" + item.name + ".json");
		createFile(item.item, "assets/modbuilder/items/" + item.name + ".json");
		addElement(item.name, item.getImage());
	}
	
	public void createFile(Object model, String dir){
		try {
			ResourcePackGenerator.createFile(model, dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void edit() {
		String value = (String) list.getSelectedValue();
		// TODO AFTER MIKE DOES HIS SHIT
	}
	
	@Override
	protected void delete() {
		String value = (String) list.getSelectedValue();
	}
	
	@Override
	public void updateList() {
		File folder = new File(ResourcePackGenerator.getURL("assets/modbuilder/items/"));
		if (folder.exists()){
			ResourceDeserializer deserializer = new ResourceDeserializer();
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(BaseItemResource.class, deserializer);
			Gson gson = builder.create();
			for (File file : folder.listFiles()){
				try {
					addItem(file, gson);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void addItem(File file, Gson gson) throws Exception{
		if (file.getAbsolutePath().endsWith(".json")){
			ImageIcon img = new ImageIcon();
			String name = file.getName().substring(0, file.getName().length() - 5);
			BaseItemResource item = gson.fromJson(new FileReader(file), BaseItemResource.class);
			if (item.model.startsWith("modbuilder:")){
				String modelName = item.model.substring(11);
				File model = new File(ResourcePackGenerator.getURL(
						"assets/modbuilder/models/item/" + modelName + ".json"));
				if (model.exists()){
					ItemModelResource itemModel = 
							gson.fromJson(new FileReader(model), ItemModelResource.class);
					String texture = null;
					if (itemModel.textures.containsKey("layer0"))
						texture = itemModel.textures.get("layer0");
					else{
						Object[] textures = itemModel.textures.values().toArray();
						if (textures.length > 0) texture = (String) textures[0];
					}
					if (texture != null){
						if (texture.startsWith("modbuilder:")){
							img = new ImageIcon(ResourcePackGenerator.getURL(
									"assets/modbuilder/textures/" + texture.substring(11) + ".png"));
						}
					}
				}
			}
			addElement(name, img);
		}
	}
}
