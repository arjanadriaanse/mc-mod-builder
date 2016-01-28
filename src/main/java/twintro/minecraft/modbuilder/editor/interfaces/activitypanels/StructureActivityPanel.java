package twintro.minecraft.modbuilder.editor.interfaces.activitypanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import twintro.minecraft.modbuilder.data.resources.structures.StructureType;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.editors.GroundStructureEditor;
import twintro.minecraft.modbuilder.editor.interfaces.editors.OreStructureEditor;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ActivityButton;
import twintro.minecraft.modbuilder.editor.resources.StructureElement;

public class StructureActivityPanel extends ObjectActivityPanel {
	private final ObjectRunnable runnable = new ObjectRunnable() {
		@Override
		public void run(Object obj) {
			saveStructure((StructureElement) obj);
		}
	};
	
	public StructureActivityPanel(String header, String button) {
		super(header, button);
		openEditors = new HashMap<String, JFrame>();
	}

	@Override
	protected void add() {
		String name = nameDialog("Structure");
		if (isValidName(name)){
			name = name.replaceAll(" ", "_");
			OreStructureEditor editor = new OreStructureEditor(name, runnable, closeHandler);
			openEditors.put(name, editor);
		}
	}
	
	private void addGroundCover(){
		String name = nameDialog("Structure");
		if (isValidName(name)){
			name = name.replaceAll(" ", "_");
			GroundStructureEditor editor = new GroundStructureEditor(name, runnable, closeHandler);
			openEditors.put(name, editor);
		}
	}
	
	@Override
	protected void edit() {
		String value = ((String) list.getSelectedValue()).replaceAll(" ", "_");
		try {
			if (!openEditors.containsKey(value)){
				StructureElement structure = StructureElement.getFromName(value);
				StructureType type = structure.structure.type;
				JFrame editor;
				if (type == StructureType.ore) editor = new OreStructureEditor(structure, runnable, closeHandler);
				else editor = new GroundStructureEditor(structure, runnable, closeHandler);
				openEditors.put(value,editor);
			}
			else {
				openEditors.get(value).setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void delete() {
		String value = (String) list.getSelectedValue();
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + value + "?\r\n"
				+ "References to this object will not be updated, which might cause problems.", 
				"Warning", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION){
			removeElement(value);
			value = value.replaceAll(" ", "_");
			
			ResourcePackIO.deleteFile("assets/modbuilder/structures/" + value + ".json");
		}
	}
	
	@Override
	public String updateList(){
		elements.clear();
		File folder = new File(ResourcePackIO.getURL("assets/modbuilder/structures/"));
		if (folder.exists()){
			for (File file : folder.listFiles()){
				if (file.getAbsolutePath().endsWith(".json")){
					try {
						String name = file.getName().substring(0, file.getName().length() - 5);
						addElement(name, StructureElement.getFromName(name).getImage());
					} catch (Exception e) {
						e.printStackTrace();
						return "Could not find all structure element objects for the structure " + file.getName();
					}
				}
			}
		}
		return null;
	}

	@Override
	protected void createButtonPanel(JPanel buttonPanel, String button) {
		JButton groundButton = new ActivityButton("New Ground Cover");
		groundButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addGroundCover();
			}
		});
		buttonPanel.add(groundButton);
		
		super.createButtonPanel(buttonPanel, button);
	}
	
	private void saveStructure(StructureElement structure){
		ResourcePackIO.createFile(structure.structure, "assets/modbuilder/structures/" + structure.name + ".json");
		addElement(structure.name, structure.getImage());
		
		Editor.metaFile.resource.modbuilder.structures.add(structure.name);
		Editor.metaFile.save();
	}
}
