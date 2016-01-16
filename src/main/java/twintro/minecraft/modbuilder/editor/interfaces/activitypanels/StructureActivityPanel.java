package twintro.minecraft.modbuilder.editor.interfaces.activitypanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import twintro.minecraft.modbuilder.data.resources.ResourceDeserializer;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockModelResource;
import twintro.minecraft.modbuilder.editor.ActivityButton;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.editors.GroundStructureEditor;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;
import twintro.minecraft.modbuilder.editor.resources.RecipeElement;

public class StructureActivityPanel extends ObjectActivityPanel {
	private final ObjectRunnable runnable = new ObjectRunnable() {
		@Override
		public void run(Object obj) {
			saveStructure();
		}
	};
	
	public StructureActivityPanel(String header, String button) {
		super(header, button);
		this.openEditors = new HashMap<String, JFrame>();
	}

	@Override
	protected void add() {
		String name = JOptionPane.showInputDialog("Structure name:");
		if (name != null){
			if (name.replaceAll(" ", "").length() > 0 && !openEditors.containsKey(name)){
				//TODO
			}
		}
	}
	
	@Override
	protected void edit() {
		String value = (String) list.getSelectedValue();
		//TODO
	}
	
	@Override
	protected void delete() {
		String value = (String) list.getSelectedValue();
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + value + "?\r\n"
				+ "References to this object will not be updated, which might cause problems.", 
				"Warning", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION){
			//TODO
		}
	}
	
	@Override
	public void updateList() {
		File folder = new File(ResourcePackIO.getURL("assets/modbuilder/structures/"));
		if (folder.exists()){
			for (File file : folder.listFiles()){
				if (file.getAbsolutePath().endsWith(".json")){
					try {
						String name = file.getName().substring(0, file.getName().length() - 5);
						addElement(name, BlockElement.getFromName(name).getImage());
					} catch (Exception e) {
						System.out.println("Could not find all structure element objects for " + file.getName());
					}
				}
			}
		}
	}

	@Override
	protected void createButtonPanel(JPanel buttonPanel, String button) {
		super.createButtonPanel(buttonPanel, button);
		//TODO
	}
	
	private void saveStructure(){
		//TODO
	}
}
